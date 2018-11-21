package com.zxu.application;

import android.app.Application;

import com.zxu.helpers.DatabaseHelper;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

public class GaiaApplication extends Application {
    private DatabaseHelper helper;

    public DatabaseHelper getDatabaseHelper() {
        if (helper == null){
            helper = new DatabaseHelper(this);
        }
        return helper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getDatabaseHelper().create();

        /* 使用OkHttpFinal */
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());

    }
}
