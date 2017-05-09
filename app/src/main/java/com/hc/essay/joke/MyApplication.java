package com.hc.essay.joke;

import android.app.Application;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.mrl.baselibrary.ExceptionCrashHandler;

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
    }
}
