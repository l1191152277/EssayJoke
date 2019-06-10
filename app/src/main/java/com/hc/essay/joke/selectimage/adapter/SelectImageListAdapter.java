package com.hc.essay.joke.selectimage.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.hc.essay.joke.R;
import com.hc.essay.joke.selectimage.SelectImageActivity;
import com.hc.essay.joke.selectimage.bean.ImageEntity;
import com.mrl.baselibrary.common.RecyclerCommonAdapter;
import com.mrl.baselibrary.common.RecyclerViewHolder;

import java.util.LinkedList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/6/12
 * Description: 图片选择器适配器
 */
public class SelectImageListAdapter extends RecyclerCommonAdapter<ImageEntity> {
    private List<ImageEntity> mSelectedImages = new LinkedList<>();
    private SelectedChangeListener mListener;
    private String sdPath;//SD卡的路径
    private String picPath;//图片存储路径

    public SelectImageListAdapter(Context context, List<ImageEntity> data, SelectedChangeListener listener) {
        super(context, data);
        this.mListener = listener;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.image_selector_item;
    }

    @Override
    protected void convert(final RecyclerViewHolder<ImageEntity> holder, final ImageEntity imageEntity, int position) {
        ImageView imageView = holder.getView(R.id.im_image);
        final CheckBox selectBox = holder.getView(R.id.cb_select);
        View mShade = holder.getView(R.id.view_shade);
        if (imageEntity == null) {
            imageView.setBackgroundColor(mContext.getResources().getColor(R.color.s1, null));
            holder.setImageResource(R.id.im_image, R.drawable.ic_media_chooser_take_picture);
            selectBox.setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(imageEntity.path).centerCrop().into(imageView);
            selectBox.setChecked(imageEntity.isSelect);
            mShade.setVisibility(imageEntity.isSelect ? VISIBLE : INVISIBLE);
        }

        holder.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageEntity != null) {
                    if (selectedChange(imageEntity)) {
                        //noinspection unchecked
                        updata(imageEntity, holder);
                    }
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    SelectImageActivity activity = (SelectImageActivity) mContext;
                    activity.startActivityForResult(intent, 1);
                }
            }
        });
    }

    /**
     * 图片选择状态改变
     *
     * @param image
     * @return
     */
    public boolean selectedChange(ImageEntity image) {
        boolean notifyRefresh;
        if (mSelectedImages.contains(image)) {
            // 如果之前在那么现在就移除
            mSelectedImages.remove(image);
            image.isSelect = false;
            // 状态已经改变则需要更新
            notifyRefresh = true;
        } else {
            if (mSelectedImages.size() >= SelectImageActivity.mMaxCount) {
                // 得到提示文字
                String str = "最多不能超过" + SelectImageActivity.mMaxCount + "张";
                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
                notifyRefresh = false;
            } else {
                mSelectedImages.add(image);
                image.isSelect = true;
                notifyRefresh = true;
            }
        }
        if (notifyRefresh) {
            notifySelectChanged();
        }
        return true;
    }

    /**
     * 刷新列表
     *
     * @param data
     * @param holder
     */
    public void updata(ImageEntity data, RecyclerView.ViewHolder holder) {
        //得到当前ViewHolder坐标
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            //数据的移除与更新
            mData.remove(pos);
            mData.add(pos, data);
            //刷新
            notifyItemChanged(pos);
        }
    }

    /**
     * 通知选中状态改变
     */
    public void notifySelectChanged() {
        // 得到监听者，并判断是否有监听者，然后进行回调数量变化
        SelectedChangeListener listener = mListener;
        if (listener != null) {
            listener.onSelectedCountChanged(mSelectedImages.size());
        }
    }


    /**
     * 得到选中的图片的全部地址
     *
     * @return 返回一个数组
     */
    public String[] getSelectedPath() {
        String[] paths = new String[mSelectedImages.size()];
        int index = 0;
        for (ImageEntity image : mSelectedImages) {
            paths[index++] = image.path;
        }
        return paths;
    }


    public interface SelectedChangeListener {
        void onSelectedCountChanged(int count);
    }

}
