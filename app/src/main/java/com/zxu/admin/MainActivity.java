package com.zxu.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.demo.FunctionDisplayActivity;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = new Intent(this, FunctionDisplayActivity.class);
        startActivity(intent);

    }
}
