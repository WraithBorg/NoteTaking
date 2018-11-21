package com.zxu.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxu.application.GaiaApplication;
import com.zxu.demo.entity.Person;
import com.zxu.helpers.DatabaseHelper;
import com.zxu.helpers.ResultHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户dao
 */
public class PersonDao {
    /**
     * 创建表
     *
     * @param database
     */
    public static void createTable(SQLiteDatabase database) {
        String createTable = "create table if not exists persons (" +
                " _id integer primary key autoincrement," +
                "name text not null ," +
                "info text not null );";
        database.execSQL(createTable);
    }

    /**
     * 删除表
     *
     * @param database
     */
    public static void dropTable(SQLiteDatabase database) {
        String dropTable = "drop table if exists persons";
        database.execSQL(dropTable);

    }

    /**
     * 结果集转换
     *
     * @param cursor
     * @return
     */
    private static Person cursorToPerson(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String info = cursor.getString(cursor.getColumnIndex("info"));

        Person person = new Person();
        person.set_id(id);
        person.setName(name);
        person.setInfo(info);

        return person;
    }

    /**
     * 获取用户
     *
     * @param application
     * @return
     */
    public static List<Person> getAll(GaiaApplication application) {
        DatabaseHelper helper = application.getDatabaseHelper();
        SQLiteDatabase database = helper.open();
        List<Person> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from persons", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Person person = cursorToPerson(cursor);
            list.add(person);
            cursor.moveToNext();
        }
        cursor.close();
        helper.close();
        return list;
    }

    /**
     * 修改员工
     *
     * @param application
     * @param person
     * @return
     */
    public static ResultHelper editPerson(GaiaApplication application, Person person) {
        DatabaseHelper helper = application.getDatabaseHelper();
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_id", person.get_id());
        values.put("name", person.getName());
        values.put("info", person.getInfo());
        int updates;
        ResultHelper res = new ResultHelper(false, "修改失败");
        try {
            updates = database.update("persons", values, "_id=?", new String[]{String.valueOf(person.get_id())});
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
     * 新增用户
     *
     * @param application
     * @param p
     * @return
     */
    public static ResultHelper addPerson(GaiaApplication application, Person p) {
        DatabaseHelper helper = application.getDatabaseHelper();
        SQLiteDatabase database = helper.getWritableDatabase();

        ResultHelper res = new ResultHelper(true, "新增成功");
        try {
            String sql = "insert into persons (name,info) values ('" + p.getName() + "','" + p.getInfo() + "')";
            database.execSQL(sql);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResultHelper(false, "新增失败");
        }
        return res;
    }

    /**
     * 批量新增用户，事物
     */
    public void addPersons(GaiaApplication application, List<Person> persons) {
        DatabaseHelper helper = application.getDatabaseHelper();
        SQLiteDatabase database = helper.getWritableDatabase();
        database.beginTransaction();
        try {
            for (Person person : persons) {
                database.execSQL("INSERT INTO persons VALUES (null,?,?,?)", new Object[]{person.getName(), person.getInfo()});
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }
}
