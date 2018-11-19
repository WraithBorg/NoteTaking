package com.zxu.record;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.demo.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class AddRecordActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final int MOVABLE_COUNT = 3;
//    private int tabCount = 3;
    private String[] tabTitle = new String[]{"支出","收入","转账"};
    private List<String> tabs;
    private List<Fragment> fragments;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_add);
        tabLayout = (TabLayout) findViewById(R.id.record_add_tab_id);
        viewPager = (ViewPager) findViewById(R.id.record_add_pager_id);
        initDatas();
        initViewPager();
        initTabLayout();
    }

    /**
     *
     */
    private void initDatas() {
        tabs = new ArrayList<>();
        for (String tabName : tabTitle) {
            tabs.add(tabName);
        }

        fragments = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) {
            fragments.add(TabFragment.newInstance(tabs.get(i)));
        }
    }
    /**
     *
     */
    private void initViewPager() {
        viewPager.setAdapter(new AddRecordPagerAdapter(getSupportFragmentManager()));
    }
    /**
     * viewPager Adapter
     */
    private class AddRecordPagerAdapter extends FragmentPagerAdapter {

        public AddRecordPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
    /**
     *
     */
    private void initTabLayout() {
        //MODE_FIXED标签栏不可滑动，各个标签会平分屏幕的宽度
        tabLayout.setTabMode(tabTitle.length <= MOVABLE_COUNT ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
        //指示条的颜色
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.holo_blue_dark));
        tabLayout.setSelectedTabIndicatorHeight((int) getResources().getDimension(R.dimen.indicatorHeight));
        //关联tabLayout和ViewPager,两者的选择和滑动状态会相互影响
        tabLayout.setupWithViewPager(viewPager);
        //自定义标签布局
        for (int i = 0; i < tabs.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.record_add_tabview_main, tabLayout, false);
            tv.setText(tabs.get(i));
            tab.setCustomView(tv);
        }
    }


}
