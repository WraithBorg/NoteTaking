package com.zxu.model;

import com.zxu.annotation.DatabaseTable;

/**
 * 收支记录
 */
@DatabaseTable(tableName = "jc_record")
public class JC_Record {
    private String id;
    private String type;// 收入:0,支出:1，转账:2
    private String category;// 类别：零食，早餐
    private String money;//金额
    private String account;//账户：男钱包，女钱包，信用卡
    private String workTime;//业务时间
    private String memo;//备注
    private String userId;//创建人
    private String createTime;//创建时间
    /********** setter and getter *********/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
