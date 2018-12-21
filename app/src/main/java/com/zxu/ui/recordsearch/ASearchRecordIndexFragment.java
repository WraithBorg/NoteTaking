package com.zxu.ui.recordsearch;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_RecordSearchQuery;
import com.zxu.model.JC_RecordSearchResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ASearchRecordIndexFragment extends DialogFragment implements SearchRecordContract.View {

    private SearchRecordContract.Persenter mPresenter;
    private List<JC_RecordSearchResult> mResultList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //
        JC_RecordSearchQuery recordQuery = new JC_RecordSearchQuery();
        // view
        View view = inflater.inflate(R.layout.record_search_index, null);
        ImageView iv_back = view.findViewById(R.id.record_search_index_back_id);
        TextView tv_comfirm = view.findViewById(R.id.record_search_index_confirm_id);
        TextView tv_showTime = view.findViewById(R.id.record_search_index_show_time_id);
        TextView tv_showWatertype = view.findViewById(R.id.record_search_index_show_watertype_id);
        TextView tv_showAccounttype = view.findViewById(R.id.record_search_index_show_accounttype_id);
        EditText et_minMoney = view.findViewById(R.id.record_search_index_select_minMoney_id);
        EditText et_maxMoney = view.findViewById(R.id.record_search_index_select_maxMoney_id);
        EditText et_memo = view.findViewById(R.id.record_search_index_select_memo_id);
        LinearLayout ll_selTime = view.findViewById(R.id.record_search_index_select_time_id);
        LinearLayout ll_selWatertype = view.findViewById(R.id.record_search_index_select_watertype_id);
        LinearLayout ll_selAccounttype = view.findViewById(R.id.record_search_index_select_accounttype_id);

        // listener
        // back
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        // jump to select time
        ll_selTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectTime4SearchFragment fragment = new SelectTime4SearchFragment();
                fragment.show(getFragmentManager(), "Test");
                fragment.setOnCloseListener(new SelectTime4SearchFragment.OnCloseListener() {
                    @Override
                    public void close(String s) {
                        tv_showTime.setText(s);
                        recordQuery.setPeroid(s);
                    }
                });
            }
        });
        // jump to select water type
        ll_selWatertype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> checked = new HashSet<>();
                checked.add(recordQuery.getWaterType());
                SelectWaterType4SearchFragment fragment = new SelectWaterType4SearchFragment();
                fragment.setChecked(checked);
                fragment.show(getFragmentManager(), "1");
                fragment.setOnCloseListener(new SelectWaterType4SearchFragment.OnCloseListener() {
                    @Override
                    public void close(String str) {
                        tv_showWatertype.setText(str);
                        recordQuery.setWaterType(str);
                    }
                });
            }
        });
        // select account type
        ll_selAccounttype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> checked = new HashSet<>();
                checked.add(recordQuery.getAccountType());
                SelectAccountType4SearchFragment fragment = new SelectAccountType4SearchFragment();
                fragment.setChecked(checked);
                fragment.show(getFragmentManager(), "Text");
                fragment.setOnCloseListener(new SelectAccountType4SearchFragment.OnCloseListener() {
                    @Override
                    public void close(String str) {
                        tv_showAccounttype.setText(str);
                        recordQuery.setAccountType(str);
                    }
                });
            }
        });
        // confirm
        tv_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordQuery.setMinMoney(et_minMoney.getText().toString());
                recordQuery.setMaxMoney(et_maxMoney.getText().toString());
                recordQuery.setMemo(et_memo.getText().toString());
                mPresenter.getRecords(recordQuery);
                SearchResult4RecordFragment fragment = new SearchResult4RecordFragment();
                fragment.setResultList(mResultList);
                fragment.show(getFragmentManager(), "test");
            }
        });
        return view;
    }

    @Override
    public void setRecords(List<JC_RecordSearchResult> result) {
        this.mResultList = result;
    }

    @Override
    public void setPresenter(SearchRecordContract.Persenter presenter) {
        this.mPresenter = presenter;
    }
}
