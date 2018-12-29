package com.example.forrestsu.microchat.model;

import android.text.TextUtils;
import android.util.Log;

import com.example.forrestsu.microchat.beans.User;
import com.example.forrestsu.microchat.model.callback.RequestCallback;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class ConnectIMServiceModel {

    private static final String TAG = "ConnectIMServiceModel";

    public static void connectIMService(final RequestCallback<String> callback) {

        //登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        //注意，此处不需要另开线程，否则报错，具体原因暂时不明
        final User user = BmobUser.getCurrentUser(User.class);

        if (!TextUtils.isEmpty(user.getObjectId()) &&
                BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            Log.i(TAG, "connectIMService: 连接判断true");
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //连接成功
                        Log.i(TAG, "done: 连接成功：");
                        callback.onSuccess("连接IMService成功");
                        callback.onComplete();
                    } else {
                        //连接失败
                        Log.i(TAG, "done: 连接失败：" + e.getErrorCode() + e.getMessage());
                        callback.onFailure("连接IMService失败");
                        callback.onComplete();
                    }
                }
            });
        } else {
            Log.i(TAG, "connectIMService: 连接判断false");
            callback.onComplete();
        }
    }
}
