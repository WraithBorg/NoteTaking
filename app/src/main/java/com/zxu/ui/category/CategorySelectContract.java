package com.zxu.ui.category;

import com.zxu.application.BaseView;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Category;

import java.util.List;

public interface CategorySelectContract {
    interface Presenter{
        void getAll(GaiaApplication application);
    }
    interface View extends BaseView<CategorySelectContract.Presenter>{
        void setCategorys(List<JC_Category> categorys);

    }
}
