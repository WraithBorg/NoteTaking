package com.zxu.net;

import android.util.Log;

import com.zxu.application.LoadTasksCallBack;
import com.zxu.model.IpInfo;

import java.io.IOException;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IpInfoTask implements NetTask<String> {
    private static IpInfoTask INSTANCE = null;

    private static final String HOST = "http://127.0.0.1:8081/z5/test";
    private static final String TestUrl = "http://ip.taobao.com/ipSearch.html?ipAddr=112.112.112.112";
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

//        Request.Builder requestBuilder = new Request.Builder().url(TestUrl);
//        requestBuilder.method("GET", null);
//        Request request = requestBuilder.build();
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        Call mcall = mOkHttpClient.newCall(request);
//        mcall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                loadTasksCallBack.onStart();
//                loadTasksCallBack.onSuccess(response);
//            }
//        });


        RequestParams requestParams = new RequestParams();
        requestParams.addFormDataPart("ipAddr", ip);
        HttpRequest.get(HOST,new BaseHttpRequestCallback<Object>(){
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
