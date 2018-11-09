package com.zxu.application;

import android.app.Application;

import com.zxu.helpers.SQLiteHelper;

public class GaiaApplication extends Application {
    private SQLiteHelper helper;

    public SQLiteHelper getHelper() {
        if (helper == null){
            helper = new SQLiteHelper(this);
        }
        return helper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getHelper().create();

    }
}
