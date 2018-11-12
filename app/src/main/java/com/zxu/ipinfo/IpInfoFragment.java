package com.zxu.ipinfo;

import android.app.Dialog;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.IpData;
import com.zxu.model.IpInfo;
import com.zxu.util.UtilTools;

/**
 * 实现View接口，用来接收InInfoPresenter的回调 并 并更新界面
 */
public class IpInfoFragment extends Fragment implements IpInfoContract.View {

    private TextView tv_country;
    private TextView tv_area;
    private TextView tv_city;
    private Button bt_ipInfo;
    private Dialog mDialog;

    private IpInfoContract.Presenter mPresenter;

    public static IpInfoFragment newInstance(){
        return new IpInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.demo_fragment_ipinfo, container, false);
        tv_country = (TextView) root.findViewById(R.id.tv_country);
        tv_area = (TextView) root.findViewById(R.id.tv_area);
        tv_city = (TextView) root.findViewById(R.id.tv_city);
        bt_ipInfo = (Button) root.findViewById(R.id.bt_ipinfo);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("获取数据中");
        bt_ipInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取数据
                mPresenter.getIpInfo("112.112.112.114");//2
            }
        });

    }
    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {//1
        // 注入IpInfoPresenter
        mPresenter = presenter;
    }
    @Override
    public void setIpInfo(IpInfo ipInfo) {
        if (ipInfo!=null&&ipInfo.getData()!=null){
            IpData ipData = ipInfo.getData();
            tv_country.setText(ipData.getCountry());
            tv_area.setText(ipData.getArea());
            tv_city.setText(ipData.getCity());
        }
    }

    @Override
    public void showLoading() {
        mDialog.show();

    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        UtilTools.showToast(getActivity().getApplicationContext(),"网络出错",2000);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


}
