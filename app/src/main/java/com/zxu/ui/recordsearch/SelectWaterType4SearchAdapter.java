package com.zxu.ui.recordsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zxu.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectWaterType4SearchAdapter extends BaseAdapter {

    private List<String> timeList;
    private Context context;
    private Set<String> checked;

    public SelectWaterType4SearchAdapter(Context context, Set<String> checked, List<String> timeList) {
        this.timeList = timeList;
        this.context = context;
        this.checked = checked;
        if (this.checked == null){
            this.checked = new HashSet<>();
        }
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
            convertView = inflater.inflate(R.layout.record_search_index_watertype_item, null);
            holder.tv_time = convertView.findViewById(R.id.record_search_index_watertype_item_name_id);
            holder.rb_checked = convertView.findViewById(R.id.record_search_index_watertype_item_select_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //
        String name = timeList.get(position);
        holder.tv_time.setText(name);
        if (checked.contains("全选")){
            holder.rb_checked.setBackgroundResource(R.mipmap.radio_selected);
        } else if (checked.contains(name)) {
            holder.rb_checked.setBackgroundResource(R.mipmap.radio_selected);
        }else {
            holder.rb_checked.setBackgroundResource(R.mipmap.radio_unselect);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_time;
        ImageView rb_checked;
    }
}
