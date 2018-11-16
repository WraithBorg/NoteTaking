package com.zxu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.zxu.R;
import com.zxu.demo.AFunctionDisplayActivity;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChromeDatabase();

        setContentView(R.layout.activity_main);
        TextView noteOne = (TextView) findViewById(R.id.noteone_id);
        noteOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteOneMessage();
            }
        });
    }

    void noteOneMessage() {
        Intent intent = new Intent(this, AFunctionDisplayActivity.class);
        startActivity(intent);

    }
    void initChromeDatabase(){
        Stetho.initializeWithDefaults(this);
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
    }
}
