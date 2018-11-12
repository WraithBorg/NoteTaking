package com.zxu.application;

/**
 * 给View绑定Presenter
 *
 * @param <T>
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
}
