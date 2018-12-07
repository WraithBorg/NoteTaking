package com.zxu.application;

import android.app.Application;

import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.AccountBookDao;
import com.zxu.dao.CategoryDao;
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
        // init database
        getDatabaseHelper().create();
        // init data
        ServiceFactory serviceFactory = new ServiceFactory();
        CategoryDao categoryDao = serviceFactory.getService(this, CategoryDao.class);
        categoryDao.initData();
        AccountBookDao accountBookDao = serviceFactory.getService(this, AccountBookDao.class);
        accountBookDao.initData();
        // 使用OkHttpFinal
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());

    }
}
