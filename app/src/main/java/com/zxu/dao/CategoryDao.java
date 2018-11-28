package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Category;
import com.zxu.model.JC_Record;
import com.zxu.util.CodeConstant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CategoryDao extends BaseDaoImpl<JC_Category> {
    public CategoryDao() {
    }

    public ResultHelper addCategory(JC_Category category) {
        super.add(category);
        return new ResultHelper(true, "新增成功");
    }

    public List<JC_Category> getBSList(boolean fromAdd) {
        List<JC_Category> list = super.getList();
        if (list.size() == 0) {
            initData(list);
        }
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
        list.clear();
        for (String key : bigs.keySet()) {
            JC_Category category = bigs.get(key);
            List<JC_Category> ss = smalls.get(key);
            if (ss == null) {
                ss = new ArrayList<>();
            }
            if (fromAdd) {
                JC_Category c = new JC_Category();
                c.setId(CodeConstant.ADDTWOTYPE);
                c.setName("新建二级支出分类");
                c.setType(1);
                ss.add(c);
            }
            category.setChilds(ss);
            list.add(category);
        }
        if (fromAdd) {
            JC_Category b = new JC_Category();
            b.setId(CodeConstant.ADDONETYPE);
            b.setName("新建一级支出分类");
            b.setChilds(new ArrayList<>());
            b.setType(0);
            list.add(b);
        }
        return list;
    }

    /**
     * 初始化数据
     *
     * @param list
     */
    private void initData(List<JC_Category> list) {
        List<String> bigList = new ArrayList<>();
        bigList.add("行车交通");
        bigList.add("休闲娱乐");
        bigList.add("人情费用");
        bigList.add("出差旅行");
        bigList.add("金融保险");
        bigList.add("购物消费");
        bigList.add("食品酒水");
        bigList.add("居家生活");
        JC_Category category;
        for (String name : bigList) {
            category = new JC_Category(name, name, "", 0, new ArrayList<>());
            super.add(category);
            list.add(category);
        }
        Map<String, String> smallList = new HashMap<>();
        smallList.put("水果", "食品酒水");
        smallList.put("买菜", "食品酒水");
        smallList.put("零食", "食品酒水");
        smallList.put("衣服鞋帽", "购物消费");
        smallList.put("厨房用品", "购物消费");
        smallList.put("电子产品", "购物消费");
        smallList.put("房租", "居家生活");
        smallList.put("水费", "居家生活");
        smallList.put("电费", "居家生活");
        smallList.put("网费", "居家生活");
        for (String key : smallList.keySet()) {
            String bigType = smallList.get(key);
            category = new JC_Category(key, key, bigType, 1, null);
            super.add(category);
            list.add(category);
        }
    }
}
