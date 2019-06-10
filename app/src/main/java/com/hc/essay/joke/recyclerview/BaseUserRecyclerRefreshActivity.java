package com.hc.essay.joke.recyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.navigationBar.DefaultNavigationBar;
import com.hc.essay.joke.R;
import com.mrl.baselibrary.ioc.ViewById;

import java.util.ArrayList;
import java.util.List;

public class BaseUserRecyclerRefreshActivity extends BaseSkinActivity {
    @ViewById(R.id.recyclerview)
    private RecyclerView mRecyclerView;
    @ViewById(R.id.layout_swipe_refresh)
    private SwipeRefreshLayout mRefreshLayout;
    private List<String> mDatas=new ArrayList<>();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_base_user_recycler_refresh);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar = new DefaultNavigationBar.Builder(this)
                .setTitle("上拉下拉")
                .builder();
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
        final TextCommonAdapter adapter =new TextCommonAdapter(this,mDatas);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent,null));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                //我在List最前面加入一条数据
                mDatas.add(0, "嘿，我是“下拉刷新”生出来的");

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                adapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_driver_01));
    }
}
