package com.example.forrestsu.microchat.model;

import com.example.forrestsu.microchat.beans.Conversation;
import com.example.forrestsu.microchat.beans.PrivateConversation;
import com.example.forrestsu.microchat.model.callback.QueryCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;

public class GetConversationModel {

    private static final String TAG = "GetAllConversationsMode";

    public static void getAllConversations(QueryCallBack<List<Conversation>> callBack) {

        List<Conversation> conversationList = new ArrayList<>();
        conversationList.clear();
        List<BmobIMConversation> list =BmobIM.getInstance().loadAllConversation();

        if(list!=null && list.size()>0){
            for (BmobIMConversation item:list){
                switch (item.getConversationType()){
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }

        /*
        //添加新朋友会话-获取好友请求表中最新一条记录
        List<NewFriend> friends = NewFriendManager.getInstance(getActivity()).getAllNewFriend();
        if(friends!=null && friends.size()>0){
            conversationList.add(new NewFriendConversation(friends.get(0)));
        }
        */

        //重新排序
        Collections.sort(conversationList);
        callBack.queryResult(conversationList);
        //return conversationList;
    }
}
