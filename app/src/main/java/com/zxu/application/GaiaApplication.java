package com.zxu.application;

import android.app.Application;

import com.zxu.helpers.SQLiteHelper;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

public class GaiaApplication extends Application {
    private SQLiteHelper helper;

    public SQLiteHelper getSQLiteHelper() {
        if (helper == null){
            helper = new SQLiteHelper(this);
        }
        return helper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getSQLiteHelper().create();

        /* 使用OkHttpFinal */
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());

    }
}
