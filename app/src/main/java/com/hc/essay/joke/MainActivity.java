package com.hc.essay.joke;


import android.os.Environment;
import android.widget.Toast;

import com.example.framelibrary.BaseSkinActivity;
import com.mrl.baselibrary.ExceptionCrashHandler;

import java.io.File;
import java.io.IOException;

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
        // 上传到服务器，后面再说.......


        }

        //每次启动去后台差分包 fix.apatch 然后修复本地BUG
        //直接获取本地内存卡里的fix.apatch   （测试）
        File fixFile=new File(Environment.getExternalStorageDirectory(),"fix.apatch");
        try {
            MyApplication.mPatchManager.addPatch(fixFile.getAbsolutePath());
            Toast.makeText(this,"修复成功",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this,"修复失败",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
