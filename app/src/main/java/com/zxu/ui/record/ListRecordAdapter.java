package com.zxu.ui.record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_Record;
import com.zxu.util.CostEnum;

import java.util.ArrayList;
import java.util.List;

public class ListRecordAdapter extends BaseAdapter {
    private List<JC_Record> recordList;
    private Context context;

    public ListRecordAdapter(List<JC_Record> recordList, Context context) {
        this.recordList = recordList;
        this.context = context;
        //
        if (recordList == null) {
            this.recordList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // view
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.records_item, null);
            holder.iv_type = (ImageView) convertView.findViewById(R.id.report_today_item_icon_id);
            holder.tv_catagory = (TextView) convertView.findViewById(R.id.report_today_item_category_id);
            holder.tv_money = (TextView) convertView.findViewById(R.id.report_today_item_money_id);
            holder.tv_time = (TextView) convertView.findViewById(R.id.report_today_item_time_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // assignment
        JC_Record record = recordList.get(position);
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

    private final class ViewHolder {
        ImageView iv_type;
        TextView tv_catagory;
        TextView tv_money;
        TextView tv_time;
    }
}
