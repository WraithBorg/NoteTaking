package com.zxu.ui.person;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.entity.Person;
import com.zxu.helpers.SQLiteHelper;
import com.zxu.util.UtilTools;

import java.util.List;

/**
 * 编辑用户页面
 */
public class PersonEditActivity extends Activity {
    private EditText etName;
    private EditText etInfo;
    private int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_person_edit);

        etName = (EditText) findViewById(R.id.etName_id);
        etInfo = (EditText) findViewById(R.id.etInfo_id);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                _id = bundle.getInt("ID");
                etName.setText(bundle.getString("NAME") + String.valueOf(_id));
                etInfo.setText(bundle.getString("INFO"));
            }
        }

    }

    public void add(View view) {
        String name = etName.getText().toString();
        String info = etInfo.getText().toString();

        try {
            SQLiteHelper helper = ((GaiaApplication) getApplication()).getSQLiteHelper();
            SQLiteDatabase database = helper.getWritableDatabase();

            String sql = "insert into persons (name,info) values ('" + name + "','" + info + "')";
            database.execSQL(sql);
            UtilTools.showToast(getApplicationContext(), "新增成功", 1000);
        } catch (Exception e) {
            e.printStackTrace();
            UtilTools.showToast(getApplicationContext(), e.getMessage(), 1000);
        }


    }

    public void update(View view) {
        String name = etName.getText().toString();
        String info = etInfo.getText().toString();

        try {
            SQLiteHelper helper = ((GaiaApplication) getApplication()).getSQLiteHelper();
            SQLiteDatabase database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("_id", _id);
            values.put("name", name);
            values.put("info", info);

            if (database.update("persons", values, "_id=?", new String[]{String.valueOf(_id)}) > 0) {
                UtilTools.showToast(getApplicationContext(), "修改成功", 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            UtilTools.showToast(getApplicationContext(), "修改失败", 1000);
        }
    }

    /**
     * 批量新增事物
     */
    public void add(List<Person> persons) {
        SQLiteHelper helper = ((GaiaApplication) getApplication()).getSQLiteHelper();
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
