package com.zxu.util;

import android.content.Context;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 工具类
 */
public class UtilTools {
    public static void showToast(Context context, String s, final int cnt) {
        final Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0, cnt);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }
    public static String format(BigDecimal decimal) {
        return new DecimalFormat("0.##########").format(decimal);
    }
}
