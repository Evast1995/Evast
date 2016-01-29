package com.example.hjiang.gactelphonedemo.bean;

/**
 * Created by hjiang on 16-1-28.
 */
public class MeetingBean {
    /** 会议名称*/
    private String name;
    /** 会议状态: 0:no start 1:closed 2:missed 此处默认为没有开始*/
    private int state;
    /** 会议循环周期 0:不循环 1:星期一　2:星期二　4:星期三　8:星期四　16:星期五 32:星期六　64:星期天
    private  * 当是几天都循环时　即将它们相加 如星期一和星期二 即:1+2=3*/
    private int cycleTime;
    /** 是否有密码　0:没有密码　1:有密码*/
    private int haspwd ;
    /** 如果有密码则添加　无密码则为空*/
    private String pswStr;
    /** 时区* "Asia/Shanghai"*/
    private String timeZone;
    /** 开始时间　时间戳 */
    private long startTime;
    /** 会议预计时长　毫秒*/
    private long duration ;
    /** 会议提醒时长　没有则-1 毫秒**/
    private long reminderTime;
    /** 是否自动会议*/
    private int autoCall;
    /** 是否自动接听会议来电*/
    private int autoAnswer;
    /** 是否拦截　即使是否加锁*/
    private int intercept;
    /** 是否自动录音*/
    private int autoRecord;
    /**　入会时禁声*/
    private int enterMute;
    /** 是否错过会议了　１:进行了会议　0:错过了会议*/
    private int isRead;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public int getHaspwd() {
        return haspwd;
    }

    public void setHaspwd(int haspwd) {
        this.haspwd = haspwd;
    }

    public String getPswStr() {
        return pswStr;
    }

    public void setPswStr(String pswStr) {
        this.pswStr = pswStr;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(long reminderTime) {
        this.reminderTime = reminderTime;
    }

    public int getAutoCall() {
        return autoCall;
    }

    public void setAutoCall(int autoCall) {
        this.autoCall = autoCall;
    }

    public int getAutoAnswer() {
        return autoAnswer;
    }

    public void setAutoAnswer(int autoAnswer) {
        this.autoAnswer = autoAnswer;
    }

    public int getIntercept() {
        return intercept;
    }

    public void setIntercept(int intercept) {
        this.intercept = intercept;
    }

    public int getAutoRecord() {
        return autoRecord;
    }

    public void setAutoRecord(int autoRecord) {
        this.autoRecord = autoRecord;
    }

    public int getEnterMute() {
        return enterMute;
    }

    public void setEnterMute(int enterMute) {
        this.enterMute = enterMute;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "MeetingBean{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", cycleTime=" + cycleTime +
                ", haspwd=" + haspwd +
                ", pswStr='" + pswStr + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", reminderTime=" + reminderTime +
                ", autoCall=" + autoCall +
                ", autoAnswer=" + autoAnswer +
                ", intercept=" + intercept +
                ", autoRecord=" + autoRecord +
                ", enterMute=" + enterMute +
                ", isRead=" + isRead +
                '}';
    }
}
