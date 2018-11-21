package com.zxu.dao;

import android.database.sqlite.SQLiteDatabase;

import com.zxu.annotation.DatabaseTable;
import com.zxu.application.GaiaApplication;
import com.zxu.helpers.ResultHelper;
import com.zxu.helpers.DatabaseHelper;
import com.zxu.model.JC_AccountBook;
import com.zxu.util.SqlUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AccountBookDao extends BaseDaoImpl<JC_AccountBook>{


    /**
     * 创建表
     *
     * @param database
     */
    private static String tableName;

    /**
     * 静态代码块优先执行
     */
    static {
        Annotation annotation = JC_AccountBook.class.getAnnotation(DatabaseTable.class);
        tableName = ((DatabaseTable) annotation).tableName();
    }
    /**
     * Construct
     */
    public AccountBookDao() {
    }

    /**
     * 建表
     *
     * @param database
     */
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
     * 新增账本
     *
     * @param p
     * @return
     */
    public ResultHelper addAccountBook(JC_AccountBook p) {

        ResultHelper res = new ResultHelper(true, "新增成功");
        try {
            super.add(p);
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
     * @param p
     * @return
     */
    public ResultHelper editAccountBook(JC_AccountBook p) {
        ResultHelper res = new ResultHelper(false, "修改成功");
        super.modify(p);
        return res;
    }

    /**
     * 获取账本
     *
     * @return
     */
    public List<JC_AccountBook> getAll() {
        return super.getList();
    }

    /**
     * 删除账本
     *
     * @param application
     * @param p
     * @return
     */
    public static ResultHelper delAccountBook(GaiaApplication application, JC_AccountBook p) {
        DatabaseHelper helper = application.getDatabaseHelper();
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
