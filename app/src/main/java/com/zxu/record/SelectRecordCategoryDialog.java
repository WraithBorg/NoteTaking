package com.zxu.record;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.util.DensityUtil;

public class SelectRecordCategoryDialog extends DialogFragment {
    private OnDialogListener onDialogListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.demo_bottom_dialog, null);
        TextView tv_01 = (TextView) view.findViewById(R.id.tv1);
        tv_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogListener.onItemClick("person-1");
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置位置在底部
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DensityUtil.dp2px(getActivity().getApplication(),330);
        window.setAttributes(params);
        // 设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public interface OnDialogListener {
        void onItemClick(String person);
    }

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }
}
