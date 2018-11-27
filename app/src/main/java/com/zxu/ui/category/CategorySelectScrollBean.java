package com.zxu.ui.category;

import com.chad.library.adapter.base.entity.SectionEntity;

public class CategorySelectScrollBean extends SectionEntity<CategorySelectScrollBean.ScrollItemBean> {

    public CategorySelectScrollBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public CategorySelectScrollBean(CategorySelectScrollBean.ScrollItemBean bean) {
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
