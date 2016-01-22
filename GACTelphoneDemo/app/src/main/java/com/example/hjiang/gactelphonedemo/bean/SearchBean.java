package com.example.hjiang.gactelphonedemo.bean;

import android.graphics.Bitmap;

/**
 * 编辑框变化时　切换到搜索页面
 * Created by hjiang on 16-1-19.
 */
public class SearchBean {
    private String displayName;
    private String phoneNum;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "displayName='" + displayName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
