package com.openvalley.ui.base;

import java.util.List;

/**
 * @author 王孟沁
 */
public class UIConstants {
    private UIConstants() {
        throw new IllegalStateException("UIConstants class");
    }

    public static final String FONT_NAME_SONG = "宋体";
    public static final String FONT_NAME_BLACK = "黑体";


    public static final Integer SELECTED_ID = -1;

    /**
     * {@link UIConverter#getMedicineData(List, List) 药品数据表格 , 所在的16进制列 -> 药品主键Id  }
     */
    public static final int MEDICINE_ID = 0X000A;


}
