package com.zxu.demo.ipinfo;

import com.zxu.model.IpInfo;
import com.zxu.application.LoadTasksCallBack;
import com.zxu.demo.net.NetTask;

/**
 * IpInfoPresenter 包含NetTask和IpInfoContract.View实例，并实现了LoadTasksCallBack接口
 * Presenter是一个中间人的角色，通过NetTask也就是Model层  获取和保存数据，然后通过View更新界面
 * 期间通过定义接口，是View和Model层 没有任何交互
 */
public class IpInfoPresenter implements IpInfoContract.Presenter, LoadTasksCallBack<IpInfo> {

    private NetTask netTask;
    private IpInfoContract.View addTaskView;

    public IpInfoPresenter(IpInfoContract.View addTaskView, NetTask netTask) {
        this.netTask = netTask;
        this.addTaskView = addTaskView;
    }

    @Override
    public void getIpInfo(String ip) {
        // 将自身传入NetTask的execute中 获取数据，并回调给IpInfoPresenter,
        // 最后通过addTaskView来和View进行交互
        netTask.execute(ip, this); // 1
    }

    @Override
    public void onSuccess(IpInfo ipInfo) {
        if (addTaskView.isActive()) {
            addTaskView.setIpInfo(ipInfo);
        }
    }

    @Override
    public void onStart() {
        if (addTaskView.isActive()) {
            addTaskView.showLoading();
        }
    }

    @Override
    public void onFailed() {
        if (addTaskView.isActive()) {
            addTaskView.showError();
            addTaskView.hideLoading();
        }
    }

    @Override
    public void onFinish() {
        if (addTaskView.isActive()) {
            addTaskView.hideLoading();
        }
    }
}
