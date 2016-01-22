package com.example.hjiang.gactelphonedemo;

import com.base.module.call.Conf;
import com.base.module.call.line.LineObj;

import java.util.List;

/**
 * Created by hjiang on 16-1-8.
 */
public interface IStatusChangeListener {

     void onLineStateChanged(List<LineObj> lines, LineObj line);
     void onConfStateChanged(Conf conf) ;
     void onStartLineRecord(LineObj lineobj) ;
     void onStopLineRecord(LineObj lineobj) ;
     void onStartConfRecord(String id) ;
     void onStopConfRecord(Conf conf) ;
}
