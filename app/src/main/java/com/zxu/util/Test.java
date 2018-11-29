package com.zxu.util;

import com.zxu.model.JC_Category;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        String[] strings = new String[]{
//              "fatherId|id|name|type|num",
                "|购物消费|购物消费|0|1000",
                "购物消费|衣服鞋帽|衣服鞋帽|1|1010",
                "购物消费|厨房用品|厨房用品|1|1020",
                "购物消费|电子产品|电子产品|1|1030",

                "|食品酒水|食品酒水|0|2000",
                "食品酒水|水果|水果|1|2010",
                "食品酒水|买菜|买菜|1|2010",
                "食品酒水|零食|零食|1|2010",

                "|居家生活|居家生活|0|3000",
                "食品酒水|房租|房租|1|3010",
                "食品酒水|水费|水费|1|3010",
                "食品酒水|电费|电费|1|3010",
                "食品酒水|网费|网费|1|3010",

                "|行车交通|行车交通|0|4000",

                "|行车交通|休闲娱乐|0|5000",

                "|行车交通|人情费用|0|6000",

                "|行车交通|出差旅行|0|7000",

                "|行车交通|金融保险|0|8000",
        };
        for (String str : strings){
            String[] split = str.split("\\|");
            String fatherId = split[0];
            String id = split[1];
            String name = split[2];
            int type = Integer.parseInt(split[3]);
            int num = Integer.parseInt(split[4]);
            JC_Category category = new JC_Category(id, name, fatherId, type, num, new ArrayList<>());
            System.out.println(category.toString());
        }
    }
}
