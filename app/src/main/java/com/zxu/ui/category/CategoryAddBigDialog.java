package com.zxu.ui.category;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Category;
import com.zxu.util.UtilTools;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class CategoryAddBigDialog extends DialogFragment implements CategoryAddBigContract.View {
    private CategoryAddBigContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.category_add_big, null);
        ImageView iv_back = (ImageView) view.findViewById(R.id.category_add_big_back_id);
        TextView tv_next = (TextView) view.findViewById(R.id.category_add_big_next_id);
        EditText et_name = (EditText) view.findViewById(R.id.category_add_big_type_name_id);

        // return
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // next
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate
                if (StringUtils.isEmpty(et_name.getText().toString())) {
                    UtilTools.showToast(getActivity().getApplicationContext(), "请输入名称", 1000);
                    return;
                }
                // save data
                JC_Category category = new JC_Category();
                String cId = UUID.randomUUID().toString();
                category.setId(cId);
                category.setName(et_name.getText().toString());
                category.setType(0);
                mPresenter.addBigCategory((GaiaApplication) getActivity().getApplication(), category);
                // close
                dismiss();
                // next dialog
                Bundle bundle = new Bundle();
                bundle.putSerializable("fatherId",cId);

                CategoryAddSmallDialog dialog = new CategoryAddSmallDialog();
                CategoryAddSmallPresenter presenter = new CategoryAddSmallPresenter((GaiaApplication) getActivity().getApplication(),dialog);
                dialog.setPresenter(presenter);
                dialog.setArguments(bundle);
                dialog.show(getActivity().getFragmentManager(), "android");

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置位置在底部
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        // 设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresenter(CategoryAddBigContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
