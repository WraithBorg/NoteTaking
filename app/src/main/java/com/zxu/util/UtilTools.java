package com.zxu.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.zxu.model.JC_MonthPeriod;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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

    /*
   * 1周,开始日期：2018-12-1,结束日期：2018-12-2,时间段12.1-12.2
   2018-12-01
   2018-12-02
   2周,开始日期：2018-12-3,结束日期：2018-12-9,时间段12.3-12.9
   2018-12-03
   2018-12-04*/
    public static List<JC_MonthPeriod> printfWeeks(String date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<JC_MonthPeriod> monthPeriods = new ArrayList<>();
        JC_MonthPeriod mPeriod;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date date1 = dateFormat.parse(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date1);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int count = 0;
        String startDate, endDate;//2018-12-31
        String monthDay = date.split("-")[1];
        String startDay, endDay;//12.31

        List<Date> dateList = new ArrayList<>();//每周日期数组
        List<String> weekDays = new ArrayList<>();//当前星期几
        Calendar cal4Week = new GregorianCalendar();
        String[] bigNum = new String[]{"","日","一","二","三","四","五","六"};
        for (int i = 1; i <= days; i++) {
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date2 = dateFormat1.parse(date + "-" + i);
            dateList.add(date2);
            //
            cal4Week.setTime(date2);
            int dayOfWeek = cal4Week.get(Calendar.DAY_OF_WEEK);
            String weekDay = "周" + bigNum[dayOfWeek];
            weekDays.add(weekDay);
            //
            calendar.clear();
            calendar.setTime(date2);
            int k = new Integer(calendar.get(Calendar.DAY_OF_WEEK));
            if (k == 1) {// 若当天是周日
                count++;
                if (i - 6 <= 1) {
                    startDate = date + "-" + 1;
                    startDay = monthDay + "." + 1;
                } else {
                    startDate = date + "-" + (i - 6);
                    startDay = monthDay + "." + (i - 6);
                }
                endDate = date + "-" + i;
                endDay = monthDay + "." + i;
                //
                mPeriod = new JC_MonthPeriod();
                mPeriod.setWeek(count + "周");
                mPeriod.setStart(format.format(format.parse(startDate)));
                mPeriod.setEnd(format.format(format.parse(endDate)));
                mPeriod.setPeriod(startDay + "-" + endDay);
                mPeriod.setDays(dateList);
                mPeriod.setWeekDay(weekDays);
                monthPeriods.add(mPeriod);
                //
                dateList = new ArrayList<>();
                weekDays = new ArrayList<>();
            } else if (i == days) {// 若是本月最后一天，且不是周日
                count++;
                startDate = date + "-" + (i - k + 2);
                endDate = date + "-" + i;
                startDay = monthDay + "." + (i - k + 2);
                endDay = monthDay + "." + i;
                //
                mPeriod = new JC_MonthPeriod();
                mPeriod.setWeek(count + "周");
                mPeriod.setStart(format.format(format.parse(startDate)));
                mPeriod.setEnd(format.format(format.parse(endDate)));
                mPeriod.setPeriod(startDay + "-" + endDay);
                mPeriod.setDays(dateList);
                mPeriod.setWeekDay(weekDays);
                monthPeriods.add(mPeriod);
                //
                dateList = new ArrayList<>();
                weekDays = new ArrayList<>();
            }
        }
        return monthPeriods;
        /* System.out.println("*******************************************");
        List<MonthPeriod> list = printfWeeks("2018-12");
        for (int i = 0; i < list.size(); i++) {
            MonthPeriod strings = list.get(i);
            System.out.println(strings.getWeek()
                    + ",开始日期：" + strings.getStart()
                    + ",结束日期：" + strings.getEnd()
                    + ",时间段" + strings.getPeriod());
            List<String> days = strings.getWeekDay();
            List<Date> dates = strings.getDays();
            for (int j = 0;j<days.size();j++) {
                System.out.println(days.get(j)+"||"+dates.get(j));
            }
        }
        System.out.println("*******************************************");*/
    }
}
