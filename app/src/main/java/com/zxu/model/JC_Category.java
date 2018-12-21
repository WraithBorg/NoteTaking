package com.zxu.model;

import com.zxu.annotation.DataType;
import com.zxu.annotation.DatabaseField;
import com.zxu.annotation.DatabaseTable;

import java.util.List;

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
    @DatabaseField(columnName = "waterType", dataType = DataType.STRING)
    private String waterType;// 0 收入，1 支出
    @DatabaseField(columnName = "num", dataType = DataType.STRING)
    private String num;//序号

    //
    List<JC_Category> childs;
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

    public List<JC_Category> getChilds() {
        return childs;
    }

    public void setChilds(List<JC_Category> childs) {
        this.childs = childs;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    public JC_Category() {
    }

    public JC_Category(String id, String name, String fatherId, int type,String waterType,String num, List<JC_Category> childs) {
        this.id = id;
        this.name = name;
        this.fatherId = fatherId;
        this.type = type;
        this.waterType = waterType;
        this.num = num;
        this.childs = childs;
    }

    @Override
    public String toString() {
        String str = id + "|" + name + "|" + fatherId + "|" + type + "|" + num;
        return str;
    }
}
