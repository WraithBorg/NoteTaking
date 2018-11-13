package com.zxu.index;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.adapter.AccountBooksAdapter;
import com.zxu.model.JC_AccountBook;
import com.zxu.util.UtilTools;

import java.util.List;

/**
 * 实现view接口，接收indexPagePresenter回调 并 更新界面
 */
public class IndexPageFragment extends Fragment implements IndexPageContract.View {
    private TextView tv_accountName;
    private Button bt_getAccount;
    private ListView lv_accounts;

    DrawerLayout mDrawerLayout;// DrawerLayout组件
    LinearLayout slipMenuView;

    private AccountBooksAdapter accountBooksAdapter;

    private IndexPageContract.Presenter mPresenter;

    public static IndexPageFragment newInstance() {
        return new IndexPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.indexpage_accountbooks, container, false);
        tv_accountName = (TextView) root.findViewById(R.id.account_name_id);
        bt_getAccount = (Button) root.findViewById(R.id.account_refresh_id);
        // 账本list
        lv_accounts = (ListView) root.findViewById(R.id.indexpage_accountbooks_id);
        accountBooksAdapter = new AccountBooksAdapter(getActivity().getApplicationContext(),null);//TODO
        lv_accounts.setAdapter(accountBooksAdapter);
        lv_accounts.setOnItemClickListener(new AcBooksItemClickListener());
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


    /**
     * 侧滑菜单 账本list
     */
    private class AcBooksItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UtilTools.showToast(getActivity().getApplicationContext(),"点击"+position,1505);
            // 关闭侧滑菜单
            lv_accounts.setItemChecked(position,true);//高亮选中item
            mDrawerLayout.closeDrawer(slipMenuView);

        }
    }

    public void transWidget(LinearLayout slipMenuView,DrawerLayout mDrawerLayout){
        this.mDrawerLayout = mDrawerLayout;
        this.slipMenuView = slipMenuView;
    }
}
