package com.zxu.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private int _id;
    private String name;
    private String info;

    public static void create(SQLiteDatabase database) {
        String createTable = "create table if not exists persons (" +
                " _id integer primary key autoincrement," +
                "name text not null ," +
                "info text not null );";
        database.execSQL(createTable);
    }

    public static void drop(SQLiteDatabase database) {
        String dropTable = "drop table if exists persons";
        database.execSQL(dropTable);

    }

    public static List<Person> getAll(SQLiteDatabase database) {
        List<Person> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from persons", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Person person = cursorToPerson(cursor);
            list.add(person);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    private static Person cursorToPerson(Cursor cursor) {
        Person person = new Person();
        person._id = cursor.getInt(cursor.getColumnIndex("_id"));
        person.name = cursor.getString(cursor.getColumnIndex("name"));
        person.info = cursor.getString(cursor.getColumnIndex("info"));
        return person;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
