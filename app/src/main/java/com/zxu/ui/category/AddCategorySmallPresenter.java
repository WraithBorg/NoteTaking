package com.zxu.ui.category;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.CategoryDao;
import com.zxu.model.JC_Category;

public class AddCategorySmallPresenter implements AddCategorySmallContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    AddCategorySmallContract.View cView;

    public AddCategorySmallPresenter(GaiaApplication application, AddCategorySmallContract.View cView) {
        this.application = application;
        this.cView = cView;
    }

    public CategoryDao categoryDao() {
        return serviceFactory.getService(application, CategoryDao.class);
    }

    @Override
    public void addSmallCategory(JC_Category category) {
        categoryDao().addCategory(category);
    }
}
