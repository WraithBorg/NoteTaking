package com.zxu.ui.person;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.dao.PersonDao;
import com.zxu.entity.Person;
import com.zxu.helpers.ResultHelper;
import com.zxu.helpers.SQLiteHelper;
import com.zxu.util.Constant;
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

    /**
     * 新增用户
     *
     * @param view
     */
    public void add(View view) {
        SQLiteHelper helper = ((GaiaApplication) getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.getWritableDatabase();
        Person p = new Person();
        p.setName(etName.getText().toString());
        p.setInfo(etInfo.getText().toString());
        ResultHelper res = PersonDao.addPerson(database, p);
        UtilTools.showToast(getApplicationContext(), res.getMessage(), Constant.MSGWATITIME);
    }

    /**
     * 更新用户
     *
     * @param view
     */
    public void update(View view) {
        String name = etName.getText().toString();
        String info = etInfo.getText().toString();
        SQLiteHelper helper = ((GaiaApplication) getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.getWritableDatabase();
        Person p = new Person();
        p.set_id(_id);
        p.setName(name);
        p.setInfo(info);
        ResultHelper res = PersonDao.editPerson(database, p);
        UtilTools.showToast(getApplicationContext(), res.getMessage(), Constant.MSGWATITIME);
    }
}
