package com.zxu.ui.record;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Account;
import com.zxu.model.JC_Category;
import com.zxu.model.JC_Record;
import com.zxu.ui.category.CategorySelectDialog;
import com.zxu.ui.category.CategorySelectPresenter;
import com.zxu.util.Constant;
import com.zxu.util.UtilTools;
import com.zxu.widget.CustomDatePicker;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class RecordAddFragment extends Fragment implements RecordAddContract.View {
    TextView tv_selAccount, tv_selCategory, tv_selTime, tv_money, tv_memo;
    //
    private String titleName;
    private String accountBookID;

    // 日期控件
    private CustomDatePicker customDatePicker2;
    //
    private RecordAddContract.Presenter mPresenter;

    public RecordAddFragment() {
    }

    /**
     * Fragment
     */
    public static RecordAddFragment newInstance(String tabTitle,String accountBookID) {

        Bundle args = new Bundle();
        args.putString(Constant.TITLE_TAG, tabTitle);
        RecordAddFragment fragment = new RecordAddFragment();
        fragment.setArguments(args);
        fragment.setTitleName(tabTitle);
        fragment.setAccountBookID(accountBookID);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_add_fragment_tab, container, false);
        tv_selAccount = (TextView) view.findViewById(R.id.record_add_account_id);
        tv_selCategory = (TextView) view.findViewById(R.id.record_add_category_id);
        tv_selTime = (TextView) view.findViewById(R.id.record_add_time_id);
        tv_money = (TextView) view.findViewById(R.id.record_add_money_id);
        tv_memo = (TextView) view.findViewById(R.id.record_add_memo_id);
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
        // 初始化时间控件
        initDatePicker();
        // 选择时间
        tv_selTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(tv_selTime.getText().toString());
            }
        });

        ((RecordAddActivity) getActivity()).setOnCompleteClickListener(new RecordAddActivity.OnCompleteClickListener() {
            @Override
            public void onClickComplete() {
                getActivity().onBackPressed();
            }
        });

    }

    /**
     * 初始化日期控件
     */
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tv_selTime.setText(now);

        customDatePicker2 = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_selTime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    /**
     * save data TODO
     */
    ResultHelper saveData() {
        // get
        String accountText = tv_selAccount.getText().toString();
        String categoryText = tv_selCategory.getText().toString();
        String timeText = tv_selTime.getText().toString();
        String moneyText = tv_money.getText().toString();
        String memoText = tv_memo.getText().toString();
        //validate
        if (StringUtils.isEmpty(accountText)
                || StringUtils.isEmpty(categoryText)
                || StringUtils.isEmpty(timeText)
                || StringUtils.isEmpty(moneyText)
                ) {
            UtilTools.showToast(getActivity().getApplicationContext(), "账户类型，消费类型，金额，日期不能为空", 1111);
            return new ResultHelper(false);
        }

        // save
        JC_Record record = new JC_Record();
        record.setId(UUID.randomUUID().toString());
        record.setMoney(moneyText);
        record.setCreateTime((new Date()).toString());
        record.setWorkTime(timeText);
        record.setCategory(categoryText);
        record.setAccount(accountText);
        record.setType("0");
        record.setMemo(memoText);
        record.setAccount(getAccountBookID());
        mPresenter.addRecord(record);

        return new ResultHelper(true);
    }

    /********** setter and getter *********/
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setAccountBookID(String accountBookID) {
        this.accountBookID = accountBookID;
    }

    public String getAccountBookID() {
        return accountBookID;
    }

    @Override
    public void setPresenter(RecordAddContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
