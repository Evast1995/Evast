package com.example.hjiang.gactelphonedemo.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.base.module.call.CallManager;
import com.base.module.call.ICallStatusListener;
import com.example.hjiang.gactelphonedemo.MyApplication;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;

/**
 * 与通话相关
 * Created by hjiang on 16-1-8.
 */
public class CallUtils {
    private static Context context;
    private static CallManager callManager;
    private CallUtils(Context context){
        callManager = (CallManager)context.getSystemService(Context.CALL_MANAGER_SERVICE);
        this.context = context;
    };
    private static CallUtils callUtils;
    public synchronized static CallUtils getInstance(Context context){
        if(callUtils ==null){
            callUtils = new CallUtils(context);
        }
        return callUtils;
    }

    /**
     * 拨出电话
     * @param accountLocalId 本地的账号ID
     * @param originNumber 本地输入的号码
     * @param dialOutNumber 通过拨号规则拨出的号码
     * @param callMode 拨号类型
     */
    public void makeCall(final int accountLocalId, final String originNumber, final String dialOutNumber, final int callMode){
        callManager.call(accountLocalId, originNumber, dialOutNumber, false, callMode);
    }

    /**
     * 添加线路状态监听事件
     * @param pkgForDebug 包名
     * @param callback 通过StatusChangeListener获取ICallStatusListener变量
     * @param events
     * @param notifyNow
     */
    public void addStatusChangeListener(String pkgForDebug, ICallStatusListener callback, int events, boolean notifyNow){
        callManager.addStatusChangeListener(pkgForDebug, callback, events, notifyNow);
    }

    /**
     * 接听电话
     * @param lineId 本地线路ID
     * @param audioMode 声道模式 蓝牙还是正常说话
     * @param isAddToConf 是否添加到会议，会议会参与混音
     */
    public void accept(final int lineId, final int audioMode, final boolean isAddToConf){
        callManager.accept(lineId, true, audioMode, isAddToConf);
    }

    /**
     * 拒接来电
     * @param lineId 本地线路ID
     */
    public void reject(int lineId){
        callManager.reject(lineId);
    }

    /**
     * 结束通话
     * @param lineId
     */
    public void endCall(int lineId){
        callManager.endCall(lineId);
    }

    /**
     * 开始录音
     * @param lineId
     */
    public void startRecord(int lineId){
        callManager.startRecord(lineId);
    }

    /**
     * 停止当前录音
     */
    public void stopRecord(){
        callManager.stopRecord();
    }

    /**
     * 开始暂停通话
     * @param lineId
     * @param isInMeeting 这条线路是否在会议室
     */
    public void startPauseCall(int lineId,Boolean isInMeeting){
        callManager.hold(lineId, isInMeeting);
    }

    /**
     * 停止暂停通话
     * @param lineId
     * @param isInMeeting
     */
    public void stopPauseCall(int lineId,Boolean isInMeeting){
        callManager.unhold(lineId, isInMeeting);
    }

    /**
     * 盲转移通话
     * @param lineId
     * @param number
     */
    public void transferCall(int lineId, String number){
        callManager.transferBlind(lineId, number);
    }

    /**
     * 将通话线路添加到会议
     * @param lineId
     */
    public void confAddLine(int lineId){
        callManager.confAddLine(lineId);
    }

    /**
     * 所有线路均加入会议室，如果有通话线路自动拉入会议室
     */
    public void autoConf(){
        Log.e("--main--","autoConf");
        callManager.autoConf();
    }

    /**
     * 开始会议并且建立一组通话
     * @param accountLocalId
     * @param originNumber
     * @param dialOutNumber
     * @param callMode
     */
    public void confCall(int accountLocalId,String originNumber,String dialOutNumber,int callMode){
        callManager.confCall(accountLocalId, originNumber, dialOutNumber, false, callMode, -1, -1, -1);
    }

    /**
     * 结束会议
     */
    public void endConf(){
        callManager.endConf();
    }

    /**
     * 开始会议录音
     */
    public void startConferenceRecord(){
        Toast.makeText(context,context.getString(R.string.start_record), Toast.LENGTH_SHORT).show();
        callManager.startConferenceRecord();
    }

    /**
     * 停止会议录音
     */
    public void stopConferenceRecord(){
        Toast.makeText(context,context.getString(R.string.stop_record), Toast.LENGTH_SHORT).show();
        callManager.stopConferenceRecord();
    }

    /**
     * 会议是否在录音
     * @return
     */
    public Boolean isConferenceInRecord(){
        return callManager.isConferenceInRecord();
    }

    /**
     * 设置会议是否全部暂停
     * @param isHold
     */
    public void setIsConfOnHold(Boolean isHold){
        callManager.setIsConfOnHold(isHold);
    }

    /**
     * 设置会议是否全部静音
     * @param ismIsMutedAll
     */
    public void setIsMutedAll(Boolean ismIsMutedAll){
        callManager.setIsMutedAll(ismIsMutedAll);
    }

    /**
     * 设置线路静音(远程的静音 远程账号不能听到说话)
     * @param lineId
     * @param isMuted
     * @return
     */
    public  boolean muteUnmuteLocal(int lineId, boolean isMuted){
         return callManager.muteUnmuteLocal(lineId,isMuted);
    }

    /**
     * 设置预约会议信息
     * @param meetingBean
     */
    public void setScheduleInf(MeetingBean meetingBean){
        boolean hasPwd = false;
        boolean isEnterMeeting = false;
        boolean isIntercept = false;
        boolean isAutoAnswer= false;
        boolean isAutoRecord = false;
        if(meetingBean.getHaspwd() == 1){
            hasPwd = true;
        }
        if(meetingBean.getEnterMute() == 1){
            isEnterMeeting = true;
        }
        if(meetingBean.getIntercept() == 1){
            isIntercept = true;
        }
        if(meetingBean.getAutoAnswer() == 1){
            isAutoAnswer = true;
        }
        if(meetingBean.getAutoRecord() ==1){
            isAutoRecord =true;
        }
        callManager.setScheduleInfo(hasPwd, meetingBean.getPswStr(), isEnterMeeting, meetingBean.getName(),
                isIntercept, true, meetingBean.getMeetingId(), isAutoAnswer, isAutoRecord);
        for(int i=0;i<meetingBean.getMemberList().size();i++){
            String phoneStr = meetingBean.getMemberList().get(i).getPhone();
            confCall(MyApplication.localId,phoneStr,phoneStr,MyApplication.callModel);
        }
        if(isAutoRecord == true){
            startConferenceRecord();
        }
    }

    /**
     * 设置会议是否上锁
     * @param isLock
     */
    public void setConfLock(Boolean isLock){
        callManager.setConfLocked(isLock);
    }

}

