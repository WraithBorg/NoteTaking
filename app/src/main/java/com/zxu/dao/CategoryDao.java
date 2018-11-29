package com.zxu.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Category;
import com.zxu.model.JC_Record;
import com.zxu.util.CodeConstant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CategoryDao extends BaseDaoImpl<JC_Category> {
    public CategoryDao() {
    }

    public ResultHelper addCategory(JC_Category category) {
        int maxNum = getMaxNum();
        if (category.getType() == 0){
            category.setNum(maxNum+1000);
        }else {
            category.setNum(maxNum+10);
        }
        super.add(category);
        return new ResultHelper(true, "新增成功");
    }

    /**
     * 获取类别数据
     * @param fromAdd
     * @return
     */
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
        // 后期加工
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
            // 小类排序
            Collections.sort(ss, new Comparator<JC_Category>() {
                @Override
                public int compare(JC_Category o1, JC_Category o2) {
                    return o1.getNum() - o2.getNum();
                }
            });
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
        // 大类排序
        Collections.sort(list, new Comparator<JC_Category>() {
            @Override
            public int compare(JC_Category o1, JC_Category o2) {
                return o1.getNum() - o2.getNum();
            }
        });

        return list;
    }

    /**
     * 初始化数据
     *
     * @param list
     */
    private void initData(List<JC_Category> list) {
        String[] strings = new String[]{
                "|购物消费|购物消费|0|1000",
                "购物消费|衣服鞋帽|衣服鞋帽|1|1010",
                "购物消费|厨房用品|厨房用品|1|1020",
                "购物消费|电子产品|电子产品|1|1030",

                "|食品酒水|食品酒水|0|2000",
                "食品酒水|水果|水果|1|2010",
                "食品酒水|买菜|买菜|1|2010",
                "食品酒水|零食|零食|1|2010",

                "|居家生活|居家生活|0|3000",
                "居家生活|房租|房租|1|3010",
                "居家生活|水费|水费|1|3010",
                "居家生活|电费|电费|1|3010",
                "居家生活|网费|网费|1|3010",

                "|行车交通|行车交通|0|4000",

                "|休闲娱乐|休闲娱乐|0|5000",

                "|人情费用|人情费用|0|6000",

                "|出差旅行|出差旅行|0|7000",

                "|金融保险|金融保险|0|8000",
        };
        JC_Category category;
        for (String str : strings){
            String[] split = str.split("\\|");
            String fatherId = split[0];
            String id = split[1];
            String name = split[2];
            int type = Integer.parseInt(split[3]);
            int num = Integer.parseInt(split[4]);
            category = new JC_Category(id, name, fatherId, type, num, new ArrayList<>());
            super.add(category);
            list.add(category);
        }
    }
    int getMaxNum(){
        SQLiteDatabase database = getDatabaseHelper().open();
        Cursor cursor = database.query("jc_category",
                null, null, null, null, null, "NUM DESC");
        int num = 0;
        if (cursor.moveToNext()){
            num = cursor.getInt(cursor.getColumnIndex("num"));
        }
        getDatabaseHelper().close();
        return num;
    }
}
