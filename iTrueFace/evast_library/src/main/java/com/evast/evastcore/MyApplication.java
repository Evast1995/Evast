package com.evast.evastcore;

import android.app.Application;

/**
 * 记得配置在Mainfest
 * Created by 72963 on 2015/11/12.
 */
public class MyApplication extends Application{
    /** 输出日志 测试环境为true输出日志 生产环境改为false不输出日志*/
    public static final boolean IS_DEBUG = true;
}
