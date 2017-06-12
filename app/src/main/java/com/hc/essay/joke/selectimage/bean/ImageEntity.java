package com.hc.essay.joke.selectimage.bean;

import android.text.TextUtils;

/**
 * Email:1191152277@qq.com
 * Created by Mr.L  on 2017/6/12
 * Description: 图片实体类
 */
public class ImageEntity {
    public String path;
    public String name;
    public long time;

    public ImageEntity(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImageEntity){
            ImageEntity compare= (ImageEntity) obj;
            return TextUtils.equals(this.path,compare.path);
        }
        return false;
    }
}
