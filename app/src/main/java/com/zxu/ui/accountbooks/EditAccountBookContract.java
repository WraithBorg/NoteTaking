package com.zxu.ui.accountbooks;

import com.zxu.application.BaseView;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_AccountBook;

/**
 * 契约接口
 */
public interface EditAccountBookContract {
    /**
     * 获取数据
     */
    interface Presenter {
        void editAccountBook(GaiaApplication application, JC_AccountBook b);
    }

    /**
     * 定义与界面交互的方法
     */
    interface View extends BaseView<EditAccountBookContract.Presenter> {

        void showLoading();

        void hideLoading();

        void showError();

    }
}
