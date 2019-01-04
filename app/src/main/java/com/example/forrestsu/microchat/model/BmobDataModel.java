package com.example.forrestsu.microchat.model;

import com.example.forrestsu.microchat.model.callback.RequestCallback;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class BmobDataModel {

    public static void save(final BmobObject object, final RequestCallback<String> callback) {

        Runnable saveTask = new Runnable() {
            @Override
            public void run() {
                object.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            callback.onSuccess("上传成功");
                        } else {
                            callback.onFailure("上传失败");
                        }
                    }
                });
                callback.onComplete();
            }
        };

        Thread saveThread = new Thread(saveTask);
        saveThread.start();
    }
}
