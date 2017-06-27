package com.hc.essay.joke.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.navigationBar.DefaultNavigationBar;
import com.hc.essay.joke.R;
import com.mrl.baselibrary.common.RecyclerItemClickListener;
import com.mrl.baselibrary.common.RecyclerItemLongClickListener;
import com.mrl.baselibrary.ioc.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的基本使用
 */
public class BaseUseRecyclerVIew extends BaseSkinActivity {
    @ViewById(R.id.base_use_recyclerview)
    private RecyclerView mRecyclerView;
    private List<String> mDatas;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_base_use_recycler_view);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar = new DefaultNavigationBar.Builder(this)
                .setTitle("RecyclerView")
                .setRightText("GridView")
                .builder();
        final TextView rightText = navigationBar.findViewById(com.example.framelibrary.R.id.navigation_righttext);
        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightText.getText().equals("GridView")) {
                    rightText.setText("ListView");
                    mRecyclerView.setLayoutManager(new GridLayoutManager(BaseUseRecyclerVIew.this, 3));
                } else {
                    rightText.setText("GridView");
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(BaseUseRecyclerVIew.this));
                }
            }
        });
    }

    @Override
    protected void initView() {

        // LinearLayoutManager -> ListView风格
        // GridLayoutManager -> GridView风格
        // StaggeredGridLayoutManager -> 瀑布流风格

        // 1 设置recyclerView的布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
        TextCommonAdapter adapter = new TextCommonAdapter(this, mDatas);
        adapter.setItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mContext,"--"+mDatas.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setItemLongClickListener(new RecyclerItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                Toast.makeText(mContext,"-长按-"+mDatas.get(position),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // 2 添加适配器
        mRecyclerView.setAdapter(adapter);
        // 3 添加分割线
        mRecyclerView.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_driver_01));
    }






    /**
     * 分割线 10px的红色
     */
    private class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
        private Paint mPaint;

        public RecyclerItemDecoration() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.RED);
        }

        /**
         * 留出分割线位置
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //在每个顶部位置留出10px 来绘制分割线 第一个位置不需要分割线
            int position = parent.getChildAdapterPosition(view);
            if (position != 0) {
                outRect.top = 10;
            }
        }

        /**
         * 绘制分割线
         */
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
                c.drawRect(rect, mPaint);
            }
        }
    }


    /**
     * 适配器
     */
    private class RecyclertAdapter extends RecyclerView.Adapter<RecyclertAdapter.ViewHolder> {

        private Context mContext;
        private List<String> mList;

        public RecyclertAdapter(Context mContext, List<String> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public RecyclertAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //创建ViewHolder
            View itemVIew = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemVIew);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(RecyclertAdapter.ViewHolder holder, int position) {
            //绑定数据
            holder.itemTv.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            //显示条数
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView itemTv;

            public ViewHolder(View itemView) {
                super(itemView);  //布局条目view
                itemTv = (TextView) itemView.findViewById(R.id.recyclerview_item_text);
            }
        }
    }

}
