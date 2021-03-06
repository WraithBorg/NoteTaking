package com.zxu.ui.accountbooks;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_AccountBook;
import com.zxu.util.UtilTools;

import org.apache.commons.lang3.StringUtils;

public class EditAccountBookDialog extends DialogFragment implements EditAccountBookContract.View {
    private EditAccountBookContract.Presenter mPresenter;
    Button bt_save;
    ImageView iv_back;
    EditText et_accountName;
    JC_AccountBook accountBook;

    private OnDialogMissListener misslListener;

    @Override
    public void setPresenter(EditAccountBookContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    public interface OnDialogMissListener {
        void onDissmiss(boolean isRrefresh);
    }

    public EditAccountBookDialog() {
    }


    /**
     * 可以设置对话框风格和各种属性，但不能设置view，因为此时对话框未创建
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 利用style设置全屏
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    /**
     * 可以设置view相关
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.indexpage_accountbook_edit, null);
        bt_save = (Button) view.findViewById(R.id.accountbook_edit_save_btn_id);
        iv_back = (ImageView) view.findViewById(R.id.indexpage_edit_back_id);
        et_accountName = (EditText) view.findViewById(R.id.indexpage_edit_accountname_id);
        et_accountName.setText(accountBook.getName());

        initWidgets();
        return view;
    }

    /**
     * 初始化按钮事件
     */
    private void initWidgets() {
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountName = et_accountName.getText().toString();
                if (StringUtils.isEmpty(accountName)) {
                    UtilTools.showToast(getActivity().getApplicationContext(), "请输入账户名", 1000);
                    return;
                }
                accountBook.setName(accountName);
                mPresenter.editAccountBook(accountBook);
                closeDialog(getDialog(), true);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog(getDialog(), false);
                dismiss();
            }
        });
    }

    /**
     * 处理dialogFragment在onActivityCreated处理，因为setContentView是在onActivityCreated
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
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
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    /**
     * 添加监听器
     *
     * @param misslListener
     */
    public void setMisslListener(OnDialogMissListener misslListener) {
        this.misslListener = misslListener;
    }

    /**
     * @param dialog
     * @param b      false表示直接关闭窗口 不需要后续操作
     */
    private void closeDialog(Dialog dialog, boolean b) {
        if (misslListener != null) {
            misslListener.onDissmiss(b);
        }
        dialog.dismiss();
    }

    public void setAccountBook(JC_AccountBook accountBook) {
        this.accountBook = accountBook;
    }
}
