package com.zxu.index.accountbooks;

import com.zxu.application.BaseView;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_AccountBook;

import java.util.List;

/**
 * 契约接口
 */
public interface AccountBooksContract {
    /**
     * 获取数据
     */
    interface Presenter {
        void getAccountBooks(GaiaApplication application,String userId);

        void addAccountBook(GaiaApplication application, JC_AccountBook b);

        void getAccountBooks4EDIT(GaiaApplication application, String s);
    }

    /**
     * 定义与界面交互的方法
     */
    interface View extends BaseView<Presenter> {

        void setAccountBooks(List<JC_AccountBook> list);

        void showLoading();

        void hideLoading();

        void showError();

        /**
         * @return 判断Fragment是否添加到Activity中
         */
        boolean isActive();

        void setAccountBooks4EDIT(List<JC_AccountBook> list);
    }

}
