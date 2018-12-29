package com.example.forrestsu.microchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.adapter.viewholder.ReceiverImageViewHolder;
import com.example.forrestsu.microchat.adapter.viewholder.ReceiverTextViewHolder;
import com.example.forrestsu.microchat.adapter.viewholder.SendImageViewHolder;
import com.example.forrestsu.microchat.adapter.viewholder.SendTextViewHolder;
import com.example.forrestsu.microchat.base.BaseAdapter;
import com.example.forrestsu.microchat.base.BaseMessageViewHolder;
import com.example.forrestsu.microchat.beans.User;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.v3.BmobUser;

public class ChatAdapter extends BaseAdapter<BmobIMMessage> {

    private static final String TAG = "ChatAdapter";

    //普通文本消息
    private final int TYPE_TEXT_SEND = 0;
    private final int TYPE_TEXT_RECEIVER = 1;
    //图片
    private final int TYPE_IMAGE_SEND = 2;
    private final int TYPE_IMAGE_RECEIVER = 3;
    //语音消息
    private final int TYPE_VOICE_SEND = 4;
    private final int TYPE_VOICE_RECEIVER = 5;

    private String currentUid = "";

    //构造
    public ChatAdapter(Context context) {
        super(context);
        try {
            currentUid = BmobUser.getCurrentUser(User.class).getObjectId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TEXT_SEND:
                View sendTextView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_right, parent, false);

                //设置item长按事件
                sendTextView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (onItemLongClickListener != null) {
                            onItemLongClickListener.onItemLongClick((Integer) view.getTag());
                        }
                        return false;
                    }
                });

                //设置OnCreateContextMenuListener
                sendTextView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.add(1, 0, 1, "删除");
                    }
                });

                return new SendTextViewHolder(sendTextView);
            case TYPE_TEXT_RECEIVER:
                View receiverTextView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_left, parent, false);
                return new ReceiverTextViewHolder(receiverTextView);
            case TYPE_IMAGE_SEND:
                View sendImageView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_image_right, parent, false);

                //设置item长按事件
                sendImageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (onItemLongClickListener != null) {
                            onItemLongClickListener.onItemLongClick((Integer) view.getTag());
                        }
                        return false;
                    }
                });

                //设置OnCreateContextMenuListener
                sendImageView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.add(1, 0, 1, "删除");
                    }
                });

                return new SendImageViewHolder(sendImageView, onImageClickListener);
            case TYPE_IMAGE_RECEIVER:
                View receiverImageView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_image_left, parent, false);
                return new ReceiverImageViewHolder(receiverImageView, onImageClickListener);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((BaseMessageViewHolder) viewHolder).bindData(getItemList(), position);
        if (viewHolder instanceof SendTextViewHolder) {
            viewHolder.itemView.setTag(position);
        } else if (viewHolder instanceof ReceiverTextViewHolder) {
            //
        } else if (viewHolder instanceof SendImageViewHolder) {
              viewHolder.itemView.setTag(position);
        } else if (viewHolder instanceof ReceiverImageViewHolder) {
            viewHolder.itemView.setTag(position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        BmobIMMessage msg = getItem(position);
        if (msg.getMsgType().equals(BmobIMMessageType.TEXT.getType())) {
            return msg.getFromId().equals(currentUid) ? TYPE_TEXT_SEND : TYPE_TEXT_RECEIVER;
        } else if (msg.getMsgType().equals(BmobIMMessageType.IMAGE.getType())) {
            return msg.getFromId().equals(currentUid) ? TYPE_IMAGE_SEND : TYPE_IMAGE_RECEIVER;
        } else if (msg.getMsgType().equals(BmobIMMessageType.VOICE.getType())) {
            return msg.getFromId().equals(currentUid) ? TYPE_VOICE_SEND : TYPE_VOICE_RECEIVER;
        } else {
            return -1;
        }
    }

    public OnImageClickListener onImageClickListener;
    //点击item事件接口
    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    //点击item事件监听
    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }
}
