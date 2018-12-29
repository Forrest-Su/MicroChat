package com.example.forrestsu.microchat.presenter;

import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.model.GetVerificationCodeModel;
import com.example.forrestsu.microchat.model.callback.RequestCallback;
import com.example.forrestsu.microchat.utils.PhoneNumber;
import com.example.forrestsu.microchat.view.GetVerificationCodeView;

public class GetVerificationCodePresenter extends BasePresenter<GetVerificationCodeView> {

    private static final String TAG = "GetVerificationCodePres";

    public void getVerificationCode(String phoneNum) {

        if (!isViewAttached()) {
            return;
        }

        //检查手机号码格式
        if (!PhoneNumber.verifyPhoneNumber(phoneNum)) {
            getIView().showToast("请输入正确的手机号码");
            return;
        }

        if (isViewAttached()) {
            getIView().showLoading();
            getIView().setGetCodeBTClickable(false);
        }

        //获取验证码
        GetVerificationCodeModel.getSMSCode(phoneNum, new RequestCallback<String>() {
            @Override
            public void onSuccess(String msg) {
                if (isViewAttached()) {
                    getIView().showToast(msg);
                    getIView().getCodeSuccess();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached()) {
                    getIView().showToast(msg);
                    getIView().setGetCodeBTClickable(true);
                }
            }

            @Override
            public void onError() {
                if (isViewAttached()) {
                    getIView().showToast("获取验证码失败");
                    getIView().setGetCodeBTClickable(true);
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
