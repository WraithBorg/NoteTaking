package com.zxu.ui.recordsearch;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxu.R;

public class SearchRecordIndexFragment extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置位置在底部
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        // 设置背景透明
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.vifrification);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // view
        View view = inflater.inflate(R.layout.record_search_index, null);
        ImageView iv_back = view.findViewById(R.id.record_search_index_back_id);
        LinearLayout ll_selTime = view.findViewById(R.id.record_search_index_select_time_id);
        TextView tv_showTime = view.findViewById(R.id.record_search_index_show_time_id);

        // listener
        // back
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        // jump to select time
        ll_selTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchRecordSelectTimeFragment fragment = new SearchRecordSelectTimeFragment();
                fragment.show(getFragmentManager(), "Test");
                fragment.setOnCloseListener(new SearchRecordSelectTimeFragment.OnCloseListener() {
                    @Override
                    public void close(String s) {
                        tv_showTime.setText(s);
                    }
                });
            }
        });
        return view;
    }
}
