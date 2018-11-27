package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Category;

public class CategoryDao extends BaseDaoImpl<JC_Category> {
    public CategoryDao() {
    }
    public ResultHelper addCategory(JC_Category category){
        super.add(category);
        return new ResultHelper(true, "新增成功");
    }
}
