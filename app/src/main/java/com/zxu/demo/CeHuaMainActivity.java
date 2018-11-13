package com.zxu.demo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zxu.R;
import com.zxu.adapter.CeHuaDrawerAdapter;
import com.zxu.fragment.ContentFragment;
import com.zxu.util.UtilTools;

/**
 * 侧滑菜单例子
 */
public class CeHuaMainActivity extends Activity {

    LinearLayout slipMenuView;//侧滑菜单视图
    ListView menuDrawer; //侧滑菜单List视图
    CeHuaDrawerAdapter menuDrawerAdapter; // 侧滑菜单ListView的Adapter
    DrawerLayout mDrawerLayout; // DrawerLayout组件
    ActionBarDrawerToggle mDrawerToggle; //侧滑菜单状态监听器


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_cehua_main);
        //为侧滑菜单设置Adapter，并为ListView添加单击事件监听器
        slipMenuView = (LinearLayout) findViewById(R.id.slipmenu_id);
        menuDrawer = (ListView) findViewById(R.id.left_drawer);
        menuDrawerAdapter = new CeHuaDrawerAdapter(this);
        menuDrawer.setAdapter(menuDrawerAdapter);
        menuDrawer.setOnItemClickListener(new DrawerItemClickListener());
        //为DrawerLayout注册状态监听器
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new DrawerMenuToggle(
                this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    /**
     * 侧滑菜单单击事件监听器
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }

        public void selectItem(int position) {

            //为内容视图加载新的Fragment
            Bundle bd = new Bundle();
            bd.putString(ContentFragment.SELECTED_ITEM, menuDrawerAdapter.getItem(position).getMenuTitle());

            Fragment contentFragment = new ContentFragment();
            contentFragment.setArguments(bd);



            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, contentFragment).commit();
            //将选中的菜单项置为高亮
            menuDrawer.setItemChecked(position, true);
            //将当前的侧滑菜单关闭，调用DrawerLayout的closeDrawer（）方法即可
            mDrawerLayout.closeDrawer(slipMenuView);
        }
    }

    /**
     * 侧滑菜单状态监听器（开、关），通过继承ActionBarDrawerToggle实现
     */
    private class DrawerMenuToggle extends ActionBarDrawerToggle {
        /**
         * @param drawerLayout             ：就是加载的DrawerLayout容器组件
         * @param openDrawerContentDescRes 、closeDrawerContentDescRes：开启和关闭的两个描述字段，没有太大的用处
         */
        public DrawerMenuToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes,
                                int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);

        }
        /**
         * 当侧滑菜单达到完全关闭的状态时，回调这个方法
         */
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            UtilTools.showToast(getApplicationContext(), "侧滑菜单关闭", 1500);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
        /**
         * 当侧滑菜单完全打开时，这个方法被回调
         */
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            UtilTools.showToast(getApplicationContext(), "侧滑菜单完全打开", 1500);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    }

    /**
     * 为了能够让ActionBarDrawerToggle监听器
     * 能够在Activity的整个生命周期中都能够以正确的逻辑工作
     * 需要添加下面两个方法
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * 《当用户按下了"手机上的返回功能按键"的时候会回调这个方法》
     */
    @Override
    public void onBackPressed() {
        boolean drawerState = mDrawerLayout.isDrawerOpen(menuDrawer);// TODO slipMenuView
        if (drawerState) {
            mDrawerLayout.closeDrawers();
            return;
        }
        //也就是说，当按下返回功能键的时候，不是直接对Activity进行弹栈，而是先将菜单视图关闭
        super.onBackPressed();
    }
}
