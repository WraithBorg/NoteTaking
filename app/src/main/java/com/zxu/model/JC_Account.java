package com.zxu.model;

public class JC_Account {
    private String id;
    private String name;


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

    public JC_Account(String name) {
        this.name = name;
    }

    public JC_Account() {
    }
}
