package com.zxu.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zxu.R;

/**
 * Demo显示入口
 */
public class FunctionDisplayActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.functiondisplay);
    }

    void showTimer(View view) {
        Intent intent = new Intent(this, TheTimerActivity.class);
        startActivity(intent);
    }
    void keySave(View view) {
        Intent intent = new Intent(this, KeySaveActivity.class);
        startActivity(intent);
    }
}
