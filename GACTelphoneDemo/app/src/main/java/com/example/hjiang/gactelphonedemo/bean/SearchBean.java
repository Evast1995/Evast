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
    /** 类型　指当bitmap为空的时候 type 为　１:联系人无头像默认头像 2:历史记录未接　３：历史记录拨出　４：拨打错误*/
    private int type;
    public static final int NO_HEAD = 0;
    public static final int INCOMING_TYPE = 1;
    public static final int OUTGOING_TYPE = 2;
    public static final int MISSED_TYPE = 3;
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "SearchBean{" +
                "displayName='" + displayName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", bitmap=" + bitmap +
                ", type=" + type +
                '}';
    }

    /**
     * 让history的数据不重复
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        SearchBean searchBean = (SearchBean) o;
        if(searchBean.getBitmap() == this.bitmap){
            if(searchBean.getPhoneNum().equals(this.phoneNum)){
                if(searchBean.getDisplayName() == this.displayName){
                    if(searchBean.getType() == this.type){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
