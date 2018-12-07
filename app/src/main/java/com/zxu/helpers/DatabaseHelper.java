package com.zxu.helpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.AccountBookDao;
import com.zxu.dao.CategoryDao;
import com.zxu.dao.PersonDao;
import com.zxu.model.JC_AccountBook;
import com.zxu.model.JC_Category;
import com.zxu.model.JC_Record;
import com.zxu.util.SqlUtil;

/**
 * 维护管理数据库的基类
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "persondata.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // create table
        PersonDao.createTable(database);
        createTable(database, JC_AccountBook.class);
        createTable(database, JC_Record.class);
        createTable(database, JC_Category.class);
    }

    @Override
    public void onConfigure(SQLiteDatabase database) {
        database.execSQL("pragma foreign_keys=ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        PersonDao.dropTable(database);
        dropTable(database, JC_AccountBook.class);
        dropTable(database, JC_Record.class);
        dropTable(database, JC_Category.class);
        onCreate(database);
    }

    public SQLiteDatabase open() {
        return getWritableDatabase();
    }

    public void create() {
        open();
    }

    /**
     * 创建表
     */
    private void createTable(SQLiteDatabase database, Class clazz) {
        database.execSQL(SqlUtil.getCreateTableSql(clazz));
    }

    /**
     * 删除表
     */
    public void dropTable(SQLiteDatabase database, Class clazz) {
        database.execSQL(SqlUtil.getDropTableSql(clazz));
    }
}
