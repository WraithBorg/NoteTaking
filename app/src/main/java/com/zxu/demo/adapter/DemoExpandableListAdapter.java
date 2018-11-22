package com.zxu.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zxu.R;

public class DemoExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    public static String[] groupStrings = {"西游记", "水浒传", "三国演义", "红楼梦"};
    public static String[][] childStrings = {
            {"唐三藏", "孙悟空", "猪八戒", "沙和尚"},
            {"宋江", "林冲", "李逵", "鲁智深"},
            {"曹操", "刘备", "孙权", "诸葛亮", "周瑜"},
            {"贾宝玉", "林黛玉", "薛宝钗", "王熙凤"}
    };
    //        获取分组的个数
    @Override
    public int getGroupCount() {
        return groupStrings.length;
    }

    //        获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return childStrings[groupPosition].length;
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupStrings[groupPosition];
    }

    //        获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childStrings[groupPosition][childPosition];
    }

    //        获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //        获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //        获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.demo_expandablelist_item, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tvTitle.setText(groupStrings[groupPosition]);
        return convertView;
    }

    //        获取显示指定分组中的指定子选项的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.demo_expandablelist_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_group);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tvTitle.setText(childStrings[groupPosition][childPosition]);
        return convertView;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
    }

    static class ChildViewHolder {
        TextView tvTitle;
    }

    public DemoExpandableListAdapter(Context context) {
        this.context = context;
    }
}
