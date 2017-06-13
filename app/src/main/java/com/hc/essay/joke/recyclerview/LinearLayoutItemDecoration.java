package com.hc.essay.joke.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/6/13
 * Description: ListView 样式的分割线
 */
public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDriver;

    public LinearLayoutItemDecoration(Context context, int drawableResId) {
        mDriver = ContextCompat.getDrawable(context, drawableResId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //在每个顶部位置留出10px 来绘制分割线 第一个位置不需要分割线
        int position = parent.getChildAdapterPosition(view);
        if (position != 0) {
            outRect.top = 10;
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //利用Canvas绘制
        int childCount = parent.getChildCount();
        //指定绘制区域
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 1; i < childCount; i++) {
            rect.bottom = parent.getChildAt(i).getTop();
            rect.top = rect.bottom - 10;
            mDriver.setBounds(rect);
            mDriver.draw(c);
        }
    }

}
