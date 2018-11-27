package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDao extends BaseDaoImpl<JC_Category> {
    public CategoryDao() {
    }
    public ResultHelper addCategory(JC_Category category){
        super.add(category);
        return new ResultHelper(true, "新增成功");
    }
    public List<JC_Category> getBSList(boolean fromAdd){
        List<JC_Category> list = super.getList();
        Map<String, JC_Category> bigs = new HashMap<>();
        Map<String, List<JC_Category>> smalls = new HashMap<>();
        for (JC_Category category : list) {
            if (category.getType() == 0) {
                bigs.put(category.getId(), category);
            } else {
                if (smalls.containsKey(category.getFatherId())) {
                    smalls.get(category.getFatherId()).add(category);
                } else {
                    List<JC_Category> li = new ArrayList<>();
                    li.add(category);
                    smalls.put(category.getFatherId(), li);
                }
            }
        }
        // assemble
        list.clear();
        for (String key : bigs.keySet()){
            JC_Category category = bigs.get(key);
            List<JC_Category> ss = smalls.get(key);
            if (ss == null){
                ss = new ArrayList<>();
            }
            if (fromAdd){
                JC_Category c = new JC_Category();
                c.setId("addTwoType");
                c.setName("新建二级支出分类");
                c.setType(1);
                ss.add(c);
            }
            category.setChilds(ss);
            list.add(category);
        }
        if (fromAdd){
            JC_Category b = new JC_Category();
            b.setId("addOneType");
            b.setName("新建一级支出分类");
            b.setChilds(new ArrayList<>());
            b.setType(0);
            list.add(b);
        }
        return list;
    }
}
