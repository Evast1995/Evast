package com.evast.evastcore.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.evast.evastcore.util.other.L;

/**
 * serviceDemo
 *
 * Created by 72963 on 2015/11/24.
 */
public class ServiceDemo extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        L.i("--onBind--");
        return null;
    }

    @Override
    public void onCreate() {
        L.i("--onCreate--");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        L.i("--onStart--");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i("--onStartCommand--");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        L.i("--onDestroy--");
        super.onDestroy();
    }
}
