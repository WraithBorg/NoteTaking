package com.zxu.ui.report;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_Record;
import com.zxu.util.CostEnum;
import com.zxu.util.UtilTools;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportMainFragment extends DialogFragment implements ReportMainContract.View {

    private ReportMainContract.Presenter mPresenter;
    private List<JC_Record> recordList;
    // param
    private String mAccountId;
    private String mPeriod;

    @Override
    public void setPresenter(ReportMainContract.Presenter presenter) {
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
        View view = inflater.inflate(R.layout.report_main, null);
        ImageView iv_back = (ImageView) view.findViewById(R.id.report_today_main_back_id); //返回
        TextView tv_topTime = (TextView) view.findViewById(R.id.report_today_main_top_time_id);//今日时间
        TextView tv_balance = (TextView) view.findViewById(R.id.report_today_main_balance_id);//结余
        TextView tv_income = (TextView) view.findViewById(R.id.report_today_main_income_id);//收入
        TextView tv_spending = (TextView) view.findViewById(R.id.report_today_main_spending_id);//支出
        ListView lv_details = (ListView) view.findViewById(R.id.report_today_main_detail_list_id);//消费记录
        // assignment
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        String nowTime = format.format(curDate);
        //
        mPresenter.getRecords(mAccountId,mPeriod);
        ReportMainAdapter adapter = new ReportMainAdapter(recordList, getActivity().getApplication());
        // calculate
        BigDecimal inCome = BigDecimal.ZERO, spending = BigDecimal.ZERO, balance;
        for (JC_Record record : recordList) {
            if (CostEnum.INCOME.code().equals(record.getType())) {
                inCome = inCome.add(new BigDecimal(record.getMoney()));
            } else if (CostEnum.SPEND.code().equals(record.getType())) {
                spending = spending.add(new BigDecimal(record.getMoney()));
            }
        }
        balance = inCome.subtract(spending);
        //
        tv_topTime.setText(nowTime);
        tv_balance.setText(UtilTools.format(balance));
        tv_income.setText(UtilTools.format(inCome));
        tv_spending.setText(UtilTools.format(spending));
        //
        lv_details.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // init widgets
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setRecords(List<JC_Record> records) {
        this.recordList = records;
    }
    // init param
    public void setAccountId(String accountId) {
        this.mAccountId = accountId;
    }

    public void setPeriod(String period) {
        this.mPeriod = period;
    }
}
