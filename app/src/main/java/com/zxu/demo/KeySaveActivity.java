package com.zxu.demo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.zxu.R;

/**
 * 数据、文件存取Demo
 */
public class KeySaveActivity extends Activity {
    private final String KEY_NAME = "MYKEY";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keysave);
    }
    public void btnWriteClick(View view){
        EditText etWrite = (EditText) findViewById(R.id.etWrite_id);
        SharedPreferences sharedPref= this.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_NAME, etWrite.getText().toString());
        editor.commit();
    }
    public void btnReadClick(View view){
        SharedPreferences preferences = getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        EditText etRead = (EditText) findViewById(R.id.etRead_id);
        etRead.setText(preferences.getString(KEY_NAME, "none"));//若没有值则显示none
    }
}
