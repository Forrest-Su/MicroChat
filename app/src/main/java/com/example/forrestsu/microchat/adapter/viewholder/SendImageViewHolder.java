package com.example.forrestsu.microchat.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.adapter.ChatAdapter;
import com.example.forrestsu.microchat.base.BaseMessageViewHolder;
import com.example.forrestsu.microchat.beans.User;

import java.util.List;

import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.BmobUser;

public class SendImageViewHolder extends BaseMessageViewHolder {

    private static final String TAG = "SendImageViewHolder";

    private TextView timeTV;
    private ImageView avatarIV;
    private ImageView contentIV;
    private ImageView sendFailIV;

    public SendImageViewHolder(final View view, final ChatAdapter.OnImageClickListener onImageClickListener) {
        super(view);
        timeTV = (TextView) view.findViewById(R.id.tv_time);
        avatarIV = (ImageView) view.findViewById(R.id.iv_avatar);
        contentIV = (ImageView) view.findViewById(R.id.iv_content);
        sendFailIV = (ImageView) view.findViewById(R.id.iv_send_fail);

        contentIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.iv_content) {
                    onImageClickListener.onImageClick((Integer) view.getTag());
                }
            }
        });
    }

    @Override
    public void bindData(List<BmobIMMessage> list, int position) {
        final BmobIMImageMessage imageMessage = BmobIMImageMessage.buildFromDB(true, list.get(position));

        if (imageMessage.getSendStatus() == 3 || imageMessage.getSendStatus() == 5) {
            if (sendFailIV.getVisibility() != View.VISIBLE) {
                sendFailIV.setVisibility(View.VISIBLE);
            }
        } else {
            if (sendFailIV.getVisibility() == View.VISIBLE) {
                sendFailIV.setVisibility(View.INVISIBLE);
            }
        }

        String time = showTime(list, position);
        if (TextUtils.isEmpty(time)) {
            timeTV.setVisibility(View.GONE);
        } else {
            timeTV.setText(time);
        }

        Glide.with(getContext())
                .load(BmobUser.getCurrentUser(User.class).getAvatar())
                .into(avatarIV);

        Glide.with(getContext())
                .load(imageMessage.getLocalPath())
                .into(contentIV);
    }
}
