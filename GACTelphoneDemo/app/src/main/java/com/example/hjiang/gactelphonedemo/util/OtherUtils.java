package com.example.hjiang.gactelphonedemo.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className
     *            某个界面名称
     */
    public static Boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 分钟转化为毫秒
     * @param minute
     * @return
     */
    public static long minuteTomillionSecond(long minute){
        return minute*60*1000;
    }


    /**
     * 获取当天　某个时间的　时间搓　
     * @param hour　当天几点
     * @param minute　当天几分
     * @return
     */
    public static long getNowDayCurrent(int hour,int minute){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String timeStr=""+year+"年"+month+"月"+day+"日"+hour+"时"+minute+"分";
        long timeCurrent = getTime(timeStr);

        return timeCurrent;
    }

    /**
     * 通过字符串获取时间戳
     * @param timeStr
     * @return
     */
    public static long getTime(String timeStr){
        long timeCurrent = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        Date date = null;
        try {
            date = sdf.parse(timeStr);
            timeCurrent = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeCurrent;
    }

}
