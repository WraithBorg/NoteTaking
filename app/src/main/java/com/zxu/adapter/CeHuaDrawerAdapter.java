package com.zxu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.item.TuiCoolMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧滑菜单 ListView
 */
public class CeHuaDrawerAdapter extends BaseAdapter {
    List<TuiCoolMenuItem> MenuItems = new ArrayList<TuiCoolMenuItem>();//存储侧滑菜单中的各项的数据
    Context context;//构造方法中传过来的activity

    //构造方法
    public CeHuaDrawerAdapter(Context context) {
        this.context = context;
        MenuItems.add(new TuiCoolMenuItem("", R.mipmap.yanjing));
        MenuItems.add(new TuiCoolMenuItem("推荐", R.mipmap.tongji));
        MenuItems.add(new TuiCoolMenuItem("发现", R.mipmap.syscaidan));
        MenuItems.add(new TuiCoolMenuItem("主题", R.mipmap.lingdang));
        MenuItems.add(new TuiCoolMenuItem("站点", R.mipmap.yanjing));
        MenuItems.add(new TuiCoolMenuItem("搜索", R.mipmap.yanjing));
        MenuItems.add(new TuiCoolMenuItem("离线", R.mipmap.yanjing));
        MenuItems.add(new TuiCoolMenuItem("设置", R.mipmap.yanjing));
    }


    @Override
    public int getCount() {
        return MenuItems.size();
    }

    @Override
    public TuiCoolMenuItem getItem(int position) {
        return MenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.demo_menudrawer_item, parent, false);
            TextView titleView = (TextView) view.findViewById(R.id.menu_drawer_title_id);
            titleView.setText(getItem(position).getMenuTitle());
//            titleView.setCompoundDrawablesWithIntrinsicBounds(getItem(position).menuIcon, 0, 0, 0);//设置图标
        }
        return view;
    }
}

