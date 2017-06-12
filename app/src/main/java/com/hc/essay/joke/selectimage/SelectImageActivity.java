package com.hc.essay.joke.selectimage;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.navigationBar.DefaultNavigationBar;
import com.hc.essay.joke.R;
import com.hc.essay.joke.selectimage.bean.ImageEntity;

import java.util.ArrayList;

/**
 * 图片选择器
 */
public class SelectImageActivity extends BaseSkinActivity {
    private int LOAD_TYPE=5;
    /*****************
     *  带过来的key  *
     *****************/
    //是否显示相机的EXTRA_KEY
    public static final String EXTRA_SHOW_CAMERA="EXTRA_SHOW_CAMERA";
    //总共可以选择多少张图片的EXTRA_KEY
    public static final String EXTRA_SELECT_COUNT="EXTRA_SELECT_COUNT";
    //原始图片路径的EXTRA_KEY
    public static final String EXTRA_DEFAULT_SELECTED_LIST="EXTRA_DEFAULT_SELECTED_LIST";
    //选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE="EXTRA_SELECT_MODE";
    //返回选择图片列表EXTRA_KEY
    public static final String EXTRA_RESULT="EXTRA_RESULT";
     /*****************
     * 传递过来的参数 *
     *****************/
     //单选
    public static final int MODE_MULTI = 0x0011;
    //多选
    public static final int MODE_SINGLE = 0x0012;
    //单选还是多选
    private int mMode = MODE_MULTI;
    //是否显示拍照按钮
    private boolean mShowCamera = true;
    //图片张数
    private int mMaxCount = 9;
    //已选好的图片
    private ArrayList<String> mResultList;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_image_selector);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar= new DefaultNavigationBar
                .Builder(this)
                .setTitle("所有图片")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
         Intent intent=getIntent();
        //获取传递过来的参数
        mMode = intent.getIntExtra(EXTRA_SELECT_MODE,mMode);
        mShowCamera=intent.getBooleanExtra(EXTRA_SHOW_CAMERA,mShowCamera);
        mMaxCount=intent.getIntExtra(EXTRA_SELECT_COUNT,mMaxCount);
        mResultList=intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        if (mResultList==null){
            mResultList=new ArrayList<>();
        }
        //初始化本地图片数据
        initImageList();
    }

    /**
     * ContentProvider获取内存卡中所有图片
     */
    private void initImageList() {
        //耗时操作  开线程  asyncTask
        getLoaderManager().initLoader(LOAD_TYPE,null,mLoaderCallBack);

    }

    /**
     * 加载图片的callback
     */
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallBack =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                private final String[] IMAGE_PROJECTION={
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media.MIME_TYPE,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media._ID
                };

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    String sql=IMAGE_PROJECTION[4] +" >0 AND "+IMAGE_PROJECTION[3]+
                            "=? OR "+IMAGE_PROJECTION[3] +" =? ";
                    //查询数据库一样
                    CursorLoader cursorLoader=new CursorLoader(SelectImageActivity.this,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_PROJECTION,
                            sql,new String[]{"image/jpeg","image/png"},IMAGE_PROJECTION[2]+" DESC");
                    return cursorLoader;
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    //解析，封装到集合
                    if (data != null && data.getCount() > 0) {
                        ArrayList<ImageEntity> images = new ArrayList<>();
                        //不断遍历循环
                        while (data.moveToNext()) {
                            String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                            String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                            long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

                            //封装数据对象
                            ImageEntity imageEntity=new ImageEntity(path,name,dateTime);
                            images.add(imageEntity);
                        }

                        //显示列表数据
                        shouImageList(images);
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {

                }
            };

    /**
     * 显示列表数据
     * @param images
     */
    private void shouImageList(ArrayList<ImageEntity> images) {

    }
}
