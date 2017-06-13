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
 * Description: GridView 样式的分割线
 */
public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDriver;

    public GridLayoutItemDecoration(Context context, int drawableResId) {
        mDriver = ContextCompat.getDrawable(context, drawableResId);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
         // 留出分割线位置 下边和右边
        int position = parent.getChildAdapterPosition(view);
        outRect.bottom=mDriver.getIntrinsicHeight();
        outRect.right=mDriver.getIntrinsicWidth();

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //绘制分割线
        drawHorizontal(c,parent);
        drawVertical(c,parent);
    }

    /**
     * 绘制垂直方向分割线
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount=parent.getChildCount();

        for (int i=0;i<childCount;i++){
            View childView=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top=childView.getTop()-params.topMargin;
            int bottom=childView.getBottom()+params.bottomMargin;
            int left=childView.getRight()+params.rightMargin;
            int right=left+mDriver.getIntrinsicWidth();
            //计算水平分割线的位置
            mDriver.setBounds(left,top,right,bottom);
            mDriver.draw(c);
        }
    }

    /**
     * 绘制水平分割线
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount=parent.getChildCount();

        for (int i=0;i<childCount;i++){
            View childView=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left=childView.getLeft()-params.leftMargin;
            int right=childView.getRight()+mDriver.getIntrinsicWidth()+params.rightMargin;
            int top=childView.getBottom()+params.topMargin;
            int bottom=top+mDriver.getIntrinsicHeight();
            //计算水平分割线的位置
            mDriver.setBounds(left,top,right,bottom);
            mDriver.draw(c);
        }
    }

}
