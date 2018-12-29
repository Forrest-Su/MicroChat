package com.example.forrestsu.microchat.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.adapter.ChatAdapter;
import com.example.forrestsu.microchat.base.BaseMessageViewHolder;

import java.util.List;

import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;

public class ReceiverImageViewHolder extends BaseMessageViewHolder {

    private static final String TAG = "ReceiverImageViewHolder";

    private TextView timeTV;
    private ImageView avatarIV;
    private ImageView contentIV;

    public ReceiverImageViewHolder(final View view, final ChatAdapter.OnImageClickListener onImageClickListener) {
        super(view);
        timeTV = (TextView) view.findViewById(R.id.tv_time);
        avatarIV = (ImageView) view.findViewById(R.id.iv_avatar);
        contentIV = (ImageView) view.findViewById(R.id.iv_content);

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
        BmobIMMessage currentMessage = list.get(position);
        //用户信息的获取必须在buildFromDB之前，否则会报错'Entity is detached from DAO context'
        BmobIMUserInfo info = currentMessage.getBmobIMUserInfo();

        final BmobIMImageMessage imageMessage = BmobIMImageMessage.buildFromDB(false, currentMessage);

        String time = showTime(list, position);
        if (TextUtils.isEmpty(time)) {
            timeTV.setVisibility(View.GONE);
        } else {
            timeTV.setText(time);
        }

        Glide.with(getContext())
                .load(info.getAvatar())
                .into(avatarIV);

        Glide.with(getContext())
                .load(imageMessage.getRemoteUrl())
                .into(contentIV);
    }
}
