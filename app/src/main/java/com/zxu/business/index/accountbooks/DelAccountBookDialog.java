package com.zxu.business.index.accountbooks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.LinearLayout;

import com.zxu.R;
import com.zxu.util.DensityUtil;

public class DelAccountBookDialog {
    private Context context;

    public DelAccountBookDialog(Context context) {
        // getActivity():不能使用getApplicationContext()获得的Context,而必须使用Activity,因为只有一个Activity才能添加一个窗体
        this.context = context;
    }

    public void create(DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDelAccountDialogStyle);
        builder.setTitle("安全提示");
        builder.setMessage("是否删除账本");
        builder.setCancelable(true);
        builder.setPositiveButton("确认", confirmListener);
        builder.setNegativeButton("取消", cancelListener);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(DensityUtil.dp2px(context, 200), DensityUtil.dp2px(context, 150));
    }
}
