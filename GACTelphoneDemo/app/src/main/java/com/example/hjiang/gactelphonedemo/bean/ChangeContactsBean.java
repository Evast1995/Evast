package com.example.hjiang.gactelphonedemo.bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by hjiang on 16-1-21.
 */
public class ChangeContactsBean {
    private String nameStr;
    private List<String> phoneStr;
    private Bitmap bitmap;
    private Boolean isChecked;

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    public List<String> getPhoneStr() {
        return phoneStr;
    }

    public void setPhoneStr(List<String> phoneStr) {
        this.phoneStr = phoneStr;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "ChangeContactsBean{" +
                "nameStr='" + nameStr + '\'' +
                ", phoneStr=" + phoneStr +
                ", bitmap=" + bitmap +
                ", isChecked=" + isChecked +
                '}';
    }
}
