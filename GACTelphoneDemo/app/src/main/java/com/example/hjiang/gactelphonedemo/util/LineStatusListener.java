package com.example.hjiang.gactelphonedemo.util;

import com.base.module.call.Conf;
import com.base.module.call.StatusChangeListener;
import com.base.module.call.line.LineObj;
import com.example.hjiang.gactelphonedemo.IStatusChangeListener;

import java.util.List;

/**
 * Created by hjiang on 16-1-8.
 */
public class LineStatusListener extends StatusChangeListener {
    private IStatusChangeListener iStatusChangeListener;
    public LineStatusListener(IStatusChangeListener iStatusChangeListener){
        this.iStatusChangeListener = iStatusChangeListener;
    }
    @Override
    public void onLineStateChanged(List<LineObj> lines, LineObj line) {
        iStatusChangeListener.onLineStateChanged(lines,line);
    }
    @Override
    public void onConfStateChanged(Conf conf) {
        iStatusChangeListener.onConfStateChanged(conf);
    }
    @Override
    public void onStartLineRecord(LineObj lineobj) {
        iStatusChangeListener.onStartLineRecord(lineobj);
    }
    @Override
    public void onStopLineRecord(LineObj lineobj) {
        iStatusChangeListener.onStopLineRecord(lineobj);
    }
    @Override
    public void onStartConfRecord(String id) {
        iStatusChangeListener.onStartConfRecord(id);
    }
    @Override
    public void onStopConfRecord(Conf conf) {
        iStatusChangeListener.onStopConfRecord(conf);
    }
}
