package com.hc.essay.joke.recyclerview;

import android.content.Context;

import com.hc.essay.joke.R;
import com.mrl.baselibrary.common.RecyclerCommonAdapter;
import com.mrl.baselibrary.common.RecyclerViewHolder;

import java.util.List;

public class TextCommonAdapter extends RecyclerCommonAdapter<String> {

    public TextCommonAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.recyclerview_item;
    }

    @Override
    protected void convert(RecyclerViewHolder<String> holder, String s, int position) {
        holder.setText(R.id.recyclerview_item_text,s);
    }
}
