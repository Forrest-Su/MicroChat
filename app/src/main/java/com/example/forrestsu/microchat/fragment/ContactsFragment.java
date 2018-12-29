package com.example.forrestsu.microchat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.activity.ChatActivity;
import com.example.forrestsu.microchat.adapter.ContactAdapter;
import com.example.forrestsu.microchat.base.BaseFragment;
import com.example.forrestsu.microchat.beans.Friend;
import com.example.forrestsu.microchat.beans.User;
import com.example.forrestsu.microchat.presenter.ContactsPresenter;
import com.example.forrestsu.microchat.view.ContactsView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

public class ContactsFragment extends BaseFragment implements ContactsView, ContactAdapter.OnItemClickListener {

    private static final String TAG = "ContactsFragment";

    private ContactsPresenter contactsPresenter;
    private List<Friend> friendList;

    private ProgressBar progressBar;
    private RecyclerView contactsRV;
    private ContactAdapter contactAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_contacts;
    }

    @Override
    public void initAllMembersView(Bundle saveInstanceState) {
        initView();
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        contactsPresenter.detachView();
    }

    //初始化view
    public void initView() {
        progressBar = (ProgressBar) mRootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        contactsRV = (RecyclerView) mRootView.findViewById(R.id.rv_contacts);
        contactsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        contactAdapter = new ContactAdapter(getContext());
        contactAdapter.setOnItemClickListener(this);
        contactsRV.setAdapter(contactAdapter);
    }

    //初始化data
    public void initData() {
        contactsPresenter = new ContactsPresenter();
        contactsPresenter.attachView(this);

        friendList = new ArrayList<>();

        contactsPresenter.getAllContacts();
    }

    @Override
    public void getContactsSuccess(final List<Friend> list) {
        if (isAttachedContext()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    friendList.addAll(list);
                    contactAdapter.addItemList(list);
                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {

        User user = friendList.get(position).getFriendUser();
        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
        Log.i(TAG, "onItemClick: " + info.getUserId());
        Log.i(TAG, "onItemClick: " + info.getName());
        Log.i(TAG, "onItemClick: " + info.getAvatar());
        BmobIMConversation conversationEntrance = BmobIM.getInstance()
                .startPrivateConversation(info, null);

        Bundle bundle = new Bundle();
        bundle.putSerializable("c", conversationEntrance);
        if (isAttachedContext()) {
            Intent toChat = new Intent(getContext(), ChatActivity.class);
            toChat.putExtras(bundle);
            startActivity(toChat);
        }
    }

    @Override
    public void getContactsFailure() {
        //
    }

    @Override //显示加载状态
    public void showLoading() {
        if (isAttachedContext()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override //隐藏加载状态
    public void hideLoading() {
        if (isAttachedContext()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
