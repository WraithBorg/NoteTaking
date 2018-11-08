package com.zxu.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxu.R;

/**
 * 计时器Demo
 */
public class TheTimer extends Activity {
    private TextView counter;
    private Button start;
    private Button stop;
    private boolean timerRunning;
    private long startedAt;
    private long lastStopped;
    private static long UPDATE_EVERY = 200;
    private Handler handler;
    private UpdateTimer updateTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thetimer);
        counter = (TextView) findViewById(R.id.thetimer_timer_id);
        start = (Button) findViewById(R.id.thetimer_start_button_id);
        stop = (Button) findViewById(R.id.thetimer_stop_button_id);
    }

    /**
     * 开始按钮点击事件
     *
     * @param view 开始按钮
     */
    void clickedStart(View view) {
        timerRunning = true;
        enableButtons();
        startedAt = System.currentTimeMillis();
        handler = new Handler();
        updateTimer = new UpdateTimer();
        handler.postDelayed(updateTimer, UPDATE_EVERY);
    }

    /**
     * @param view 停止按钮
     */
    void clickedStop(View view) {
        timerRunning = false;
        enableButtons();
        lastStopped = System.currentTimeMillis();
        handler.removeCallbacks(updateTimer);
        handler = null;
    }

    /**
     * 开始和停止按钮互斥
     */
    void enableButtons() {
        start.setEnabled(!timerRunning);
        stop.setEnabled(timerRunning);
    }

    /**
     * 显示时间
     */
    void setTimeDisplay() {
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        if (timerRunning) {
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }
        diff = timeNow - startedAt;
        // 没有负值的时间
        if (diff < 0) {
            diff = 0;
        }
        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        display = String.format("%d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);
        counter.setText(display);
    }

    /**
     * 单据线程显示计时器 防止ANR
     */
    class UpdateTimer implements Runnable {

        @Override
        public void run() {
            if (handler != null) {
                handler.postDelayed(this, UPDATE_EVERY);
                setTimeDisplay();
            }
        }
    }
}
