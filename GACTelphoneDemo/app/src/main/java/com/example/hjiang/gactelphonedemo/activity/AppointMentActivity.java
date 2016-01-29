package com.example.hjiang.gactelphonedemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;
import com.example.hjiang.gactelphonedemo.util.OtherUtils;



/**
 * 预约会议
 * Created by hjiang on 16-1-27.
 */
public class AppointMentActivity extends BaseActivity implements View.OnClickListener{
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        timePicker = (TimePicker) findViewById(R.id.tpPicker);
        timePicker.setIs24HourView(true);
        initView();
    }


    private void initView(){
        findViewById(R.id.test).setOnClickListener(this);
    }

    private void insert(){
        MeetingBean meetingBean = new MeetingBean();
        meetingBean.setName("E_vast");
        meetingBean.setAutoAnswer(1);
        meetingBean.setAutoCall(1);
        meetingBean.setAutoRecord(1);
        meetingBean.setCycleTime(6);
        meetingBean.setDuration(6000);
        meetingBean.setEnterMute(0);
        meetingBean.setHaspwd(1);
        meetingBean.setPswStr("123");
        meetingBean.setReminderTime(6000000);
        meetingBean.setStartTime(getTimeCurrent());
        meetingBean.setState(0);
        meetingBean.setTimeZone("Asia/Shanghai");
    }

//    /**
//     * 获取时区
//     */
//    private String void getTimeZone(){
//        TimeZone
//    }

    /**
     * 获取时间
     * @return
     */
    private long getTimeCurrent(){
        int hour  = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        long currentTime = OtherUtils.getNowDayCurrent(hour,minute);
        return currentTime;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test:{
                insert();
                break;
            }
        }
    }
}
