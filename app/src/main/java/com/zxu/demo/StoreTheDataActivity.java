package com.zxu.demo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zxu.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件存取demo:内部存储和外部存储
 */
public class StoreTheDataActivity extends Activity {
    String filename = "zxu4862";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keysave);

    }

    /**
     * 写文本到文件
     *
     * @param view 按钮
     */
    public void btnWriteClick(View view) {
        EditText etWrite = (EditText) findViewById(R.id.etWrite_id);
        String string = etWrite.getText().toString();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从URL提取文件，创建app内部存储缓存目录的名字创建文件
     */
    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 读文件
     *
     * @param view 按钮
     */
    public void btnReadClick(View view) {
        String path = this.getFilesDir() + "/" + filename;//获取完整路径
        String content = "";//文件内容字符串
        // 打开文件
        File file = new File(path);
        try {
            InputStream inputStream = new FileInputStream(file);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                // 分行读取
                while ((line = bufferedReader.readLine()) != null) {
                    content += line + "\n";
                }
                inputStream.close();

            }
        } catch (FileNotFoundException e) {
            Log.d("TestFile", "文件不存在");
        } catch (Exception e) {
            Log.d("TestFile", e.getMessage());
        }
        EditText etRead = (EditText) findViewById(R.id.etRead_id);
        etRead.setText(content);
    }

}
