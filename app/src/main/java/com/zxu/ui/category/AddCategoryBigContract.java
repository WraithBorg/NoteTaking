package com.zxu.ui.category;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Category;

public interface AddCategoryBigContract {
    /**
     * 业务处理
     */
    interface Presenter{
        void addBigCategory(JC_Category category);
    }
    /**
     * 界面显示
     */
    interface View extends BaseView<AddCategoryBigContract.Presenter>{
        void showLoading();
    }

}
