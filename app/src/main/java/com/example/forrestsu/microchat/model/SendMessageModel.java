package com.example.forrestsu.microchat.model;


import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.listener.MessageSendListener;

public class SendMessageModel {

    private static final String TAG = "SendMessageModel";

    /**
     * 发送文本消息
     * @param text 文本内容
     * @param bmobIMConversation
     * @param listener
     */
    public static void sendTextMsg(String text, final BmobIMConversation bmobIMConversation,
                                   final MessageSendListener listener) {
        final BmobIMTextMessage msg = new BmobIMTextMessage();
        msg.setContent(text);
//        //可随意设置额外信息
//        Map<String, Object> map = new HashMap<>();
//        map.put("level", "1");
//        msg.setExtraMap(map);
//        msg.setExtra("OK");
        bmobIMConversation.sendMessage(msg, listener);
    }


    /**
     * 发送本地图片消息
     * @param imagePath 本地图片路径
     * @param bmobIMConversation
     * @param listener
     */
    public static void sendImageMsg(String imagePath, final BmobIMConversation bmobIMConversation,
                                    final MessageSendListener listener) {
        //正常情况下，需要调用系统的图库或拍照功能获取到图片的本地地址，只需要将本地的文件地址传过去就可以发送文件类型的消息
        BmobIMImageMessage imageMsg = new BmobIMImageMessage(imagePath);
        bmobIMConversation.sendMessage(imageMsg, listener);
    }
}
