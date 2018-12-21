package com.zxu.ui.record;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.helpers.ResultHelper;
import com.zxu.model.JC_Account;
import com.zxu.model.JC_Category;
import com.zxu.model.JC_Record;
import com.zxu.ui.category.CategoryPresenter;
import com.zxu.ui.category.SelectCategoryDialog;
import com.zxu.util.CodeConstant;
import com.zxu.util.Constant;
import com.zxu.util.UtilTools;
import com.zxu.util.ZUID;
import com.zxu.widget.CustomDatePicker;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddRecordFragment extends Fragment implements AddRecordContract.View {
    // view
    private TextView tv_selAccount, tv_selCategory, tv_selTime, tv_money, tv_memo;
    private ImageView tv_camera;
    //
    private String mAccountBookID;
    private String waterType;
    // 日期控件
    private CustomDatePicker customDatePicker2;
    //
    private AddRecordContract.Presenter mPresenter;
    //
    private int REQUEST_CODE_CAPTURE_SMALL = 101;
    private String mImgPath;

    /**
     *
     */
    public AddRecordFragment() {
    }

    /**
     * @param tabTitle      title
     * @param accountBookID 账本
     * @param type          消费类型
     * @return
     */
    public static AddRecordFragment newInstance(String tabTitle, String accountBookID, String type) {

        Bundle args = new Bundle();
        args.putString(Constant.TITLE_TAG, tabTitle);
        AddRecordFragment fragment = new AddRecordFragment();
        fragment.setArguments(args);
        fragment.mAccountBookID = accountBookID;
        fragment.waterType = type;

        return fragment;
    }

    /**
     * 拍照后显示缩略图，保存图片到本地
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        if (requestCode == REQUEST_CODE_CAPTURE_SMALL) {//
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            tv_camera.setImageBitmap(bitmap);
            // 保存bitmap到本地
            mImgPath = Environment.getExternalStorageDirectory() + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";

            File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                System.out.println("___________保存的__sd___下_______________________");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_add_fragment_tab, container, false);
        tv_selAccount = (TextView) view.findViewById(R.id.record_add_account_id);
        tv_selCategory = (TextView) view.findViewById(R.id.record_add_category_id);
        tv_selTime = (TextView) view.findViewById(R.id.record_add_time_id);
        tv_money = (TextView) view.findViewById(R.id.record_add_money_id);
        tv_memo = (TextView) view.findViewById(R.id.record_add_memo_id);
        tv_camera = (ImageView) view.findViewById(R.id.record_add_camera_id);
        initWidgets();
        return view;
    }

    /**
     * 初始化按钮式事件
     */
    void initWidgets() {
        // 选择账户类型
        tv_selAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountSelectDialog accountDialog = new AccountSelectDialog(getContext(), CodeConstant.ACCOUNTTYPE, tv_selAccount.getText().toString());
                accountDialog.create(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JC_Account account = accountDialog.getSelectAccount();
                        tv_selAccount.setText(account.getName());
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
        // 选择消费类型
        tv_selCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCategoryDialog dialog = new SelectCategoryDialog();
                dialog.setWaterType(waterType);
                CategoryPresenter presenter = new CategoryPresenter((GaiaApplication) getActivity().getApplication(), dialog);
                dialog.setPresenter(presenter);
                // 监听选择类别事件
                dialog.setOnSelectSmallTypeListener(new SelectCategoryDialog.onSelectSmallTypeListener() {
                    @Override
                    public void selectOn(JC_Category category) {
                        tv_selCategory.setText(category.getName());
                    }
                });
                dialog.show(getActivity().getFragmentManager(), "android");
            }
        });
        // 初始化时间控件
        initDatePicker();
        // 选择时间
        tv_selTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(tv_selTime.getText().toString());
            }
        });
        // 返回按钮监听
        ((AddRecordActivity) getActivity()).setOnCompleteClickListener(new AddRecordActivity.OnCompleteClickListener() {
            @Override
            public void onClickComplete() {
                getActivity().onBackPressed();
            }
        });
        // 拍照按钮
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断有无存储权限
                PackageManager pm = getActivity().getPackageManager();
                boolean permission = (PackageManager.PERMISSION_GRANTED ==
                        pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", getActivity().getPackageName()));
                if (!permission) {
                    UtilTools.showToast(getActivity(), "没有存储权限", 1111);
                    return;
                }
                // 打开相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.resolveActivity(getActivity().getPackageManager());
                startActivityForResult(intent, REQUEST_CODE_CAPTURE_SMALL);
            }
        });

    }

    /**
     * 初始化日期控件
     */
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tv_selTime.setText(now);

        customDatePicker2 = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_selTime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    /**
     * save data TODO
     */
    ResultHelper saveData() {
        // get
        String accountText = tv_selAccount.getText().toString();
        String categoryText = tv_selCategory.getText().toString();
        String timeText = tv_selTime.getText().toString();
        String moneyText = tv_money.getText().toString();
        String memoText = tv_memo.getText().toString();
        //validate
        if (StringUtils.isEmpty(accountText)
                || StringUtils.isEmpty(categoryText)
                || StringUtils.isEmpty(timeText)
                || StringUtils.isEmpty(moneyText)
                ) {
            UtilTools.showToast(getActivity().getApplicationContext(), "账户类型，消费类型，金额，日期不能为空", 1111);
            return new ResultHelper(false);
        }

        // save
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JC_Record record = new JC_Record();
        record.setId(new ZUID().next());
        record.setMoney(moneyText);
        record.setCreateTime(format.format(new Date()));
        record.setWorkTime(timeText);
        record.setCategory(categoryText);
        record.setAccount(accountText);
        record.setWaterType(waterType);
        record.setMemo(memoText);
        record.setBookId(mAccountBookID);
        if (!StringUtils.isEmpty(mImgPath)) {
            record.setImgUrl(mImgPath);
        }
        mPresenter.addRecord(record);

        return new ResultHelper(true);
    }

    /********** setter and getter *********/


    @Override
    public void setPresenter(AddRecordContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
