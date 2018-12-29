package com.example.forrestsu.microchat.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    private List<T> itemList = new ArrayList<>();

    public OnItemClickListener onItemClickListener;
    public OnItemLongCLickListener onItemLongClickListener;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    //添加itemList
    public void addItemList(List<T> list) {
        itemList.addAll(list);
        notifyDataSetChanged();
    }

    //添加itemList到队首
    public void addItemListToHead(List<T> list) {
        itemList.addAll(0, list);
        notifyDataSetChanged();
    }

    //替换itemList
    public void replaceItemList(List<T> list) {
        itemList.clear();
        itemList.addAll(list);
        notifyDataSetChanged();
    }

    //添加一个item
    public void addItem(T item) {
        itemList.add(item);
        notifyDataSetChanged();
    }

    //添加一个item到队首
    public void addItemToHead(T item) {
        itemList.add(0, item);
        notifyDataSetChanged();
    }

    //清空itemList
    public void clearItemList() {
        itemList.clear();
        notifyDataSetChanged();
    }

    //移除置顶位置的item
    public void removeItem(int position) {
        itemList.remove(position);
        notifyDataSetChanged();
    }

    //获取itemList
    public List<T> getItemList() {
        return itemList;
    }

    //获取指定位置的item
    public T getItem(int position) {
        if (itemList == null || itemList.size() == 0) {
            return null;
        } else {
            return itemList.get(position);
        }
    }

    //获取item对应的position
    public int getPosition(T item) {
        int index = getItemCount();
        int position = -1;
        while(index-- > 0) {
            if(item.equals(this.getItem(index))) {
                position = index;
                break;
            }
        }
        return position;
    }

    //点击item事件接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //点击item事件监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //长按item事件接口
    public interface OnItemLongCLickListener {
        void onItemLongClick(int position);
    }

    //长按item事件监听
    public void setOnItemLongClickListener(OnItemLongCLickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
