package com.zxu.ui.category;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Category;

import java.util.List;

public interface CategoryContract {
    interface Presenter{
        void getCategorys(boolean fromAdd,String mType);
    }
    interface View extends BaseView<CategoryContract.Presenter>{
        void setCategorys(List<JC_Category> categorys);

        void setCostType(String type);
    }
}
