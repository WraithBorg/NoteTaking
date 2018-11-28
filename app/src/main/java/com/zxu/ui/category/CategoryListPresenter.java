package com.zxu.ui.category;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.CategoryDao;
import com.zxu.model.JC_Category;

import java.util.List;

public class CategoryListPresenter implements CategoryListContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    CategoryListContract.View cView;

    public CategoryListPresenter(GaiaApplication application, CategoryListContract.View cView) {
        this.application = application;
        this.cView = cView;
    }

    public CategoryDao categoryDao() {
        return serviceFactory.getService(application, CategoryDao.class);
    }

    @Override
    public void getAll() {
        List<JC_Category> list = categoryDao().getBSList(true);
        cView.setCategorys(list);
    }
}
