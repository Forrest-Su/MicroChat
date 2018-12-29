package com.example.forrestsu.microchat.utils;


import android.text.TextUtils;

public class Password {

    /**
     * 核对密码格式：由大小写字母+数字+下划线组成的6-16位组合
     * @param password 密码
     * @return 布尔值
     */
    public static boolean verifyPassword(String password) {

        String passwordRegex = "[A-Za-z0-9.]{6,16}";

        return (!TextUtils.isEmpty(password) && password.matches(passwordRegex));
    }
}
