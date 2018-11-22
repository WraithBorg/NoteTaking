package com.zxu.business.index.accountbooks;

import android.app.Fragment;
import android.content.DialogInterface;
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

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_AccountBook;
import com.zxu.util.UtilTools;

import java.util.List;

/**
 * 实现view接口，接收indexPagePresenter回调 并 更新界面
 */
public class AccountBookFragment extends Fragment implements AccountBookContract.View {
    private Button bt_getAccount;
    private Button bt_addAccount;
    private Button bt_editAccount;
    private Button bt_complete;
    private ListView lv_accounts;
    private LinearLayout ll_bottom;
    // 外部传递对象
    LinearLayout mainContent;   // 主页面内容
    DrawerLayout mDrawerLayout; // DrawerLayout组件
    LinearLayout slipMenuView;  // 滑动菜单view
    //
    private AccountBookAdapter accountBooksAdapter;
    private AccountBookContract.Presenter mPresenter;
    private AddAccountBookDialog addAccountBookDialogFragment = new AddAccountBookDialog();

    //
    public static AccountBookFragment newInstance() {
        return new AccountBookFragment();
    }

    /**
     * 创建view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.indexpage_accountbooks, container, false);
        bt_getAccount = (Button) root.findViewById(R.id.accountbook_refresh_id);
        bt_addAccount = (Button) root.findViewById(R.id.accountbook_add_id);
        bt_editAccount = (Button) root.findViewById(R.id.accountbook_edit_id);
        bt_complete = (Button) root.findViewById(R.id.accountbook_complete_id);
        ll_bottom = (LinearLayout) root.findViewById(R.id.indexpage_accountbooks_bottom_id);
        lv_accounts = (ListView) root.findViewById(R.id.indexpage_accountbooks_id);
        return root;
    }

    /**
     * activity创建后 view绑定事件
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getAccountBooks();
        lv_accounts.setAdapter(accountBooksAdapter);
        lv_accounts.setOnItemClickListener(new AcBooksItemClickListener());
        bt_getAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAccountBooks();
            }
        });
        bt_addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccountBookDialogFragment = new AddAccountBookDialog();
                AddAccountBookPresenter addAccountBookPresenter
                        = new AddAccountBookPresenter((GaiaApplication) getActivity().getApplication(), addAccountBookDialogFragment);
                addAccountBookDialogFragment.setPresenter(addAccountBookPresenter);
                addAccountBookDialogFragment.show(getFragmentManager(), "WHo is W");
                addAccountBookDialogFragment.setMisslListener(new AddAccountBookDialog.OnDialogMissListener() {
                    @Override
                    public void onDissmiss(boolean isRrefresh) {
                        if (isRrefresh) {
                            mPresenter.getAccountBooks();
                        }
                    }
                });
            }
        });
        bt_editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAccountBooks4EDIT();
//                ll_bottom.setVisibility(View.GONE);
//                bt_complete.setVisibility(View.VISIBLE);
            }
        });
        bt_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAccountBooks();
//                ll_bottom.setVisibility(View.VISIBLE);
//                bt_complete.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void setPresenter(AccountBookContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 刷新账本
     *
     * @param list
     */
    @Override
    public void setAccountBooks(List<JC_AccountBook> list) {
        //
        ll_bottom.setVisibility(View.VISIBLE);
        bt_complete.setVisibility(View.GONE);
        //
        accountBooksAdapter = new AccountBookAdapter(getActivity().getApplicationContext(), list, false);
        lv_accounts.setAdapter(accountBooksAdapter);
        accountBooksAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新，编辑样式
     *
     * @param list
     */
    @Override
    public void setAccountBooks4EDIT(List<JC_AccountBook> list) {
        //
        ll_bottom.setVisibility(View.GONE);
        bt_complete.setVisibility(View.VISIBLE);
        //
        accountBooksAdapter = new AccountBookAdapter(getActivity().getApplicationContext(), list, true);
        lv_accounts.setAdapter(accountBooksAdapter);
        accountBooksAdapter.notifyDataSetChanged();

        // 删除接口调用
        accountBooksAdapter.setOnDeleteItem(new AccountBookAdapter.OnDeleteItem() {
            @Override
            public void deleteClick(JC_AccountBook item) {
                // 弹窗确认
                DelAccountBookDialog delDialog = new DelAccountBookDialog(getActivity());
                delDialog.create(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delAccountBook(item);
                        UtilTools.showToast(getActivity().getApplicationContext(), "删除成功", 1000);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
        });
        // 编辑接口调用
        accountBooksAdapter.setOnEditItem(new AccountBookAdapter.OnEditItem() {
            @Override
            public void editClick(JC_AccountBook item) {
                EditAccountBookDialog dialog = new EditAccountBookDialog();
                EditAccountBookPresenter presenter = new EditAccountBookPresenter((GaiaApplication) getActivity().getApplication(), dialog);
                dialog.setAccountBook(item);
                dialog.setPresenter(presenter);
                dialog.show(getFragmentManager(), "");
                dialog.setMisslListener(new EditAccountBookDialog.OnDialogMissListener() {
                    @Override
                    public void onDissmiss(boolean isRrefresh) {
                        if (isRrefresh) {
                            mPresenter.getAccountBooks();
                        }
                    }
                });
            }
        });
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
            UtilTools.showToast(getActivity().getApplicationContext(), "点击" + position, 1505);
            // 关闭侧滑菜单
            lv_accounts.setItemChecked(position, true);//高亮选中item
            mDrawerLayout.closeDrawer(slipMenuView);
            LinearLayout lv_topcard = (LinearLayout) mainContent.findViewById(R.id.indexpage_topcard_id);
            lv_topcard.setBackgroundColor(getResources().getColor(R.color.app_red));
        }
    }

    /**
     * 传递额外对象
     *
     * @param slipMenuView  滑动菜单view
     * @param mDrawerLayout 滑动菜单组件
     * @param mainContent
     */
    public void transWidget(LinearLayout slipMenuView, DrawerLayout mDrawerLayout,
                            LinearLayout mainContent) {
        this.mDrawerLayout = mDrawerLayout;
        this.slipMenuView = slipMenuView;
        this.mainContent = mainContent;
    }
}