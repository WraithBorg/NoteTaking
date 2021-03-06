package com.zxu.ui.accountbooks;

import com.zxu.application.GaiaApplication;
import com.zxu.dao.AccountBookDao;
import com.zxu.base.database.ServiceFactory;
import com.zxu.model.JC_AccountBook;

public class AddAccountBookPresenter implements AddAccountBookContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private AddAccountBookContract.View cView;

    public AddAccountBookPresenter(GaiaApplication application, AddAccountBookContract.View cView) {
        this.cView = cView;
        this.application = application;
    }

    @Override
    public void addAccountBook(JC_AccountBook b) {
        getAccountBookDao().addAccountBook(b);
    }

    /**
     * @return
     */
    public AccountBookDao getAccountBookDao() {
        return serviceFactory.getService(application, AccountBookDao.class);
    }
}
