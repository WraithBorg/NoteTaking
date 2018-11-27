package com.zxu.ui.record;

import com.chad.library.adapter.base.entity.SectionEntity;

public class AddRecordCategoryScrollBean extends SectionEntity<AddRecordCategoryScrollBean.ScrollItemBean> {

    public AddRecordCategoryScrollBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public AddRecordCategoryScrollBean(AddRecordCategoryScrollBean.ScrollItemBean bean) {
        super(bean);
    }

    public static class ScrollItemBean {
        private String text;
        private String type;

        public ScrollItemBean(String text, String type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
