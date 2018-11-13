package com.zxu.index;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zxu.R;
import com.zxu.util.ActivityUtils;

/**
 * 索引页,activity 不是view层，是view model presenter三层的纽带
 */
public class IndexPageActivity extends Activity {
    private IndexPagePresenter indexPagePresenter;


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
    }
}
