package com.hc.essay.joke.infraedcode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ScanBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("-MyBroadcastReceiver--",intent.toString());
        Log.e(TAG,intent.getAction().toString());
        if (intent.getAction().equals("com.android.server.scannerservice.broadcast")) {
            //收到广播所获取的值，与手持机上保持一致不能，一般情况不能改变
            String data = intent.getStringExtra("scannerdata");
            Log.e(TAG, "intent:" + intent);
            Log.e(TAG, "data:" + data);
        }
        //扫描后获得的值 可能需要处理。
        Activity currentActivity = MyActivityManager.getInstance().getCurrentActivity();
        if (currentActivity != null) {
            if (currentActivity instanceof InfraredCodeActivity){
                Log.e(TAG,"处理结果！");
            }
           /* if(currentActivity instanceof ShelveActivity){
                ((ShelveActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof ShelveDetailActivity){
                ((ShelveDetailActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof UnShelveActivity){
                ((UnShelveActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof PDActivity){
                ((PDActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof DBActivity){
                ((DBActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof JYGHActivity){
                ((JYGHActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof CheckTenAndTenActivity){
                ((CheckTenAndTenActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof WBActivity){
                ((WBActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof CJActivity){
                ((CJActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof DetailActivity){
                ((DetailActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof ScrapDetailActivity){
                ((ScrapDetailActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof ShelveByBarCodeActivity){
                ((ShelveByBarCodeActivity)currentActivity).dealScanCode(name);
            }else if(currentActivity instanceof AbandonActivity){
                ((AbandonActivity)currentActivity).dealScanCode(name);
            }*/
           else {
                Toast.makeText(context, "当前页面不需要扫码！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "当前页面不需要扫码！", Toast.LENGTH_SHORT).show();
        }

    }
}