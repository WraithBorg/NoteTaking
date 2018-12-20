package com.zxu.ui.recordsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.util.CodeConstant;

import java.util.List;

public class SelectTime4SearchAdapter extends BaseAdapter {

    private List<String> timeList;
    private Context context;
    private String checked;

    public SelectTime4SearchAdapter(Context context, String checked, List<String> timeList) {
        this.timeList = timeList;
        this.context = context;
        this.checked = checked;
    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int position) {
        return timeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.record_search_index_time_item, null);
            holder.tv_time = convertView.findViewById(R.id.record_search_index_time_item_name_id);
            holder.iv_checked = convertView.findViewById(R.id.record_search_index_time_item_checked_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //
        String name = timeList.get(position);
        holder.tv_time.setText(name);
        if (checked.equals(name)) {
            holder.iv_checked.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_time;
        ImageView iv_checked;
    }
}
