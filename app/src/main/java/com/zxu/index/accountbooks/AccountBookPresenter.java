package com.zxu.index.accountbooks;

import com.zxu.application.GaiaApplication;
import com.zxu.dao.AccountBookDao;
import com.zxu.model.JC_AccountBook;

import java.util.List;

/**
 * 利用接口 通过Model层 获取保存数据，通过view更新界面
 */
public class AccountBookPresenter implements AccountBookContract.Presenter {

    private AccountBookContract.View cView;

    public AccountBookPresenter(AccountBookContract.View cView) {
        this.cView = cView;
    }

    /**
     * 获取账本
     *
     * @param userId 用户ID
     */
    @Override
    public void getAccountBooks(GaiaApplication application, String userId) {
        // 1: 获取数据,传给Presenter
        // 2: 通过view进行交互
        List<JC_AccountBook> list = AccountBookDao.getAll(application);
        cView.setAccountBooks(list);
    }

    /**
     * 新增
     *
     * @param application
     * @param b
     */
    @Override
    public void addAccountBook(GaiaApplication application, JC_AccountBook b) {
        AccountBookDao.addAccountBook(application, b);
    }

    /**
     * 查询
     *
     * @param application
     */
    @Override
    public void getAccountBooks4EDIT(GaiaApplication application, String s) {
        List<JC_AccountBook> list = AccountBookDao.getAll(application);
        cView.setAccountBooks4EDIT(list);
    }

    /**
     * 删除
     *
     * @param application
     */
    @Override
    public void delAccountBook(GaiaApplication application, JC_AccountBook item) {
        AccountBookDao.delAccountBook(application, item);
        List<JC_AccountBook> list = AccountBookDao.getAll(application);
        cView.setAccountBooks4EDIT(list);
    }
}
