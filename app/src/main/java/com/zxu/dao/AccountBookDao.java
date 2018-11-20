package com.zxu.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxu.annotation.DatabaseTable;
import com.zxu.application.GaiaApplication;
import com.zxu.helpers.ResultHelper;
import com.zxu.helpers.SQLiteHelper;
import com.zxu.model.JC_AccountBook;
import com.zxu.util.SqlUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AccountBookDao {
    /**
     * 创建表
     *
     * @param database
     */
    private static String tableName;

    // 静态代码块优先执行
    static {
        Annotation annotation = JC_AccountBook.class.getAnnotation(DatabaseTable.class);
        tableName = ((DatabaseTable) annotation).tableName();

    }

    public AccountBookDao() {
    }

    public static void createTable(SQLiteDatabase database) {
        String createTableSql = SqlUtil.getCreateTableSql(JC_AccountBook.class);
        database.execSQL(createTableSql);
    }

    /**
     * 删除表
     *
     * @param database
     */
    public static void dropTable(SQLiteDatabase database) {
        String dropTable = "drop table if exists " + tableName;
        database.execSQL(dropTable);
    }

    /**
     * 结果集转换
     *
     * @param cursor
     * @return
     */
    private static JC_AccountBook cursorToPerson(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String imgUrl = cursor.getString(cursor.getColumnIndex("imgurl"));
        String userId = cursor.getString(cursor.getColumnIndex("userid"));

        JC_AccountBook accountBook = new JC_AccountBook();
        accountBook.setId(id);
        accountBook.setName(name);
        accountBook.setImgUrl(imgUrl);
        accountBook.setUserId(userId);

        return accountBook;
    }

    /**
     * 新增账本
     *
     * @param application
     * @param p
     * @return
     */
    public static ResultHelper addAccountBook(GaiaApplication application, JC_AccountBook p) {
        SQLiteHelper helper = application.getSQLiteHelper();
        SQLiteDatabase database = helper.getWritableDatabase();

        ResultHelper res = new ResultHelper(true, "新增成功");
        try {
            String sql = "insert into " + tableName + " (id,name,imgurl,userId) values (" +
                    "'" + p.getId() + "'," +
                    "'" + p.getName() + "'," +
                    "'" + p.getImgUrl() + "'," +
                    "'" + p.getUserId() + "')";
            database.execSQL(sql);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResultHelper(false, "新增失败");
        }
        return res;
    }

    /**
     * 修改账本
     *
     * @param application
     * @param p
     * @return
     */
    public static ResultHelper editAccountBook(GaiaApplication application, JC_AccountBook p) {
        SQLiteHelper helper = application.getSQLiteHelper();
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", p.getId());
        values.put("name", p.getName());
        values.put("imgurl", p.getImgUrl());
        values.put("userId", p.getUserId());
        int updates;
        ResultHelper res = new ResultHelper(false, "修改失败");
        try {
            updates = database.update(tableName, values, "id=?", new String[]{String.valueOf(p.getId())});
            if (updates > 0) {
                res = new ResultHelper(true, "修改成功");
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取账本
     *
     * @param application
     * @return
     */
    public static List<JC_AccountBook> getAll(GaiaApplication application) {
        SQLiteHelper helper = application.getSQLiteHelper();
        SQLiteDatabase database = helper.open();
        List<JC_AccountBook> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + tableName, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            JC_AccountBook p = cursorToPerson(cursor);
            list.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        helper.close();
        return list;
    }

    /**
     * 删除账本
     *
     * @param application
     * @param p
     * @return
     */
    public static ResultHelper delAccountBook(GaiaApplication application, JC_AccountBook p) {
        SQLiteHelper helper = application.getSQLiteHelper();
        SQLiteDatabase database = helper.getWritableDatabase();

        ResultHelper res = new ResultHelper(true, "删除成功");
        try {
            database.delete(tableName, "id=?", new String[]{String.valueOf(p.getId())});
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResultHelper(false, "删除失败");
        }
        return res;
    }
}
