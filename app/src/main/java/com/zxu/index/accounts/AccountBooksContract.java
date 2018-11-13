package com.zxu.index.accounts;

import com.zxu.application.BaseView;
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
        void getAccountBooks(String userId);

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

    }

}
