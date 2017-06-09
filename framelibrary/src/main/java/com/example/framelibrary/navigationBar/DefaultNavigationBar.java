package com.example.framelibrary.navigationBar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.framelibrary.R;
import com.mrl.baselibrary.navigationbar.AbsNavigationBar;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/6/9
 * Description:
 */
public class DefaultNavigationBar extends AbsNavigationBar <DefaultNavigationBar.Builder.DefaultNavigationParams>{

    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.navigation_title,getParams().mTitle);
        setText(R.id.navigation_righttext,getParams().mRightText);
        setIcon(R.id.navigation_righttext,getParams().mRightIcon);
        setOnClickListener(R.id.navigation_righttext, getParams().mRightOnClickListener);
        setOnClickListener(R.id.navigation_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getParams().mContext.
            }
        });
    }



    public static class Builder extends AbsNavigationBar.Builder {
        DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }

        //设置效果
        //设置title
        public DefaultNavigationBar.Builder setTitle(String title){
            P.mTitle=title;
            return this;
        }
        //设置右边文字
        public DefaultNavigationBar.Builder setRightText(String rightText){
            P.mRightText=rightText;
            return this;
        }
        //设置右边图片
        public DefaultNavigationBar.Builder setRightIcon(int rightIcon){
            P.mRightIcon=rightIcon;
            return this;
        }

        //设置右边点击事件
        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener onClickListener){
            P.mRightOnClickListener=onClickListener;
            return this;
        }


        public static class DefaultNavigationParams extends AbsNavigationParams {
            public String mTitle;
            public String mRightText;
            public int mRightIcon;
            public View.OnClickListener mRightOnClickListener;
            public Context mContext;
            //所有的效果
            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
                this.mContext=context;
            }
        }
    }
}
