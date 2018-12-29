package com.example.forrestsu.microchat.base;

public class BasePresenter<V extends BaseView> {

    //绑定的view接口
    private V iView;

    /**
     * 绑定iView
     * 为了防止Activity被意外销毁引发空指针，这里不直接将iView作为构造参数
     * 绑定和解绑方法写在Activity的生命周期中
     */
    public void attachView(V iView) {
        this.iView = iView;
    }

    /**
     * 解绑iView
     */
    public void detachView() {
        this.iView = null;
    }

    /**
     * 是否与iView建立连接
     * 每次调用业务请求时都要先调用此方法检查是否于iView建立连接
     * @return
     */
    public boolean isViewAttached() {
        return iView != null;
    }

    /**
     * 获取连接的iView
     * @return
     */
    public V getIView() {
        return iView;
    }
}
