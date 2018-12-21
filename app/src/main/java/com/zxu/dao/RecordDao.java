package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.base.database.ZWhere;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_MonthPeriod;
import com.zxu.model.JC_Record;
import com.zxu.model.JC_RecordSearchQuery;
import com.zxu.model.JC_RecordSearchResult;
import com.zxu.model.JC_RecordSum;
import com.zxu.util.CodeConstant;
import com.zxu.util.CostEnum;
import com.zxu.util.UtilTools;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecordDao extends BaseDaoImpl<JC_Record> {


    /**
     * 新增
     *
     * @param p
     * @return
     */
    public ResultHelper addRecord(JC_Record p) {
        super.add(p);
        return new ResultHelper(true, "新增成功");
    }

    /**
     * 修改
     *
     * @param p
     * @return
     */
    public ResultHelper editRecord(JC_Record p) {
        super.modify(p);
        return new ResultHelper(false, "修改成功");
    }

    /**
     * 获取
     *
     * @return
     */
    public List<JC_Record> getAll(String accountId, String period) {

        String[] strings = new String[2];
        if (CodeConstant.DAY.equals(period)) {
            strings = UtilTools.DayBeginAndEnd();
        } else if (CodeConstant.WEEK.equals(period)) {
            strings = UtilTools.WeekBeginAndEnd();
        } else if (CodeConstant.MONTH.equals(period)) {
            strings = UtilTools.MonthBeginAndEnd();
        } else if (CodeConstant.YEAR.equals(period)) {
            strings = UtilTools.YearBeginAndEnd();
        }
        String start = strings[0], end = strings[1];

        return super.where().eq("bookId", accountId).and().between("workTime", start, end).query();
    }

    /**
     * 删除
     *
     * @param p
     * @return
     */
    public ResultHelper delRecord(JC_Record p) {
        super.delete(p.getId());
        return new ResultHelper(true, "删除成功");
    }

    /**
     * 获取本周每天的记录数
     *
     * @param accountId
     * @return
     */
    public List<JC_RecordSum> getRecordSumByWeek(String accountId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek());

        List<JC_RecordSum> recordSumWeeks = new ArrayList<>();

        JC_RecordSum sumWeek;
        for (int i = 1; i <= 7; i++) {
            sumWeek = new JC_RecordSum();

            String s = format.format(calendar.getTime());
            List<JC_Record> records = super.where().eq("bookId", accountId).and()
                    .between("workTime", s + " 00:00", s + " 23:59").query();
            // 可抽取到adapter
            BigDecimal inCome = BigDecimal.ZERO, spend = BigDecimal.ZERO;//每天汇总
            for (int j = 0; j < records.size(); j++) {
                JC_Record record = records.get(j);
                if (CostEnum.SPEND.code().equals(record.getWaterType())) {
                    spend = spend.add(new BigDecimal(record.getMoney()));
                } else if (CostEnum.INCOME.code().equals(record.getWaterType())) {
                    inCome = inCome.add(new BigDecimal(record.getMoney()));
                }
            }
            //
            sumWeek.setBottomTime(new SimpleDateFormat("MM/yyyy").format(calendar.getTime()));
            sumWeek.setDayOrWeek(new SimpleDateFormat("dd日").format(calendar.getTime()));
            //
            sumWeek.setInCome(UtilTools.format(inCome));
            sumWeek.setSpend(UtilTools.format(spend));
            sumWeek.setBalance(UtilTools.format(inCome.subtract(spend)));
            sumWeek.setFromWeek(false);
            calendar.add(Calendar.DATE, 1);
            if (records.size() > 0) {
                sumWeek.setRecords(records);
                recordSumWeeks.add(sumWeek);
            }
        }
        return recordSumWeeks;
    }

    /**
     * 获取每月记录天数
     *
     * @param accountId
     * @return
     */
    public List<JC_RecordSum> getRecordSumByMonth(String accountId) {
        List<JC_MonthPeriod> monthPeriods = new ArrayList<>();
        try {
            monthPeriods = UtilTools.printfWeeks(new SimpleDateFormat("yyyy-MM").format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<JC_RecordSum> recordSumWeeks = new ArrayList<>();
        JC_RecordSum sumWeek;
        for (JC_MonthPeriod period : monthPeriods) {
            sumWeek = new JC_RecordSum();
            sumWeek.setDayOrWeek(period.getWeek());
            sumWeek.setBottomTime(period.getPeriod());
            BigDecimal inCome = BigDecimal.ZERO, spend = BigDecimal.ZERO;//每周汇总
            List<JC_Record> records = super.where().eq("bookId", accountId).and()
                    .between("workTime", period.getStart() + " 00:00", period.getEnd() + " 23:59").query();
            for (int j = 0; j < records.size(); j++) {
                JC_Record record = records.get(j);
                if (CostEnum.SPEND.code().equals(record.getWaterType())) {
                    spend = spend.add(new BigDecimal(record.getMoney()));
                } else if (CostEnum.INCOME.code().equals(record.getWaterType())) {
                    inCome = inCome.add(new BigDecimal(record.getMoney()));
                }
            }
            sumWeek.setInCome(UtilTools.format(inCome));
            sumWeek.setSpend(UtilTools.format(spend));
            sumWeek.setBalance(UtilTools.format(inCome.subtract(spend)));
            sumWeek.setFromWeek(true);
            if (records.size() > 0) {
                sumWeek.setRecords(records);
                recordSumWeeks.add(sumWeek);
            }
        }
        return recordSumWeeks;
    }

    /**
     * 获取查询结果
     *
     * @param recordQuery
     * @return
     */
    public List<JC_RecordSearchResult> getSearchResult(JC_RecordSearchQuery recordQuery) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //
        String accountBookId = recordQuery.getAccountBookId();
        String start, end;
        String peroid = recordQuery.getPeroid();
        String today = format.format(new Date());
        if (peroid.equals("今天")) {
            start = today + " 00:00";
            end = today + " 23:59";
        } else if (peroid.equals("昨天")) {
            String lastDay = UtilTools.getLastDay(format, -1);
            start = lastDay+ " 00:00";
            end = lastDay + " 23:59";
        } else if (peroid.equals("近七天")) {
            start = UtilTools.getLastDay(format, -7)+ " 00:00";
            end = today + " 23:59";
        } else if (peroid.equals("近30天")) {
            start = UtilTools.getLastDay(format, -30)+ " 00:00";
            end = today + " 23:59";
        } else {
            start = today + " 00:00";
            end = today + " 23:59";
        }

        ZWhere<JC_Record> where = super.where().eq("bookId", accountBookId);
        if (StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
            where.and().between("workTime", start, end);
        }
        if (StringUtils.isNotEmpty(recordQuery.getWaterType())) {
            where.and().eq("waterType", recordQuery.getWaterType());
        }
        if (StringUtils.isNotEmpty(recordQuery.getAccountType())) {
            where.and().eq("account", recordQuery.getAccountType());
        }
        if (StringUtils.isNotEmpty(recordQuery.getMemo())) {
            where.and().eq("memo", recordQuery.getMemo());
        }
        if (StringUtils.isNotEmpty(recordQuery.getMinMoney()) && StringUtils.isNotEmpty(recordQuery.getMaxMoney())) {
            where.and().between("money", recordQuery.getMinMoney(), recordQuery.getMaxMoney());// TODO 字符比较 2>11
        }
        List<JC_Record> records = where.query();

        JC_RecordSearchResult result = new JC_RecordSearchResult();
        result.setRecords(records);

        result.setWeekDay("星期一");
        result.setDate(new Date());

        List<JC_RecordSearchResult> list = new ArrayList<>();
        list.add(result);

        return list;
    }
}
