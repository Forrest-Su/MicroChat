package com.example.forrestsu.microchat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;;

import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.adapter.ConversationAdapter;
import com.example.forrestsu.microchat.base.BaseFragment;
import com.example.forrestsu.microchat.beans.Conversation;
import com.example.forrestsu.microchat.presenter.ConversationPresenter;
import com.example.forrestsu.microchat.view.ConversationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.event.MessageEvent;

public class ConversationFragment extends BaseFragment implements ConversationView,
        ConversationAdapter.OnItemClickListener, ConversationAdapter.OnItemLongCLickListener {

    private static final String TAG = "ConversationFragment";

    private ConversationPresenter conversationPresenter;

    private RecyclerView conversationRV;
    private ConversationAdapter conversationAdapter;

    private int clickOption;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_conversation;
    }

    @Override
    public void initAllMembersView(Bundle saveInstanceState) {
        initView();
        initData();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取所有会话
        conversationPresenter.getAllConversations();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        conversationPresenter.detachView();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    //初始化view
    public void initView() {
        conversationAdapter = new ConversationAdapter(getContext());
        conversationAdapter.setOnItemClickListener(this);
        conversationAdapter.setOnItemLongClickListener(this);
        conversationRV = (RecyclerView) mRootView.findViewById(R.id.rv_conversation);
        conversationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationRV.setAdapter(conversationAdapter);
    }

    //初始化data
    public void initData() {
        conversationPresenter = new ConversationPresenter();
        conversationPresenter.attachView(this);
    }

    @Override
    public void onItemClick(int position) {
        if (isAttachedContext()) {
            Conversation conversation = conversationAdapter.getItem(position);
            conversation.onClick(getContext());
        }
    }

    @Override
    public void onItemLongClick(int position) {
        clickOption = position;
//      Conversation conversation = conversationAdapter.getConversation(position);
//      conversation.onLongClick(getContext());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                if (isAttachedContext()) {
                    Conversation conversation = conversationAdapter.getItem(clickOption);
                    conversation.onLongClick(getContext());
                    conversationPresenter.getAllConversations();
                }
                break;
            case 1:
                if (isAttachedContext()) {
                    BmobIM.getInstance().clearAllConversation();
                    conversationPresenter.getAllConversations();
                }
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override //获取会话的结果
    public void getAllConversationsResult(List<Conversation> list) {
        conversationAdapter.replaceItemList(list);
    }


    @Override //显示加载状态
    public void showLoading() {
        //
    }

    @Override //隐藏加载状态
    public void hideLoading() {
        //
    }

    //通知有在线消息接收
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        //conversationAdapter.addConversationToFirst(new PrivateConversation(event.getConversation()));
        conversationPresenter.getAllConversations();
    }



    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
