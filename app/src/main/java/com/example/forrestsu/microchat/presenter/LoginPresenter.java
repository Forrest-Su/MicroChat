package com.example.forrestsu.microchat.presenter;

import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.model.LoginModel;
import com.example.forrestsu.microchat.model.callback.RequestCallback;
import com.example.forrestsu.microchat.utils.Password;
import com.example.forrestsu.microchat.utils.PhoneNumber;
import com.example.forrestsu.microchat.view.LoginView;

public class LoginPresenter extends BasePresenter<LoginView> {

    private static final String TAG = "LoginPresenter";

    public void login(String phoneNum, String password) {

        if (!isViewAttached()) {
            return;
        }

        //检查手机号码格式
        if (!PhoneNumber.verifyPhoneNumber(phoneNum)) {
            getIView().showToast("请输入正确的手机号码");
            return;
        }

        //检查密码格式
        if (!Password.verifyPassword(password)) {
            getIView().showToast("请输只包含“字母”、“数字”、“.”的6-16位密码");
            return;
        }

        if (isViewAttached()) {
            getIView().showLoading();
            getIView().setLoginBTClickable(false);
        }

        LoginModel.login(phoneNum, password, new RequestCallback<String>() {
            @Override
            public void onSuccess(String msg) {
                if (isViewAttached()) {
                    getIView().showToast(msg);
                    getIView().loginSuccess();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached()) {
                    getIView().showToast(msg);
                    getIView().setLoginBTClickable(true);
                }
            }

            @Override
            public void onError() {
                if (isViewAttached()) {
                    getIView().showToast("登录失败");
                    getIView().setLoginBTClickable(true);
                }
            }

            @Override
            public void onComplete() {
                getIView().hideLoading();
            }
        });

    }
}
