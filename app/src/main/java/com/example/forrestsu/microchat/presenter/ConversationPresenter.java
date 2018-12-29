package com.example.forrestsu.microchat.presenter;

import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.beans.Conversation;
import com.example.forrestsu.microchat.model.GetConversationModel;
import com.example.forrestsu.microchat.model.callback.QueryCallBack;
import com.example.forrestsu.microchat.view.ConversationView;

import java.util.List;

public class ConversationPresenter extends BasePresenter<ConversationView> {

    private static final String TAG = "ConversationPresenter";

    /**
     * 获取所有的会话
     */
    public void getAllConversations() {

        if (!isViewAttached()) {
            return;
        }

        GetConversationModel.getAllConversations(new QueryCallBack<List<Conversation>>() {
            @Override
            public void queryResult(List<Conversation> list) {
                if (isViewAttached()) {
                    getIView().getAllConversationsResult(list);
                }
            }
        });
    }
}
