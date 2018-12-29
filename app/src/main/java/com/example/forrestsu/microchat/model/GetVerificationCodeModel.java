package com.example.forrestsu.microchat.model;

import android.util.Log;

import com.example.forrestsu.microchat.model.callback.RequestCallback;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class GetVerificationCodeModel {

    private static final String TAG = "GetCodeModel";

    public static void getSMSCode(final String phoneNum, final RequestCallback<String> callback) {

        Runnable getSMSCodeTask = new Runnable() {
            @Override
            public void run() {
                BmobSMS.requestSMSCode(phoneNum, "", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            callback.onSuccess("获取验证码成功");
                        } else {
                            callback.onFailure("获取验证码失败");
                        }
                    }
                });
                callback.onComplete();
            }
        };

        Thread getSMSCodeThread = new Thread(getSMSCodeTask);
        getSMSCodeThread.start();
    }
}
