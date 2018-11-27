package com.zxu.ui.category;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.CategoryDao;
import com.zxu.model.JC_Category;

public class CategoryAddBigPresenter implements CategoryAddBigContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private CategoryAddBigContract.View cView;

    public CategoryAddBigPresenter(GaiaApplication application, CategoryAddBigContract.View cView) {
        this.application = application;
        this.cView = cView;
    }

    public CategoryDao categoryDao() {
        return serviceFactory.getService(application, CategoryDao.class);
    }

    @Override
    public void addBigCategory(GaiaApplication application, JC_Category category) {
        categoryDao().addCategory(category);
    }
}
