package com.zxu.ui.recordsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_Record;
import com.zxu.model.JC_RecordSearchResult;
import com.zxu.util.CostEnum;
import com.zxu.util.UtilTools;

import java.text.SimpleDateFormat;
import java.util.List;

public class SearchResult4RecordAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<JC_RecordSearchResult> resultList;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public SearchResult4RecordAdapter(Context context, List<JC_RecordSearchResult> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @Override
    public int getGroupCount() {
        return resultList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return resultList.get(groupPosition).getRecords().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return resultList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return resultList.get(groupPosition).getRecords().get(childPosition);
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
            convertView = inflater.inflate(R.layout.records_search_result_item_sum, null);
            holder.tv_date = convertView.findViewById(R.id.record_search_result_date_id);

            convertView.setTag(holder);
        } else {
            holder = (SumViewHolder) convertView.getTag();
        }
        JC_RecordSearchResult recordSum = resultList.get(groupPosition);
        holder.tv_date.setText(format.format(recordSum.getDate())+"  "+UtilTools.getWeekDay(dateFormat.format(recordSum.getDate())));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.records_search_result_item, null);
            holder.tv_money = convertView.findViewById(R.id.search_result_money_id);
            holder.tv_category = convertView.findViewById(R.id.search_result_category_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // assignment
        JC_RecordSearchResult jc_recordSum = resultList.get(groupPosition);
        JC_Record record = jc_recordSum.getRecords().get(childPosition);
        holder.tv_money.setText(record.getMoney());
        holder.tv_category.setText(record.getCategory());
        //
        if (CostEnum.SPEND.code().equals(record.getWaterType())) {
            holder.tv_money.setTextColor(context.getColor(R.color.app_green));
        } else if (CostEnum.INCOME.code().equals(record.getWaterType())) {
            holder.tv_money.setTextColor(context.getColor(R.color.app_red));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private final class ViewHolder {
        TextView tv_category;
        TextView tv_money;
    }

    private final class SumViewHolder {
        TextView tv_date;
    }
}
