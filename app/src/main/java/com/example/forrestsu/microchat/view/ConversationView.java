package com.example.forrestsu.microchat.view;

import com.example.forrestsu.microchat.base.BaseView;
import com.example.forrestsu.microchat.beans.Conversation;

import java.util.List;

public interface ConversationView extends BaseView {
    void getAllConversationsResult(List<Conversation> list);
}
