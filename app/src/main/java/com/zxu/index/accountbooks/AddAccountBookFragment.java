package com.zxu.index.accountbooks;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.zxu.R;

public class AddAccountBookFragment extends DialogFragment {
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
        return view;
    }

    /**
     * 处理dialogFragment在onActivityCreated处理，因为setContentView是在onActivityCreated
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }
}
