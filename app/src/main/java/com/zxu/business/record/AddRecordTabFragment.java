package com.zxu.business.record;

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
import com.zxu.model.JC_Account;
import com.zxu.util.UtilTools;

public class AddRecordTabFragment extends Fragment {
    public static final String TITLE_TAG = "tabTitle";
    TextView tv_selAccount,tv_selCategory;

    public static AddRecordTabFragment newInstance(String tabTitle) {

        Bundle args = new Bundle();
        AddRecordTabFragment fragment = new AddRecordTabFragment();
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
                AddRecordAccountDialog accountDialog = new AddRecordAccountDialog(getContext(), JC_Account.getAccounts(), tv_selAccount.getText().toString());
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
                SelectRecordCategoryDialog fragment = new SelectRecordCategoryDialog();
                fragment.setOnDialogListener(new SelectRecordCategoryDialog.OnDialogListener() {
                    @Override
                    public void onItemClick(String person) {
                        UtilTools.showToast(getActivity().getApplicationContext(), person, 1111);
                    }
                });
                fragment.show(getActivity().getFragmentManager(), "android");
            }
        });
    }
}
