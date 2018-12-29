package com.example.forrestsu.microchat.presenter;

import android.text.TextUtils;

import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.model.RegisterModel;
import com.example.forrestsu.microchat.model.callback.RequestCallback;
import com.example.forrestsu.microchat.utils.Password;
import com.example.forrestsu.microchat.utils.PhoneNumber;
import com.example.forrestsu.microchat.view.RegisterView;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private static final String TAG = "RegisterPresenter";

    public void register(String phoneNum, String password, String verificationCode) {

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

        //检查验证码
        if (TextUtils.isEmpty(verificationCode)) {
            getIView().showToast("请输入验证码");
            return;
        }

        if (isViewAttached()) {
            getIView().showLoading();
            getIView().setRegisterBTClickable(false);
        }

        //注册
        RegisterModel.register(phoneNum, password, verificationCode, new RequestCallback<String>() {
            @Override
            public void onSuccess(String msg) {
                if (isViewAttached()) {
                    getIView().showToast(msg);
                    getIView().registerSuccess();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached()) {
                    getIView().showToast(msg);
                    getIView().setRegisterBTClickable(true);
                }
            }

            @Override
            public void onError() {
                if (isViewAttached()) {
                    getIView().showToast("注册失败");
                    getIView().setRegisterBTClickable(true);
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    getIView().hideLoading();
                }
            }
        });

    }
}
