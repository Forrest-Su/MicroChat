package com.example.forrestsu.microchat.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.base.BaseMessageViewHolder;

import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;


public class ReceiverTextViewHolder extends BaseMessageViewHolder {

    private static final String TAG = "ReceiverTextViewHolder";

    private TextView timeTV;
    private ImageView avatarIV;
    private TextView msgTV;

    public ReceiverTextViewHolder(View view) {
        super(view);
        timeTV = (TextView) view.findViewById(R.id.tv_time);
        avatarIV = (ImageView) view.findViewById(R.id.iv_avatar);
        msgTV = (TextView) view.findViewById(R.id.tv_message);
    }

    @Override
    public void bindData(List<BmobIMMessage> list, int position) {
        String time = showTime(list, position);
        if (TextUtils.isEmpty(time)) {
            timeTV.setVisibility(View.GONE);
        } else {
            timeTV.setText(time);
        }

        BmobIMMessage currentMessage = list.get(position);
        String content = currentMessage.getContent();
        msgTV.setText(content);

        Glide.with(getContext())
                .load(currentMessage.getBmobIMUserInfo().getAvatar())
                .into(avatarIV);
    }
}
