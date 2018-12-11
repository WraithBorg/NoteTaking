package com.zxu.ui.category;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Category;
import com.zxu.util.CodeConstant;
import com.zxu.util.DensityUtil;

import java.util.List;

public class SelectCategoryDialog extends DialogFragment implements CategoryContract.View {
    // view
    ImageView iv_addCategory;
    ImageView iv_folding;
    private ListView recLeft;
    private ListView recRight;
    // java
    private String costType;
    private SelectCategoryAdapterLeft leftAdapter;
    private SelectCategoryAdapterRight rightAdapter;
    //记录右侧当前可见的第一个item的position

    private CategoryContract.Presenter mPresenter;
    private List<JC_Category> categoryList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.record_category_scroll, null);
        iv_addCategory = (ImageView) view.findViewById(R.id.record_category_add_category_id);
        iv_folding = (ImageView) view.findViewById(R.id.record_category_search_category_id);
        recLeft = (ListView) view.findViewById(R.id.record_category_rec_left);
        recRight = (ListView) view.findViewById(R.id.record_category_rec_right);

        initWidgets();
        return view;
    }

    /**
     * 初始化按钮事件
     */
    void initWidgets() {
        // 新增类别
        iv_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, CodeConstant.DIALOGWAITTIME);
                ListCategoryDialog dialog = new ListCategoryDialog();
                dialog.setCostType(costType);
                CategoryPresenter presenter = new CategoryPresenter((GaiaApplication) getActivity().getApplication(), dialog);
                dialog.setPresenter(presenter);
                dialog.show(getActivity().getFragmentManager(), "android");
            }
        });
        // 折叠
        iv_folding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //
        mPresenter.getCategorys(false,costType);
        // left
        leftAdapter = new SelectCategoryAdapterLeft(categoryList, getActivity());
        recLeft.setAdapter(leftAdapter);
        leftAdapter.notifyDataSetChanged();
        recLeft.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                JC_Category category = categoryList.get(firstVisibleItem);
                initRight(category.getChilds());
            }
        });
        // right
        initRight(categoryList.get(0).getChilds());

    }

    private void initRight(List<JC_Category> list) {

        rightAdapter = new SelectCategoryAdapterRight(list, getActivity());

        recRight.setAdapter(rightAdapter);
        rightAdapter.notifyDataSetChanged();
        if (list == null || list.size() == 0) {
            return;
        }

        recRight.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                JC_Category category = list.get(firstVisibleItem);
                onSelectSmallTypeListener.selectOn(category);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置位置在底部
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DensityUtil.dp2px(getActivity().getApplication(), 260);// TODO 会影响record_category_left_text高度及滚动条
        window.setAttributes(params);
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.vifrification);
    }


    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public List<JC_Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<JC_Category> categoryList) {
        this.categoryList = categoryList;
    }

    // 选择类别 监听器 for CategorySelectDialog
    private onSelectSmallTypeListener onSelectSmallTypeListener;

    public interface onSelectSmallTypeListener {
        void selectOn(JC_Category category);
    }

    public void setOnSelectSmallTypeListener(onSelectSmallTypeListener onSelectSmallTypeListener) {
        this.onSelectSmallTypeListener = onSelectSmallTypeListener;
    }
    //
    @Override
    public void setCategorys(List<JC_Category> categorys) {
        setCategoryList(categorys);
    }
    /**
     *
     */
    @Override
    public void setCostType(String type) {
        costType = type;
    }
}
