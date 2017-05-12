package com.hc.essay.joke;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;
import com.mrl.baselibrary.ExceptionCrashHandler;

import java.io.File;
import java.io.IOException;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/5/9
 * Description:
 */
public class MyApplication extends Application{
    public static  PatchManager mPatchManager;
    private static final String TAG="MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        //设置全局异常捕捉
        ExceptionCrashHandler.getInstance().init(this);

        //初始化阿里热修复
        mPatchManager = new PatchManager(this);
        //获取版本号
        try {
            String  versionName = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            //初始化版本
            mPatchManager.init(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 加载 patch 包
        mPatchManager.loadPatch();
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
