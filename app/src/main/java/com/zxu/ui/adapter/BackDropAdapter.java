package com.zxu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zxu.R;

import java.util.List;

/**
 * 账本背景缩略图
 */
public class BackDropAdapter extends BaseAdapter {
    private List<String> backDrops;
    private Integer selectedBackDrops;
    private Context context;
    private LayoutInflater mInFlater;

    public BackDropAdapter(Context context, List<String> backDrops) {
        this.backDrops = backDrops;
        this.context = context;
        this.mInFlater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return backDrops.size();
    }

    @Override
    public Object getItem(int position) {
        return backDrops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInFlater.inflate(R.layout.indexpage_accountbook_add_backdrop_item, null);
        LinearLayout ll_item = (LinearLayout) convertView.findViewById(R.id.indexpage_add_backdrop_item_id);
        ImageView iv_back = (ImageView) convertView.findViewById(R.id.indexpage_add_backdrop_item_icon_id);
        if (position == selectedBackDrops){
            ll_item.setBackgroundResource(R.drawable.backdrops_shape);
        }
        iv_back.setBackgroundResource(Integer.parseInt(backDrops.get(position)));
        return convertView;
    }

    public void setSelectedBackDrops(Integer selectedBackDrops) {
        this.selectedBackDrops = selectedBackDrops;
    }
}
