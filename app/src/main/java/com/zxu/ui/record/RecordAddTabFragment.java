package com.zxu.ui.record;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Account;
import com.zxu.model.JC_Category;
import com.zxu.ui.category.CategorySelectDialog;
import com.zxu.ui.category.CategorySelectPresenter;

import java.util.Calendar;
import java.util.Date;

public class RecordAddTabFragment extends Fragment {
    public static final String TITLE_TAG = "tabTitle";
    TextView tv_selAccount,tv_selCategory,tv_selTime;

    public static RecordAddTabFragment newInstance(String tabTitle) {

        Bundle args = new Bundle();
        RecordAddTabFragment fragment = new RecordAddTabFragment();
        args.putString(TITLE_TAG, tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_add_fragment_tab, container, false);
        tv_selAccount = (TextView) view.findViewById(R.id.record_add_account_id);
        tv_selCategory = (TextView) view.findViewById(R.id.record_add_category_id);
        tv_selTime = (TextView) view.findViewById(R.id.record_add_time_id);
        initWidgets();
        return view;
    }

    /**
     * 初始化按钮式事件
     */
    void initWidgets() {
        // 选择账户类型
        tv_selAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountSelectDialog accountDialog = new AccountSelectDialog(getContext(), JC_Account.getAccounts(), tv_selAccount.getText().toString());
                accountDialog.create(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JC_Account account = accountDialog.getSelectAccount();
                        tv_selAccount.setText(account.getName());
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
        // 选择消费类型
        tv_selCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategorySelectDialog dialog = new CategorySelectDialog();
                CategorySelectPresenter presenter = new CategorySelectPresenter((GaiaApplication) getActivity().getApplication(), dialog);
                dialog.setPresenter(presenter);
                // 监听选择类别事件
                dialog.setOnSelectSmallTypeListener(new CategorySelectDialog.onSelectSmallTypeListener() {
                    @Override
                    public void selectOn(JC_Category category) {
                        tv_selCategory.setText(category.getName());
                    }
                });
                dialog.show(getActivity().getFragmentManager(), "android");
            }
        });
        // 选择时间
        tv_selTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int monthOfYear = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yyyy, int mm, int dd) {
                        String birthday = yyyy + "-" + (mm + 1) + "-" + dd;
                        Log.v("Info",birthday);
                    }
                }, year, monthOfYear, dayOfMonth);
                DatePicker datePicker = datePickerDialog.getDatePicker();
                Date taday = Calendar.getInstance().getTime();//当天

                datePickerDialog.show();
            }
        });
    }
}
