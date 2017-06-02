package com.mrl.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.mrl.baselibrary.R;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/6/2
 * Description:  自定义万能Dialog
 */
public class BaseDialog extends Dialog{
    private DialogController mAlert;

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mAlert=new DialogController(this,getWindow());
    }
    public static class Builder{
        private final DialogController.DialogParams P;

        /**
         * @param context the parent context
         */
        public Builder(@NonNull Context context) {
            this(context, R.style.DialogTheme);
        }


        /**
         * @param context the parent context
         * @param themeResId the resource ID of the theme
         */
        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            P = new DialogController.DialogParams(context, themeResId);
        }


        /**
         * 设置Dialog布局View
         */
        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置Dialog布局Layout Id
         */
        public Builder setContentView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * 设置文本
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId ,CharSequence text ){
            P.mTextArray.put(viewId,text);
            return this;
        }

        /**
         * 设置点击事件
         * @param viewId
         * @param onClickListener
         * @return
         */
        public Builder setOnClickListener(int viewId ,View.OnClickListener onClickListener){
            P.mOnClickListenerArray.put(viewId,onClickListener);
            return this;
        }

        /**
         * 设置全屏
         * @return
         */
        public Builder fullWidth(){
            P.mWidth= ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置弹出方向和动画
         * @param isAnimation
         * @return
         */
        public Builder fromBottom(boolean isAnimation){
           if (isAnimation){
              P.mAnimations = R.style.DialogFromBottomAnim;
           }
            P.mGravity=Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置宽高
         *
         * @return
         */
        public Builder widthAndHeight(int width,int height ){
            P.mWidth=width;
            P.mHeight=height;
            return this;
        }


        /**
         * 添加默认动画
         * @return
         */
        public Builder addDufaultAnimation(){
            P.mAnimations=R.style.DialogFromBottomAnim;

            return this;
        }

        /**
         * 添加动画
         * @param styleAnimation
         * @return
         */
        public Builder setAnimation(int styleAnimation){
            P.mAnimations=styleAnimation;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public BaseDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public BaseDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public BaseDialog.Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 创建Dialog.
         */
        public BaseDialog create() {
            // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
            // so we always have to re-set the theme
            final BaseDialog dialog = new BaseDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         *     显示Dialog
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         */
        public BaseDialog show() {
            final BaseDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }
}
