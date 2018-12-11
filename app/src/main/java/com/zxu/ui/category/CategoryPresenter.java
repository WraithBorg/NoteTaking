package com.zxu.ui.category;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.CategoryDao;
import com.zxu.model.JC_Category;

import java.util.List;

public class CategoryPresenter implements CategoryContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    CategoryContract.View cView;

    public CategoryPresenter(GaiaApplication application, CategoryContract.View cView) {
        this.application = application;
        this.cView = cView;
    }

    public CategoryDao categoryDao() {
        return serviceFactory.getService(application, CategoryDao.class);
    }

    @Override
    public void getCategorys(boolean fromAdd, String mType) {
        List<JC_Category> list = categoryDao().getBSList(fromAdd, mType);
        cView.setCategorys(list);
    }
}
