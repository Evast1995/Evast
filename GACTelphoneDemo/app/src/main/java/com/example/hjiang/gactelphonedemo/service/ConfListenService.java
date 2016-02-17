package com.example.hjiang.gactelphonedemo.service;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.base.module.call.Conf;
import com.base.module.call.StatusChangeListener;
import com.base.module.call.line.LineObj;
import com.example.hjiang.gactelphonedemo.IStatusChangeListener;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
import com.example.hjiang.gactelphonedemo.util.Contants;
import com.example.hjiang.gactelphonedemo.util.LineStatusListener;

import java.util.List;

/**
 * 会议事件的监听
 * Created by hjiang on 16-1-15.
 */
public class ConfListenService extends Service implements IStatusChangeListener {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StatusChangeListener changeListener= new LineStatusListener(this);
        CallUtils.getInstance(this).addStatusChangeListener(getPackageName(),
                changeListener.callback, StatusChangeListener.LISTEN_CONF_STATE, true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLineStateChanged(List<LineObj> lines, LineObj line) {

    }

    @Override
    public void onConfStateChanged(Conf conf) {
        setConfChangeBroadCast(conf.getIsConfOnHold(),conf.getIsMutedAll(),conf.isLocked());
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

    /** 发送会议变化的广播*/
    private void setConfChangeBroadCast(Boolean isOnHold,Boolean isMute,Boolean isLock){
        Intent intent = new Intent();
        intent.setAction(Contants.BROADCAST_CONF);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Contants.IS_ONHOLD_KEY,isOnHold);
        bundle.putBoolean(Contants.IS_MUTE_KEY,isMute);
        bundle.putBoolean(Contants.IS_LOCK,isLock);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

}
