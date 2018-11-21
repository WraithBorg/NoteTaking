package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Record;

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
    public List<JC_Record> getAll() {
        return super.getList();
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
