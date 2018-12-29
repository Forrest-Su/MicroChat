package com.example.forrestsu.microchat.presenter;

import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.beans.Conversation;
import com.example.forrestsu.microchat.model.GetConversationModel;
import com.example.forrestsu.microchat.model.QueryMessagesModel;
import com.example.forrestsu.microchat.model.SendMessageModel;
import com.example.forrestsu.microchat.model.callback.QueryCallBack;
import com.example.forrestsu.microchat.view.ChatView;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.listener.MessageSendListener;

public class ChatPresenter extends BasePresenter<ChatView> {

    private static final String TAG = "ChatPresenter";

    /**
     * 发送文本消息
     * @param msg
     * @param bmobIMConversation
     * @param listener
     */
    public void sendTextMsg(String msg, BmobIMConversation bmobIMConversation, MessageSendListener listener) {

        if (!isViewAttached()) {
            return;
        }

        SendMessageModel.sendTextMsg(msg, bmobIMConversation, listener);
    }

    /**
     * 发送图片消息
     * @param imagePath
     * @param bmobIMConversation
     * @param listener
     */
    public void sendImageMsg(String imagePath, BmobIMConversation bmobIMConversation, MessageSendListener listener) {
        if (!isViewAttached()) {
            return;
        }
        SendMessageModel.sendImageMsg(imagePath, bmobIMConversation, listener);
    }

    /**
     * 查询消息
     * @param conversation
     * @param message
     */
    public void queryMessages(BmobIMConversation conversation, BmobIMMessage message) {

        if (!isViewAttached()) {
            return;
        }

        QueryMessagesModel.queryMessages(conversation, message, new QueryCallBack<List<BmobIMMessage>>() {
            @Override
            public void queryResult(List<BmobIMMessage> list) {
                if (isViewAttached()) {
                    getIView().queryMessagesResult(list);
                }
            }
        });
    }

}
