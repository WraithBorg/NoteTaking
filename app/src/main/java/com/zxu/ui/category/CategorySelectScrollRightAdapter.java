package com.zxu.ui.category;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxu.R;

import java.util.List;

public class CategorySelectScrollRightAdapter extends BaseSectionQuickAdapter<CategorySelectScrollBean, BaseViewHolder> {

    public CategorySelectScrollRightAdapter(int layoutResId, int sectionHeadResId, List<CategorySelectScrollBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, CategorySelectScrollBean item) {
        helper.setText(R.id.right_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategorySelectScrollBean item) {
        CategorySelectScrollBean.ScrollItemBean t = item.t;
        helper.setText(R.id.right_text, t.getText());
    }
}