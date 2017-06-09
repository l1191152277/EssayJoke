package com.mrl.baselibrary.navigationbar;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/6/9
 * Description: 头部的基类
 */
public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {
    private View mNavigationView;
    private P mParams;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    protected void setText(int viewId, String text) {
        TextView tv= findViewById(viewId);
        if (! TextUtils.isEmpty(text)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    protected void setIcon(int viewId, int iconId) {

    }

    protected void setOnClickListener(int viewId, View.OnClickListener listener){
        findViewById(viewId).setOnClickListener(listener);
    }

    public <T extends View> T findViewById(int viewId){
        return (T) mNavigationView.findViewById(viewId);
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {
        //创建view
        mNavigationView= LayoutInflater.from(mParams.mContext).
              inflate(bindLayoutId(),mParams.mParent,false);
        //添加
        mParams.mParent.addView(mNavigationView,0);
        //绑定
        applyView();
    }



    public abstract static class Builder{
        AbsNavigationParams P;
        public Builder(Context context, ViewGroup parent) {
            P=new AbsNavigationParams(context,parent);
        }

        public abstract AbsNavigationBar builder();


        public static class AbsNavigationParams{
            Context mContext;
            ViewGroup mParent;
            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext=context;
                this.mParent=parent;
            }
        }



    }

}
