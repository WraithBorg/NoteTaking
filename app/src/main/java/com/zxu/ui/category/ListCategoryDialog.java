package com.zxu.ui.category;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
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
import com.zxu.util.CodeConstant;

import java.util.List;

public class ListCategoryDialog extends DialogFragment implements CategoryContract.View  {

    private CategoryContract.Presenter mPresenter;
    private List<JC_Category> categoryList;
    // java
    private String mType;// TODO
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
                AddCategoryBigDialog dialog = new AddCategoryBigDialog();
                AddCategoryBigPresenter presenter = new AddCategoryBigPresenter((GaiaApplication) getActivity().getApplication(), dialog);
                dialog.setPresenter(presenter);
                dialog.show(getActivity().getFragmentManager(), "android");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, CodeConstant.DIALOGWAITTIME);
            }
        });
        // return
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, CodeConstant.DIALOGWAITTIME);
            }
        });

        // list
        mPresenter.getCategorys(true,mType);
        List<JC_Category> list = getCategoryList();
        ListCategoryAdapter listCategoryAdapter = new ListCategoryAdapter(getActivity(), list);// TODO getActivity() getApplication() getApplicationContext() 区别
        elv_category.setAdapter(listCategoryAdapter);
        // list event
        // 大类点击事件
        elv_category.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                JC_Category category = categoryList.get(groupPosition);
                if (category.getId().equals(CodeConstant.ADDONETYPE)){

                    AddCategoryBigDialog dialog = new AddCategoryBigDialog();
                    AddCategoryBigPresenter presenter = new AddCategoryBigPresenter((GaiaApplication) getActivity().getApplication(), dialog);
                    dialog.setPresenter(presenter);
                    dialog.show(getActivity().getFragmentManager(), "android");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, CodeConstant.DIALOGWAITTIME);
                }
                return false;
            }
        });
        // 小类点击事件
        elv_category.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                JC_Category fatherC = categoryList.get(groupPosition);
                JC_Category category = fatherC.getChilds().get(childPosition);
                if (category.getId().equals(CodeConstant.ADDTWOTYPE)){

                    // next dialog
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("fatherId",fatherC.getId());

                    AddCategorySmallDialog dialog = new AddCategorySmallDialog();
                    AddCategorySmallPresenter presenter = new AddCategorySmallPresenter((GaiaApplication) getActivity().getApplication(),dialog);
                    dialog.setPresenter(presenter);
                    dialog.setArguments(bundle);
                    dialog.show(getActivity().getFragmentManager(), "android");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, CodeConstant.DIALOGWAITTIME);
                }
                return false;
            }
        });
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
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.vifrification);
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
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
