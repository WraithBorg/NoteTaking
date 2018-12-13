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
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Category;
import com.zxu.util.CodeConstant;
import com.zxu.util.UtilTools;
import com.zxu.util.ZUID;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class AddCategoryBigDialog extends DialogFragment implements AddCategoryBigContract.View {
    private AddCategoryBigContract.Presenter mPresenter;
    private String mSpecies;
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, CodeConstant.DIALOGWAITTIME);
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
                ZUID zuid = new ZUID();
                String cId = zuid.next();
                JC_Category category = new JC_Category(cId,et_name.getText().toString(),"",0,mSpecies,zuid.next(),null);
                mPresenter.addBigCategory(category);
                // next dialog
                Bundle bundle = new Bundle();
                bundle.putSerializable("fatherId",cId);

                AddCategorySmallDialog dialog = new AddCategorySmallDialog();
                dialog.setmSpecies(mSpecies);
                AddCategorySmallPresenter presenter = new AddCategorySmallPresenter((GaiaApplication) getActivity().getApplication(),dialog);
                dialog.setPresenter(presenter);
                dialog.setArguments(bundle);
                dialog.show(getActivity().getFragmentManager(), "android");
                // close
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, CodeConstant.DIALOGWAITTIME);
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
    public void setPresenter(AddCategoryBigContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setmSpecies(String mSpecies) {
        this.mSpecies = mSpecies;
    }
}
