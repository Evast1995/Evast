package com.example.hjiang.gactelphonedemo.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.example.hjiang.gactelphonedemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
                list.add(".");
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
        long timeCurrent = getTime(year, month, day, hour, minute);
        return timeCurrent;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return mYear + "年" +String.format("%02d",Integer.valueOf(mMonth)) + "月" +
                String.format("%02d",Integer.valueOf(mDay))+"日"+"星期"+mWay;
    }

    /**
     * 获取当前时间　时：分
     * @return
     */
    public static String getCurrentTime(){
        Date date = new Date();
        return String.format("%02d",date.getHours())+":"+String.format("%02d", date.getMinutes());
    }

    public static int getCurrentWeek(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getFirstDayOfWeek();
    }

    /**
     * 拼接出日期
     * 通过年月日计算周几 这里以20世纪为准
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String spellDate(int year,int month,int day){
        String myWeek = null;
        int c = 20;//表示多少世纪
        int m = month;
        if(m == 1){
            m = 13;
        }else if(m ==2){
            m = 14;
        }
        int week = (year+(year/4)+(c/4)-2*c+(26*(m+1)/10)+day-1)%7;
        switch(week) {
            case 0:
                myWeek="日";
                break;
            case 1:
                myWeek="一";
                break;
            case 2:
                myWeek="二";
                break;
            case 3:
                myWeek="三";
                break;
            case 4:
                myWeek="四";
                break;
            case 5:
                myWeek="五";
                break;
            case 6:
                myWeek="六";
                break;
            default:
                break;
        }

        return year + "年" +String.format("%02d",(month+1)) + "月" + String.format("%02d",day)+"日"+"星期"+myWeek;
    }

    /**
     * 获取时间戳
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return
     */
    public static long getTime(int year,int month,int day,int hour,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        long timeCurrent = calendar.getTimeInMillis();
        return timeCurrent;
    }

    /**
     * 获取本周几中某个时间段的时间
     * @param week
     * @param hour
     * @param minute
     * @param isNextWeek
     * @return
     */
    public static long getCurrentWeekTime(int week,int hour,int minute,Boolean isNextWeek){
        Calendar calendar = Calendar.getInstance();
        if(isNextWeek) {
            calendar.add(Calendar.WEEK_OF_MONTH, calendar.getFirstDayOfWeek());
        }
        calendar.set(Calendar.DAY_OF_WEEK,week);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前时区
     * @return
     */
    public static String getCurrentTimeZone(){
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getDisplayName();
    }

    /** 判断一个字符串是否是标准的long型*/
    public static boolean isValidLong(String str){
        try{
            long v = Long.parseLong(str);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取一段时间的字符串　格式 xxxx年xx月xx日xx时：xx分-xx时xx分
     * @param timeStamp　开始时间的时间戳
     * @param context
     * @param timeLong 时间长度　单位ms
     * @return
     */
    public static String getTimeStrByTimeStamp(long timeStamp,Context context,long timeLong){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        int year = calendar.get(Calendar.YEAR);
        int month =calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.getTime().getHours();
        int minutes = calendar.getTime().getMinutes();

        calendar.setTimeInMillis(timeStamp+timeLong);
        int endHour = calendar.getTime().getHours();
        int endMinutes = calendar.getTime().getMinutes();
        String startTimeStr = ""+hour+":"+minutes;
        String endTimeStr = ""+endHour+":"+endMinutes;
        String timeStr = context.getResources().getString(R.string.time_format);
        timeStr = String.format(timeStr,year,month,day,startTimeStr,endTimeStr);
        return timeStr;
    }

    /**
     * 通过时间戳获取时间字符串
     * @param stamp
     * @return
     */
    public static String getTimeStrByStamp(long stamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(stamp);
        return time;
    }

    /**
     * 通过毫秒获取分钟
     * @param million
     * @return
     */
    public static int getMinuteByMillion(long million){
        return (int) (million/60/1000);
    }

    /**
     * 获取该时间戳下的日期 格式xxxx年xx月xx日
     * @return
     */
    public static String getDateByStamp(long million){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(million);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dataStr =  ""+String.format("%02d",year)+"年"+String.format("%02d",month)+"月"+String.format("%02d",day)+"日";
        return dataStr;
    }

    /**
     * 获取该时间戳下的时间 格式xx:xx
     * @param million
     * @return
     */
    public static String getTimeByStamp(long million){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(million);
        int hour = calendar.getTime().getHours();
        int minute = calendar.getTime().getMinutes();
        String timeStr = ""+String.format("%02d",hour)+":"+String.format("%02d",minute);
        return timeStr;
    }

}
