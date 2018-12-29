package com.example.forrestsu.microchat.model;

import com.example.forrestsu.microchat.beans.User;
import com.example.forrestsu.microchat.model.callback.RequestCallback;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginModel {

    private static final String TAG = "LoginModel";

    public static void login(final String phoneNum, final String password, final RequestCallback<String> callback) {

        Runnable loginTask = new Runnable() {
            @Override
            public void run() {
                User.loginByAccount(phoneNum, password, new LogInListener<User>() {

                    @Override
                    public void done(User user, BmobException e) {
                        if (user != null) {
                            if (e == null) {
                                callback.onSuccess("登录成功");
                            } else {
                                callback.onFailure("登录失败");
                            }
                        }
                    }
                });
                callback.onComplete();
            }
        };

        Thread loginThread = new Thread(loginTask);
        loginThread.start();
    }
}
