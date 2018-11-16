package com.zxu.index.accountbooks;

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

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_AccountBook;
import com.zxu.util.UtilTools;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class AddAccountBookFragment extends DialogFragment implements AddAccountBookContract.View {
    private AddAccountBookContract.Presenter mPresenter;
    Button bt_save;
    EditText et_accountName;
    /**
     * 监听弹出窗是否被取消
     */
    private OnDialogCancelListener mCancelListener;

    public interface OnDialogCancelListener {
        void onDissmiss();
    }

    public AddAccountBookFragment() {
    }


    /**
     * 可以设置对话框风格和各种属性，但不能设置view，因为此时对话框为创建
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
        View view = inflater.inflate(R.layout.indexpage_accountbook_add, null);
        bt_save = (Button) view.findViewById(R.id.accountbook_add_save_btn_id);
        et_accountName = (EditText) view.findViewById(R.id.indexpage_add_accountname_id);
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
                JC_AccountBook b = new JC_AccountBook();
                b.setId(UUID.randomUUID().toString());
                b.setName(accountName);
                mPresenter.addAccountBook((GaiaApplication) getActivity().getApplication(), b);
                dismiss();
                // TODO 刷新列表

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
    public void setPresenter(AddAccountBookContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //监听miss 在DialogFragment里写一个回调接口，当Dialog触发onDismiss时触发
        super.onDismiss(dialog);
        if (mCancelListener != null) {
            mCancelListener.onDissmiss();
        }
    }

    /**
     * 添加监听器
     *
     * @param mCancelListener
     */
    public void setmCancelListener(OnDialogCancelListener mCancelListener) {
        this.mCancelListener = mCancelListener;
    }
}