package com.zxu.ui.category;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
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
import com.zxu.model.JC_Category;
import com.zxu.util.CodeConstant;
import com.zxu.util.ZUID;

public class AddCategorySmallDialog extends DialogFragment implements AddCategorySmallContract.View {
    private AddCategorySmallContract.Presenter mPresenter;
    private String waterType;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.category_add_small, null);
        ImageView iv_back = (ImageView) view.findViewById(R.id.category_add_big_back_id);
        TextView tv_next = (TextView) view.findViewById(R.id.category_add_big_next_id);
        EditText et_name = (EditText) view.findViewById(R.id.category_add_big_type_name_id);
        // get FatherId
        Bundle bundle = getArguments();
        String fatherId = (String) bundle.getSerializable("fatherId");
        // return
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, CodeConstant.DIALOGWAITTIME);
            }
        });
        // complete
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, CodeConstant.DIALOGWAITTIME);
                // save data
                ZUID zuid = new ZUID();
                JC_Category category = new JC_Category(zuid.next(),et_name.getText().toString(),fatherId,1,waterType,zuid.next(),null);
                mPresenter.addSmallCategory(category);
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
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.vifrification);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresenter(AddCategorySmallContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setWaterType(String mSpecies) {
        this.waterType = mSpecies;
    }
}
