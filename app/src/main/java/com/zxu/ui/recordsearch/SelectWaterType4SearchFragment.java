package com.zxu.ui.recordsearch;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zxu.R;
import com.zxu.util.CodeConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectWaterType4SearchFragment extends DialogFragment {
    private Set<String> checked;

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
        View view = inflater.inflate(R.layout.record_search_index_watertype, null);
        ListView lv_time = view.findViewById(R.id.record_search_index_watertype_list_id);

        List<String> waterTypeList = new ArrayList<>();
        waterTypeList.add("全选");
        waterTypeList.addAll(Arrays.asList(CodeConstant.WATERTYPE));
        SelectWaterType4SearchAdapter adapter = new SelectWaterType4SearchAdapter(getActivity(),checked, waterTypeList);
        lv_time.setAdapter(adapter);
        lv_time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = waterTypeList.get(position);
                dismiss();
                onCloseListener.close(s);
            }
        });
        return view;
    }

    //
    public OnCloseListener onCloseListener;
    interface  OnCloseListener{
        void close(String str);
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    //

    public void setChecked(Set<String> checked) {
        this.checked = checked;
    }
}
