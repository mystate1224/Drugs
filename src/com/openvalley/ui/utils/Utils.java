package com.openvalley.ui.utils;

import com.eltima.components.ui.DatePicker;
import com.openvalley.server.entity.Medicine;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * 帮助类
 */
public class Utils {
    /**
     * 获取数据转换成填充表格适配的数据格式
     *
     * @param list
     * @return
     */
    public static Vector<Vector<String>> getMedicineData(List<Medicine> list) {
        Vector<Vector<String>> data = new Vector<>();
        for (int i = 0; i < list.size(); i++) {
            Medicine medicine = list.get(i);
            Vector<String> vector = new Vector<>();
            vector.add(String.valueOf(i + 1));
            vector.add(String.valueOf(medicine.getMedicineNo()));
            vector.add(String.valueOf(medicine.getName()));
            vector.add(String.valueOf(medicine.getDescription()));
            vector.add(String.valueOf(medicine.getFactoryAddress()));
            vector.add(String.valueOf(medicine.getExpire()));
            vector.add(String.valueOf(medicine.getNumber()));
            vector.add(String.valueOf(medicine.getPrice()));
            vector.add(String.valueOf(medicine.getUnit()));
            vector.add(String.valueOf(medicine.getCategoryId()));
            vector.add(String.valueOf(medicine.getId()));
            data.add(vector);
        }
        return data;
    }

    /**
     * 表头列名数据
     *
     * @return Vector
     */
    public static Vector<String> getMedicineColumns() {
        Vector<String> vector = new Vector<>();
        vector.add("序号");
        vector.add("药品批号");
        vector.add("药品名称");
        vector.add("描述");
        vector.add("厂家");
        vector.add("有效期");
        vector.add("数量");
        vector.add("价格");
        vector.add("单位");
        vector.add("类别");
        vector.add("id");
        return vector;
    }

    //日期选择器
    public static DatePicker getDatePicker(Date date, int width, int height) {
        final DatePicker datepick;
        // 格式
        String defaultFormat = "yyyy-MM-dd";
        // 字体
        Font font = new Font("Times New Roman", Font.BOLD, 14);
        //控件大小
        Dimension dimension = new Dimension(width, height);
        //构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, defaultFormat, font, dimension);
        datepick.setLocale(Locale.CHINA);

        return datepick;
    }

}
