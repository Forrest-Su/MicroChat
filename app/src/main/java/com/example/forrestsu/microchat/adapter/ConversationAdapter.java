package com.example.forrestsu.microchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.adapter.viewholder.ConversationViewHolder;
import com.example.forrestsu.microchat.base.BaseAdapter;
import com.example.forrestsu.microchat.beans.Conversation;

public class ConversationAdapter extends BaseAdapter<Conversation> {

    private static final String TAG = "ConversationAdapter";

    public ConversationAdapter(Context context) {
        super(context);
    }


    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation, parent, false);

        //设置item点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick((Integer) view.getTag());
                }
            }
        });

        //设置item长按事件
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick((Integer) view.getTag());
                }
                return false;
            }
        });

        //设置OnCreateContextMenuListener
        view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(1, 0, 1, "删除会话");
                menu.add(1, 1, 1, "删除所有会话");
            }
        });

        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConversationViewHolder conversationViewHolder = (ConversationViewHolder) holder;
        conversationViewHolder.bindData(context, getItem(position));
        conversationViewHolder.itemView.setTag(position);
    }
}
