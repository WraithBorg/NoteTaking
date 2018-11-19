package com.zxu.index.accountbooks;

import com.zxu.application.GaiaApplication;
import com.zxu.dao.AccountBookDao;
import com.zxu.model.JC_AccountBook;

public class EditAccountBookPresenter implements EditAccountBookContract.Presenter {

    private EditAccountBookContract.View cView;

    public EditAccountBookPresenter(EditAccountBookContract.View cView) {
        this.cView = cView;
    }

    @Override
    public void editAccountBook(GaiaApplication application, JC_AccountBook b) {
        AccountBookDao.editAccountBook(application, b);
    }
}
