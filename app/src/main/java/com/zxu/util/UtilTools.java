package com.zxu.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 工具类
 */
public class UtilTools {
    /**
     * 提示
     *
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
        }, 0, cnt);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    /**
     * 格式化
     *
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

    /**
     * 获取当天开始时间和结束时间
     */
    public static String[] DayBeginAndEnd() {
        String[] str = new String[2];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day = format.format(new Date());
        str[0] = day + " 00:00";
        str[1] = day + " 23:59";
        return str;

    }

    /**
     * 获取本周开始日期和结束日期
     */
    public static String[] WeekBeginAndEnd() {
        String[] str = new String[2];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek()); // Sunday
        str[0] = format.format(calendar.getTime()) + " 00:00";
        //
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek() + 6);
        str[1] = format.format(calendar.getTime()) + " 23:59";
        return str;
    }

    /**
     * 获取本月开始日期和结束日期
     */
    public static String[] MonthBeginAndEnd() {
        String[] str = new String[2];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        str[0] = format.format(calendar.getTime()) + " 00:00";
        calendar.setTime(new Date());
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, -1);
        str[1] = format.format(calendar.getTime()) + " 23:59";
        return str;
    }

    /**
     * 获取本月开始日期和结束日期
     */
    public static String[] YearBeginAndEnd() {
        String[] str = new String[2];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.getTime();
        str[0] = format.format(calendar.getTime()) + " 00:00";
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        str[1] = format.format(calendar.getTime()) + " 23:59";
        return str;
    }
}
