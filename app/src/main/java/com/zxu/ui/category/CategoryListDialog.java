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
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Category;

import java.util.List;

public class CategoryListDialog extends DialogFragment implements CategoryListContract.View  {

    private CategoryListContract.Presenter mPresenter;
    private List<JC_Category> categoryList;
    /**
     * dialog 创建
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.category_list, null);
        ExpandableListView elv_category = (ExpandableListView) view.findViewById(R.id.category_list_id);
        ImageView iv_back = (ImageView) view.findViewById(R.id.category_list_back_id);
        ImageView iv_plus = (ImageView) view.findViewById(R.id.category_list_plus_id);
        // add
        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryAddBigDialog dialog = new CategoryAddBigDialog();
                CategoryAddBigPresenter presenter = new CategoryAddBigPresenter((GaiaApplication) getActivity().getApplication(), dialog);
                dialog.setPresenter(presenter);
                dialog.show(getActivity().getFragmentManager(), "android");
            }
        });
        // return
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // list
        mPresenter.getAll((GaiaApplication) getActivity().getApplication());
        List<JC_Category> list = getCategoryList();
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getActivity(), list);// TODO getActivity() getApplication() getApplicationContext() 区别
        elv_category.setAdapter(categoryListAdapter);
        // list event
        // return
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

    @Override
    public void setPresenter(CategoryListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setCategorys(List<JC_Category> categorys) {
        setCategoryList(categorys);
    }

    public List<JC_Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<JC_Category> categoryList) {
        this.categoryList = categoryList;
    }
}
