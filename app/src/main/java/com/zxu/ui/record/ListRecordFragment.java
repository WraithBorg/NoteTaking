package com.zxu.ui.record;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Record;
import com.zxu.model.JC_RecordSum;
import com.zxu.ui.recordsearch.ASearchRecordIndexFragment;
import com.zxu.ui.recordsearch.SearchRecordPresenter;
import com.zxu.util.CodeConstant;
import com.zxu.util.CostEnum;
import com.zxu.util.UtilTools;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListRecordFragment extends DialogFragment implements ListRecordContract.View {

    private ListRecordContract.Presenter mPresenter;
    private List<JC_Record> recordList;
    private List<JC_RecordSum> recordSums4WeekOrMonth;
    // param
    private String mAccountBookId;//账本
    private String mPeriod;// 区间：月周日
    // view
    private ExpandableListView lv_mWeekdetails, lv_dayDetails;
    private ImageView iv_back, iv_search;
    private TextView tv_topTime, tv_balance, tv_income, tv_spending;

    @Override
    public void setPresenter(ListRecordContract.Presenter presenter) {
        mPresenter = presenter;
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
        View view = inflater.inflate(R.layout.records_main, null);
        iv_back = view.findViewById(R.id.report_today_main_back_id); //返回
        tv_topTime = view.findViewById(R.id.report_today_main_top_time_id);//今日时间
        tv_balance = view.findViewById(R.id.report_today_main_balance_id);//结余
        tv_income = view.findViewById(R.id.report_today_main_income_id);//收入
        tv_spending = view.findViewById(R.id.report_today_main_spending_id);//支出
        lv_mWeekdetails = view.findViewById(R.id.report_today_main_detail_list_month_week_id);//消费记录
        lv_dayDetails = view.findViewById(R.id.report_today_main_detail_list_day_id);//每天消费记录
        iv_search = view.findViewById(R.id.report_today_main_search_id);//搜索
        // assignment
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String nowTime = format.format(new Date());
        //
        if (CodeConstant.DAY.equals(mPeriod)) {
            lv_dayDetails.setVisibility(View.VISIBLE);
            mPresenter.getRecords(mAccountBookId, mPeriod);//自动刷新list
        } else if (CodeConstant.WEEK.equals(mPeriod)) {
            lv_mWeekdetails.setVisibility(View.VISIBLE);
            mPresenter.getRecordSumByWeek(mAccountBookId, mPeriod);
        } else if (CodeConstant.MONTH.equals(mPeriod)) {
            lv_mWeekdetails.setVisibility(View.VISIBLE);
            mPresenter.getRecordSumByMonth(mAccountBookId, mPeriod);
        }
        tv_topTime.setText(nowTime);
        // init widgets
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 每周每月记录点击时间 jump to edit page
        lv_mWeekdetails.setGroupIndicator(null);
        lv_mWeekdetails.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                JC_Record record = recordSums4WeekOrMonth.get(groupPosition).getRecords().get(childPosition);
                EditRecordDialog dialog = new EditRecordDialog();
                dialog.setRecord(record);
                RecordPresenter presenter = new RecordPresenter((GaiaApplication) (getActivity().getApplication()), dialog);
                dialog.setPresenter(presenter);
                dialog.show(getFragmentManager(), "Test");

                dialog.setDialogListener(new EditRecordDialog.DialogListener() {
                    @Override
                    public void onDismiss() {
                        if (CodeConstant.DAY.equals(mPeriod)) {
                            mPresenter.getRecords(mAccountBookId, mPeriod);
                        } else if (CodeConstant.WEEK.equals(mPeriod)) {
                            mPresenter.getRecordSumByWeek(mAccountBookId, mPeriod);
                        } else if (CodeConstant.MONTH.equals(mPeriod)) {
                            mPresenter.getRecordSumByMonth(mAccountBookId, mPeriod);
                        }
                    }
                });
                return false;
            }
        });
        // 每天消费记录点击事件
        lv_dayDetails.setGroupIndicator(null);
        lv_dayDetails.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                JC_Record record = recordList.get(groupPosition);
                EditRecordDialog dialog = new EditRecordDialog();
                dialog.setRecord(record);
                RecordPresenter presenter = new RecordPresenter((GaiaApplication) (getActivity().getApplication()), dialog);
                dialog.setPresenter(presenter);
                dialog.show(getFragmentManager(), "Test");

                dialog.setDialogListener(new EditRecordDialog.DialogListener() {
                    @Override
                    public void onDismiss() {
                        if (CodeConstant.DAY.equals(mPeriod)) {
                            mPresenter.getRecords(mAccountBookId, mPeriod);
                        } else if (CodeConstant.WEEK.equals(mPeriod)) {
                            mPresenter.getRecordSumByWeek(mAccountBookId, mPeriod);
                        } else if (CodeConstant.MONTH.equals(mPeriod)) {
                            mPresenter.getRecordSumByMonth(mAccountBookId, mPeriod);
                        }
                    }
                });
                return false;
            }
        });
        // jump to search index page
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ASearchRecordIndexFragment fragment = new ASearchRecordIndexFragment();
                fragment.setAccountBookId(mAccountBookId);
                SearchRecordPresenter presenter = new SearchRecordPresenter((GaiaApplication) getActivity().getApplication(), fragment);
                fragment.setPresenter(presenter);
                fragment.show(getFragmentManager(), "TExt");
            }
        });
        return view;
    }

    /**
     * 查询每天的记录
     */
    private void refreshAdapter4Day() {
        //
        ListRecordAdapterDay adapter = new ListRecordAdapterDay(recordList, getActivity().getApplication());
        lv_dayDetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // calculate
        BigDecimal inCome = BigDecimal.ZERO, spending = BigDecimal.ZERO, balance;
        for (JC_Record record : recordList) {
            if (CostEnum.INCOME.code().equals(record.getWaterType())) {
                inCome = inCome.add(new BigDecimal(record.getMoney()));
            } else if (CostEnum.SPEND.code().equals(record.getWaterType())) {
                spending = spending.add(new BigDecimal(record.getMoney()));
            }
        }
        balance = inCome.subtract(spending);
        //
        tv_balance.setText(UtilTools.format(balance));
        tv_income.setText(UtilTools.format(inCome));
        tv_spending.setText(UtilTools.format(spending));
    }

    /**
     * 查询 每周的记录并刷新
     */
    private void refreshAdapter4Week() {
        //
        ListRecordAdapter adapter = new ListRecordAdapter(recordSums4WeekOrMonth, getActivity().getApplication());
        lv_mWeekdetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // calculate
        BigDecimal inCome = BigDecimal.ZERO, spending = BigDecimal.ZERO, balance;
        for (JC_RecordSum sumWeek : recordSums4WeekOrMonth) {
            inCome = inCome.add(new BigDecimal(sumWeek.getInCome()));
            spending = spending.add(new BigDecimal(sumWeek.getSpend()));
        }
        balance = inCome.subtract(spending);
        //
        tv_balance.setText(UtilTools.format(balance));
        tv_income.setText(UtilTools.format(inCome));
        tv_spending.setText(UtilTools.format(spending));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置位置在底部
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        // 设置背景透明
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.vifrification);
    }

    @Override
    public void setRecords(List<JC_Record> records) {
        this.recordList = records;
        refreshAdapter4Day();
    }

    @Override
    public void setRecordSumByWeekOrMonth(List<JC_RecordSum> sums) {
        this.recordSums4WeekOrMonth = sums;
        refreshAdapter4Week();
    }

    // init param
    public void setAccountId(String accountId) {
        this.mAccountBookId = accountId;
    }

    public void setPeriod(String period) {
        this.mPeriod = period;
    }
}
