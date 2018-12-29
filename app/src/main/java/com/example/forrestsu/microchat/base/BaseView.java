package com.example.forrestsu.microchat.base;

import android.content.Context;

public interface BaseView {
    /**
     * 显示Toast
     * @param msg 要显示的内容
     */
    void showToast(String msg);

    /*
    显示加载状态
     */
    void showLoading();

    /*
    隐藏加载状态
     */
    void hideLoading();

    /*
    显示错误提示
     */
    void showError();

    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();
}
