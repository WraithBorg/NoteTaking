package com.zxu.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.util.Constant;

/**
 * Demo显示入口
 */
public class AFunctionDisplayActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_afunctiondisplay);
    }

    void showTimer(View view) {
        Intent intent = new Intent(this, TheTimerActivity.class);
        startActivity(intent);
    }

    void keySave(View view) {
        Intent intent = new Intent(this, KeySaveActivity.class);
        startActivity(intent);
    }

    void storeTheData(View view) {
        Intent intent = new Intent(this, StoreTheDataActivity.class);
        startActivity(intent);
    }

    void databaseDemo(View view) {
        Intent intent = new Intent(this, PersonActivity.class);
        startActivity(intent);
    }

    void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
        startActivityForResult(intent, Constant.LoginRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String name = bundle.getString(Constant.NAME, "");
            String pwd = bundle.getString(Constant.PASSWORD, "");
            String str = "用户名：" + name + ";密码：" + pwd;
            TextView tv = (TextView) findViewById(R.id.tvLogin_id);
            tv.setText(str);
        }


    }
}
