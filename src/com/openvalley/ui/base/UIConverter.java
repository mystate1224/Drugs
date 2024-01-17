package com.openvalley.ui.base;

import com.eltima.components.ui.DatePicker;
import com.openvalley.server.entity.Category;
import com.openvalley.server.entity.Medicine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @author 王孟沁
 */
public class UIConverter {
    private UIConverter() {
        throw new IllegalStateException("UIConverter class");
    }

    public static void initFrame(JFrame frame, String title) {
        frame.setTitle(title);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void initDialog(JDialog dialog, Frame owner, String title) {
        dialog.setTitle(title);
        dialog.setSize(350, 500);
        dialog.setLocationRelativeTo(owner);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }


    public static JButton initButton(String context) {
        return UIConverter.initButton(context, Color.BLUE, Color.ORANGE);
    }

    public static JButton initButton(String context, Color textColor, Color buttonColor) {
        JButton target = new JButton(context);
        target.setForeground(textColor);
        target.setBackground(buttonColor);
        target.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, 20));
        target.setBorderPainted(false);
        return target;
    }


    public static JLabel initLabel(String context) {
        return initLabel(context, 15);
    }

    public static JLabel initLabel(String context, Integer fontSize) {
        JLabel target = new JLabel(context);

        target.setBackground(Color.GRAY);
        target.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, fontSize));
        target.setHorizontalAlignment(SwingConstants.CENTER);
        return target;
    }

    public static JLabel initBlankLabel() {
        return initBlankLabel("     ", 50, 360, 15);
    }

    public static JLabel initBlankLabel(String context, Integer width, Integer height, Integer fontSize) {
        JLabel target = new JLabel(context);
        target.setSize(width, height);
        target.setBackground(Color.GRAY);
        target.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, fontSize));
        target.setHorizontalAlignment(SwingConstants.CENTER);
        return target;
    }

    public static JPanel getLabelAndComponentGroup(String labelContext, JComponent component) {
        return getLabelAndComponentGroup(labelContext, 15, component);
    }

    public static JPanel getLabelAndComponentGroup(String labelContext, Integer fontSize, JComponent component) {
        JPanel target = new JPanel();
        target.setLayout(new GridLayout(0, 2));
        target.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        target.add(initLabel(labelContext, fontSize));
        target.add(component);
        return target;
    }

    public static JTextField initTextField() {
        JTextField target = new JTextField();
        target.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, 15));
        target.setSelectedTextColor(Color.RED);
        return target;
    }

    public static DatePicker getDatePicker(Date date) {
        return getDatePicker(date, 0, 0);
    }

    public static DatePicker getDatePicker(Date date, int width, int height) {
        // 格式
        String defaultFormat = "yyyy-MM-dd";
        // 字体
        Font font = new Font("Times New Roman", Font.BOLD, 10);
        // 控件大小
        Dimension dimension = new Dimension(width, height);
        // 构造方法（初始时间，时间显示格式，字体，控件大小）
        DatePicker datePicker = new DatePicker(date, defaultFormat, font, dimension);
        datePicker.setLocale(Locale.CHINA);

        return datePicker;
    }

    public static DefaultTableModel getMedicineData(List<Medicine> medicines, List<Category> categories) {
        DefaultTableModel tableModel = new DefaultTableModel();

        // 1.列名
        String[] columnData = {"序号", "编号", "名称", "描述", "厂家", "有效期", "数量", "价格", "单位", "类别", "Id"};

        // 2.行数据
        List<List<String>> medicineData = new LinkedList<>();
        for (int i = 0, j = medicines.size(); i < j; i++) {
            Medicine medicine = medicines.get(i);

            List<String> dataOne = new ArrayList<>();
            dataOne.add(String.valueOf(i + 1));                                         // 序号
            dataOne.add(String.valueOf(medicine.getMedicineNo()));                        // 药品编号
            dataOne.add(String.valueOf(medicine.getName()));                              // 药品名称
            dataOne.add(String.valueOf(medicine.getDescription()));                       // 药品描述
            dataOne.add(String.valueOf(medicine.getFactoryAddress()));                   // 药品厂家
            dataOne.add(String.valueOf(medicine.getExpire()));                            // 药品有效期
            dataOne.add(String.valueOf(medicine.getNumber()));                            // 药品数量
            dataOne.add(String.valueOf(medicine.getPrice()));                             // 药品价格
            dataOne.add(String.valueOf(medicine.getUnit()));                              // 药品价格
            dataOne.add(getCategoryName(medicine.getCategoryId(), categories));           // 药品类别
            dataOne.add(String.valueOf(medicine.getId()));                                // 药品主键Id , 用于增删
            medicineData.add(dataOne);
        }
        Object[][] medicineDataTemp = new Object[medicineData.size()][];
        for (int i = 0, j = medicineData.size(); i < j; i++) {
            medicineDataTemp[i] = medicineData.get(i).toArray();
        }

        tableModel.setDataVector(medicineDataTemp, columnData);
        return tableModel;
    }

    private static String getCategoryName(Integer id, List<Category> categories) {
        if (categories.isEmpty()) return null;
        for (Category category : categories) {
            if (Objects.equals(id, category.getId())) {
                return category.getName();
            }
        }
        return null;
    }


}
