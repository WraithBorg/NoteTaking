package com.zxu.ui.record;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.zxu.R;
import com.zxu.model.JC_Account;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 选择账户
 */
public class RecordAddDialog {
    private Context context;
    private List<JC_Account> accounts;
    private JC_Account selectAccount;
    private String selectedName;
    public RecordAddDialog(Context context, List<JC_Account> accounts, String selectedName) {
        this.context = context;
        this.accounts = accounts;
        this.selectedName = selectedName;
    }

    public void create(DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        int defauleSelected = 0;// 默认选中
        String[] strs = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            strs[i] = accounts.get(i).getName();
            if (!StringUtils.isEmpty(selectedName) && selectedName.equals(strs[i])){
                defauleSelected = i;
            }
        }
        setSelectAccount(accounts.get(defauleSelected));
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.SelectAccounyDialogStyle)
                .setTitle("选择账户")
                .setSingleChoiceItems(strs, defauleSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setSelectAccount(accounts.get(i));
                    }
                })
                .setPositiveButton("确认", confirmListener)
                .setNegativeButton("取消", cancelListener)
                .create();
        alertDialog.show();
    }

    public JC_Account getSelectAccount() {
        return selectAccount;
    }

    public void setSelectAccount(JC_Account selectAccount) {
        this.selectAccount = selectAccount;
    }
}
