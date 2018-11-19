package com.zxu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_AccountBook;
import com.zxu.util.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配器
 */
public class AccountBooksAdapter extends BaseAdapter {
    List<JC_AccountBook> accountBooks;
    Context context;
    boolean isEdit;//是否是编辑样式
    private LayoutInflater mInFlater;

    public AccountBooksAdapter(Context context, List<JC_AccountBook> accountBooks, boolean isEdit) {
        this.context = context;
        this.accountBooks = accountBooks;
        this.isEdit = isEdit;
        this.mInFlater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return accountBooks.size();
    }

    @Override
    public JC_AccountBook getItem(int position) {
        return accountBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // 判断是否有缓存
        if (convertView == null) {
            holder = new ViewHolder();
            // 通过LayoutInflater实例化布局
            convertView = mInFlater.inflate(R.layout.indexpage_accountbooks_item, null);
            holder.tv_boolName = (TextView) convertView.findViewById(R.id.indexpage_accountbooks_name_id);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.indexpage_accountbooks_delete_id);
            holder.iv_setting = (ImageView) convertView.findViewById(R.id.indexpage_accountbooks_setting_id);
            convertView.setTag(holder);

        } else {
            // 通过tag找到缓存的布局
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_boolName.setText(getItem(position).getName());
        // 如果是编辑样式 则显示删除按钮等元素
        if (isEdit) {
            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.iv_setting.setVisibility(View.VISIBLE);

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilTools.showToast(context, "编辑", 1000);
                }
            });
            holder.iv_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_boolName;
        public ImageView iv_delete;
        public ImageView iv_setting;
    }

}
