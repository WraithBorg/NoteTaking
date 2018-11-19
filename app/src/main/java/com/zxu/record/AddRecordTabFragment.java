package com.zxu.record;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxu.R;

import java.util.Random;

public class AddRecordTabFragment extends Fragment {
    public static final String TITLE_TAG = "tabTitle";

    public static AddRecordTabFragment newInstance(String tabTitle) {

        Bundle args = new Bundle();

        AddRecordTabFragment fragment = new AddRecordTabFragment();
        args.putString(TITLE_TAG, tabTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_add_fragment_tab, container, false);
        return view;
    }
}
