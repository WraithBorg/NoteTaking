package com.zxu.ui.recordsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_Account;

import java.util.List;
import java.util.Set;

public class SelectAccountType4SearchAdapter extends BaseAdapter {

    private List<JC_Account> accountTypeList;
    private Context context;
    private Set<String> checked;

    public SelectAccountType4SearchAdapter(Context context, Set<String> checked, List<JC_Account> timeList) {
        this.accountTypeList = timeList;
        this.context = context;
        this.checked = checked;
    }

    @Override
    public int getCount() {
        return accountTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return accountTypeList.get(position);
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
            convertView = inflater.inflate(R.layout.record_search_index_accounttype_item, null);
            holder.tv_time = convertView.findViewById(R.id.record_search_index_accounttype_item_name_id);
            holder.rb_checked = convertView.findViewById(R.id.record_search_index_accounttype_item_select_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //
        String name = accountTypeList.get(position).getName();
        holder.tv_time.setText(name);
        if (checked.contains(name)) {
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
