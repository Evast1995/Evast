package com.example.hjiang.gactelphonedemo.util;

/**
 * 常量
 * Created by hjiang on 16-1-8.
 */
public interface Contants {
    String INTENT_LINEOBJ ="lineobj";
    String CALL_STATUS = "call_status";
    String LINE_MUTE = "line_ismute";
    /** 存放line信息的key*/
    String LINEOBJ_KEY = "lineObj";
    String BROADCAST_ACTION = "com.example.hjiang.gactelphonedemo"+" statuschange";

    String BROADCAST_PHONE =  "com.example.hjiang.gactelphonedemo"+" phoenchange";

    String BROADCAST_CONF = "com.example.hjiang.gactelphonedemo" + "confchange";

    /** 电话集合的key值*/
    String PHONE_LIST_KEY = "phone_list_key";
    /** 是否是呼叫转移key*/
    String IS_TRANSFER = "transfer";
    /** 是否是会议通话key*/
    String IS_CONFLINE = "conf_line";
    /** 电话的key值*/
    String PHONE_NUM = "phonenum";
    /** 会议信息的key值*/
    String MEETING_BEAN = "meeting_bean";
    /** 是否全部暂停*/
    String IS_ONHOLD_KEY = "conf_onhold";
    /** 是否全部静音*/
    String IS_MUTE_KEY = "conf_mute";
    /** 是否上锁*/
    String IS_LOCK = "conf_lock";
}
