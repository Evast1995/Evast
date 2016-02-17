package com.example.hjiang.gactelphonedemo.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;
import com.example.hjiang.gactelphonedemo.bean.MemberBean;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
import com.example.hjiang.gactelphonedemo.util.ContactsUtil;
import com.example.hjiang.gactelphonedemo.util.Contants;
import com.example.hjiang.gactelphonedemo.util.OtherUtils;

import java.util.List;

/**
 * Created by hjiang on 16-2-5.
 */
public class ScheduleDetailActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout memberOneLayout;
    private RelativeLayout memberTwoLayout;
    private RelativeLayout memberThreeLayout;
    private RelativeLayout memberFourLayout;
    private RelativeLayout memberFiveLayout;
    private RelativeLayout memberSixLayout;

    private TextView memberOneNameTv;
    private TextView memberTwoNameTv;
    private TextView memberThreeNameTv;
    private TextView memberFourNameTv;
    private TextView memberFiveNameTv;
    private TextView memberSixNameTv;

    private TextView memberOneNumberTv;
    private TextView memberTwoNumberTv;
    private TextView memberThreeNumberTv;
    private TextView memberFourNumberTv;
    private TextView memberFiveNumberTv;
    private TextView memberSixNumberTv;

    private RelativeLayout pswLayout;
    private RelativeLayout cycleLayout;
    private TextView meetingCycleTv;
    private TextView meetingTimeTv;
    private TextView meetingLongTv;
    private TextView meetingRemindTv;
    private TextView meetingAutoCallTv;
    private TextView meetingAutoAnswerTv;
    private TextView meetingPswTv;
    private TextView meetingInterceptTv;
    private TextView meetingAutoRecordTv;
    private TextView meetingNoSoundTv;
    
    private TextView meetingNameTv;
    private MeetingBean meetingBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduledetail);
        init();
        initData();
    }
    private void init(){
        findViewById(R.id.action_call_icon).setOnClickListener(this);
        findViewById(R.id.action_edit_icon).setOnClickListener(this);
        findViewById(R.id.action_del_icon).setOnClickListener(this);
        memberOneLayout = (RelativeLayout) findViewById(R.id.member_one_layout);
        memberTwoLayout = (RelativeLayout) findViewById(R.id.member_two_layout);
        memberThreeLayout = (RelativeLayout) findViewById(R.id.member_three_layout);
        memberFourLayout = (RelativeLayout) findViewById(R.id.member_four_layout);
        memberFiveLayout = (RelativeLayout) findViewById(R.id.member_five_layout);
        memberSixLayout = (RelativeLayout) findViewById(R.id.member_six_layout);

        memberOneNameTv = (TextView) findViewById(R.id.member_one_name);
        memberTwoNameTv = (TextView) findViewById(R.id.member_two_name);
        memberThreeNameTv = (TextView) findViewById(R.id.member_three_name);
        memberFourNameTv = (TextView) findViewById(R.id.member_four_name);
        memberFiveNameTv = (TextView) findViewById(R.id.member_five_name);
        memberSixNameTv = (TextView) findViewById(R.id.member_six_name);

        memberOneNumberTv = (TextView) findViewById(R.id.member_one_phone);
        memberTwoNumberTv = (TextView) findViewById(R.id.member_two_phone);
        memberThreeNumberTv = (TextView) findViewById(R.id.member_three_phone);
        memberFourNumberTv = (TextView) findViewById(R.id.member_four_phone);
        memberFiveNumberTv = (TextView) findViewById(R.id.member_five_phone);
        memberSixNumberTv = (TextView) findViewById(R.id.member_six_phone);

        meetingCycleTv = (TextView) findViewById(R.id.cycle_meeting);
        meetingTimeTv = (TextView) findViewById(R.id.meeting_time);
        meetingLongTv = (TextView) findViewById(R.id.schedule_long);
        meetingNameTv = (TextView) findViewById(R.id.schedule_title_tv);
        meetingRemindTv = (TextView) findViewById(R.id.meeting_remind);
        meetingAutoCallTv = (TextView) findViewById(R.id.auto_meeting);
        meetingAutoAnswerTv = (TextView) findViewById(R.id.auto_accept);
        meetingPswTv = (TextView) findViewById(R.id.password);
        meetingInterceptTv = (TextView) findViewById(R.id.intercept);
        meetingAutoRecordTv = (TextView) findViewById(R.id.autorecord);
        meetingNoSoundTv = (TextView) findViewById(R.id.nosound);

        cycleLayout = (RelativeLayout) findViewById(R.id.cycle_layout);
        pswLayout = (RelativeLayout) findViewById(R.id.psw_layout);

        findViewById(R.id.back_icon).setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        Bundle bundle = getIntent().getExtras();
        String openStr = getResources().getString(R.string.open);
        String closeStr = getResources().getString(R.string.close);
        /** 设置会议详情中会议的成员*/
        meetingBean = (MeetingBean) bundle.getSerializable(Contants.MEETING_BEAN);
        List<MemberBean> memberBeanList = meetingBean.getMemberList();
        setMemberData(memberBeanList);

        /** 设置会议详情会议名称*/
        String meetingTitleStr = meetingBean.getName();
        meetingNameTv.setText(meetingTitleStr);

        /** 设置循环会议的会议周期*/
        int cycle = meetingBean.getCycleTime();
        if(cycle == 0){
            cycleLayout.setVisibility(View.GONE);
        }else {
            cycleLayout.setVisibility(View.VISIBLE);
            meetingCycleTv.setText(getCycleTime(cycle));
        }

        /** 设置会议开始时间*/
        String meetingTimeStr = OtherUtils.getTimeStrByStamp(meetingBean.getStartTime());
        meetingTimeTv.setText(meetingTimeStr);

        /** 设置会议时长*/
        String meeingLong =getString(R.string.minute);
        meeingLong = String.format(meeingLong,OtherUtils.getMinuteByMillion(meetingBean.getDuration()));
        meetingLongTv.setText(meeingLong);

        /** 设置会议提醒*/
        if(meetingBean.getReminderTime() == -1){
            meetingRemindTv.setText(closeStr);
        }else{
            String remindStr = getString(R.string.advence);
            remindStr = String.format(remindStr,OtherUtils.getMinuteByMillion(meetingBean.getReminderTime()));
            meetingRemindTv.setText(remindStr);
        }
        
        /** 设置自动会议*/
        if(meetingBean.getAutoCall()==1){
            meetingAutoCallTv.setText(openStr);
        }else{
            meetingAutoCallTv.setText(closeStr);
        }
        
        /** 设置自动接听成员来电*/
        if(meetingBean.getAutoAnswer() == 1){
            meetingAutoAnswerTv.setText(openStr);
        }else{
            meetingAutoAnswerTv.setText(closeStr);
        }

        /**
         * 设置密码加锁
         */
        if(meetingBean.getIntercept() == 1){
            meetingInterceptTv.setText(openStr);
            pswLayout.setVisibility(View.VISIBLE);
            meetingPswTv.setText(meetingBean.getPswStr());
        }else{
            meetingInterceptTv.setText(closeStr);
            pswLayout.setVisibility(View.GONE);
        }

        /**
         * 设置自动录音
         */
        if(meetingBean.getAutoRecord() == 1){
            meetingAutoRecordTv.setText(openStr);
        }else{
            meetingAutoRecordTv.setText(closeStr);
        }

        /**
         * 设置入会时禁声
         */
        if(meetingBean.getEnterMute() == 1){
            meetingNoSoundTv.setText(openStr);
        }else{
            meetingNoSoundTv.setText(closeStr);
        }
    }


    /**
     * 获取循环周期字符串
     * @param cycle
     * @return
     */
    private String getCycleTime(int cycle){
        StringBuilder cycleSb = new StringBuilder();
        Resources resources = getResources();
        if(cycle > 0) {
            if (cycle >= 64) {
                cycleSb = cycleSb.append(resources.getString(R.string.sunday) + ",");
                cycle -= 64;
            }
            if (cycle >= 32 && cycle < 64) {
                cycleSb = cycleSb.append(resources.getString(R.string.saturday) + ",");
                cycle -= 32;
            }
            if (cycle >= 16 && cycle < 32) {
                cycleSb = cycleSb.append(resources.getString(R.string.friday) + ",");
                cycle -= 16;
            }
            if (cycle >= 8 && cycle < 16) {
                cycleSb = cycleSb.append(resources.getString(R.string.thursday) + ",");
                cycle -= 8;
            }
            if (cycle >= 4 && cycle < 8) {
                cycleSb = cycleSb.append(resources.getString(R.string.wednesday) + ",");
                cycle -= 4;
            }
            if (cycle >= 2 && cycle < 4) {
                cycleSb = cycleSb.append(resources.getString(R.string.tuesday) + ",");
                cycle -= 2;
            }
            if (cycle >= 1 && cycle < 2) {
                cycleSb = cycleSb.append(resources.getString(R.string.monday) + ",");
                cycle -= 1;
            }
        }
        cycleSb = cycleSb.delete(cycleSb.length()-1,cycleSb.length());
        return cycleSb.toString();
    }

    /**
     * 设置会议成员的显示
     */
    private void setMemberShow(TextView nameTv,TextView phoneTv,RelativeLayout layout,MemberBean memberBean){
        layout.setVisibility(View.VISIBLE);
        String phoneStr = memberBean.getOriginNum();
        String nameStr = ContactsUtil.getInstance(this).getDisplayNameByPhone(phoneStr).get(0);
        nameTv.setText(nameStr);
        phoneTv.setText(phoneStr);
    }

    /**
     * 设置成员的数据
     */
    private void setMemberData(List<MemberBean> memberBeanList){
        for(int i=1;i<=memberBeanList.size();i++){
            MemberBean mem = memberBeanList.get(i-1);
            switch (i){
                case 1:{
                    setMemberShow(memberOneNameTv,memberOneNumberTv,memberOneLayout,mem);
                    break;
                }
                case 2:{
                    setMemberShow(memberTwoNameTv,memberTwoNumberTv,memberTwoLayout,mem);
                    break;
                }
                case 3:{
                    setMemberShow(memberThreeNameTv,memberThreeNumberTv,memberThreeLayout,mem);
                    break;
                }
                case 4:{
                    setMemberShow(memberFourNameTv,memberFourNumberTv,memberFourLayout,mem);
                    break;
                }
                case 5:{
                    setMemberShow(memberFiveNameTv,memberFiveNumberTv,memberFiveLayout,mem);
                    break;
                }
                case 6:{
                    setMemberShow(memberSixNameTv,memberSixNumberTv,memberSixLayout,mem);
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_icon:{
                finish();
                break;
            }
            case R.id.action_call_icon:{
                makeSchedule();
                break;
            }
            case R.id.action_del_icon:{
                ContactsUtil.getInstance(this).delScheduleById(meetingBean.getMeetingId());
                finish();
                break;
            }
            case R.id.action_edit_icon:{
                makeEdit();
                finish();
                break;
            }
        }
    }


    /**
     * 创建编辑修改
     */
    private void makeEdit(){
        Intent intent = new Intent(this,AddMeetingActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable(Contants.MEETING_BEAN,meetingBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 创建会议通话
     */
    private void makeSchedule(){
        CallUtils.getInstance(this).setScheduleInf(meetingBean);

    }
}
