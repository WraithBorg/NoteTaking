package com.zxu.model;

import java.util.List;

public class JC_RecordSum {
    private String inCome;
    private String spend;
    private String balance;
    private String dayOrWeek;//18日or6周
    private String bottomTime;
    private List<JC_Record> records;

    public List<JC_Record> getRecords() {
        return records;
    }

    public void setRecords(List<JC_Record> records) {
        this.records = records;
    }

    public String getInCome() {
        return inCome;
    }

    public void setInCome(String inCome) {
        this.inCome = inCome;
    }

    public String getSpend() {
        return spend;
    }

    public void setSpend(String spend) {
        this.spend = spend;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDayOrWeek() {
        return dayOrWeek;
    }

    public void setDayOrWeek(String dayOrWeek) {
        this.dayOrWeek = dayOrWeek;
    }

    public String getBottomTime() {
        return bottomTime;
    }

    public void setBottomTime(String bottomTime) {
        this.bottomTime = bottomTime;
    }
}
