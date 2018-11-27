package com.zxu.ui.category;

import com.zxu.application.BaseView;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Category;

public interface CategoryAddSmallContract {
    interface Presenter{
        void addSmallCategory(GaiaApplication application, JC_Category category);
    }
    interface View extends BaseView<CategoryAddSmallContract.Presenter>{
        void showLoading();
    }
}
