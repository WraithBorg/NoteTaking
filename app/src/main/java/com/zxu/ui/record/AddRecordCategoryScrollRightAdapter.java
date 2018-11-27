package com.zxu.ui.record;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxu.R;

import java.util.List;

public class AddRecordCategoryScrollRightAdapter extends BaseSectionQuickAdapter<AddRecordCategoryScrollBean, BaseViewHolder> {

    public AddRecordCategoryScrollRightAdapter(int layoutResId, int sectionHeadResId, List<AddRecordCategoryScrollBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, AddRecordCategoryScrollBean item) {
        helper.setText(R.id.right_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddRecordCategoryScrollBean item) {
        AddRecordCategoryScrollBean.ScrollItemBean t = item.t;
        helper.setText(R.id.right_text, t.getText());
    }
}