package com.zxu.helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zxu.dao.PersonDao;
import com.zxu.entity.Person;

/**
 * 维护管理数据库的基类
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "persondata.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        PersonDao.createTable(database);
    }

    @Override
    public void onConfigure(SQLiteDatabase database) {
        database.execSQL("pragma foreign_keys=ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        PersonDao.dropTable(database);
        onCreate(database);
    }

    public SQLiteDatabase open() {
        return getWritableDatabase();
    }

    public void create() {
        open();
    }
}
