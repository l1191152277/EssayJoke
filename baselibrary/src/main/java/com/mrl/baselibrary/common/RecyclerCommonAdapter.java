package com.mrl.baselibrary.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

/**
 * Email: 1191152277@qq.com
 * Created by MrL on 2017/6/27 12:47
 * Description: Recyeler 基础适配器
 */

public abstract class RecyclerCommonAdapter<Data> extends RecyclerView.Adapter<RecyclerViewHolder<Data>> {
    private int mLayoutId; //布局id
    private List<Data> mData; //数据
    private Context mContext; //上下文
    private LayoutInflater mInflater;
    private RecyclerItemClickListener itemClickListener;
    private RecyclerItemLongClickListener itemLongClickListener;

    public RecyclerCommonAdapter(Context context, List<Data> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = setLayoutId();
        this.mData = data;
    }

    /**
     * 设置点击事件
     *
     * @param itemClickListener
     */
    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 设置长按事件
     * @param itemLongClickListener
     */
    public void setItemLongClickListener(RecyclerItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = mInflater.inflate(mLayoutId, parent, false);
        return new RecyclerViewHolder<>(root);
    }

    /**
     * 绑定ViewHolder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder<Data> holder, final int position) {
        //绑定ViewHolder
        convert(holder, mData.get(position), position);
        //设置点击事件
        if (itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(position);
                }
            });
        }
        //设置长按事件
        if (itemLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return itemLongClickListener.onItemLongClick(position);
                }
            });
        }
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int setLayoutId();


    /**
     * 设置显示内容
     *
     * @param holder
     * @param data
     * @param position
     */
    protected abstract void convert(RecyclerViewHolder<Data> holder, Data data, int position);

    /**
     * 获取item条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }


}
