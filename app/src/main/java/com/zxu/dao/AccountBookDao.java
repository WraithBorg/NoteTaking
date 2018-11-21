package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_AccountBook;

import java.util.List;

public class AccountBookDao extends BaseDaoImpl<JC_AccountBook> {

    /**
     * Construct
     */
    public AccountBookDao() {
    }

    /**
     * 新增账本
     *
     * @param p
     * @return
     */
    public ResultHelper addAccountBook(JC_AccountBook p) {
        super.add(p);
        return new ResultHelper(true, "新增成功");
    }

    /**
     * 修改账本
     *
     * @param p
     * @return
     */
    public ResultHelper editAccountBook(JC_AccountBook p) {
        super.modify(p);
        return new ResultHelper(false, "修改成功");
    }

    /**
     * 获取账本
     *
     * @return
     */
    public List<JC_AccountBook> getAll() {
        return super.getList();
    }

    /**
     * 删除账本
     *
     * @param p
     * @return
     */
    public ResultHelper delAccountBook(JC_AccountBook p) {
        super.delete(p.getId());
        return new ResultHelper(true, "删除成功");
    }
}
