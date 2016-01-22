package com.example.hjiang.gactelphonedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.base.module.call.Conf;
import com.base.module.call.StatusChangeListener;
import com.base.module.call.line.LineObj;
import com.base.module.call.line.LineObjManager;
import com.example.hjiang.gactelphonedemo.IStatusChangeListener;
import com.example.hjiang.gactelphonedemo.MyApplication;
import com.example.hjiang.gactelphonedemo.activity.CallingActivity;
import com.example.hjiang.gactelphonedemo.activity.ConnectingActivity;
import com.example.hjiang.gactelphonedemo.activity.RingingActivity;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
import com.example.hjiang.gactelphonedemo.util.Contants;
import com.example.hjiang.gactelphonedemo.util.LineStatusListener;

import java.util.List;

/**
 * Created by hjiang on 16-1-11.
 */
public class StatusListenService extends Service implements IStatusChangeListener {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StatusChangeListener changeListener= new LineStatusListener(this);
        CallUtils.getInstance(this).addStatusChangeListener(getPackageName(),
                changeListener.callback, StatusChangeListener.LISTEN_LINE_STATE, true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 打开RingActivity界面
     */
    private void openRingingActivity(LineObj line){
        Intent intent = new Intent(this,RingingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contants.INTENT_LINEOBJ, line);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 打开通话CallActivity界面
     */
    private void openCallingActivity(LineObj line){
        Intent intent = new Intent(this,CallingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contants.INTENT_LINEOBJ, line);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * 连接成功进入通话页面
     * @param line
     */
    private void openConnectingActivity(LineObj line){
        Intent intent = new Intent(this,ConnectingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contants.INTENT_LINEOBJ, line);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 设置暂停状态时的操作１：按下暂停按键 2：呼叫转移时会触发暂停事件
     * @param line
     */
    private void setOnHoldHandle(LineObj line){
        /** 暂停是因为呼叫转移引起的*/
        if(MyApplication.isTransfer) {
            Intent intent = new Intent(this,CallingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(Contants.IS_TRANSFER,true);
            bundle.putSerializable(Contants.INTENT_LINEOBJ, line);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            startActivity(intent);
            MyApplication.isTransfer = false;
        }else{//是暂停事件引起

        }
    }


    /**
     * 发送线路变化的广播
     * @param status
     */
    private void sendBroadCastForStautsChange(int status,LineObj lineObj){
        Intent intent = new Intent(Contants.BROADCAST_ACTION);
        Bundle bundle = new Bundle();
        bundle.putInt(Contants.CALL_STATUS, status);
        bundle.putSerializable(Contants.LINEOBJ_KEY,lineObj);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    public void onLineStateChanged(List<LineObj> lines, LineObj line) {
        Boolean isInConf = line.getIsInConf();
        /** 发送状态变化广播*/
        sendBroadCastForStautsChange(line.getState(), line);
        /** 根据线路状态的改变情况来做对应的相关操作*/
        switch (line.getState()){
            /** 响铃状态*/
            case LineObjManager.STATUS_RINGING:{
                openRingingActivity(line);
                break;
            }
            /** 空闲状态*/
            case LineObjManager.STATUS_IDLE:{
                break;
            }
            /** 建立连接*/
            case LineObjManager.STATUS_CONNECTED:{
                if(!isInConf) {
                    openConnectingActivity(line);
                }
                break;
            }
            case LineObjManager.STATUS_FAILED:{
                break;
            }
            case LineObjManager.STATUS_DIALING:{
                break;
            }
            case LineObjManager.STATUS_CONFERENCE:{
                break;
            }
            case LineObjManager.STATUS_CALLING:{
                if(!isInConf){
                    openCallingActivity(line);
                }
                break;
            }
            case LineObjManager.STATUS_ENDING:{
                break;
            }
            case LineObjManager.STATUS_ONHOLD:{
                if(!isInConf){
                    setOnHoldHandle(line);
                }
                break;
            }
        }

    }

    @Override
    public void onConfStateChanged(Conf conf) {
    }

    @Override
    public void onStartLineRecord(LineObj lineobj) {
    }

    @Override
    public void onStopLineRecord(LineObj lineobj) {
    }

    @Override
    public void onStartConfRecord(String id) {
    }

    @Override
    public void onStopConfRecord(Conf conf) {
    }
}
