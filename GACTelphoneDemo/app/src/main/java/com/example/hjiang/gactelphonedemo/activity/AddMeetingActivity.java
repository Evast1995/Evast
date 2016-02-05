package com.example.hjiang.gactelphonedemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;
import com.example.hjiang.gactelphonedemo.util.ContactsUtil;
import com.example.hjiang.gactelphonedemo.util.Contants;
import com.example.hjiang.gactelphonedemo.util.OtherUtils;
import com.example.hjiang.gactelphonedemo.weight.DelEdit;

import java.util.List;

/**
 * Created by hjiang on 16-2-2.
 */
public class AddMeetingActivity extends BaseActivity implements View.OnClickListener{
    /** 添加会议名称　字符串长度的文本*/
    private TextView addMeetingTotalTv;
    /** 添加会议名称的编辑框*/
    private EditText addMeetingNameEt;
    /** 打开选择联系人页面的请求码*/
    private static final int REQUESTCODE = 111;

    /** 添加会议成员的编辑框*/
    private DelEdit addMeetingMemberEt;
    /** 添加会议成员的点击图片*/
    private ImageView addMeetingMemberIv;

    /** 是否循环会议的checkbox*/
    private CheckBox isLoopCb;
    /** 不循环会议的时候显示预约会议的日期*/
    private LinearLayout dataLayout;
    /** 循环会议的时候显示循环周期列表*/
    private LinearLayout loopLayout;
    /** 不循环会议时候　选择会议日期的按钮*/
    private Button meetingDataBtn;
    /** 循环会议列表中星期一的checkbox*/
    private CheckBox mondayCb;
    /** 循环会议列表中星期二的checkbox*/
    private CheckBox tuesdayCb;
    /** 循环会议列表中星期三的checkbox*/
    private CheckBox wednesdayCb;
    /** 循环会议列表中星期四的checkbox*/
    private CheckBox thursdayCb;
    /** 循环会议列表中星期五的checkbox*/
    private CheckBox fridayCb;
    /** 循环会议列表中星期六的checkbox*/
    private CheckBox saturdayCb;
    /** 循环会议列表中星期日的checkbox*/
    private CheckBox sundayCb;

    /** 选择时区的按钮*/
    private Button timeZoneBtn;
    /** 选择开始时间*/
    private Button startTimeBtn;
    /** 时长编辑框*/
    private EditText longTimeEt;
    /** 提前几分钟按钮*/
    private Button advenceBtn;

