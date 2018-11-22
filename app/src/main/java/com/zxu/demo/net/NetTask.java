package com.zxu.demo.net;

import com.zxu.application.LoadTasksCallBack;

/**
 * 获取网络数据接口类
 */
public interface NetTask<T> {
    void execute(T data, LoadTasksCallBack callBack);
}
