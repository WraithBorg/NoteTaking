package com.zxu.ui.category;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.zxu.R;
import com.zxu.model.JC_Category;
import com.zxu.ui.category.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryBookFragment extends DialogFragment {
    private Context mContext;

    /**
     * dialog 创建
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = getActivity();

        View view = inflater.inflate(R.layout.category_list, null);
        ExpandableListView elv_category = (ExpandableListView) view.findViewById(R.id.category_list_id);
        ImageView iv_back = (ImageView) view.findViewById(R.id.category_list_back_id);
        ImageView iv_plus = (ImageView) view.findViewById(R.id.category_list_plus_id);
        //
        List<JC_Category> big = new ArrayList<>();
        List<JC_Category> small1 = new ArrayList<>();
        List<JC_Category> small2 = new ArrayList<>();

        JC_Category a = new JC_Category(UUID.randomUUID().toString(),"大类AAA");
        JC_Category b = new JC_Category(UUID.randomUUID().toString(),"大类BBB");
        JC_Category c = new JC_Category(UUID.randomUUID().toString(),"小类cc");
        JC_Category d = new JC_Category(UUID.randomUUID().toString(),"小类dd");
        JC_Category e = new JC_Category(UUID.randomUUID().toString(),"小类ee");
        JC_Category f = new JC_Category(UUID.randomUUID().toString(),"小类ff");
        JC_Category g = new JC_Category(UUID.randomUUID().toString(),"小类gg");

        small1.add(c);
        small1.add(d);
        small2.add(e);
        small2.add(f);
        small2.add(g);
        a.setChilds(small1);
        b.setChilds(small2);
        big.add(a);
        big.add(b);

        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity().getApplication(),big);//getApplication() getApplicationContext()
        elv_category.setAdapter(categoryAdapter);

        return view;
    }

    /**
     * dialog开始
     */
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
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
