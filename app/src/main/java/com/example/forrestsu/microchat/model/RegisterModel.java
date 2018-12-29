package com.example.forrestsu.microchat.model;

import com.example.forrestsu.microchat.beans.User;
import com.example.forrestsu.microchat.model.callback.RequestCallback;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterModel {

    private static final String TAG = "RegisterModel";

    public static void register(final String phoneNum, final String password, final String verificationCode, final RequestCallback<String> callback) {


        Runnable registerTask = new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setMobilePhoneNumber(phoneNum);
                user.setPassword(password);
                user.signOrLogin(verificationCode, new SaveListener<User>() {

                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            callback.onSuccess("注册成功");
                        } else {
                            callback.onFailure("注册失败");
                        }
                    }
                });
                callback.onComplete();
            }
        };
        Thread registerThread = new Thread(registerTask);
        registerThread.start();
    }
}
