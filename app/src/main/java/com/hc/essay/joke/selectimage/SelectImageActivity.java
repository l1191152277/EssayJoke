package com.hc.essay.joke.selectimage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.navigationBar.DefaultNavigationBar;
import com.hc.essay.joke.R;
import com.hc.essay.joke.selectimage.adapter.SelectImageListAdapter;
import com.hc.essay.joke.selectimage.bean.ImageEntity;
import com.mrl.baselibrary.ioc.ViewById;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * 图片选择器
 */
public class SelectImageActivity extends BaseSkinActivity implements SelectImageListAdapter.SelectedChangeListener {
    private int LOAD_TYPE = 5;
    /*****************
     *  带过来的key  *
     *****************/
    //是否显示相机的EXTRA_KEY
    public static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    //总共可以选择多少张图片的EXTRA_KEY
    public static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    //原始图片路径的EXTRA_KEY
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "EXTRA_DEFAULT_SELECTED_LIST";
    //选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    //返回选择图片列表EXTRA_KEY
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
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
    public static int mMaxCount = 9;
    //已选好的图片
    private ArrayList<String> mResultList;

    @ViewById(R.id.image_list_rv)
    private RecyclerView mRecycler;
    private SelectImageListAdapter adapter;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_image_selector);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this)
                .setTitle("所有图片")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        //获取传递过来的参数
        mMode = intent.getIntExtra(EXTRA_SELECT_MODE, mMode);
        mShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, mShowCamera);
        mMaxCount = intent.getIntExtra(EXTRA_SELECT_COUNT, mMaxCount);
        mResultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        if (mResultList == null) {
            mResultList = new ArrayList<>();
        }
        //初始化本地图片数据
        initImageList();
    }

    /**
     * ContentProvider获取内存卡中所有图片
     */
    private void initImageList() {
        //耗时操作  开线程  asyncTask
        getLoaderManager().initLoader(LOAD_TYPE, null, mLoaderCallBack);

    }

    /**
     * 加载图片的callback
     */
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallBack =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                private final String[] IMAGE_PROJECTION = {
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media.MIME_TYPE,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media._ID
                };

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    String sql = IMAGE_PROJECTION[4] + " >0 AND " + IMAGE_PROJECTION[3] +
                            "=? OR " + IMAGE_PROJECTION[3] + " =? ";
                    //查询数据库一样
                    CursorLoader cursorLoader = new CursorLoader(SelectImageActivity.this,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                            sql, new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
                    return cursorLoader;
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    //解析，封装到集合
                    if (data != null && data.getCount() > 0) {
                        ArrayList<ImageEntity> images = new ArrayList<>();
                        if (mShowCamera) {
                            images.add(null);
                        }

                        //不断遍历循环
                        while (data.moveToNext()) {
                            String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                            String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                            long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

                            //封装数据对象
                            ImageEntity imageEntity = new ImageEntity(path, name, dateTime);
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
     *
     * @param images
     */
    private void shouImageList(ArrayList<ImageEntity> images) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecycler.setLayoutManager(layoutManager);
        adapter = new SelectImageListAdapter(this, images, this);
        mRecycler.setAdapter(adapter);
    }


    /**
     * 返回应用时回调方法
     */

    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                Log.i("TestFile",
                        "SD card is not avaiable/writeable right now.");
                return;
            }
            new DateFormat();
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");//

            FileOutputStream b = null;
            File file = new File("/sdcard/Image/");
            file.mkdirs();//
            String fileName = "/sdcard/Image/" + name;

            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);//
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Log.e("保存成功---", "路径--" + fileName);
                //view.setImageBitmap(bitmap);// Ω´Õº∆¨œ‘ æ‘⁄ImageView¿Ô
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }

        }
    }

    @Override
    public void onSelectedCountChanged(int count) {
        if (count > 0) {
            String[] paths = adapter.getSelectedPath();
        }
    }


}
