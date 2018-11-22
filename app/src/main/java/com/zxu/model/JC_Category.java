package com.zxu.model;

import com.zxu.annotation.DataType;
import com.zxu.annotation.DatabaseField;
import com.zxu.annotation.DatabaseTable;

@DatabaseTable(tableName = "jc_category")
public class JC_Category {
    @DatabaseField(columnName = "id", id = true, dataType = DataType.STRING)
    private String id;
    @DatabaseField(columnName = "name", dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = "fatherId", dataType = DataType.STRING)
    private String fatherId;
    @DatabaseField(columnName = "type", dataType = DataType.INTEGER)
    private int type;// 0 一级分类，1 二级分类

    /********** setter and getter *********/
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

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}