package com.zxu.index.accountbooks;

import com.zxu.application.GaiaApplication;
import com.zxu.dao.AccountBookDao;
import com.zxu.model.JC_AccountBook;

public class AddAccountBookPresenter implements AddAccountBookContract.Presenter {

    private AddAccountBookContract.View cView;

    public AddAccountBookPresenter(AddAccountBookContract.View cView) {
        this.cView = cView;
    }

    @Override
    public void addAccountBook(GaiaApplication application, JC_AccountBook b) {
        AccountBookDao.addAccountBook(application, b);
    }
}
