package com.zxu.net;

import com.zxu.application.LoadTasksCallBack;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 淘宝IP库地址
 * http://ip.taobao.com/service/getIpInfo.php?ip=114.114.114.114
 */
public class IpInfoTask implements NetTask<String> {
    private static IpInfoTask INSTANCE = null;

    private static final String HOST = "http://ip.taobao.com/service/getIpInfo.php";
    private LoadTasksCallBack loadTasksCallBack;
    private IpInfoTask() {

    }
    public static IpInfoTask getInstance(){
        if (INSTANCE == null){
            INSTANCE = new IpInfoTask();
        }
        return INSTANCE;
    }

    @Override
    public void execute(String ip, LoadTasksCallBack loadTasksCallBack){
        RequestParams requestParams = new RequestParams();
        requestParams.addFormDataPart("ip", ip);
        HttpRequest.get(HOST,requestParams,new BaseHttpRequestCallback<Object>(){
            @Override
            public void onStart() {
                super.onStart();
                loadTasksCallBack.onStart();
            }

            @Override
            protected void onSuccess(Object ipInfo) {
                super.onSuccess(ipInfo);
                loadTasksCallBack.onSuccess(ipInfo);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                loadTasksCallBack.onFinish();
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                System.out.println(msg);
                super.onFailure(errorCode, msg);
                loadTasksCallBack.onFailed();
            }
        });
    }
}
