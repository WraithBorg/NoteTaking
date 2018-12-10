package com.zxu.ui.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_Category;

import java.util.List;

public class CategoryRightAdapter extends BaseAdapter {
    private List<JC_Category> mCategoryList;
    private Context mContext;

    public CategoryRightAdapter(List<JC_Category> list, Context mContext) {
        this.mCategoryList = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.category_right_item, null);
        TextView tv_catagory = (TextView) convertView.findViewById(R.id.category_right_name_id);
        tv_catagory.setText(mCategoryList.get(position).getName());
        if (position == mCategoryList.size()-1){
            ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,260);//设置宽度和高度
            convertView.setLayoutParams(params);
        }
        return convertView;
    }
}
