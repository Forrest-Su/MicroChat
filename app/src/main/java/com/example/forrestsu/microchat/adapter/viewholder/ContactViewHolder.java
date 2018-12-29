package com.example.forrestsu.microchat.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.beans.Friend;
import com.example.forrestsu.microchat.beans.User;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "ContactViewHolder";

    private ImageView avatarIV;
    private TextView nameTV;

    public ContactViewHolder(View view) {
        super(view);
        avatarIV = (ImageView) view.findViewById(R.id.iv_avatar);
        nameTV = (TextView) view.findViewById(R.id.tv_title);
    }

    public void bindData(Context context, Friend friend) {
        User user = friend.getFriendUser();
        nameTV.setText(user.getNickName());
        Glide.with(context)
                .load(user.getAvatar())
                .into(avatarIV);
    }
}
