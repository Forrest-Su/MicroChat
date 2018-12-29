package com.example.forrestsu.microchat.model;

import com.example.forrestsu.microchat.model.callback.QueryCallBack;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;

public class QueryMessagesModel {

    public static void queryMessages(BmobIMConversation conversation, BmobIMMessage message,
                                     final QueryCallBack<List<BmobIMMessage>> callBack) {
        conversation.queryMessages(message, 10, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if (e == null) {
                    if (null != list && list.size() > 0) {
                        callBack.queryResult(list);
                    } else {
                        callBack.queryResult(null);
                    }
                } else {
                    callBack.queryResult(null);
                }
            }
        });
    }
}
