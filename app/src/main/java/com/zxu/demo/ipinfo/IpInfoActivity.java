package com.zxu.demo.ipinfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zxu.R;
import com.zxu.demo.net.IpInfoTask;
import com.zxu.util.ActivityUtil;

/**
 * Activity不是View层，而是View Model 和 Presenter三层的纽带
 */
public class IpInfoActivity extends Activity {
    private IpInfoPresenter ipInfoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_ipinfo);
        IpInfoFragment ipInfoFragment = (IpInfoFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if (ipInfoFragment == null) {
            // 新建 IpInfoFragment ,并将IpInfoFragment添加到IpInfoActivity中
            ipInfoFragment = IpInfoFragment.newInstance();//1
            ActivityUtil.addFragmentToActivity(getFragmentManager(), ipInfoFragment, R.id.contentFrame);//2
        }
        // 创建IpInfoTask,并将IpInfoFragment作为参数传入IpInfoPresenter
        IpInfoTask ipInfoTask = IpInfoTask.getInstance();
        ipInfoPresenter = new IpInfoPresenter(ipInfoFragment, ipInfoTask);
        // 将IpInfoPresenter注入到IpInfoFragment中
        // IpInfoPresenter 和 IpInfoFragment是互相注入的
        ipInfoFragment.setPresenter(ipInfoPresenter);//3

    }
}
