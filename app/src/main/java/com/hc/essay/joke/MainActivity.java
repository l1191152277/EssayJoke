package com.hc.essay.joke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrl.baselibrary.ioc.CheckNet;
import com.mrl.baselibrary.ioc.OnClick;
import com.mrl.baselibrary.ioc.VIewUtils;
import com.mrl.baselibrary.ioc.ViewById;

public class MainActivity extends AppCompatActivity{
    @ViewById(R.id.text_tv)
    private TextView mTextTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VIewUtils.inject(this);
        mTextTv.setText("IOCTEXT");
    }

    @OnClick(R.id.text_tv)
    @CheckNet
    public void login(View view){
        Toast.makeText(this,"登录：",Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.text_iv)
    public void onClick(View view){
        Toast.makeText(this,"点击：",Toast.LENGTH_LONG).show();
    }

}
