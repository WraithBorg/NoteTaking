package com.zxu.index;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.zxu.R;
import com.zxu.util.ActivityUtils;

/**
 * 索引页,activity 不是view层，是view model presenter三层的纽带
 */
public class IndexPageActivity extends Activity {
    private IndexPagePresenter indexPagePresenter;

    DrawerLayout mDrawerLayout; // DrawerLayout组件
    ActionBarDrawerToggle mDrawerToggle; //侧滑菜单状态监听器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indexpage_main);
        IndexPageFragment indexPageFragment = (IndexPageFragment) getFragmentManager().findFragmentById(R.id.accountsFrame_id);
        if (indexPageFragment == null) {
            indexPageFragment = IndexPageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getFragmentManager(), indexPageFragment, R.id.accountsFrame_id);
        }
        indexPagePresenter = new IndexPagePresenter(indexPageFragment);
        indexPageFragment.setPresenter(indexPagePresenter);
        //
        mDrawerLayout = (DrawerLayout) findViewById(R.id.indexpage_drawer_id);
        mDrawerToggle = new IndexPageActivity.DrawerMenuToggle(
                this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    /**
     * 侧滑菜单状态监听器（开，关），，通过继承ActionBarDrawerToggle实现
     */
    private class DrawerMenuToggle extends ActionBarDrawerToggle {
        public DrawerMenuToggle(Activity activity, DrawerLayout drawerLayout, int drawer_open, int drawer_close) {
            super(activity, drawerLayout, drawer_open, drawer_close);
        }

        /**
         * 测试菜单完全关闭
         * @param drawerView
         */
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        /**
         * 侧滑菜单完全打开
         * @param drawerView
         */
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();//保证监听器在整个Activity生命周期正确工作
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);//保证监听器在整个Activity生命周期正确工作
    }

    @Override
    public void onBackPressed() {
       // TODO 按下返回功能键的时候，不是直接对Activity进行弹栈，而是先将菜单视图关闭
        super.onBackPressed();
    }
}
