package com.example.forrestsu.microchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.adapter.viewholder.ContactViewHolder;
import com.example.forrestsu.microchat.base.BaseAdapter;
import com.example.forrestsu.microchat.beans.Friend;


public class ContactAdapter extends BaseAdapter<Friend> {

    private static final String TAG = "ContactAdapter";

    public ContactAdapter(Context context) {
        super(context);
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacts, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick((Integer) view.getTag());
                }
            }
        });
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ContactViewHolder contactViewHolder = (ContactViewHolder) viewHolder;
        contactViewHolder.bindData(context, getItem(position));
        contactViewHolder.itemView.setTag(position);
    }
}
