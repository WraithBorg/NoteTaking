package com.zxu.dao;

import com.zxu.base.database.BaseDaoImpl;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Category;
import com.zxu.util.CodeConstant;
import com.zxu.util.CostEnum;
import com.zxu.util.ZUID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDao extends BaseDaoImpl<JC_Category> {
    public CategoryDao() {
    }

    /**
     * @param category
     * @return
     */
    public ResultHelper addCategory(JC_Category category) {
        ZUID zuid = new ZUID();
        category.setNum(zuid.next().toString());
        super.add(category);
        return new ResultHelper(true, "新增成功");
    }

    /**
     * 获取类别数据
     *
     * @param fromAdd
     * @return
     */
    public List<JC_Category> getBSList(boolean fromAdd, String costType) {
//        List<JC_Category> list = super.getList();
        List<JC_Category> list = super.where().eq("species", costType).query();

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
        ZUID zuid = new ZUID();
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
                c.setNum(zuid.next());
                ss.add(c);
            }
            // 小类排序
            Collections.sort(ss, new Comparator<JC_Category>() {
                @Override
                public int compare(JC_Category o1, JC_Category o2) {
                    return Long.compare(Long.parseLong(o1.getNum()), Long.parseLong(o2.getNum()));
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
            b.setNum(zuid.next());
            list.add(b);
        }
        // 大类排序
        Collections.sort(list, new Comparator<JC_Category>() {
            @Override
            public int compare(JC_Category o1, JC_Category o2) {
                return Long.compare(Long.parseLong(o1.getNum()), Long.parseLong(o2.getNum()));
            }
        });

        return list;
    }


    /**
     * 初始化数据
     */
    public void initData() {
        if (super.getList() != null && super.getList().size() > 0) {
            return;
        }
        String[] strings = new String[]{
                "|购物消费|购物消费|0",
                "购物消费|衣服鞋帽|衣服鞋帽|1",
                "购物消费|厨房用品|厨房用品|1",
                "购物消费|电子产品|电子产品|1",

                "|食品酒水|食品酒水|0",
                "食品酒水|水果|水果|1",
                "食品酒水|买菜|买菜|1",
                "食品酒水|零食|零食|1",

                "|居家生活|居家生活|0",
                "居家生活|房租|房租|1",
                "居家生活|水费|水费|1",
                "居家生活|电费|电费|1",
                "居家生活|网费|网费|1",

                "|行车交通|行车交通|0",

                "|休闲娱乐|休闲娱乐|0",

                "|人情费用|人情费用|0",

                "|出差旅行|出差旅行|0",

                "|金融保险|金融保险|0",
        };
        ZUID zuid = new ZUID();
        JC_Category category;
        for (String str : strings) {
            String[] split = str.split("\\|");
            String fatherId = split[0];
            String id = split[1];
            String name = split[2];
            int type = Integer.parseInt(split[3]);
            category = new JC_Category(id, name, fatherId, type, CostEnum.SPEND.code(), zuid.next(), new ArrayList<>());
            super.add(category);
        }

        strings = new String[]{
                "|职业收入|职业收入|0",
                "职业收入|工资收入|工资收入|1"
        };

        for (String str : strings) {
            String[] split = str.split("\\|");
            String fatherId = split[0];
            String id = split[1];
            String name = split[2];
            int type = Integer.parseInt(split[3]);
            category = new JC_Category(id, name, fatherId, type, CostEnum.INCOME.code(), zuid.next(), new ArrayList<>());
            super.add(category);
        }
    }
}
