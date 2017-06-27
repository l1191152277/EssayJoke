package com.hc.essay.joke.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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
        int bottom = mDriver.getIntrinsicHeight();
        int right = mDriver.getIntrinsicWidth();

        if (isLastColumn(view, parent)) {
            //最后一列
            right = 0;
        }
        if (isLastRow(view, parent)) {
            //最后一行
            bottom = 0;
        }

        outRect.bottom = bottom;
        outRect.right = right;
    }


    /**
     * 判断是否是最后一列
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isLastColumn(View view, RecyclerView parent) {
        //当前位置
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams())
                .getViewLayoutPosition();
        //列数
        int spanCount = getSpanCount(parent);
        //当前位置+1 & 列数 = 0
        return (position + 1) % spanCount == 0;

    }

    /**
     * 判断是否最后一行
     *
     * @param view
     * @param parent
     * @return
     */
    private boolean isLastRow(View view, RecyclerView parent) {
        //当前位置
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams())
                .getViewLayoutPosition();
        //条目数
        int childCount = parent.getAdapter().getItemCount();
        //列数
        int spanCount = getSpanCount(parent);
        //行数
        int rowCount = childCount % spanCount == 0 ? childCount / spanCount :
                childCount / spanCount + 1;
        //当前位置+1 > （行数-1）* 列数
        return (position + 1) > (rowCount - 1) * spanCount;
    }

    /**
     * 获取列数
     *
     * @param parent
     * @return
     */

    public int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            int spanCount = manager.getSpanCount();
            return spanCount;
        }
        //如果是LinerLayoutManager 返回1列
        return 1;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //绘制分割线
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    /**
     * 绘制垂直方向分割线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getTop() - params.topMargin;
            int bottom = childView.getBottom() + params.bottomMargin;
            int left = childView.getRight() + params.rightMargin;
            int right = left + mDriver.getIntrinsicWidth();
            //计算水平分割线的位置
            mDriver.setBounds(left, top, right, bottom);
            mDriver.draw(c);
        }
    }

    /**
     * 绘制水平分割线
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - params.leftMargin;
            int right = childView.getRight() + mDriver.getIntrinsicWidth() + params.rightMargin;
            int top = childView.getBottom() + params.topMargin;
            int bottom = top + mDriver.getIntrinsicHeight();
            //计算水平分割线的位置
            mDriver.setBounds(left, top, right, bottom);
            mDriver.draw(c);
        }
    }

}
