package com.example.hjiang.gactelphonedemo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ListView;
import android.widget.TimePicker;

import com.base.module.call.CallManager;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;
import com.example.hjiang.gactelphonedemo.bean.MemberBean;
import com.example.hjiang.gactelphonedemo.util.ContactsUtil;
import com.example.hjiang.gactelphonedemo.util.OtherUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 预约会议
 * Created by hjiang on 16-1-27.
 */
public class AppointMentActivity extends BaseActivity implements View.OnClickListener{
    private TimePicker timePicker;
    private ListView timeZoneLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /** 将actionBar隐藏*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_addapointment);
//        setContentView(R.layout.activity_appointment);
//        timePicker = (TimePicker) findViewById(R.id.tpPicker);
//        timeZoneLv = (ListView) findViewById(R.id.time_zone);
//        timePicker.setIs24HourView(true);
//        initView();
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
        String meetingId = ContactsUtil.getInstance(this).insertMeetingsTable(meetingBean);

        List<MemberBean> list = new ArrayList<MemberBean>();
        MemberBean memberBean = new MemberBean();
        memberBean.setAccount(0);
        memberBean.setCallMode(CallManager.CALLMODE_SIP);
        memberBean.setMeetingId(meetingId);
        memberBean.setOrigin(0);
        memberBean.setOriginNum("13015");
        memberBean.setPhone("13015");
        list.add(memberBean);
        ContactsUtil.getInstance(this).insertMembersTable(list);
    }


    /**
     * 获取时区
     */
//    private String getTimeZone(){
//
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
