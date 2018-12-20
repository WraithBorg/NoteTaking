package com.zxu.model;

import java.util.Date;
import java.util.List;

public class JC_RecordSearchResult {
    private Date date;//日期
    private String weekDay;//星期几
    private List<JC_Record> records;
    /*
     * */

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public List<JC_Record> getRecords() {
        return records;
    }

    public void setRecords(List<JC_Record> records) {
        this.records = records;
    }
}
