package com.zxu.business.accountbooks;

import com.zxu.application.GaiaApplication;
import com.zxu.dao.AccountBookDao;
import com.zxu.base.database.ServiceFactory;
import com.zxu.model.JC_AccountBook;

public class EditAccountBookPresenter implements EditAccountBookContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private EditAccountBookContract.View cView;

    public EditAccountBookPresenter(GaiaApplication application, EditAccountBookContract.View cView) {
        this.cView = cView;
        this.application = application;
    }

    @Override
    public void editAccountBook(GaiaApplication application, JC_AccountBook b) {
        getAccountBookDao().editAccountBook(b);
    }

    /**
     * @return
     */
    public AccountBookDao getAccountBookDao() {
        return serviceFactory.getService(application, AccountBookDao.class);
    }
}
