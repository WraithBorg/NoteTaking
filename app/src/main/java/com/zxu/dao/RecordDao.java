package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Record;
import com.zxu.util.CodeConstant;
import com.zxu.util.UtilTools;

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

//        SELECT * FROM jc_record WHERE workTime >= '2018-12-11 12:20' AND workTime <= '2018-12-11 12:20'
        return super.where().eq("account", accountId).and().between("workTime", start, end).query();
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
}
