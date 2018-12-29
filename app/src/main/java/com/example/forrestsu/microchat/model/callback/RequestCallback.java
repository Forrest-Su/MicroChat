package com.example.forrestsu.microchat.model.callback;

public interface RequestCallback<T> {

    /**
     * 请求成功
     * @param data 请求得到的数据
     */
    void onSuccess(T data);

    /**
     * 请求失败
     * 由于账户或密码错误
     * @param msg 返回的message
     */
    void onFailure(String msg);

    /**
     * 请求出错
     * 由于网络或者其他原因
     */
    void onError();

    /**
     * 请求完成，无论成功失败或是出错
     */
    void onComplete();
}
