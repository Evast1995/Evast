package com.example.hjiang.gactelphonedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.base.module.call.Conf;
import com.base.module.call.StatusChangeListener;
import com.base.module.call.line.LineObj;
import com.example.hjiang.gactelphonedemo.IStatusChangeListener;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
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
