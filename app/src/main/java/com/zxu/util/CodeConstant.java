package com.zxu.util;

import com.zxu.model.JC_Account;

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
    // 时间
    public static List<String> SELTIMELIST = new ArrayList(){{
        add("今天");
        add("昨天");
        add("近七天");
        add("近30天");
    }};
    // 流水类型
    public static String[] WATERTYPE = new String[]{"支出", "收入", "转账"};
    // 账户类型
    public static List<JC_Account> ACCOUNTTYPE = new ArrayList() {{
        JC_Account a1 = new JC_Account();
        a1.setId("1");
        a1.setName("老公账户");
        JC_Account a2 = new JC_Account();
        a2.setId("2");
        a2.setName("老婆账户");
        add(a1);
        add(a2);
    }};
}
