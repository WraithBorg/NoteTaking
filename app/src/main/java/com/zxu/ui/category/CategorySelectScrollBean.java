package com.zxu.ui.category;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.zxu.model.JC_Category;

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
        private JC_Category category;

        public ScrollItemBean(String text, String type, JC_Category c) {
            this.text = text;
            this.type = type;
            this.category = c;
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

        public JC_Category getCategory() {
            return category;
        }

        public void setCategory(JC_Category category) {
            this.category = category;
        }
    }
}
