package com.example.forrestsu.microchat.view;

import com.example.forrestsu.microchat.base.BaseView;

import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;

public interface ChatView extends BaseView {
    void queryMessagesResult(List<BmobIMMessage> list);
}
