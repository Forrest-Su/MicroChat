package com.example.forrestsu.microchat.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.adapter.ChatAdapter;
import com.example.forrestsu.microchat.base.BaseActivity;
import com.example.forrestsu.microchat.beans.User;
import com.example.forrestsu.microchat.presenter.ChatPresenter;
import com.example.forrestsu.microchat.utils.ChoosePhoto;
import com.example.forrestsu.microchat.utils.LogUtil;
import com.example.forrestsu.microchat.utils.PermissionUtil;
import com.example.forrestsu.microchat.view.ChatView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;

import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class ChatActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, ChatView, ChatAdapter.OnItemLongCLickListener,
        ChatAdapter.OnImageClickListener {

    private static final String TAG = "ChatActivity";

    private ChatPresenter chatPresenter;

    private BmobIMConversation bmobIMConversation;

    private Toolbar toolbar;
    private SwipeRefreshLayout chatSRL;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    LinearLayoutManager linearLayoutManager;
    private EditText msgET;
    private Button imageBT, sendBT;

    private ImageView imageIV;

    private int clickPosition;
    private String currentUid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        LogUtil.d(TAG, "测试");

        BmobIMConversation conversationEntrance = (BmobIMConversation) getIntent().getExtras().getSerializable("c");
        bmobIMConversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);

        //请求权限
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionUtil.requestPermission(this, permissions);

        initView();
        initData();

        //注册EventBus
        EventBus.getDefault().register(this);
        //进入界面时自动刷新
        onRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        chatPresenter.detachView();
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_toolbar, menu);
        return true;
    }

    //初始化view
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(bmobIMConversation.getConversationTitle());

        chatSRL = (SwipeRefreshLayout) findViewById(R.id.srl_chat);
        chatSRL.setOnRefreshListener(this);

        chatAdapter = new ChatAdapter(this);
        chatAdapter.setOnItemLongClickListener(this);
        chatAdapter.setOnImageClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_chat);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatAdapter);

        msgET = (EditText) findViewById(R.id.et_message);
        imageBT = (Button) findViewById(R.id.bt_image);
        imageBT.setOnClickListener(this);
        sendBT = (Button) findViewById(R.id.bt_send);
        sendBT.setOnClickListener(this);

        imageIV = (ImageView) findViewById(R.id.iv_image);
        imageIV.setVisibility(View.INVISIBLE);
        imageIV.setOnClickListener(this);
    }

    //初始化data
    public void initData() {
        chatPresenter = new ChatPresenter();
        chatPresenter.attachView(this);

        try {
            currentUid = BmobUser.getCurrentUser(User.class).getObjectId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_messages:
                chatAdapter.clearItemList();
                bmobIMConversation.clearMessage();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_image:
                ChoosePhoto.openAlbum(this);
                break;
            case R.id.bt_send:
                if (!TextUtils.isEmpty(msgET.getText().toString())) {
                    chatPresenter.sendTextMsg(msgET.getText().toString(), bmobIMConversation, msgSendListener);
                }
                break;
            case R.id.iv_image:
                if (imageIV.getVisibility() == View.VISIBLE) {
                    imageIV.setVisibility(View.INVISIBLE);
                }
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(int position) {
        clickPosition = position;
    }

    @Override
    public void onImageClick(int position) {
        if (imageIV.getVisibility() == View.INVISIBLE) {
            imageIV.setVisibility(View.VISIBLE);
            final BmobIMImageMessage imageMessage = BmobIMImageMessage.buildFromDB(true, chatAdapter.getItem(position));
            if (imageMessage.getFromId().equals(currentUid)) {
                Glide.with(this)
                        .load(imageMessage.getLocalPath())
                        .into(imageIV);
            } else {
                Glide.with(this)
                        .load(imageMessage.getRemoteUrl())
                        .into(imageIV);
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                bmobIMConversation.deleteMessage(chatAdapter.getItem(clickPosition));
                chatAdapter.removeItem(clickPosition);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onRefresh() {
        chatPresenter.queryMessages(bmobIMConversation, chatAdapter.getItem(0));
        chatSRL.setRefreshing(false);
    }

    @Override //查询消息结果
    public void queryMessagesResult(List<BmobIMMessage> list) {
        if (list != null && list.size() > 0) {
            chatAdapter.addItemListToHead(list);
            linearLayoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
        }
    }


    //接收在线消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {
        addMessage2Chat(event);
    }

    //处理离线消息
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        //event.getEventMap().get;
    }


    /**
     * 添加消息到聊天界面中
     * @param event
     */
    private void addMessage2Chat(MessageEvent event) {
        BmobIMMessage msg = event.getMessage();
        //如果是当前会话的消息
        if (bmobIMConversation != null && event != null && bmobIMConversation.getConversationId().equals(event.getConversation().getConversationId())
                && !msg.isTransient()) {//并且不为暂态消息
            chatAdapter.addItem(msg);
            //更新该会话下面的已读状态
            bmobIMConversation.updateReceiveStatus(msg);
            linearLayoutManager.scrollToPositionWithOffset(chatAdapter.getItemCount() - 1, 0);
            /*
            if (chatAdapter.findPosition(msg) < 0) {//如果未添加到界面中
                chatAdapter.addMsg(msg);
                //更新该会话下面的已读状态
                bmobIMConversation.updateReceiveStatus(msg);
            }
            //滚动到底部
            linearLayoutManager.scrollToPositionWithOffset(chatAdapter.getItemCount() - 1, 0);
            */
        } else {
            //不是与当前聊天对象的消息
        }
    }

    //发送消息监听
    public MessageSendListener msgSendListener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
            //文件类型的消息才有进度值
            //Log.i("onProgress：" + value);
            BmobIMMessage msg = new BmobIMMessage();
            chatAdapter.notifyItemChanged(0);
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
            chatAdapter.addItem(msg);
            msgET.setText("");
            //滚动到底部
            linearLayoutManager.scrollToPositionWithOffset(chatAdapter.getItemCount() - 1, 0);

        }

        @Override
        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
            if (e == null) {
                //发送成功
                int sendStatus = bmobIMMessage.getSendStatus();
            } else {
                //发送失败
                int position = chatAdapter.getPosition(bmobIMMessage);
                int sendStatus = bmobIMMessage.getSendStatus();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ChoosePhoto.TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //
                }
                break;
            case ChoosePhoto.CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    String imagePath = ChoosePhoto.getImagePath(this, data);
                    chatPresenter.sendImageMsg(imagePath, bmobIMConversation, msgSendListener);
                }
                break;
            default:
                break;
        }
    }

}
