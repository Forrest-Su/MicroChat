package com.example.forrestsu.microchat.model;

import android.util.Log;

import com.example.forrestsu.microchat.beans.Friend;
import com.example.forrestsu.microchat.beans.User;
import com.example.forrestsu.microchat.model.callback.RequestCallback;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetAllContactsModel {

    private static final String TAG = "GetAllContactsModel";

    public static void getAllContacts(final RequestCallback<List<Friend>> callback) {
        final User user = BmobUser.getCurrentUser(User.class);
         Runnable getContactsTask = new Runnable() {
             @Override
             public void run() {
                 BmobQuery<Friend> query = new BmobQuery<>();
                 query.addWhereEqualTo("user", user.getObjectId());
                 query.include("user,friendUser");
                 query.findObjects(new FindListener<Friend>() {
                     @Override
                     public void done(List<Friend> list, BmobException e) {
                         if (e == null) {
                             callback.onSuccess(list);
                             Log.i(TAG, "done: 获取联系人成功" + list.size());
                         } else {
                             callback.onFailure("获取联系人失败");
                             Log.i(TAG, "done: 获取联系人失败" + e.getErrorCode() + e.getMessage());
                         }
                     }
                 });
                 callback.onComplete();
             }
         };

         Thread getContactThread = new Thread(getContactsTask);
         getContactThread.start();
    }
}
