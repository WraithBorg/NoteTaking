package com.zxu.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.zxu.R;
import com.zxu.util.Constant;

/**
 * 登陆，演示activity之间传递参数和接收参数
 */
public class LoginActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_login);
    }
    public void btnLoginClick(View view){
        // 获取用户名密码
        EditText etName = (EditText) findViewById(R.id.txtName_id);
        EditText etPws = (EditText) findViewById(R.id.txtPassword_id);
        String name = etName.getText().toString().trim();
        String pwd = etPws.getText().toString().trim();
        // 返回调用者
        Intent intent = new Intent();
        intent.putExtra(Constant.NAME, name);
        intent.putExtra(Constant.PASSWORD, pwd);
        setResult(Activity.RESULT_OK, intent);//结果码Activity.RESULT_OK
        finish();//关闭自己
    }
}
