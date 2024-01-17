package com.openvalley.ui.medicine;

import com.eltima.components.ui.DatePicker;
import com.openvalley.server.entity.Category;
import com.openvalley.server.entity.Medicine;
import com.openvalley.server.mapper.CategoryMapper;
import com.openvalley.server.mapper.MedicineMapper;
import com.openvalley.server.mapper.UserMapper;
import com.openvalley.server.mapper.impl.CategoryMapperImpl;
import com.openvalley.server.mapper.impl.MedicineMapperImpl;
import com.openvalley.server.mapper.impl.UserMapperImpl;
import com.openvalley.server.query.MedicineQuery;
import com.openvalley.ui.base.UIConstants;
import com.openvalley.ui.base.UIConverter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class MedicineFrame extends JFrame {

    private JPanel panel;

    private JPanel upPanel;

    private JScrollPane midPane;

    private JPanel downPanel;


    // upPanel -> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private JTextField medicineName;
    private JTextField medicineMinPrice;
    private JTextField medicineMaxPrice;
    private JComboBox<Category> medicineCategory;
    private DatePicker datePick;
    private JButton search;
    // upPanel -> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓

    // midPane -> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private JTable table;
    private DefaultTableModel tableModel;
    private Integer selectedMedicineId = UIConstants.SELECTED_ID;
    // midPane -> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓

    // downPanel -> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private JButton addMedicine;
    private JButton modifyMedicine;
    private JButton removeMedicine;
    private JButton refreshMedicine;
    // downPanel -> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓
    // ↓        ↓       ↓        ↓       ↓        ↓       ↓        ↓       ↓

    // server -> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    // server -> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private final transient MedicineMapper medicineMapper = new MedicineMapperImpl();
    private final transient CategoryMapper categoryMapper=new CategoryMapperImpl();

    public MedicineFrame() {
        initPanel();
        initFrame();
        medicineButtonListener();
        tableDataSelectedListener();
    }

    private void initFrame() {
        UIConverter.initFrame(this, ("医药管理系统"));
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(1, 1));

        upPanel();
        midPane();
        downPanel();
        this.getContentPane().add(upPanel, BorderLayout.NORTH);
        this.getContentPane().add(midPane, BorderLayout.CENTER);
        this.getContentPane().add(downPanel, BorderLayout.SOUTH);
    }

    private void upPanel() {
        upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(0, 5));

        // 1.药品名称查询 容器初始化
        medicineName = UIConverter.initTextField();
        upPanel.add(UIConverter.getLabelAndComponentGroup("药品名称:", medicineName));

        // 2.药品价格查询 容器初始化
        JPanel price = new JPanel();
        price.setLayout(new GridLayout(0, 3));
        medicineMinPrice = UIConverter.initTextField();
        medicineMaxPrice = UIConverter.initTextField();
        price.add(medicineMinPrice, BorderLayout.WEST);
        price.add(UIConverter.initLabel(" - "), BorderLayout.CENTER);
        price.add(medicineMaxPrice, BorderLayout.EAST);
        upPanel.add(UIConverter.getLabelAndComponentGroup("药品价格:", price));

        // 3.所属类别查询 容器初始化
        medicineCategory = initCategoryData(true);
        upPanel.add(UIConverter.getLabelAndComponentGroup("所属类别:", medicineCategory));

        // 4.有效期查询 容器初始化
        datePick = UIConverter.getDatePicker(dealDate(true));
        upPanel.add(UIConverter.getLabelAndComponentGroup("有效期至:", datePick));

        // 5.查询按钮 容器初始化
        search = UIConverter.initButton("查询");
        upPanel.add(search);
    }

    private void midPane() {
        loadTableData();

        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 重写:让所有单元格不可编辑
            }
        };

        hideMedicineId();

        midPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
    }

    private void downPanel() {
        addMedicine = UIConverter.initButton("新增");
        modifyMedicine = UIConverter.initButton("修改");
        removeMedicine = UIConverter.initButton("删除");
        refreshMedicine = UIConverter.initButton("刷新");

        downPanel = new JPanel();
        downPanel.add(addMedicine);
        downPanel.add(modifyMedicine);
        downPanel.add(removeMedicine);
        downPanel.add(refreshMedicine);
    }


    public JComboBox<Category> initCategoryData(boolean isSearch) {
        JComboBox<Category> target = new JComboBox<>();
        target.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, 12));
        // todo 对类别进行赋值
        if(Boolean.TRUE.equals(isSearch)){
            Category category=new Category();
            category.setId(UIConstants.SELECTED_ID);
            category.setName("所有类别");
            target.addItem(category);
        }
        List<Category> categories=categoryMapper.selectAllCategory();
        categories.forEach(target::addItem);
        return target;
    }

    public Date dealDate(boolean deal) {
        if (Boolean.FALSE.equals(deal)) {
            return new Date();
        } else {
            LocalDate threeYearsLater = LocalDate.now().plusYears(3L);
            return Date.from(threeYearsLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }


    private void medicineButtonListener() {
        // 1.监听查询按钮
        search.addActionListener(ae -> searchButtonListener());

        // 2.监听添加按钮
        addMedicine.addActionListener(ae -> new AddMedicineFrame(MedicineFrame.this, true));


        // 3.监听删除按钮
        removeMedicine.addActionListener(ae -> removeButtonListener());

        // 4.监听修改按钮
        modifyMedicine.addActionListener(ae -> {
            if (Objects.equals(selectedMedicineId, UIConstants.SELECTED_ID)) {
                JOptionPane.showMessageDialog(MedicineFrame.this, "请先选择要修改的数据");
            } else {
                // todo 获取到当前药品对象，打开新的弹框
            }
        });

        // 5.监听刷新按钮
        refreshMedicine.addActionListener(ae -> refreshButtonListener());
    }

    private void tableDataSelectedListener() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //获得选中行
                int selectRow = table.getSelectedRow();
                if (selectRow != UIConstants.SELECTED_ID) {
                    //找到隐藏的药品mid
                    String midStr = tableModel.getValueAt(selectRow, UIConstants.MEDICINE_ID).toString();
                    selectedMedicineId = Integer.parseInt(midStr);
                }
            }
        });
    }

    public void searchButtonListener() {
        loadTableData();
        table.setModel(tableModel);
        hideMedicineId();
        selectedMedicineId = UIConstants.SELECTED_ID; // 查询后 , 重置表格选择的药品Id
    }

    private void removeButtonListener() {
        if (Objects.equals(selectedMedicineId, UIConstants.SELECTED_ID)) {
            JOptionPane.showMessageDialog(this, "请选择需要删除的药品数据!");
        } else {
            int choose = JOptionPane.showConfirmDialog(this,
                    "您确认要删除该条记录？",
                    "药品删除",
                    JOptionPane.YES_NO_OPTION);

            if (choose == JOptionPane.YES_OPTION) {
                // todo 删除逻辑
            }
            searchButtonListener(); // 刷新加载数据
        }
    }

    private void refreshButtonListener() {
        clearMedicineQuery();
        searchButtonListener();
    }

    private void hideMedicineId() {
        if (table != null) {
            // 隐藏 药品主键Id
            table.getColumnModel().getColumn(UIConstants.MEDICINE_ID).setMinWidth(0);
           table.getColumnModel().getColumn(UIConstants.MEDICINE_ID).setMaxWidth(0);
           table.getColumnModel().getColumn(UIConstants.MEDICINE_ID).setPreferredWidth(0);
       }
    }


    private void loadTableData() {
        if (tableModel != null) {
            tableModel = null;
        }
        // todo 查询药品列表
        MedicineQuery medicineQuery = getMedicineQuery();
        List<Medicine> medicines=medicineMapper.search(medicineQuery);
        // todo 查询分类列表
        List<Category> categories=categoryMapper.selectAllCategory();
        // 赋值给tablemodel
        tableModel = UIConverter.getMedicineData(medicines, categories);
    }

    private MedicineQuery getMedicineQuery() {
        // TODO 一些过滤条件 , 价格的简单判断
        String medicineNameStr = medicineName.getText().trim();
        String medicineMinPriceStr=medicineMinPrice.getText().trim();
        String medicineMaxPriceStr=medicineMaxPrice.getText().trim();
        Category category=(Category) medicineCategory.getSelectedItem();
        String categoryId=(category == null)? null:String.valueOf(category.getId());
        String datePickStr=datePick.getText();
        return MedicineQuery.from(medicineNameStr, medicineMinPriceStr, medicineMaxPriceStr, categoryId, datePickStr);
    }

    private void clearMedicineQuery() {
        // todo 清除查询条件
        medicineName.setText(null);
        medicineMinPrice.setText(null);
        medicineMaxPrice.setText(null);
        medicineCategory.setSelectedIndex(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datePick.getInnerTextField().setText(dateFormat.format(dealDate(true)));
    }

    public static void main(String[] args) {
        new MedicineFrame();
    }
}
