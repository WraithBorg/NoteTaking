package com.zxu.index.accounts;

import com.zxu.application.GaiaApplication;
import com.zxu.dao.AccountBookDao;
import com.zxu.model.JC_AccountBook;

import java.util.ArrayList;
import java.util.List;

/**
 * 利用接口 通过Model层 获取保存数据，通过view更新界面
 */
public class AccountBooksPresenter implements AccountBooksContract.Presenter {

    private AccountBooksContract.View cView;

    public AccountBooksPresenter(AccountBooksContract.View cView) {
        this.cView = cView;
    }

    /**
     * 获取账本
     *
     * @param userId 用户ID
     */
    @Override
    public void getAccountBooks(GaiaApplication application,String userId) {
        // 1: 获取数据,传给Presenter
        // 2: 通过view进行交互
        List<JC_AccountBook> list = AccountBookDao.getAll(application);
        cView.setAccountBooks(list);
    }

    /**
     * 新增账本
     * @param application
     * @param b
     */
    @Override
    public void addAccountBook(GaiaApplication application, JC_AccountBook b) {
        AccountBookDao.addAccountBook(application, b);
    }
}
