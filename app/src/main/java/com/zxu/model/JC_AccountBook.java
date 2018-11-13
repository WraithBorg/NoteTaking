package com.zxu.model;

/**
 * 账本
 */
public class JC_AccountBook {
    private String id;
    private String name;
    private String imgUrl;
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
