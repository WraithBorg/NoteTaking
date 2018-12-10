package com.zxu.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 工具类
 */
public class UtilTools {
    /**
     * 提示
     * @param context
     * @param s
     * @param cnt
     */
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

    /**
     * 格式化
     * @param decimal
     * @return
     */
    public static String format(BigDecimal decimal) {
        return new DecimalFormat("0.##########").format(decimal);
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }
}
