package com.zxu.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zxu.R;
import com.zxu.demo.fragment.BottomDialogFragment;
import com.zxu.demo.ipinfo.IpInfoActivity;
import com.zxu.ui.IndexPageActivity;
import com.zxu.ui.accountbooks.AddAccountBookDialog;
import com.zxu.util.Constant;
import com.zxu.util.UtilTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Demo显示入口
 */
public class AFunctionDisplayActivity extends Activity {
    int REQUEST_CODE_CAPTURE_SMALL = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_demo_aafunctiondisplay);
    }

    /**
     * 多线程，计时器
     *
     * @param view 视图
     */
    void showTimer(View view) {
        Intent intent = new Intent(this, TheTimerActivity.class);
        startActivity(intent);
    }

    /**
     * 保存数据
     *
     * @param view 视图
     */
    void keySave(View view) {
        Intent intent = new Intent(this, KeySaveActivity.class);
        startActivity(intent);
    }

    /**
     * 保存数据
     *
     * @param view 视图
     */
    void storeTheData(View view) {
        Intent intent = new Intent(this, StoreTheDataActivity.class);
        startActivity(intent);
    }

    /**
     * 保存数据到数据库
     *
     * @param view 视图
     */
    void databaseDemo(View view) {
        Intent intent = new Intent(this, PersonActivity.class);
        startActivity(intent);
    }

    /**
     * 登陆跳转到主界面
     *
     * @param view 视图
     */
    void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, Constant.LoginRequestCode);
    }


    /**
     * 网络请求
     *
     * @param view 视图
     */
    void OkHttpTest(View view) {
        Request.Builder requestBuilder = new Request.Builder().url("http://ip.taobao.com/ipSearch.html?ipAddr=220.115.231.8");
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("TAG", str);
            }
        });
    }

    /**
     * 展示mvp框架
     *
     * @param view 视图
     */
    void showMVP(View view) {
        Intent intent = new Intent(this, IpInfoActivity.class);
        startActivity(intent);
    }

    /**
     * 主界面 侧滑菜单
     *
     * @param view 视图
     */
    void showMainViewActivity(View view) {
        Intent intent = new Intent(this, MainViewActivity.class);
        startActivity(intent);
    }

    /**
     * 主界面 侧滑菜单2
     *
     * @param view 视图
     */
    void showMainViewActivity2(View view) {
        Intent intent = new Intent(this, CeHuaMainActivity.class);
        startActivity(intent);
    }

    /**
     * 主界面
     *
     * @param view 视图
     */
    void showMvp2(View view) {
        Intent intent = new Intent(this, IndexPageActivity.class);
        startActivity(intent);
    }

    /**
     * 主界面
     *
     * @param view 视图
     */
    void showAddAccountBook(View view) {
    }

    /**
     * DialogFragment
     *
     * @param view 视图
     */
    void showDialogFragment(View view) {
        new AddAccountBookDialog().show(getFragmentManager(), "WHo is W");
    }

    /**
     * showTabLayout
     *
     * @param view 视图
     */
    void showTabLayout(View view) {
        Intent intent = new Intent(this, TabLayoutActivity.class);
        startActivity(intent);
    }

    /**
     * 底部弹窗
     *
     * @param view 视图
     */
    void showBottomDialog(View view) {
        BottomDialogFragment fragment = new BottomDialogFragment();
        fragment.setOnDialogListener(new BottomDialogFragment.OnDialogListener() {
            @Override
            public void onDialogClick(String person) {
                UtilTools.showToast(getApplicationContext(), person, 1111);
            }
        });

        fragment.show(getFragmentManager(), "android");
    }

    /**
     * ExpandableListView 二级类别树
     *
     * @param view 视图
     */
    void showExpandableListView(View view) {
        Intent intent = new Intent(this, ExpandableListViewActivity.class);
        startActivity(intent);
    }

    /**
     * showTimeBar
     *
     * @param view 视图
     */
    void showTimeBar(View view) {
        Intent intent = new Intent(this, TimeBarActivity.class);
        startActivity(intent);
    }

    /**
     * 相机
     *
     * @param view 视图
     */
    void demoCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    /**
     * 拍照返回缩略图
     *
     * @param view 视图
     */
    void cameraAndReturn(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.resolveActivity(getPackageManager());
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_SMALL);
//        intent.resolveActivity(packageManager)?.let {
//            startActivityForResult(intent, REQUEST_CODE_CAPTURE_SMALL)
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            String name = bundle.getString(Constant.NAME, "");
//            String pwd = bundle.getString(Constant.PASSWORD, "");
//            String str = "用户名：" + name + ";密码：" + pwd;
//            TextView tv = findViewById(R.id.tvLogin_id);
//            tv.setText(str);
//        }
        if (requestCode == REQUEST_CODE_CAPTURE_SMALL) {//
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            ImageView iv_result = findViewById(R.id.ivResult);
            iv_result.setImageBitmap(bitmap);
            // 保存bitmap到本地
            File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
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
            Toast.makeText(AFunctionDisplayActivity.this,"保存已经至"+Environment.getExternalStorageDirectory()+"下", Toast.LENGTH_SHORT).show();
        }

    }
}
