package com.zxu.demo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.util.Constant;

import java.util.Random;

public class TabFragment extends Fragment {

    private int[] colors = {0xff009999, 0xffc6e2ff, 0xff668b8b, 0xff7A67EE, 0xffCD853F, 0xffEECFA1};

    public static TabFragment newInstance(String tabTitle) {

        Bundle args = new Bundle();

        TabFragment fragment = new TabFragment();
        args.putString(Constant.TITLE_TAG, tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aaa_demo_tab_fragment_tab, container, false);

        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setBackgroundColor(colors[new Random().nextInt(colors.length)]);

        if (getArguments() != null) tv.setText(getArguments().getString(Constant.TITLE_TAG));

        return view;
    }
}
