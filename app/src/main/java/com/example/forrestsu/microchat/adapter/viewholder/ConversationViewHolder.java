package com.example.forrestsu.microchat.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.beans.Conversation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversationViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "ConversationViewHolder";

    private ImageView avatarIV;
    private TextView titleTV;
    private TextView messageTV;
    private TextView timeTV;
    private View redPointView;

    public ConversationViewHolder(View view) {
        super(view);
        avatarIV = (ImageView) view.findViewById(R.id.iv_avatar);
        titleTV = (TextView) view.findViewById(R.id.tv_title);
        messageTV = (TextView) view.findViewById(R.id.tv_message);
        timeTV = (TextView) view.findViewById(R.id.tv_time);
        redPointView = (View) view.findViewById(R.id.view_red_point);
    }

    public void bindData(Context context, Conversation conversation) {
        titleTV.setText(conversation.getcName());
        messageTV.setText(conversation.getLastMessageContent());

        //将时间戳转换为指定格式的时间
        long time = conversation.getLastMessageTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String messageTime = sdf.format(new Date(time));
        if (messageTime.equals(sdf.format(new Date()))) {
            sdf = new SimpleDateFormat("HH:mm");
        }
        timeTV.setText(sdf.format(new Date(time)));

        if (conversation.getUnReadCount() > 0) {
            if (redPointView.getVisibility() == View.INVISIBLE) {
                redPointView.setVisibility(View.VISIBLE);
            }
        } else {
            if (redPointView.getVisibility() == View.VISIBLE) {
                redPointView.setVisibility(View.INVISIBLE);
            }
        }

        Glide.with(context)
                .load(conversation.getAvatar())
                .into(avatarIV);
    }
}
