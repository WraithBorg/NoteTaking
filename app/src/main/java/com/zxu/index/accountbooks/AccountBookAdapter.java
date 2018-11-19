package com.zxu.index.accountbooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_AccountBook;

import java.util.List;

/**
 * 适配器
 */
public class AccountBookAdapter extends BaseAdapter {
    List<JC_AccountBook> accountBooks;
    Context context;
    boolean isEdit;//是否是编辑样式
    private LayoutInflater mInFlater;
    private OnDeleteItem onDeleteItem;
    private OnEditItem onEditItem;

    public AccountBookAdapter(Context context, List<JC_AccountBook> accountBooks, boolean isEdit) {
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
        ViewHolder holder;
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
        JC_AccountBook item = getItem(position);
        holder.tv_boolName.setText(item.getName());
        // 如果是编辑样式 则显示删除按钮等元素
        if (isEdit) {
            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.iv_setting.setVisibility(View.VISIBLE);
            if (onDeleteItem != null) {
                holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onDeleteItem.deleteClick(item);

                    }
                });
            }

            holder.iv_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEditItem != null) {
                        onEditItem.editClick(item);
                    }
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

    /*
    Adapter回调数据分为两种方式，
    第一种是在adapter之中写一个回调接口，在点击item的时候使用接口回调，将点击的position传递给activity之中使用的adapter，
        在adapter中使用定义的回调方法获取到传递的position。
    第二种是在activity之中传递数据到adapter的时候将handler一并传进去，使用handler获取到点listView点击之时的position传递。
     */
    // 删除按钮 接口回调
    public interface OnDeleteItem {
        public void deleteClick(JC_AccountBook item);
    }

    public interface OnEditItem {
        public void editClick(JC_AccountBook item);
    }

    public void setOnDeleteItem(OnDeleteItem onDeleteItem) {
        this.onDeleteItem = onDeleteItem;
    }

    public void setOnEditItem(OnEditItem onEditItem) {
        this.onEditItem = onEditItem;
    }
}
