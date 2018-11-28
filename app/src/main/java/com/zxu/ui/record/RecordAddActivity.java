package com.zxu.ui.record;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.util.UtilTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordAddActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final int MOVABLE_COUNT = 3;
    private String[] tabTitle = new String[]{"支出", "收入", "转账"};
    private List<String> tabs;
    private List<Fragment> fragments;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_add);
        //
        tabLayout = (TabLayout) findViewById(R.id.record_add_tab_id);
        viewPager = (ViewPager) findViewById(R.id.record_add_pager_id);
        ImageView iv_back = (ImageView) findViewById(R.id.record_add_back_id);
        ImageView iv_complete = (ImageView) findViewById(R.id.record_add_complete_id);
        //
        // return
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // complete
        iv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordAddTabFragment currentFragment = (RecordAddTabFragment)((AddRecordPagerAdapter) viewPager.getAdapter()).currentFragment;
                String titleName = currentFragment.getTitleName();
                int currentItem = viewPager.getCurrentItem();
                currentFragment.saveData();
                onCompleteClickListener.onClickComplete();
            }
        });
        //
        initDatas();
        initViewPager();
        initTabLayout();
    }

    /**
     *
     */
    private void initDatas() {
        tabs = Arrays.asList(tabTitle);
        fragments = new ArrayList<>();
        for (String title : tabs) {
            fragments.add(RecordAddTabFragment.newInstance(title));
        }
    }

    /**
     *
     */
    private void initViewPager() {
        viewPager.setAdapter(new AddRecordPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println(1);
            }
        });
    }

    /**
     * viewPager Adapter
     */
    private class AddRecordPagerAdapter extends FragmentPagerAdapter {
        public Fragment currentFragment;
        public AddRecordPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            currentFragment = (RecordAddTabFragment) object;
            super.setPrimaryItem(container, position, object);
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

    // 监听viewPage外部 点击完成按钮事件
    public OnCompleteClickListener onCompleteClickListener;

    public interface OnCompleteClickListener {
        void onClickComplete();
    }

    public void setOnCompleteClickListener(OnCompleteClickListener onCompleteClickListener) {
        this.onCompleteClickListener = onCompleteClickListener;
    }
}
