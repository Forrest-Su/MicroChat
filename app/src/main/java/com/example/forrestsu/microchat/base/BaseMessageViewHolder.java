package com.example.forrestsu.microchat.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;

public abstract class BaseMessageViewHolder extends RecyclerView.ViewHolder {

    public BaseMessageViewHolder(View view) {
        super(view);
    }

    //绑定数据
    public abstract void bindData(List<BmobIMMessage> list, int position);

    //显示时间
    public String showTime(List<BmobIMMessage> list, int position) {
        long lastMessageTime = 0;
        if (position > 0) {
            lastMessageTime = list.get(position - 1).getCreateTime();
        }

        long currentMessageTime = list.get(position).getCreateTime();
        String time = "";
        if (currentMessageTime - lastMessageTime > 5 * 60 * 1000) {
            //将时间戳转换为指定格式的时间
            SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
            if (sdf.format(new Date(currentMessageTime)).equals(sdf.format(new Date()))) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {
                sdf = new SimpleDateFormat("MM月dd日 HH:mm");
            }
            time = sdf.format(new Date(currentMessageTime));
        }
        return time;
    }

    //获取context
    public Context getContext() {
        return itemView.getContext();
    }
}
