package com.zxu.business.index.accountbooks;

import com.zxu.application.BaseView;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_AccountBook;

/**
 * 契约接口
 */
public interface AddAccountBookContract {
    /**
     * 获取数据
     */
    interface Presenter {
        void addAccountBook(GaiaApplication application, JC_AccountBook b);
    }

    /**
     * 定义与界面交互的方法
     */
    interface View extends BaseView<AddAccountBookContract.Presenter> {

        void showLoading();

        void hideLoading();

        void showError();

    }
}
