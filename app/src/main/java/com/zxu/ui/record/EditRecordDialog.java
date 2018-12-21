package com.zxu.ui.record;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Account;
import com.zxu.model.JC_Category;
import com.zxu.model.JC_Record;
import com.zxu.ui.category.CategoryPresenter;
import com.zxu.ui.category.SelectCategoryDialog;
import com.zxu.util.CodeConstant;
import com.zxu.util.UtilTools;
import com.zxu.widget.CustomDatePicker;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditRecordDialog extends DialogFragment implements EditRecordContract.View {
    private EditRecordContract.Presenter mPresenter;
    // 日期控件
    private CustomDatePicker customDatePicker2;
    // view
    private TextView tv_selAccount, tv_selCategory, tv_selTime, tv_money, tv_memo, tv_save, tv_delete;
    ImageView iv_confirm, iv_back, tv_camera;
    // java
    private JC_Record mRecord;
    //
    private int REQUEST_CODE_CAPTURE_SMALL = 101;
    private String mImgPath;

    /**
     * @param presenter
     */
    @Override
    public void setPresenter(EditRecordContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);//设置全屏
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_edit, null);
        tv_selAccount = (TextView) view.findViewById(R.id.record_add_account_id);
        tv_selCategory = (TextView) view.findViewById(R.id.record_add_category_id);
        tv_selTime = (TextView) view.findViewById(R.id.record_add_time_id);
        tv_money = (TextView) view.findViewById(R.id.record_add_money_id);
        tv_memo = (TextView) view.findViewById(R.id.record_add_memo_id);
        iv_confirm = (ImageView) view.findViewById(R.id.report_edit_complete_id);
        iv_back = (ImageView) view.findViewById(R.id.report_edit_back_id);
        tv_camera = (ImageView) view.findViewById(R.id.record_edit_camera_id);
        //assignment
        tv_selAccount.setText(mRecord.getAccount());
        tv_selCategory.setText(mRecord.getCategory());
        tv_selTime.setText(mRecord.getWorkTime());
        tv_money.setText(mRecord.getMoney());
        tv_memo.setText(mRecord.getMemo());
        //
        tv_save = (TextView) view.findViewById(R.id.record_edit_save_id);
        tv_delete = (TextView) view.findViewById(R.id.record_edit_delete_id);
        //
        initWidgets();
        return view;
    }

    /**
     *
     */
    void initWidgets() {
        // 保存
        iv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get
                String accountText = tv_selAccount.getText().toString();
                String categoryText = tv_selCategory.getText().toString();
                String timeText = tv_selTime.getText().toString();
                String moneyText = tv_money.getText().toString();
                String memoText = tv_memo.getText().toString();
                // validate
                if (StringUtils.isEmpty(accountText)
                        || StringUtils.isEmpty(categoryText)
                        || StringUtils.isEmpty(timeText)
                        || StringUtils.isEmpty(moneyText)
                        ) {
                    UtilTools.showToast(getActivity().getApplicationContext(), "账户类型，消费类型，金额，日期不能为空", 1111);
                }
                // save
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mRecord.setMoney(moneyText);
                mRecord.setCreateTime(format.format(new Date()));
                mRecord.setWorkTime(timeText);
                mRecord.setCategory(categoryText);
                mRecord.setAccount(accountText);
                mRecord.setMemo(memoText);
                if (!StringUtils.isEmpty(mImgPath)) {
                    mRecord.setImgUrl(mImgPath);
                }
                mPresenter.editRecord(mRecord);
                //
                dialogListener.onDismiss();
                dismiss();
            }
        });
        // 保存
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_confirm.callOnClick();
            }
        });
        // 删除
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.delete(mRecord);
                dialogListener.onDismiss();
                dismiss();
            }
        });
        // 返回
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onDismiss();
                dismiss();
            }
        });
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
                dialog.setCostType(mRecord.getType());
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
        // 显示图片 TODO 内存溢出优化
        if (!StringUtils.isEmpty(mRecord.getImgUrl())) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(mRecord.getImgUrl());
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                tv_camera.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // 拍照
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
            File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            mImgPath = file.getPath();
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

    /**
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }

    /**
     *
     */
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
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.vifrification);
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
     * @param mRecord
     */
    public void setRecord(JC_Record mRecord) {
        this.mRecord = mRecord;
    }

    //
    private DialogListener dialogListener;

    public interface DialogListener {
        void onDismiss();
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }
}
