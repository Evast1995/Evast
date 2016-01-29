package com.example.hjiang.gactelphonedemo.bean;

/**
 * 会议成员的bean
 * Created by hjiang on 16-1-28.
 */
public class MemberBean {
    /** 电话号码*/
    private String phone;
    /** 添加拨号规则的号码　　暂时不考虑不添加*/
    private String originNum;
    /** 用来呼出的本地账号id*/
    private int account;
    /** 表示从哪里来如通讯录，历史记录等　在CallSettingsManager中有对应的参数　与拨号规则有关这里不考虑*/
    private int origin;
    /** 拨号方式　在CallManger中有相应的参数*/
    private int callMode;
    /** 会议id*/
    private int meetingId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOriginNum() {
        return originNum;
    }

    public void setOriginNum(String originNum) {
        this.originNum = originNum;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getCallMode() {
        return callMode;
    }

    public void setCallMode(int callMode) {
        this.callMode = callMode;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public String toString() {
        return "MemberBean{" +
                "phone='" + phone + '\'' +
                ", originNum='" + originNum + '\'' +
                ", account=" + account +
                ", origin=" + origin +
                ", callMode=" + callMode +
                ", meetingId=" + meetingId +
                '}';
    }
}
