package com.zxu.ui.report;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.zxu.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodayReportMainFragment extends DialogFragment implements TodayReportMainContract.View{

    private TodayReportMainContract.Presenter mPresenter;

    @Override
    public void showError() {

    }

    @Override
    public void setPresenter(TodayReportMainContract.Presenter presenter) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);//设置全屏
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // view
        View view = inflater.inflate(R.layout.report_today_main, null);
        TextView tv_topTime = (TextView) view.findViewById(R.id.report_today_main_top_time_id);//今日时间
        TextView tv_balance = (TextView) view.findViewById(R.id.report_today_main_balance_id);//结余
        TextView tv_income = (TextView) view.findViewById(R.id.report_today_main_income_id);//收入
        TextView tv_spending = (TextView) view.findViewById(R.id.report_today_main_spending_id);//支出
        ListView lv_details = (ListView) view.findViewById(R.id.report_today_main_detail_list_id);
        // assignment
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String nowTime = format.format(curDate);
        //
        tv_topTime.setText(nowTime);
        tv_balance.setText("999");
        tv_income.setText("1999");
        tv_spending.setText("1000");


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }
}
