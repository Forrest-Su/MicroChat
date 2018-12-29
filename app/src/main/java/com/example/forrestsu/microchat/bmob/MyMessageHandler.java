package com.example.forrestsu.microchat.bmob;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.activity.HomeActivity;
import com.example.forrestsu.microchat.model.UserModel;
import com.example.forrestsu.microchat.model.callback.UpdateCacheListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;

public class MyMessageHandler extends BmobIMMessageHandler {

    private static final String TAG = "MyMessageHandler";
    private Context context;

    public MyMessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessageReceive(final MessageEvent event) {
        //在线消息
        Log.i(TAG, "onMessageReceive: 有用户发来消息");
        executeMessage(event);
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
        Map<String, List<MessageEvent>> map = event.getEventMap();
        Log.i("", "有" + map.size() + "个用户发来离线消息");
        //挨个检测下离线消息所属的用户的信息是否需要更新
        for (Map.Entry<String, List<MessageEvent>> entry : map.entrySet()) {
            List<MessageEvent> list = entry.getValue();
            int size = list.size();
            Log.i("", "用户" + entry.getKey() + "发来" + size + "条消息");
            for (int i = 0; i < size; i++) {
                //处理每条消息
                executeMessage(list.get(i));
            }
        }
    }

    /**
     * 处理消息
     *
     * @param event
     */
    private void executeMessage(final MessageEvent event) {
        //检测用户信息是否需要更新
        UserModel.getInstance().updateUserInfo(event, new UpdateCacheListener() {
            @Override
            public void done(BmobException e) {
                BmobIMMessage msg = event.getMessage();
                Log.i(TAG, "done: " + msg.toString());
                Log.i(TAG, "done: " + msg.getExtra());
                Log.i(TAG, "done: " + msg.getExtra());

                if (BmobIMMessageType.getMessageTypeValue(msg.getMsgType()) == 0) {
                    //自定义消息类型：0
                    //processCustomMessage(msg, event.getFromUserInfo());
                } else {
                    //SDK内部内部支持的消息类型
                    processSDKMessage(msg, event);
                }
            }
        });
    }

    /**
     * 处理SDK支持的消息
     *
     * @param msg
     * @param event
     */
    private void processSDKMessage(BmobIMMessage msg, MessageEvent event) {
        if (BmobNotificationManager.getInstance(context).isShowNotification()) {
            //如果需要显示通知栏，SDK提供以下两种显示方式：
            Intent pendingIntent = new Intent(context, HomeActivity.class);
            pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);


            //多个用户的多条消息合并成一条通知：有XX个联系人发来了XX条消息
            //BmobNotificationManager.getInstance(context).showNotification(event, pendingIntent);

            //自定义通知消息：始终只有一条通知，新消息覆盖旧消息
            BmobIMUserInfo info = event.getFromUserInfo();
            //这里可以是应用图标，也可以将聊天头像转成bitmap
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            BmobNotificationManager.getInstance(context).showNotification(largeIcon,
                    info.getName(), msg.getContent(), "您有一条新消息", pendingIntent);
        } else {
            //直接发送消息事件
            EventBus.getDefault().post(event);
        }
    }
}
