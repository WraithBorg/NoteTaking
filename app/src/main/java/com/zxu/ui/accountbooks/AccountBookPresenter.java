package com.zxu.ui.accountbooks;

import com.zxu.application.GaiaApplication;
import com.zxu.dao.AccountBookDao;
import com.zxu.base.database.ServiceFactory;
import com.zxu.model.JC_AccountBook;

import java.util.List;

/**
 * 利用接口 通过Model层 获取保存数据，通过view更新界面
 */
public class AccountBookPresenter implements AccountBookContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();

    private AccountBookContract.View cView;

    public AccountBookPresenter(GaiaApplication application, AccountBookContract.View cView) {
        this.cView = cView;
        this.application = application;
    }

    /**
     * 获取账本
     */
    @Override
    public void getAccountBooks() {
        // 1: 获取数据,传给Presenter
        // 2: 通过view进行交互
        List<JC_AccountBook> list = getAccountBookDao().getAll();
        cView.setAccountBooks(list);
    }

    /**
     * 新增
     *
     * @param b
     */
    @Override
    public void addAccountBook(JC_AccountBook b) {
        getAccountBookDao().addAccountBook(b);
    }

    /**
     * 查询
     */
    @Override
    public void getAccountBooks4EDIT() {
        List<JC_AccountBook> list = getAccountBookDao().getAll();
        cView.setAccountBooks4EDIT(list);
    }

    /**
     * 删除
     */
    @Override
    public void delAccountBook(JC_AccountBook item) {
        getAccountBookDao().delAccountBook(item);
        List<JC_AccountBook> list = getAccountBookDao().getAll();
        cView.setAccountBooks4EDIT(list);
    }

    /**
     * @return
     */
    public AccountBookDao getAccountBookDao() {
        return serviceFactory.getService(application, AccountBookDao.class);
    }
}
