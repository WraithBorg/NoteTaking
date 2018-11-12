package com.zxu.ipinfo;

import com.zxu.model.IpInfo;
import com.zxu.application.BaseView;

/**
 * 契约接口
 */
public interface IpInfoContract {
    /**
     * 定义获取数据的的方法
     */
    interface Presenter {
        void getIpInfo(String ip);
    }

    /**
     * 定义与界面交互的方法
     */
    interface View extends BaseView<Presenter> {
        void setIpInfo(IpInfo ipInfo);

        void showLoading();

        void hideLoading();

        void showError();

        /**
         * 判断Fragment是否添加到Activity中
         *
         * @return
         */
        boolean isActive();


    }
}
