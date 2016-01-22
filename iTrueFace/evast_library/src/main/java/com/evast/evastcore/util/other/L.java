package com.evast.evastcore.util.other;

import android.util.Log;

import com.evast.evastcore.MyApplication;

/**
 * 日志控制工具类
 */
public class L {
	private static final String KEY = "--main--";

	public static void i(Object message) {
		if (MyApplication.IS_DEBUG) {
			Log.i(KEY, message.toString());
		}
	}

	public static void e(Object message) {
		if (MyApplication.IS_DEBUG) {
			Log.e(KEY, message.toString());
		}
	}

	public static void d(Object message) {
		if (MyApplication.IS_DEBUG) {
			Log.d(KEY, message.toString());
		}
	}

	public static void w(Object message) {
		if (MyApplication.IS_DEBUG) {
			Log.w(KEY, message.toString());
		}
	}

	public static void w(Object message, Throwable tr) {
		if (MyApplication.IS_DEBUG) {
			Log.w(KEY, message.toString(), tr);
		}
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, Object msg) {
		if (MyApplication.IS_DEBUG)
			Log.i(tag, msg.toString());
	}

	public static void d(String tag, Object msg) {
		if (MyApplication.IS_DEBUG)
			Log.i(tag, msg.toString());
	}

	public static void e(String tag, Object msg) {
		if (MyApplication.IS_DEBUG)
			Log.i(tag, msg.toString());
	}

	public static void v(String tag, Object msg) {
		if (MyApplication.IS_DEBUG)
			Log.i(tag, msg.toString());
	}
}
