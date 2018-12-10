package com.zxu.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.demo.AFunctionDisplayActivity;
import com.zxu.ui.accountbooks.AccountBookFragment;
import com.zxu.ui.accountbooks.AccountBookPresenter;
import com.zxu.ui.record.RecordAddActivity;
import com.zxu.ui.report.TodayReportMainFragment;
import com.zxu.ui.report.TodayReportMainPresenter;
import com.zxu.util.ActivityUtil;
import com.zxu.util.Constant;
import com.zxu.util.UtilTools;

/**
 * 索引页,activity 不是view层，是view model presenter三层的纽带
 */
public class IndexPageActivity extends Activity {
    private AccountBookPresenter accountBooksPresenter;

    LinearLayout slipMenuView;
    DrawerLayout mDrawerLayout; // DrawerLayout组件
    ActionBarDrawerToggle mDrawerToggle; //侧滑菜单状态监听器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChromeDatabase();
        setContentView(R.layout.indexpage_main);
        AccountBookFragment indexPageFragment = (AccountBookFragment) getFragmentManager().findFragmentById(R.id.accountsFrame_id);
        if (indexPageFragment == null) {
            indexPageFragment = AccountBookFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getFragmentManager(), indexPageFragment, R.id.accountsFrame_id);
        }

        //
        mDrawerLayout = (DrawerLayout) findViewById(R.id.indexpage_drawer_id);
        mDrawerToggle = new IndexPageActivity.DrawerMenuToggle(
                this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //
        slipMenuView = (LinearLayout) findViewById(R.id.indexpage_slipmenu_id);
        mDrawerLayout.closeDrawer(slipMenuView);
        //
        accountBooksPresenter = new AccountBookPresenter((GaiaApplication) getApplication(), indexPageFragment);
        indexPageFragment.setPresenter(accountBooksPresenter);
        // 传递对象给Fragment
        LinearLayout mainContent = (LinearLayout) findViewById(R.id.indexpage_content_id);
        indexPageFragment.transWidget(slipMenuView, mDrawerLayout, mainContent);
        // plane
        ImageView iv_takeOff = (ImageView) findViewById(R.id.main_plane_icon_id);
        iv_takeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeOff();// TODO mvp
            }
        });
        // account id
        TextView tv_accountBookId = (TextView) findViewById(R.id.indexpage_accountbook_id_id);
        tv_accountBookId.setText(Constant.DEFAULT_ACCOUNT_BOOK_ID);
        // add record
        TextView tv_noteOne = (TextView) findViewById(R.id.noteone_id);
        tv_noteOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传递账户ID
                Bundle args = new Bundle();
                args.putString(Constant.AccountBookID,tv_accountBookId.getText().toString());
                Intent intent = new Intent(getApplication(), RecordAddActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        // 今天
        TextView tv_today = (TextView) findViewById(R.id.indexpage_today_id);
        tv_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodayReportMainFragment fragment = new TodayReportMainFragment();
                TodayReportMainPresenter presenter = new TodayReportMainPresenter((GaiaApplication) (getApplication()), fragment);
                fragment.setPresenter(presenter);
                fragment.show(getFragmentManager()," Test ");
            }
        });
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
         *
         * @param drawerView
         */
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        /**
         * 侧滑菜单完全打开
         *
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
        // 按下返回功能键的时候，不是直接对Activity进行弹栈，而是先将菜单视图关闭
        boolean drawerState = mDrawerLayout.isDrawerOpen(slipMenuView);
        if (drawerState) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    void takeOff() {
        Intent intent = new Intent(this, AFunctionDisplayActivity.class);
        startActivity(intent);

    }

    void initChromeDatabase() {
        Stetho.initializeWithDefaults(this);
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
    }
}
