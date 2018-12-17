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
import com.zxu.model.JC_RecordSumWeek;
import com.zxu.util.CostEnum;

import java.util.ArrayList;
import java.util.List;

public class ListRecordAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {
    private List<JC_RecordSumWeek> mRecordSum;
    private Context context;

    public ListRecordAdapter(List<JC_RecordSumWeek> recordSums, Context context) {
        this.mRecordSum = recordSums;
        this.context = context;
        if (mRecordSum == null) {
            this.mRecordSum = new ArrayList<>();
        }
    }

    @Override
    public int getGroupCount() {
        return mRecordSum.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mRecordSum.get(groupPosition).getRecords().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mRecordSum.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mRecordSum.get(groupPosition).getRecords().get(childPosition);
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
        SumViewHolder holder;
        if (convertView == null) {
            holder = new SumViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.records_item_sum, null);
            holder.tv_day = convertView.findViewById(R.id.record_item_sum_day);
            holder.tv_month = convertView.findViewById(R.id.record_item_sum_month);
            holder.tv_income = convertView.findViewById(R.id.record_item_sum_income);
            holder.tv_spend = convertView.findViewById(R.id.record_item_sum_spend);
            holder.tv_balance = convertView.findViewById(R.id.record_item_sum_balance);
            holder.iv_more = convertView.findViewById(R.id.record_item_sum_more);
            convertView.setTag(holder);
        } else {
            holder = (SumViewHolder) convertView.getTag();
        }
        JC_RecordSumWeek recordSum = mRecordSum.get(groupPosition);
        holder.tv_day.setText(recordSum.getDay());
        holder.tv_month.setText(recordSum.getMonth());
        holder.tv_income.setText(recordSum.getInCome());
        holder.tv_spend.setText(recordSum.getSpend());
        holder.tv_balance.setText(recordSum.getBalance());

        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.records_item, null);
            holder.iv_type = convertView.findViewById(R.id.report_today_item_icon_id);
            holder.tv_catagory = convertView.findViewById(R.id.report_today_item_category_id);
            holder.tv_money = convertView.findViewById(R.id.report_today_item_money_id);
            holder.tv_time = convertView.findViewById(R.id.report_today_item_time_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // assignment
        JC_Record record = mRecordSum.get(groupPosition).getRecords().get(childPosition);
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private final class ViewHolder {
        ImageView iv_type;
        TextView tv_catagory;
        TextView tv_money;
        TextView tv_time;
    }

    private final class SumViewHolder {
        TextView tv_day;
        TextView tv_month;
        TextView tv_income;
        TextView tv_spend;
        TextView tv_balance;
        ImageView iv_more;
    }
    ////

}
