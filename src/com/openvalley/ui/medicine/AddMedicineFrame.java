package com.openvalley.ui.medicine;

import com.eltima.components.ui.DatePicker;
import com.openvalley.server.entity.Category;
import com.openvalley.server.entity.Medicine;
import com.openvalley.server.helper.JDBCHelper;
import com.openvalley.server.mapper.MedicineMapper;
import com.openvalley.server.mapper.impl.MedicineMapperImpl;
import com.openvalley.ui.base.UIConstants;
import com.openvalley.ui.base.UIConverter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AddMedicineFrame extends JDialog {

    JPanel panel;

    private JPanel upPanel;

    private JPanel midPane;

    private JPanel downPanel;

    private final MedicineFrame medicineFrame;

    // upPanel -> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // upPanel -> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓

    // midPane -> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private JTextField medicineNo;                      // 药品编号
    private JTextField medicineName;                    // 药品名称
    private JTextField medicineDescription;             // 药品描述
    private JTextField medicineFactoryAddress;          // 药品厂家
    private DatePicker datePick;                        // 药品有效期
    private JTextField medicineNumber;                  // 药品数量
    private JTextField medicinePrice;                   // 药品价格
    private JTextField medicineUnit;                    // 药品单位
    private JComboBox<Category> medicineCategory;       // 药品类别
    // midPane -> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓

    // downPanel -> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private JButton save;

    private JButton cancel;
    // downPanel -> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓


    // server -> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private final transient MedicineMapper medicineMapper=new MedicineMapperImpl();
    // server -> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public AddMedicineFrame(Frame owner, boolean modal) {
        super(owner, modal);
        medicineFrame = (MedicineFrame) owner;
        initPanel();
        medicineButtonListener();
        UIConverter.initDialog(this, owner, "新增药品");
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(1, 1));

        midPane();
        upPanel();
        downPanel();

        this.getContentPane().add(upPanel, BorderLayout.NORTH);
        this.getContentPane().add(UIConverter.initBlankLabel(), BorderLayout.WEST);
        this.getContentPane().add(midPane, BorderLayout.CENTER);
        this.getContentPane().add(UIConverter.initBlankLabel(), BorderLayout.EAST);
        this.getContentPane().add(downPanel, BorderLayout.SOUTH);
    }

    private void upPanel() {
        JLabel text = new JLabel("新增药品");
        text.setForeground(Color.BLUE);
        text.setHorizontalTextPosition(SwingConstants.CENTER);
        text.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, 40));
        text.setSize(350, 60);
        upPanel = new JPanel();
        upPanel.add(text);
    }

    private void midPane() {
        midPane = new JPanel();
        LayoutManager layout = new BoxLayout(midPane, BoxLayout.PAGE_AXIS);
        midPane.setLayout(layout);

        // 1.药品编号 初始化
        medicineNo = UIConverter.initTextField();
        midPane.add(createFiledJPanel("药品编号:", medicineNo));

        // 2.药品名称 初始化
        medicineName = UIConverter.initTextField();
        midPane.add(createFiledJPanel("药品名称:", medicineName));

        // 3.药品描述 初始化
        medicineDescription = UIConverter.initTextField();
        midPane.add(createFiledJPanel("药品描述:", medicineDescription));

        // 4.药品厂家 初始化
        medicineFactoryAddress = UIConverter.initTextField();
        midPane.add(createFiledJPanel("药品厂家:", medicineFactoryAddress));

        // 5.有效期 容器初始化
        datePick = UIConverter.getDatePicker(medicineFrame.dealDate(true));
        midPane.add(createFiledJPanel("有效期至:", datePick));

        // 6.药品名称 初始化
        medicineNumber = UIConverter.initTextField();
        midPane.add(createFiledJPanel("药品数量:", medicineNumber));

        // 7.药品价格 初始化
        medicinePrice = UIConverter.initTextField();
        midPane.add(createFiledJPanel("药品价格:", medicinePrice));

        // 8.药品单位 初始化
        medicineUnit = UIConverter.initTextField();
        midPane.add(createFiledJPanel("药品单位:", medicineUnit));

        // 9.所属类别查询 容器初始化
        medicineCategory = medicineFrame.initCategoryData(true);
        midPane.add(createFiledJPanel("所属类别:", medicineCategory));
    }
    private JPanel createFiledJPanel(String labelContext, JComponent component) {
        JPanel result = UIConverter.getLabelAndComponentGroup(labelContext, 15, component);
        result.setSize(280, 0);
        return result;
    }

    private void downPanel() {
        save = UIConverter.initButton("保存");
        cancel = UIConverter.initButton("取消");

        downPanel = new JPanel();
        downPanel.add(save);
        downPanel.add(cancel);
    }

    private void medicineButtonListener() {
        save.addActionListener(ae -> saveButtonListener());
        cancel.addActionListener(ae -> this.dispose());
    }

    private void saveButtonListener() {
        // todo 保存
        Category category=(Category)medicineCategory.getSelectedItem();
        Medicine medicine=new Medicine();
        medicine.setMedicineNo(medicineNo.getText().trim());// 药品编号
        medicine.setName(medicineName.getText().trim());// 药品名称
        medicine.setDescription(medicineDescription.getText().trim());// 药品描述
        medicine.setFactoryAddress(medicineFactoryAddress.getText().trim());// 药品厂家
        medicine.setExpire(datePick.getText().trim());// 药品有效期
        medicine.setNumber(Integer.valueOf(medicineNumber.getText().trim()));// 药品数量
        medicine.setPrice(Double.valueOf(medicinePrice.getText().trim()));// 药品价格
        medicine.setUnit(medicineUnit.getText().trim());// 药品单位
        String categoryId = category ==null?null:String.valueOf(category.getId());
        if(categoryId==null||categoryId.isEmpty()|| Objects.equals("-1",categoryId)){
            JOptionPane.showMessageDialog(this,"药品类别选择异常");
        }else{
            medicine.setCategoryId(Integer.valueOf(categoryId));
        }

        Boolean flag=medicineMapper.insert(medicine);
        if(Boolean.TRUE.equals(flag)){
            JOptionPane.showMessageDialog(this,"添加数据成功");
            medicineFrame.searchButtonListener();
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this,"数据添加失败");
        }
        // 药品类别
    }
}
