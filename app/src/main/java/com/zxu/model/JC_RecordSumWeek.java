package com.zxu.model;

import java.util.Date;
import java.util.List;

public class JC_RecordSumWeek {
    private Date date;
    private String inCome;
    private String spend;
    private String balance;
    private String day;
    private String month;
    private List<JC_Record> records;

    public List<JC_Record> getRecords() {
        return records;
    }

    public void setRecords(List<JC_Record> records) {
        this.records = records;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
