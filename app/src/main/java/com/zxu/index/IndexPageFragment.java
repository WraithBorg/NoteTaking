package com.zxu.index;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_AccountBook;

import java.util.List;

/**
 * 实现view接口，接收indexPagePresenter回调 并 更新界面
 */
public class IndexPageFragment extends Fragment implements IndexPageContract.View {
    private TextView tv_accountName;
    private Button bt_editAccount;
    private Button bt_addAccount;
    private Button bt_getAccount;

    private IndexPageContract.Presenter mPresenter;

    public static IndexPageFragment newInstance() {
        return new IndexPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.indexpage_accounts, container, false);
        tv_accountName = (TextView) root.findViewById(R.id.account_name_id);
        bt_getAccount = (Button) root.findViewById(R.id.account_refresh_id);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt_getAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAccountBooks("");
            }
        });
    }

    @Override
    public void setPresenter(IndexPageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAccountBooks(List<JC_AccountBook> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                JC_AccountBook book = list.get(i);
                tv_accountName.setText(book.getName());

            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


}