    /** 自动会议开关*/
    private Switch autoMeetingSc;
    /** 自动接听成员来电*/
    private Switch autoAcceptSc;
    /** 自动录音*/
    private Switch autoRecordSc;
    /** 入会时禁声*/
    private Switch joinNoSoundSc;
    /** 加锁*/
    private Switch addLockSc;
    /** 密码选择*/
    private CheckBox passwordCb;
    /** 添加密码编辑框*/
    private EditText passwordEt;
    /** 循环会议 周一到周日 是否选中 0：周日 1：周一 2：周二*/
    private Boolean[] loopWeek = new Boolean[]{false,false,false,false,false,false,false};
    /** 提前多少分钟提醒 字符串组*/
    private String[] advencesArray = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapointment);
        init();
    }

    private void init(){
        addMeetingNameEt = (EditText) findViewById(R.id.addmeeting_name_edit);
        addMeetingTotalTv = (TextView) findViewById(R.id.total_text);
        addMeetingMemberIv = (ImageView) findViewById(R.id.addmeeting_contacts);
        addMeetingMemberEt = (DelEdit) findViewById(R.id.addmeeting_member);
        isLoopCb = (CheckBox) findViewById(R.id.addmeeting_loop_checkbox);
        dataLayout = (LinearLayout) findViewById(R.id.data_layout);
        loopLayout = (LinearLayout) findViewById(R.id.loop_layout);
        meetingDataBtn = (Button) findViewById(R.id.meetingdata_btn);
        mondayCb = (CheckBox) findViewById(R.id.addmeeting_monday_checkbox);
        tuesdayCb = (CheckBox) findViewById(R.id.addmeeting_tuesday_checkbox);
        wednesdayCb = (CheckBox) findViewById(R.id.addmeeting_wednesday_checkbox);
        thursdayCb = (CheckBox) findViewById(R.id.addmeeting_thursday_checkbox);
        fridayCb = (CheckBox) findViewById(R.id.addmeeting_friday_checkbox);
        saturdayCb = (CheckBox) findViewById(R.id.addmeeting_saturday_checkbox);
        sundayCb = (CheckBox) findViewById(R.id.addmeeting_sunday_checkbox);
        meetingDataBtn = (Button) findViewById(R.id.meetingdata_btn);
        timeZoneBtn = (Button) findViewById(R.id.addmeeing_timezone);
        startTimeBtn = (Button) findViewById(R.id.addmeeing_starttime);
        longTimeEt = (EditText) findViewById(R.id.addmeeing_timelong);
        advenceBtn = (Button) findViewById(R.id.addmeeing_remind);
        autoMeetingSc = (Switch) findViewById(R.id.addmeeting_automeeting);
        autoAcceptSc = (Switch) findViewById(R.id.addmeeting_autoaccept);
        joinNoSoundSc = (Switch) findViewById(R.id.addmeeting_nosound);
        addLockSc = (Switch) findViewById(R.id.addmeeting_addpsw);
        passwordCb = (CheckBox) findViewById(R.id.addmeeting_password_checkbox);
        passwordEt = (EditText) findViewById(R.id.addmeeing_password);
        autoRecordSc = (Switch) findViewById(R.id.addmeeting_autorecord);
        advenceBtn.setOnClickListener(this);
        timeZoneBtn.setOnClickListener(this);
        startTimeBtn.setOnClickListener(this);
        addMeetingMemberIv.setOnClickListener(this);
        meetingDataBtn.setOnClickListener(this);
        findViewById(R.id.back_icon).setOnClickListener(this);
        findViewById(R.id.sure_icon).setOnClickListener(this);

        addMeetingMemberEt.setEditBackgound(R.color.white);
        passwordCb.setClickable(false);
        joinNoSoundSc.setClickable(false);

        /** 设置会议名称变化的监听事件*/
        setMeetingNameListener();
        /** 设置选择是否循环会议的显隐事件*/
        setLoopShowOrHide();
        /** 初始化提前多少分钟提醒数组*/
        initAdvenceArray();
        /** 密码按钮的改编事件*/
        setPasswordCbChangeListener();
        /** 设置加锁开关的变化监听器*/
        setAddPswScListener();
        /** 设置自动接听来电开关变化监听器*/
        setAcceptScListener();

        meetingDataBtn.setText(OtherUtils.getCurrentDate());
        startTimeBtn.setText(OtherUtils.getCurrentTime());
        longTimeEt.setText(R.string.default_minutes);
        advenceBtn.setText(advencesArray[1]);
        timeZoneBtn.setText(OtherUtils.getCurrentTimeZone());
    }

    /**
     * 自动接听成员来电开关的选择事件
     */
    private  void setAcceptScListener(){
        autoAcceptSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                joinNoSoundSc.setClickable(isChecked);
                if(!isChecked){
                    joinNoSoundSc.setTextColor(getResources().getColor(R.color.gray));
                }else{
                    joinNoSoundSc.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
    }

    /**
     * 添加加锁开关的变化监听事件
     */
    private void setAddPswScListener(){
        addLockSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordCb.setTextColor(getResources().getColor(R.color.black));
                }else{
                    passwordCb.setTextColor(getResources().getColor(R.color.gray));
                    passwordCb.setChecked(false);
                }
                passwordCb.setClickable(isChecked);
            }
        });
    }

    /**
     * 设置密码选择按钮的改变事件
     */
    private void setPasswordCbChangeListener(){
        passwordCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordEt.setVisibility(View.VISIBLE);
                }else{
                    passwordEt.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 初始化提前多收分数组
     */
    private void initAdvenceArray(){
        Resources resources = getResources();
        for(int i=0;i<advencesArray.length;i++){
            if(i==0){
                advencesArray[i] = resources.getString(R.string.nothing);
            }else{
                advencesArray[i] = String.format(resources.getString(R.string.advence),i*10);
            }
        }
    }

    /**
     * 打开选择会议提前提醒视图
     */
    private void openChangeAdvenceView(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.meetingremind);
        builder.setItems(advencesArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                advenceBtn.setText(advencesArray[which]);
            }
        });
        builder.show();
    }

    /**
     * 循环会议的显隐事件
     */
    private void setLoopShowOrHide(){
        loopLayout.setVisibility(View.GONE);
        dataLayout.setVisibility(View.VISIBLE);
        isLoopCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    loopLayout.setVisibility(View.VISIBLE);
                    dataLayout.setVisibility(View.GONE);
                } else {
                    loopLayout.setVisibility(View.GONE);
                    dataLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 设置会议名称监听事件
     */
    private void setMeetingNameListener(){
        final String totalStr = getResources().getString(R.string.people_total);
        addMeetingTotalTv.setText(String.format(totalStr, 0));
        addMeetingNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 64) {
                    addMeetingTotalTv.setText(String.format(totalStr, s.length()));
                } else if (s.length() > 64) {
                    Toast.makeText(AddMeetingActivity.this, R.string.charmore, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 打开选择联系人界面
     */
    private void openChangeContactsActivity(){
        Intent intent = new Intent(this,ContactsActivity.class);
        startActivityForResult(intent, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            List<String> phoneNumList = bundle.getStringArrayList(Contants.PHONE_LIST_KEY);
            for(int i=0;i<phoneNumList.size();i++) {
                addMeetingMemberEt.setAddTextView(phoneNumList.get(i));
            }
        }
    }

    /**
     * 打开开始时间选择页面
     */
    private void openStartTimeChangeView(){
        String timeStr = startTimeBtn.getText().toString();
        int hours = Integer.valueOf(timeStr.substring(0, 2));
        int minutes = Integer.valueOf(timeStr.substring(3,5));
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeBtn.setText(String.format("%02d",hourOfDay)+":"+String.format("%02d",minute));
            }
        },hours,minutes,true);
        dialog.show();
    }

    /**
     * 打开时区列表界面
     */
    private void openTimeZoneChange(){
//        String timezoneIds[]= SimpleTimeZone.getAvailableIDs();
//        Date date=new Date();
//        SimpleDateFormat format=new SimpleDateFormat("z yyyy-MM-dd HH:mm:ss");
//        for(int i=0;i<timezoneIds.length;i++){
//            TimeZone tz= SimpleTimeZone.getTimeZone(timezoneIds[i]);
//            format.setTimeZone(tz);
//            System.out.println(tz.getDisplayName() +" ,"+ format.format(date));
//        }
    }

    /**
     * 打开选择日期的视图
     */
    private void openChangeDataView(){
        String timeStr = meetingDataBtn.getText().toString();
        int year = Integer.parseInt(timeStr.substring(0, 4));
        int month = Integer.parseInt(timeStr.substring(5,7));
        int day= Integer.parseInt(timeStr.substring(8,10));

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                meetingDataBtn.setText(OtherUtils.spellDate(year,monthOfYear,dayOfMonth));
            }
        },year,month-1,day);
        datePickerDialog.show();
    }

    /**
     * 检查输入的数据格式是否正确
     * @return
     */
    private Boolean checkUpInformation(){
        Boolean isRight = true;
        if(TextUtils.isEmpty(addMeetingNameEt.getText())){
            Toast.makeText(this,R.string.no_meetingname,Toast.LENGTH_LONG).show();
            isRight = false;
        }
        if(addMeetingMemberEt.getImagesText().size() == 0){
            Toast.makeText(this,R.string.no_meetingmember,Toast.LENGTH_LONG).show();
            isRight = false;
        }
        if(!OtherUtils.isValidLong(longTimeEt.getText().toString())){
            Toast.makeText(this,R.string.longtime_error,Toast.LENGTH_LONG).show();
            isRight = false;
        }
        return isRight;
    }

    /**
     * 确定按钮点击事件
     */
    private void sureBtnClick(){
        /** 检查数据格式是否正确*/
        if(!checkUpInformation()){
            return;
        }
        /** 会议名称*/
        String meetingNameStr = addMeetingNameEt.getText().toString();
        /** 会议成员*/
        List<String> memberList = addMeetingMemberEt.getImagesText();

        /** 会议下一次开始时间戳*/
        long startTime;
        /** 会议循环周期*/
        int cycleNum = 0;
        /** 时长 **/
        long longTime = Long.parseLong(longTimeEt.getText().toString())*60*1000;
        if(isLoopCb.isChecked()){
            cycleNum = calculationCycleNum();
            startTime = getLoopStartTime();
        }else{
            startTime = getWindowTime();
        }
        /** 获取时区*/
        String timeZone = "Asia/Shanghai";
        /** 提前了多少分钟 单位ms*/
        long remindTime = getAdvenceTime();
        /** 是否是自动会议*/
        int isAutoMeeting = 0;
        if(autoMeetingSc.isChecked()){
            isAutoMeeting = 1;
        }
        /** 是否自动接听成员来电*/
        int isAccept = 0;
        if(autoAcceptSc.isChecked()){
            isAccept = 1;
        }
        /** 是否入会时禁声*/
        int isNoSound = 0;
        if(joinNoSoundSc.isChecked()){
            isNoSound = 1;
        }
        /** 是否加锁 */
        int intercept = 0;
        if(addLockSc.isChecked()){
            intercept = 1;
        }
        /** 是否有密码*/
        int hasPsw = 0;
        if(passwordCb.isChecked()){
            hasPsw = 1;
        }
        /** 会议密码*/
        String pswStr = passwordEt.getText().toString();
        /** 是否自动录音*/
        int isRecord = 0;
        if(autoRecordSc.isChecked()){
            isRecord = 1;
        }
        MeetingBean meetingBean = new MeetingBean();
        meetingBean.setName(meetingNameStr);
        meetingBean.setTimeZone(timeZone);
        meetingBean.setState(0);
        meetingBean.setStartTime(startTime);
        meetingBean.setReminderTime(remindTime);
        meetingBean.setAutoAnswer(isAccept);
        meetingBean.setAutoCall(isAutoMeeting);
        meetingBean.setAutoRecord(isRecord);
        meetingBean.setCycleTime(cycleNum);
        meetingBean.setDuration(longTime);
        meetingBean.setHaspwd(hasPsw);
        meetingBean.setIntercept(intercept);
        meetingBean.setPswStr(pswStr);
        meetingBean.setEnterMute(isNoSound);
        ContactsUtil.getInstance(this).insertMeetings(meetingBean, memberList);
        finish();
    }

    /**
     * 获取提前多少分钟提醒 单位ms
     * @return
     */
    private long getAdvenceTime(){
        long advencetTimeNum;
        String advenceTimeStr = advenceBtn.getText().toString();
        if(advenceTimeStr.length() == 1){
            advencetTimeNum = -1;
        }else {
            advencetTimeNum = Integer.parseInt(advenceTimeStr.substring(2,4))*60*1000;
        }
        return advencetTimeNum;
    }

    /**
     * 获取循环周期的开始时间
     */
    private long getLoopStartTime(){
        long startime = 0;
        long currentTime = System.currentTimeMillis();
        for(int i=0;i<12;i++) {
            if(i<7&&loopWeek[i]&&isBackToCurrentWeek(i+1,false)>currentTime){
                startime = isBackToCurrentWeek(i+1,false);
                break;
            }else if(i>=7&&loopWeek[i-7]&&isBackToCurrentWeek(i+1,true)>currentTime){
                startime = isBackToCurrentWeek(i+1,true);
                break;
            }
        }
        return startime;
    }

    /**
     * 获取周几中某段时间的时间戳
     * @param week
     * @return
     */
    private long isBackToCurrentWeek(int week,Boolean isNextWeek){
        String timeStr = startTimeBtn.getText().toString();
        int hours = Integer.valueOf(timeStr.substring(0, 2));
        int minutes = Integer.valueOf(timeStr.substring(3, 5));
        long timeStamp = OtherUtils.getCurrentWeekTime(week,hours,minutes,isNextWeek);
        return timeStamp;
    }

    /**
     * 获取窗口时间(即非循环周期的时间)
     */
    private long getWindowTime(){
        String timeStr = startTimeBtn.getText().toString();
        int hours = Integer.valueOf(timeStr.substring(0, 2));
        int minutes = Integer.valueOf(timeStr.substring(3, 5));
        String dateStr = meetingDataBtn.getText().toString();
        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(5, 7));
        int day= Integer.parseInt(dateStr.substring(8, 10));


        return OtherUtils.getTime(year,month,day,hours,minutes);
    }

    /**
     * 计算循环周期短额数值
     */
    private int calculationCycleNum(){
        int num = 0;
        if(mondayCb.isChecked()){
            num+=1;
            loopWeek[1] = true;
        }
        if(tuesdayCb.isChecked()){
            num+=2;
            loopWeek[2] = true;
        }
        if(wednesdayCb.isChecked()){
            num+=4;
            loopWeek[3] = true;
        }
        if(thursdayCb.isChecked()){
            num+=8;
            loopWeek[4] = true;
        }
        if(fridayCb.isChecked()){
            num+=16;
            loopWeek[5] = true;
        }
        if(saturdayCb.isChecked()){
            num+=32;
            loopWeek[6] = true;
        }
        if(sundayCb.isChecked()){
            num+=64;
            loopWeek[0] = true;
        }
        return num;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /** 点击标题中的退出图标*/
            case R.id.back_icon:{
                finish();
                break;
            }
            /** 点击标题中的确认图标*/
            case R.id.sure_icon:{
                sureBtnClick();
                break;
            }
            /** 点击添加会议成员的图标*/
            case R.id.addmeeting_contacts:{
                openChangeContactsActivity();
                break;
            }
            /**　点击会议日期按钮*/
            case R.id.meetingdata_btn:{
                openChangeDataView();
                break;
            }
            /** 点击选择时区*/
            case R.id.addmeeing_timezone:{
                openTimeZoneChange();
                break;
            }
            /** 点击选择开始时间*/
            case R.id.addmeeing_starttime:{
                openStartTimeChangeView();
                break;
            }
            /** 提前多少分钟提醒按钮点击*/
            case R.id.addmeeing_remind:{
                openChangeAdvenceView();
                break;
            }
        }
    }
}
