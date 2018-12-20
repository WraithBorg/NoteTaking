package com.zxu.util;

import java.util.ArrayList;
import java.util.List;

public class CodeConstant {
    public static String ADDONETYPE = "addOneType";
    public static String ADDTWOTYPE = "addTwoType";
    public static int DIALOGWAITTIME = 300;

    public static String DAY = "DAY";
    public static String WEEK = "WEEK";
    public static String MONTH = "MONTH";
    public static String YEAR = "YEAR";

    public static List<String> SELTIMELIST = new ArrayList(){{
        add("今天");
        add("昨天");
        add("近七天");
        add("近30天");
    }};


}
