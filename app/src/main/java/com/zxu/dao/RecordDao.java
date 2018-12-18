package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Record;
import com.zxu.model.JC_RecordSumWeek;
import com.zxu.util.CodeConstant;
import com.zxu.util.CostEnum;
import com.zxu.util.UtilTools;

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
    public List<JC_RecordSumWeek> getRecordSumByWeek(String accountId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek());

        List<JC_RecordSumWeek> recordSumWeeks = new ArrayList<>();

        JC_RecordSumWeek sumWeek;
        for (int i = 1; i <= 7; i++) {
            sumWeek = new JC_RecordSumWeek();
            sumWeek.setDate(calendar.getTime());

            String s = format.format(calendar.getTime());
            List<JC_Record> records = super.where().eq("bookId", accountId).and()
                    .between("workTime", s + " 00:00", s + " 23:59").query();
            // 可抽取到adapter
            BigDecimal inCome = BigDecimal.ZERO, spend = BigDecimal.ZERO;
            for (int j = 0; j < records.size(); j++) {
                JC_Record record = records.get(j);
                if (CostEnum.SPEND.code().equals(record.getType())) {
                    spend = spend.add(new BigDecimal(record.getMoney()));
                } else if (CostEnum.INCOME.code().equals(record.getType())) {
                    inCome = inCome.add(new BigDecimal(record.getMoney()));
                }
            }
            //
            sumWeek.setMonth(new SimpleDateFormat("MM/yyyy").format(calendar.getTime()));
            sumWeek.setDay(new SimpleDateFormat("dd日").format(calendar.getTime()));
            //
            sumWeek.setInCome(UtilTools.format(inCome));
            sumWeek.setSpend(UtilTools.format(spend));
            sumWeek.setBalance(UtilTools.format(inCome.subtract(spend)));

            calendar.add(Calendar.DATE, 1);
            if (records.size() > 0) {
                sumWeek.setRecords(records);
                recordSumWeeks.add(sumWeek);
            }
        }
        return recordSumWeeks;
    }
}
