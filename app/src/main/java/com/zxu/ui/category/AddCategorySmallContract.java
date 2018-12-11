package com.zxu.ui.category;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Category;

public interface AddCategorySmallContract {
    interface Presenter{
        void addSmallCategory(JC_Category category);
    }
    interface View extends BaseView<AddCategorySmallContract.Presenter>{
        void showLoading();
    }
}
