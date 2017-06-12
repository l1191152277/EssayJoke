package com.hc.essay.joke.selectimage;

import android.content.Intent;
import android.view.View;

import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.navigationBar.DefaultNavigationBar;
import com.hc.essay.joke.R;

import java.util.ArrayList;

public class TestImageActivity extends BaseSkinActivity {
    private ArrayList<String> mImageList;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_test_image);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar= new DefaultNavigationBar
                .Builder(this)
                .setTitle("选择图片")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void selectImage(View view) {
        Intent intent=new Intent(this,SelectImageActivity.class);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_MODE,SelectImageActivity.MODE_MULTI);
        intent.putExtra(SelectImageActivity.EXTRA_SHOW_CAMERA,true);
        intent.putExtra(SelectImageActivity.EXTRA_DEFAULT_SELECTED_LIST,mImageList);
        startActivity(intent);
    }
}
