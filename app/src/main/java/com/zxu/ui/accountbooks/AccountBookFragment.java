package com.zxu.ui.accountbooks;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_AccountBook;
import com.zxu.ui.record.RecordAddActivity;
import com.zxu.ui.report.ReportMainFragment;
import com.zxu.ui.report.ReportMainPresenter;
import com.zxu.util.Constant;
import com.zxu.util.UtilTools;

import org.apache.commons.lang3.StringUtils;

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
        // 刷新
        mPresenter.getAccountBooks();
        // 刷新
        bt_getAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAccountBooks();
            }
        });
        // 新增
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
        // 编辑账本
        bt_editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAccountBooks4EDIT();
            }
        });
        // 完成按钮
        bt_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAccountBooks();
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
        refreshAdapter(list, false);

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
        refreshAdapter(list, true);
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
     * 侧滑菜单 账本list item 点击事件
     */
    private class AcBooksItemClickListener implements ListView.OnItemClickListener {
        private List<JC_AccountBook> accountBooks;

        public AcBooksItemClickListener(List<JC_AccountBook> list) {
            this.accountBooks = list;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            JC_AccountBook accountBook = accountBooks.get(position);
            UtilTools.showToast(getActivity().getApplicationContext(), "点击" + accountBook.getName(), 1505);

            lv_accounts.setItemChecked(position, true);//高亮选中item
            mDrawerLayout.closeDrawer(slipMenuView);// 关闭侧滑菜单
            // 主界面UI更新
            // 账本名称
            TextView tv_accountBookId = (TextView) getActivity().findViewById(R.id.indexpage_accountbook_id_id);
            TextView tv_accountBookName = (TextView) getActivity().findViewById(R.id.indexpage_accountbook_name_id);
            tv_accountBookId.setText(accountBook.getId());
            tv_accountBookName.setText(accountBook.getName());
            // 背景色
            LinearLayout lv_topcard = (LinearLayout) mainContent.findViewById(R.id.indexpage_topcard_id);
            if (!StringUtils.isEmpty(accountBook.getImgUrl())) {
                lv_topcard.setBackgroundResource(Integer.parseInt(accountBook.getImgUrl()));
            }
            // 记一笔
            TextView tv_noteOne = (TextView) mainContent.findViewById(R.id.noteone_id);
            tv_noteOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 传递账户ID
                    Bundle args = new Bundle();
                    args.putString(Constant.AccountBookID, tv_accountBookId.getText().toString());
                    Intent intent = new Intent(getActivity(), RecordAddActivity.class);
                    intent.putExtras(args);
                    startActivity(intent);
                }
            });
            // 今天
            TextView tv_today = (TextView) getActivity().findViewById(R.id.indexpage_today_id);
            tv_today.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReportMainFragment fragment = new ReportMainFragment();
                    fragment.setAccountId(tv_accountBookId.getText().toString());
                    ReportMainPresenter presenter = new ReportMainPresenter((GaiaApplication) (getActivity().getApplication()), fragment);
                    fragment.setPresenter(presenter);
                    fragment.show(getFragmentManager(), " Test ");
                }
            });
            // 本周
            TextView tv_week = (TextView) getActivity().findViewById(R.id.indexpage_week_id);
            // 本年
            TextView tv_year = (TextView) getActivity().findViewById(R.id.indexpage_year_id);
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

    /**
     *
     */
    private void refreshAdapter(List<JC_AccountBook> list, boolean isEdit) {
        accountBooksAdapter = new AccountBookAdapter(getActivity().getApplicationContext(), list, isEdit);
        lv_accounts.setAdapter(accountBooksAdapter);
        lv_accounts.setOnItemClickListener(new AcBooksItemClickListener(accountBooksAdapter.getAccountBooks()));
        // list view 触发第一个item click 事件
        lv_accounts.performItemClick(lv_accounts.getAdapter().getView(0, null, null),
                0, lv_accounts.getAdapter().getItemId(0));
        accountBooksAdapter.notifyDataSetChanged();
    }

}
