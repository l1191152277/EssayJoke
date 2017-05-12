package com.hc.essay.joke;


import com.example.framelibrary.BaseSkinActivity;
import com.mrl.baselibrary.ExceptionCrashHandler;

import java.io.File;

public class MainActivity extends BaseSkinActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // 获取上次的崩溃信息
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        if(crashFile.exists()){
        // 上传到服务器


        }
    }
}
