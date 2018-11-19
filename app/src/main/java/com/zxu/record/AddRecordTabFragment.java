package com.zxu.record;

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

import java.util.Random;

public class AddRecordTabFragment extends Fragment {
    public static final String TITLE_TAG = "tabTitle";

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
        TextView tv_selAccount = (TextView) view.findViewById(R.id.record_add_account_id);
        tv_selAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordAccountDialog accountDialog = new AddRecordAccountDialog(getContext(),JC_Account.getAccounts(),tv_selAccount.getText().toString());
                accountDialog.create(new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JC_Account account = accountDialog.getSelectAccount();
                        tv_selAccount.setText(account.getName());
                    }
                }, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });


        return view;
    }
}
