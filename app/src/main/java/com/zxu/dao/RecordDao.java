package com.zxu.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxu.annotation.DatabaseTable;
import com.zxu.application.GaiaApplication;
import com.zxu.helpers.ResultHelper;
import com.zxu.helpers.SQLiteHelper;
import com.zxu.model.JC_Record;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {
    private static String tableName;

    static {
        Annotation annotation = JC_Record.class.getAnnotation(DatabaseTable.class);
        tableName = ((DatabaseTable) annotation).tableName();
    }

    public RecordDao() {
    }

    public static void createTable(SQLiteDatabase database) {
        String createTable = "create table if not exists " + tableName + " (" +
                " id text primary key ," +
                " type text not null ," +
                " category text not null ," +
                " money text not null ," +
                " account text not null ," +
                " workTime text not null ," +
                " memo text not null ," +
                " createTime text not null ," +
                " userid text not null );";
        database.execSQL(createTable);
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
    private static JC_Record cursorToPerson(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex("id"));
        String type = cursor.getString(cursor.getColumnIndex("type"));
        String category = cursor.getString(cursor.getColumnIndex("category"));
        String money = cursor.getString(cursor.getColumnIndex("money"));
        String account = cursor.getString(cursor.getColumnIndex("account"));
        String workTime = cursor.getString(cursor.getColumnIndex("workTime"));
        String memo = cursor.getString(cursor.getColumnIndex("memo"));
        String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
        String userid = cursor.getString(cursor.getColumnIndex("userid"));

        JC_Record r = new JC_Record();
        r.setId(id);
        r.setType(type);
        r.setCategory(category);
        r.setMoney(money);
        r.setAccount(account);
        r.setWorkTime(workTime);
        r.setMemo(memo);
        r.setCreateTime(createTime);
        r.setUserId(userid);

        return r;
    }

    /**
     * 新增
     *
     * @param application
     * @param p
     * @return
     */
    public static ResultHelper addRecord(GaiaApplication application, JC_Record p) {
        SQLiteHelper helper = application.getSQLiteHelper();
        SQLiteDatabase database = helper.getWritableDatabase();

        ResultHelper res = new ResultHelper(true, "新增成功");
        try {
            String sql = "insert into " + tableName + " (id,type,category,money,account,workTime,memo,createTime,userId) values (" +
                    "'" + p.getId() + "'," +
                    "'" + p.getType() + "'," +
                    "'" + p.getCategory() + "'," +
                    "'" + p.getMoney() + "'," +
                    "'" + p.getAccount() + "'," +
                    "'" + p.getWorkTime() + "'," +
                    "'" + p.getMemo() + "'," +
                    "'" + p.getCreateTime() + "'," +
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
     * 修改
     *
     * @param application
     * @param p
     * @return
     */
    public static ResultHelper editRecord(GaiaApplication application, JC_Record p) {
        SQLiteHelper helper = application.getSQLiteHelper();
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", p.getId());
        values.put("type", p.getType());
        values.put("category", p.getCategory());
        values.put("money", p.getMoney());
        values.put("account", p.getAccount());
        values.put("workTime", p.getWorkTime());
        values.put("memo", p.getMemo());
        values.put("createTime", p.getCreateTime());
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
     * 获取
     *
     * @param application
     * @return
     */
    public static List<JC_Record> getAll(GaiaApplication application) {
        SQLiteHelper helper = application.getSQLiteHelper();
        SQLiteDatabase database = helper.open();
        List<JC_Record> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + tableName, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            JC_Record p = cursorToPerson(cursor);
            list.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        helper.close();
        return list;
    }

    /**
     * 删除
     *
     * @param application
     * @param p
     * @return
     */
    public static ResultHelper delRecord(GaiaApplication application, JC_Record p) {
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
