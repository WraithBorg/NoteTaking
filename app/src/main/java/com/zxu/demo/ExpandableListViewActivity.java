package com.zxu.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.zxu.R;
import com.zxu.demo.adapter.DemoExpandableListAdapter;
import com.zxu.util.UtilTools;

public class ExpandableListViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_expandablelist_main);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandable_list);
        DemoExpandableListAdapter adapter = new DemoExpandableListAdapter(this);
        listView.setAdapter(adapter);

        //  设置分组项的点击监听事件
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                UtilTools.showToast(getApplicationContext(),"setOnGroupClickListener",1111);
                return false;
            }
        });
        //  设置子选项点击监听事件
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                UtilTools.showToast(getApplicationContext(), "setOnChildClickListener", 1111);
                return true;
            }
        });
    }
}
