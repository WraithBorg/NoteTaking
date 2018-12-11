package com.zxu.ui.record;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Record;
import com.zxu.util.CostEnum;
import com.zxu.util.UtilTools;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListRecordFragment extends DialogFragment implements ListRecordContract.View {

    private ListRecordContract.Presenter mPresenter;
    private List<JC_Record> recordList;
    // param
    private String mAccountId;
    private String mPeriod;
    // view
    private ListView lv_details;
    private ImageView iv_back;
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
        iv_back = (ImageView) view.findViewById(R.id.report_today_main_back_id); //返回
        tv_topTime = (TextView) view.findViewById(R.id.report_today_main_top_time_id);//今日时间
        tv_balance = (TextView) view.findViewById(R.id.report_today_main_balance_id);//结余
        tv_income = (TextView) view.findViewById(R.id.report_today_main_income_id);//收入
        tv_spending = (TextView) view.findViewById(R.id.report_today_main_spending_id);//支出
        lv_details = (ListView) view.findViewById(R.id.report_today_main_detail_list_id);//消费记录
        // assignment
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String nowTime = format.format(new Date());
        //
        mPresenter.getRecords(mAccountId, mPeriod);//自动刷新list
        tv_topTime.setText(nowTime);
        // init widgets
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // jump to edit page
        lv_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JC_Record record = recordList.get(position);
                EditRecordDialog dialog = new EditRecordDialog();
                dialog.setRecord(record);
                RecordPresenter presenter = new RecordPresenter((GaiaApplication) (getActivity().getApplication()), dialog);
                dialog.setPresenter(presenter);
                dialog.show(getFragmentManager(), "Test");

                dialog.setDialogListener(new EditRecordDialog.DialogListener() {
                    @Override
                    public void onDismiss() {
                        mPresenter.getRecords(mAccountId, mPeriod);
                    }
                });
            }
        });

        return view;
    }

    /**
     * 局部刷新
     */
    private void refreshAdapter() {
        //
        ListRecordAdapter adapter = new ListRecordAdapter(recordList, getActivity().getApplication());
        lv_details.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //
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
        refreshAdapter();
    }

    // init param
    public void setAccountId(String accountId) {
        this.mAccountId = accountId;
    }

    public void setPeriod(String period) {
        this.mPeriod = period;
    }
}
