package com.example.hjiang.gactelphonedemo.util;

import android.content.Context;

import com.base.module.call.line.LineObj;
import com.base.module.call.line.LineObjManager;

import java.util.List;

/**
 * 与LineObjManager相关的操作
 * Created by hjiang on 16-1-8.
 */
public class LineUtils {
    private static LineObjManager lineObjManager;
    private LineUtils(Context context){
        lineObjManager = (LineObjManager)context.getSystemService(Context.CALL_LINE_SERVICE);
    };
    private static LineUtils lineUtils;
    public synchronized static LineUtils getInstance(Context context){
        if(lineUtils ==null){
            lineUtils = new LineUtils(context);
        }
        return lineUtils;
    }

    /**
     * 获取当前话机所有线路
     * @return
     */
    public List<LineObj> getAllLines(){
        return lineObjManager.getAllLines();
    }
}
