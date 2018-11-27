package com.zxu.ui.category.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_Category;

import java.util.List;

public class CategoryAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {

    private Context context;
    private List<JC_Category> bigList;

    private CategoryAdapter() {
    }

    private CategoryAdapter(Context context) {
        this();
        this.context = context;
    }

    public CategoryAdapter(Context context, List<JC_Category> bigList) {
        this(context);
        this.bigList = bigList;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        BigViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.category_big_item, null);
            holder = new BigViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.category_item_icon_id);
            holder.tv_name = (TextView) convertView.findViewById(R.id.category_item_name_id);
            convertView.setTag(holder);
        } else {
            holder = (BigViewHolder) convertView.getTag();
        }
        JC_Category category = bigList.get(groupPosition);
        holder.tv_name.setText(category.getName());
        initBigWidgets(holder);
        return convertView;
    }

    /**
     * 初始化 大类按钮事件
     *
     * @param holder
     */
    private void initBigWidgets(BigViewHolder holder) {
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SmallViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.category_small_item, null);
            holder = new SmallViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.category_item_icon_id);
            holder.tv_name = (TextView) convertView.findViewById(R.id.category_item_name_id);
            convertView.setTag(holder);
        } else {
            holder = (SmallViewHolder)convertView.getTag();
        }
        JC_Category category = bigList.get(groupPosition).getChilds().get(childPosition);
        holder.tv_name.setText(category.getName());
        return convertView;
    }

    public final class BigViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
    }
    public final class SmallViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
    }
    /************************************** *************************************/
    @Override
    public int getGroupCount() {
        return bigList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bigList.get(groupPosition).getChilds().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return bigList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return bigList.get(groupPosition).getChilds().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    /************************************** *************************************/

}
