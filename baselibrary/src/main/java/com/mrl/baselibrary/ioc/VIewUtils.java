package com.mrl.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L on 2017/5/7
 * Description:View的findviewbyid的辅助类
 */

public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }


    //兼容上面方法
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 属性注入
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1. 获取类里面所有的属性
        Class<?> clazz=object.getClass();
        //获取所有属性  包括共有和私有
        Field[] fields= clazz.getDeclaredFields();

        //2. 获取viewById里面的value值
        for(Field field:fields){
            ViewById viewById=field.getAnnotation(ViewById.class);
            if(viewById!=null){
                //获取注解里面的ID值
                int viewId= viewById.value();
                //3. findViewById找到找到View
                View view=finder.findViewById(viewId);
                if(view!=null){
                    //能够注入所有注释
                    field.setAccessible(true);
                    //4. 动态的注入找到的View
                    try {
                        field.set(object,view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 事件注入
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //1. 获取类里所有的方法
        Class<?> clazz=object.getClass();
        Method[] methods=clazz.getMethods();
        //2. 获取OnClick里面的value值
        for(Method method:methods){
            OnClick onClick=method.getAnnotation(OnClick.class);
            if(onClick!=null){
               int[]viewIds= onClick.value();
                for (int viewId:viewIds){
                    //3. findViewById找到找到View
                    View view= finder.findViewById(viewId);

                    //扩展功能 检测网络
                    Boolean isCheckNet=method.getAnnotation(CheckNet.class)!=null;
                    if(view!=null){
                        //4. setOnClickListener
                        view.setOnClickListener(new DeclaredOnClickListener(method,object,isCheckNet));
                    }
                }
            }
        }
    }



    /**
     * 事件的监听
     */
    private static class DeclaredOnClickListener implements View.OnClickListener{
        private Method mMethod;
        private Object mObject;
        private Boolean mIsCheckNet;

        public DeclaredOnClickListener(Method method, Object object, Boolean isCheckNet) {
            this.mMethod=method;
            this.mObject=object;
            this.mIsCheckNet=isCheckNet;
        }


        @Override
        public void onClick(View v) {
            //需不需要检测网络
            if(mIsCheckNet){
                //需要
                if(!isConnected(v.getContext())){
                    Toast.makeText(v.getContext(),"您的网络不给力",Toast.LENGTH_LONG).show();
                    return;
                }
            }
            //点击会调用的方法
            try {
                mMethod.setAccessible(true);//能够注入所有注释 包括共有和私有
                //5. 反射执行方法
                mMethod.invoke(mObject,v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject,null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        /**
         * 判断网络是否连接
         */
        public static boolean isConnected(Context context)
        {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (null != connectivity)
            {

                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (null != info && info.isConnected())
                {
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
