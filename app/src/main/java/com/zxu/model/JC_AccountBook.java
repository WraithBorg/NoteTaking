package com.zxu.model;

import com.zxu.annotation.DataType;
import com.zxu.annotation.DatabaseField;
import com.zxu.annotation.DatabaseTable;

/**
 * 账本
 */
@DatabaseTable(tableName = "jc_accountbook")
public class JC_AccountBook {
    @DatabaseField(columnName = "id",id = true,dataType = DataType.STRING)
    private String id;
    @DatabaseField(columnName = "name",dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = "imgUrl",dataType = DataType.STRING)
    private String imgUrl;
    @DatabaseField(columnName = "userId",dataType = DataType.STRING)
    private String userId;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JC_AccountBook() {
    }

    public JC_AccountBook(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
