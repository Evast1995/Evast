package com.example.hjiang.gactelphonedemo;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import com.base.module.call.CallManager;
import com.example.hjiang.gactelphonedemo.service.ConfListenService;
import com.example.hjiang.gactelphonedemo.service.StatusListenService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-6.
 */
public class MyApplication extends Application{
    /** 对已有Activity做相应管理*/
    public static List<ActionBarActivity> list = new ArrayList<ActionBarActivity>();
    /** 当前本地账号的ID*/
    public static int localId = 0;
    /** 判断是否在转拨*/
    public static Boolean isTransfer = false;
    /** 当前本地账号的号码*/
    public static String localPhone;
    /** 当前的拨号模式*/
    public static int callModel = CallManager.CALLMODE_SIP;


    @Override
    public void onCreate() {
        super.onCreate();
        createLineService();
        createConfService();
    }
    private void createLineService(){
        Intent intent = new Intent(this,StatusListenService.class);
        intent.addFlags(Service.START_STICKY);
        startService(intent);
    }

    /**
     * 创建会议事件监听
     */
    private void createConfService(){
        Intent intent = new Intent(this,ConfListenService.class);
        intent.addFlags(Service.START_STICKY);
        startService(intent);
    }


}
