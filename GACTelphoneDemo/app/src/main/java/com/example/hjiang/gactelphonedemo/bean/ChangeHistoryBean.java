package com.example.hjiang.gactelphonedemo.bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * 选择联系人界面中历史记录
 * Created by hjiang on 16-2-1.
 */
public class ChangeHistoryBean {
    private String phoneNumStr;
    private List<String> nameStr;
    private int type;
    private Bitmap bitmap;
    private int account;
    private Boolean isSelected;

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getPhoneNumStr() {
        return phoneNumStr;
    }

    public void setPhoneNumStr(String phoneNumStr) {
        this.phoneNumStr = phoneNumStr;
    }

    public List<String> getNameStr() {
        return nameStr;
    }

    public void setNameStr(List<String> nameStr) {
        this.nameStr = nameStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "ChangeHistoryBean{" +
                "phoneNumStr='" + phoneNumStr + '\'' +
                ", nameStr='" + nameStr + '\'' +
                ", type=" + type +
                ", bitmap=" + bitmap +
                ", account=" + account +
                ", isSelected=" + isSelected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        ChangeHistoryBean changeHistoryBean = (ChangeHistoryBean) o;
        if(changeHistoryBean.getBitmap() == this.bitmap){
            if(changeHistoryBean.getPhoneNumStr().equals(this.phoneNumStr)){
                if(changeHistoryBean.getNameStr() == this.getNameStr()){
                    if(changeHistoryBean.getType() == this.type){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
