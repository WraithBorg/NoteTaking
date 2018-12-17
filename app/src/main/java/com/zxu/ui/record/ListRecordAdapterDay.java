package com.zxu.ui.record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_Record;
import com.zxu.util.CostEnum;

import java.util.ArrayList;
import java.util.List;

public class ListRecordAdapterDay extends BaseExpandableListAdapter implements ExpandableListAdapter {
    private List<JC_Record> recordList;
    private Context context;

    public ListRecordAdapterDay(List<JC_Record> recordList, Context context) {
        this.recordList = recordList;
        this.context = context;
        //
        if (recordList == null) {
            this.recordList = new ArrayList<>();
        }
    }

    @Override
    public int getGroupCount() {
        return recordList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return recordList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // view
        ListRecordAdapterDay.ViewHolder holder;
        if (convertView == null) {
            holder = new ListRecordAdapterDay.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.records_item, null);
            holder.iv_type = (ImageView) convertView.findViewById(R.id.report_today_item_icon_id);
            holder.tv_catagory = (TextView) convertView.findViewById(R.id.report_today_item_category_id);
            holder.tv_money = (TextView) convertView.findViewById(R.id.report_today_item_money_id);
            holder.tv_time = (TextView) convertView.findViewById(R.id.report_today_item_time_id);
            convertView.setTag(holder);
        } else {
            holder = (ListRecordAdapterDay.ViewHolder) convertView.getTag();
        }
        // assignment
        JC_Record record = recordList.get(groupPosition);
        holder.iv_type.setBackgroundResource(R.mipmap.rmb);
        holder.tv_catagory.setText(record.getCategory());
        holder.tv_money.setText(record.getMoney());
        holder.tv_time.setText(record.getWorkTime());
        // prefect
        if (CostEnum.SPEND.code().equals(record.getType())) {
            holder.tv_money.setTextColor(context.getColor(R.color.app_red));
        } else if (CostEnum.INCOME.code().equals(record.getType())) {
            holder.tv_money.setTextColor(context.getColor(R.color.app_green));
        } else {
            holder.tv_money.setTextColor(context.getColor(R.color.app_black));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private final class ViewHolder {
        ImageView iv_type;
        TextView tv_catagory;
        TextView tv_money;
        TextView tv_time;
    }
}
