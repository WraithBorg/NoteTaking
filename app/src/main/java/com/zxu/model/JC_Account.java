package com.zxu.model;

import java.util.ArrayList;
import java.util.List;

public class JC_Account {
    private String id;
    private String name;

    public static List<JC_Account> getAccounts() {
        JC_Account a1 = new JC_Account();
        a1.setId("1");
        a1.setName("老公账户");
        JC_Account a2 = new JC_Account();
        a2.setId("2");
        a2.setName("老婆账户");
        List<JC_Account> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        return list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
