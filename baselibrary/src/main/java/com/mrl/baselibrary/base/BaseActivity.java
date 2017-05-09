package com.mrl.baselibrary.base;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrl.baselibrary.ioc.ViewUtils;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L on 2017/5/7
 * Description:
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    public String TAG;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        TAG = this.getClass().getSimpleName();
        setContentView();
        // IOC注解注入
        ViewUtils.inject(this);
        initTitle();
        initView();
        initData();
    }


    // 设置界面视图
    protected abstract void setContentView();
    // 初始化头部
    protected abstract void initTitle();
    // 初始化界面
    protected abstract void initView();
    // 初始化数据
    protected abstract void initData();

    /**
     * 启动一个Activity
     * @param activity  需要启动的Activity的Class
     */
    public void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }

    /**
     *  findViewById 不需要再去强转
     */
    public <T extends View> T viewById(@IdRes int resId) {
        return (T) super.findViewById(resId);
    }
}
