package com.example.forrestsu.microchat.model;

import com.example.forrestsu.microchat.beans.User;

import cn.bmob.newim.BmobIM;

public class LoginOutModel {

    private static final String TAG = "LoginOutModel";

    public static void loginOut() {
        //断开与IMService的连接
        BmobIM.getInstance().disConnect();
        User.logOut();
    }
}
