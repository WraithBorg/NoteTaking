package com.zxu.ui.accountbooks;

import com.zxu.application.BaseView;
import com.zxu.model.JC_AccountBook;

import java.util.List;

/**
 * 契约接口
 */
public interface AccountBookContract {
    /**
     * 获取数据
     */
    interface Presenter {
        void getAccountBooks();

        void addAccountBook( JC_AccountBook b);

        void getAccountBooks4EDIT();

        void delAccountBook( JC_AccountBook item);

        void getMonthReport(String accountId, String period);
    }

    /**
     * 定义与界面交互的方法
     */
    interface View extends BaseView<Presenter> {

        void setAccountBooks(List<JC_AccountBook> list);

        void showLoading();

        void setMonthReport(String[] strings);//收入支出结余

        /**
         * @return 判断Fragment是否添加到Activity中
         */
        boolean isActive();

        void setAccountBooks4EDIT(List<JC_AccountBook> list);
    }
}
