package com.mrl.baselibrary.common;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Email: 1191152277@qq.com
 * Created by MrL on 2017/6/27 12:47
 * Description: Recycle 优化的ViewHolder
 */

public class RecyclerViewHolder<Data> extends RecyclerView.ViewHolder{
    //缓存View的集合 为了不重复执行findviewbyid
    private SparseArray<View> mViews;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mViews =new SparseArray<>();

    }

    /**
     * 通过viewid 获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewHolder setText(int viewId, String text) {
        TextView view = (TextView) getView(viewId);
        view.setText(""+text);
        return this;
    }

    /**
     * 设置bitmap图片
     * @param viewId
     * @param bitmap
     * @return
     */
    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = (ImageView) getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置本地资源图片
     * @param viewId
     * @param resId
     * @return
     */
    public RecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView view = (ImageView) getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 网络图片加载
     *
     * @param viewId
     * @param imageLoader
     * @return
     */
    public RecyclerViewHolder setImageURL(int viewId, HolderImageLoader imageLoader) {
        ImageView view = (ImageView) getView(viewId);
        //加载图片
        imageLoader.load(view,imageLoader.mImgPath);
        return this;
    }

    /**
     * 图片加载实现类，
     */
    abstract class HolderImageLoader{
        private String mImgPath;

        public HolderImageLoader(String mImgPath) {
            this.mImgPath = mImgPath;
        }

        public abstract void load(ImageView view, String mImgPath);
    }

}
