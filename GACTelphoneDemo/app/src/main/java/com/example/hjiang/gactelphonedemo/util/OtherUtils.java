package com.example.hjiang.gactelphonedemo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-7.
 */
public class OtherUtils {

    /**
     * 布置拨号盘数据
     * @return
     */
    public static List<String> getDialsNum(){
        List<String> list = new ArrayList<String>();
        for(int i=0;i<12;i++){
            if(i>=0 && i<9) {
                list.add(String.valueOf(i + 1));
            }else if(i==9){
                list.add("*");
            }else if(i==10){
                list.add("0");
            }else if(i==11){
                list.add("#");
            }
        }
        return list;
    }

    /**
     * 判断运行的APP是否在前台
     * @param context
     * @return
     */
    public static Boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
            /* BACKGROUND=400 EMPTY=500 FOREGROUND=100 GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200 */
                Log.i(context.getPackageName(), "此appimportace =" + appProcess.importance +
                        ",context.getClass().getName()=" + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台" + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台" + appProcess.processName);
                    return false;
                }
            }
        } return false;
    }
}
