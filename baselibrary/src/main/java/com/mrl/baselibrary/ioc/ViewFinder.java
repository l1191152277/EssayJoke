package com.mrl.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L on 2017/5/7
 * Description:
 */

public class ViewFinder {
    private Activity mActivity;
    private View mView;


    public ViewFinder(Activity activity) {
        this.mActivity=activity;
    }

    public ViewFinder(View view) {
        this.mView=view;
    }

    public View findViewById(int viewId){
        return mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }
}
