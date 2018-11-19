package com.zxu.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.index.IndexPageActivity;
import com.zxu.index.accountbooks.AddAccountBookDialog;
import com.zxu.ipinfo.IpInfoActivity;
import com.zxu.util.Constant;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_aafunctiondisplay);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String name = bundle.getString(Constant.NAME, "");
            String pwd = bundle.getString(Constant.PASSWORD, "");
            String str = "用户名：" + name + ";密码：" + pwd;
            TextView tv = (TextView) findViewById(R.id.tvLogin_id);
            tv.setText(str);
        }
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
}
