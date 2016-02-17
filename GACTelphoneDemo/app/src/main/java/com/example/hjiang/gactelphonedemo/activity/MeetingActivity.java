package com.example.hjiang.gactelphonedemo.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.module.call.line.CallErrorCode;
import com.base.module.call.line.LineObj;
import com.base.module.call.line.LineObjManager;
import com.example.hjiang.gactelphonedemo.MyApplication;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.service.StatusListenService;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
import com.example.hjiang.gactelphonedemo.util.ContactsUtil;
import com.example.hjiang.gactelphonedemo.util.Contants;
import com.example.hjiang.gactelphonedemo.util.ImageUtils;
import com.example.hjiang.gactelphonedemo.util.LineUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hjiang on 16-1-11.
 */
public class MeetingActivity extends BaseActivity implements View.OnClickListener{
    private ImageView oneImage;
    private ImageView twoImage;
    private ImageView threeImage;
    private ImageView fourImage;
    private ImageView fiveImage;
    private ImageView sixImage;
    private ImageView sevenImage;

    private TextView twoTv;
    private TextView threeTv;
    private TextView fourTv;
    private TextView fiveTv;
    private TextView sixTv;
    private TextView sevenTv;

    private TextView twoNameTv;
    private TextView threeNameTv;
    private TextView fourNameTv;
    private TextView fiveNameTv;
    private TextView sixNameTv;
    private TextView sevenNameTv;

    private TextView peopleNameTv;
    private TextView peoplePhoneTv;

    private ImageButton pauseBtn;
    private ImageButton lockBtn;
    private Button moreBtn;
    private LinearLayout tipLayout;
    private ImageView recallIv;
    private ImageView holdIv;
    private ImageView muteIv;

    private ImageView twoBlockIv;
    private ImageView threeBlockIv;
    private ImageView fourBlockIv;
    private ImageView fiveBlockIv;
    private ImageView sixBlockIv;
    private ImageView sevenBlockIv;


    private int REQUESTCODE = 201;
    private NotificationManager notificationManager;

    /** 设置初始化时六个位置的暂停状态*/
    private Boolean[] pauseStates = new Boolean[]{false,false,false,false,false,false};
    /** 设置初始化时六个位置的静音状态*/
    private Boolean[] muteStates = new Boolean[]{false,false,false,false,false,false};
    /** 判断当前位置上图片的状态　防止图片多次重复加载*/
    private Boolean[] bitmapStates = new Boolean[]{false,false,false,false,false,false};
    /** 设置位置对应的线路id key：位置 value：ID*/
    private Map<Integer,Integer> map = new HashMap<Integer, Integer>();

    /** 选着了第几个位置*/
    private int position = 2;

    public static final int NO_PEOPLE = -1;

    /** 是否上锁*/
    private Boolean isLock;
    /** 是否全部暂停*/
    private Boolean isOnHold;
    /** 是否全部静音*/
    private Boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        initMap();
        initView();
        initLineObjes();
        setBroadCastRevice();
        setConfChangeReceiver();
    }


    @Override
    protected void onDestroy() {
        if(receiver!=null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        if(confChangeReceiver!=null){
            unregisterReceiver(confChangeReceiver);
            confChangeReceiver = null;
        }
        super.onDestroy();
    }

    private void initView(){
        oneImage = (ImageView) findViewById(R.id.clockwise_one);
        twoImage = (ImageView) findViewById(R.id.clockwise_two);
        threeImage = (ImageView) findViewById(R.id.clockwise_three);
        fourImage = (ImageView) findViewById(R.id.clockwise_four);
        fiveImage = (ImageView) findViewById(R.id.clockwise_five);
        sixImage = (ImageView) findViewById(R.id.clockwise_six);
        sevenImage = (ImageView) findViewById(R.id.clockwise_seven);
        pauseBtn = (ImageButton) findViewById(R.id.pause_btn);
        lockBtn = (ImageButton) findViewById(R.id.lock_btn);

        twoTv = (TextView) findViewById(R.id.clockwise_two_text);
        threeTv = (TextView) findViewById(R.id.clockwise_three_text);
        fourTv = (TextView) findViewById(R.id.clockwise_four_text);
        fiveTv = (TextView) findViewById(R.id.clockwise_five_text);
        sixTv = (TextView) findViewById(R.id.clockwise_six_text);
        sevenTv = (TextView) findViewById(R.id.clockwise_seven_text);

        twoNameTv= (TextView) findViewById(R.id.clockwise_two_text_name);
        threeNameTv= (TextView) findViewById(R.id.clockwise_three_text_name);
        fourNameTv= (TextView) findViewById(R.id.clockwise_four_text_name);
        fiveNameTv= (TextView) findViewById(R.id.clockwise_five_text_name);
        sixNameTv= (TextView) findViewById(R.id.clockwise_six_text_name);
        sevenNameTv= (TextView) findViewById(R.id.clockwise_seven_text_name);
        peopleNameTv = (TextView) findViewById(R.id.people_name);
        peoplePhoneTv = (TextView) findViewById(R.id.people_phone);
        muteIv = (ImageView) findViewById(R.id.people_mute);
        holdIv = (ImageView) findViewById(R.id.people_pause);

        twoBlockIv = (ImageView) findViewById(R.id.clockwise_two_block);
        threeBlockIv = (ImageView) findViewById(R.id.clockwise_three_block);
        fourBlockIv = (ImageView) findViewById(R.id.clockwise_four_block);
        fiveBlockIv = (ImageView) findViewById(R.id.clockwise_five_block);
        sixBlockIv = (ImageView) findViewById(R.id.clockwise_six_block);
        sevenBlockIv = (ImageView) findViewById(R.id.clockwise_seven_block);

        twoBlockIv.setOnClickListener(this);
        threeBlockIv.setOnClickListener(this);
        fourBlockIv.setOnClickListener(this);
        fiveBlockIv.setOnClickListener(this);
        sixBlockIv.setOnClickListener(this);
        sevenBlockIv.setOnClickListener(this);

        muteIv.setOnClickListener(this);
        holdIv.setOnClickListener(this);

        tipLayout = (LinearLayout) findViewById(R.id.meeting_tip_layout);

        moreBtn = (Button) findViewById(R.id.more_btn);
        moreBtn.setOnClickListener(this);


        findViewById(R.id.end_btn).setOnClickListener(this);
        recallIv = (ImageView) findViewById(R.id.people_recall);
        findViewById(R.id.people_remove).setOnClickListener(this);
        recallIv.setOnClickListener(this);

        oneImage.setOnClickListener(this);
        twoImage.setOnClickListener(this);
        threeImage.setOnClickListener(this);
        fourImage.setOnClickListener(this);
        fiveImage.setOnClickListener(this);
        sixImage.setOnClickListener(this);
        sevenImage.setOnClickListener(this);

        pauseBtn.setOnClickListener(this);
        lockBtn.setOnClickListener(this);

        setBtnEnable();
    }



    /**
     * 通过线路的id 来确定该线路在屏幕上对应的位置 -1为该线路为添加到屏幕
     * @param lineId
     * @return
     */
    private int getPositionByLineId(int lineId){
        if(map == null ||map.size() == 0){
            initMap();
        }
        int position = -1;
        for(int i=2;i<=7;i++){
            if(map.get(i)==lineId){
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * 判断当前会议上是否有人
     * @return
     */
    private Boolean isHavingPeople(){
        Boolean isHavingPeople = false;
        if(map == null||map.size()==0){
            initMap();
        }
        for(int i=2;i<7;i++){
            if(map.get(i)!=-1){
                isHavingPeople =true;
                break;
            }
        }
        return isHavingPeople;
    }
    /**
     * 设置更多按钮的焦点问题
     */
    private void setBtnEnable(){
        if(isHavingPeople()){
            moreBtn.setEnabled(true);
            pauseBtn.setEnabled(true);
            lockBtn.setEnabled(true);
        }else{
            moreBtn.setEnabled(false);
            pauseBtn.setEnabled(false);
            lockBtn.setEnabled(false);
        }
    }

    /**
     * 设置点击录音事件
     */
    private void setPressRecordListener(){
        if(CallUtils.getInstance(this).isConferenceInRecord()){
            CallUtils.getInstance(this).stopConferenceRecord();
        }else{
            CallUtils.getInstance(this).startConferenceRecord();
        }
    }

    /**
     * 设置全部静音点击事件
     */
    private void setIsAllMute(){
        CallUtils.getInstance(this).setIsMutedAll(!isMute);
    }

    /**
     * 设置点击暂停事件
     */
    private void setPressPauseListener(){
        CallUtils.getInstance(this).setIsConfOnHold(!isOnHold);
    }
    private void setAlertOnClickListener(DialogInterface dialog, int which){
        switch (which) {
            case 0: {//开始暂停
                setPressPauseListener();
                break;
            }
            case 1: {//开始录音
                setPressRecordListener();
                break;
            }
            case 2:{//开始静音
                setIsAllMute();
                break;
            }
            case 3: {//结束会议
                CallUtils.getInstance(MeetingActivity.this).endConf();
                break;
            }
        }
        dialog.dismiss();
    }

    /**
     * 获取不同状态下，alert对应显示的数组
     * @return
     */
    private String[] getStringArray(){
        String [] confStateStrs= new String[]{getString(R.string.start_pause),
                getString(R.string.start_record),getString(R.string.start_mute),
                getString(R.string.end_meeting)};

        if(CallUtils.getInstance(this).isConferenceInRecord()){
            confStateStrs[1] = getString(R.string.stop_record);
        }
        if(isMute){
            confStateStrs[2] = getString(R.string.stop_mute);
        }
        if(isOnHold){
            confStateStrs[0] = getString(R.string.stop_pause);
        }
        return confStateStrs;
    }
    /**
     * 点击更多时候的事件
     */
    private void pressMoreBtnListener(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(getStringArray(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setAlertOnClickListener(dialog, which);
            }
        });
        builder.show();
    }

    /**
     * 从会议中移除某人
     */
    private void removePeople(int position){
        int lineId = map.get(position);
        CallUtils.getInstance(this).endCall(lineId);
        map.put(position, -1);
        tipLayout.setVisibility(View.GONE);
    }

    /**
     * 重播
     */
    private void recallPeople(int position){
        int lineId = map.get(position);
        CallUtils.getInstance(this).confAddLine(lineId);
        tipLayout.setVisibility(View.GONE);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tipLayout.setVisibility(View.GONE);
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pause_btn:{
                pauseBtn.setSelected(!pauseBtn.isSelected());
                setPressPauseListener();
                break;
            }
            case R.id.lock_btn:{
                lockBtn.setSelected(!lockBtn.isSelected());
                lockBtnOnClick();
                break;
            }
            case R.id.clockwise_two:{
                addLineToConf(2);
                break;
            }
            case R.id.clockwise_three:{
                addLineToConf(3);
                break;
            }
            case R.id.clockwise_four:{
                addLineToConf(4);
                break;
            }
            case R.id.clockwise_five:{
                addLineToConf(5);
                break;
            }
            case R.id.clockwise_six:{
                addLineToConf(6);
                break;
            }
            case R.id.clockwise_seven:{
                addLineToConf(7);
                break;
            }
            case R.id.more_btn:{
                pressMoreBtnListener();
                break;
            }
            case R.id.end_btn:{
                setEndBtnListener();
                break;
            }
            case R.id.people_remove:{
                removePeople(position);
                break;
            }
            case R.id.people_recall:{
                recallPeople(position);
                break;
            }
            case R.id.people_pause:{//点击暂停
                setOnClickPasueListener();
                break;
            }
            case R.id.people_mute:{
                setMuteIvListener();
                break;
            }
            case R.id.clockwise_two_block:{
                Integer lineId = (Integer) v.getTag();
                setMuteIvClick(lineId);
                break;
            }
            case R.id.clockwise_three_block:{
                Integer lineId = (Integer) v.getTag();
                setMuteIvClick(lineId);
                break;
            }
            case R.id.clockwise_four_block:{
                Integer lineId = (Integer) v.getTag();
                setMuteIvClick(lineId);
                break;
            }
            case R.id.clockwise_five_block:{
                Integer lineId = (Integer) v.getTag();
                setMuteIvClick(lineId);
                break;
            }
            case R.id.clockwise_six_block:{
                Integer lineId = (Integer) v.getTag();
                setMuteIvClick(lineId);
                break;
            }
            case R.id.clockwise_seven_block:{
                Integer lineId = (Integer) v.getTag();
                setMuteIvClick(lineId);
                break;
            }
        }
    }

    /**
     * 设置线路是否静音
     * @param lineId
     */
    private void setMuteIvClick(int lineId){
        LineObj lineObj = LineUtils.getInstance(this).getLineById(lineId);
        if(lineObj == null){return;}
        CallUtils.getInstance(this).muteUnmuteLocal(lineId,!lineObj.getIsLocalMuted());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            CallUtils.getInstance(this).endConf();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置单个静音点击事件
     */
    private void setMuteIvListener(){
        Boolean isMute = muteStates[position-2];
        Boolean isSuccess = CallUtils.getInstance(this).muteUnmuteLocal(map.get(position),!isMute);
        tipLayout.setVisibility(View.GONE);
        if(isSuccess == false){
            Toast.makeText(this, R.string.failed_mute, Toast.LENGTH_SHORT).show();
            return;
        }
        muteStates[position-2] = !muteStates[position-2];
    }



    /**
     * 设置个人暂停图标点击事件
     */
    private void setOnClickPasueListener(){
        Boolean isHold = pauseStates[position-2];
        if(isHold){
            CallUtils.getInstance(this).stopPauseCall(map.get(position), true);
            pauseBtn.setSelected(false);
        }else {
            CallUtils.getInstance(this).startPauseCall(map.get(position), true);
            pauseBtn.setSelected(true);
        }
        tipLayout.setVisibility(View.GONE);
        pauseStates[position-2] = !pauseStates[position-2];
    }

    /**
     * 设置结束按钮的点击事件
     */
    private void setEndBtnListener(){
        if(isHavingPeople()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.isEndConf);
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    CallUtils.getInstance(MeetingActivity.this).endConf();
                    unregisterReceiver(receiver);
                    receiver = null;
                    MeetingActivity.this.finish();
                    if (notificationManager != null) {
                        notificationManager.cancelAll();
                    }
                }
            });
            builder.show();
        }else {
            this.finish();
        }
    }

    /**
     * 添加一路通话到会议
     */
    private void addLineToConf(int positioned){
        position = positioned;
        if(!isHavingLine(positioned)) {//如果该位置没有人占用，则添加
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(Contants.IS_CONFLINE, true);
            startActivityForResult(intent, REQUESTCODE);
        }else{//该位置有人占用显示该人的相关操作按键
            int lineId = map.get(positioned);
            if(lineId>=0){
                tipLayout.setVisibility(View.VISIBLE);
                LineObj lineObj = LineUtils.getInstance(this).getLineById(lineId);
                String phoneNumStr = lineObj.getCallConnection().getOriginDialNumber();
                String nameStr = null;
                List<String> list = ContactsUtil.getInstance(this).getDisplayNameByPhone(phoneNumStr);
                if(list.size()>0){
                    nameStr = ContactsUtil.getInstance(this).getDisplayNameByPhone(phoneNumStr).get(0);
                }
                peoplePhoneTv.setText(phoneNumStr);
                peopleNameTv.setText(nameStr);
                int status = LineUtils.getInstance(this).getLineById(lineId).getState();
                if(status == LineObjManager.STATUS_FAILED){//表示该位置的人连接但没连接上
                    recallIv.setVisibility(View.VISIBLE);
                    holdIv.setVisibility(View.GONE);
                    muteIv.setVisibility(View.GONE);
                }
                else{//该位置有人而且正在通话
                    recallIv.setVisibility(View.GONE);
                    holdIv.setVisibility(View.VISIBLE);
                    holdIv.setSelected(pauseStates[positioned-2]);
                    muteIv.setSelected(muteStates[positioned-2]);
                    muteIv.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 判断当前位置是否有人坐
     * @param position
     * @return
     */
    private Boolean isHavingLine(int position){
        Boolean isHavinLine;
        if(map.get(position) == -1){
            isHavinLine = false;
        }else{
            isHavinLine = true;
        }
        return isHavinLine;
    }

    /**
     * 随机获取一个没人的位置　没有位置的时候返回－１
     * @return
     */
    private int getEmptyPosition(){
        int position = -1;
        for(int i=2;i<=7;i++){
            if(map.get(i) == NO_PEOPLE){
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * 初始化map的值　使其开始默认　所有位置上都没有人 NO_PEOPLE为没有通话线路
     */
    private void initMap(){
        if(map!=null&&map.size()>0){
            return;
        }
        for(int i=2;i<=7;i++) {
            map.put(i,NO_PEOPLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODE && resultCode == MainActivity.RESULT_CODE){
            List<String> phoneStrList = data.getStringArrayListExtra(Contants.PHONE_LIST_KEY);
            for(int i=0;i<phoneStrList.size();i++) {
                String phoneStr = phoneStrList.get(i);
                CallUtils.getInstance(this).confCall(MyApplication.localId, phoneStr, phoneStr, MyApplication.callModel);

            }
        }
    }


    /**
     * 锁定按钮点击事件
     */
    private void lockBtnOnClick(){
        CallUtils.getInstance(this).setConfLock(!isLock);
    }

    /**
     * 设置相应状态的对应事件
     * @param lineObj
     * @return
     */
    private String getLineChangeString(LineObj lineObj,int status){
        String handle = null;
        setBtnEnable();
        switch (status){
            /** 等待会议的验证*/
            case LineObjManager.STATUS_VERIFING:{
                handle = getString(R.string.verifing);
                break;
            }
            /** 响铃状态*/
            case LineObjManager.STATUS_RINGING:{
                break;
            }
            /** 空闲状态*/
            case LineObjManager.STATUS_IDLE:{
                break;
            }
            /** 建立连接*/
            case LineObjManager.STATUS_CONNECTED:{
                handle = getResources().getString(R.string.calling);
                break;
            }
            case LineObjManager.STATUS_FAILED:{
                int errorCode = lineObj.getErrCode();
                String errorStr = CallErrorCode.parseErrCode(this,errorCode);
                handle = errorStr;
                break;
            }
            case LineObjManager.STATUS_DIALING:{
                handle = getResources().getString(R.string.in_call);
                break;
            }
            case LineObjManager.STATUS_CONFERENCE:{
                break;
            }
            case LineObjManager.STATUS_CALLING:{
                break;
            }
            case LineObjManager.STATUS_ENDING:{
                break;
            }
            /** 暂停状态*/
            case LineObjManager.STATUS_ONHOLD:{
                break;
            }
        }
        return handle;
    }

    /**
     * 判断当前会议是否还有人　true没人　false有人
     * @return
     */
    private Boolean isNoPeople(){
        Boolean isNoPeople = true;
        for(int i=2;i<=7;i++){
            if(map.get(i)!=-1){
                isNoPeople = false;
                break;
            }
        }
        return isNoPeople;
    }

    /**
     *点击哪个位置　响应线路变化该位置视图的改变
     * @param imageView
     * @param textView
     */
    private void setLineChangeView(final ImageView imageView, final TextView textView,
                                   final TextView textNameView, final int position,
                                   final int status,ImageView localMuteIv){
        int lineId = map.get(position);
        if(lineId == -1){
            return;
        }
        LineObj lineObj = LineUtils.getInstance(MeetingActivity.this).getLineById(lineId);
        String handle = getLineChangeString(lineObj,status);
        if(status == LineObjManager.STATUS_ENDED || status == LineObjManager.STATUS_ENDING){
            Bitmap bitmap = ImageUtils.getBitmapByResId(R.mipmap.add_user, MeetingActivity.this);
            imageView.setImageBitmap(bitmap);
            textView.setText("");
            textNameView.setText("");
            map.put(position, -1);
            bitmapStates[position -2] = false;
            pauseStates[position -2] = false;
            muteStates[position -2] = false;
        } else if(lineObj!=null){
            if(!bitmapStates[position-2]){
                setBitmap(imageView,lineObj);
                bitmapStates[position-2] = true;
            }
            textNameView.setText(lineObj.getCallConnection().getOriginDialNumber());
            textView.setText(handle);

            if(lineObj.getIsLocalMuted()) {
                localMuteIv.setVisibility(View.VISIBLE);
            }else{
                localMuteIv.setVisibility(View.GONE);
            }
            localMuteIv.setTag(lineId);
        }
        if(isNoPeople()) {
            moreBtn.setFocusable(false);
            if(notificationManager!=null){
                notificationManager.cancelAll();
            }
        }else{
            moreBtn.setFocusable(true);
            setShowInNotification();
        }


    }

    /**
     * 设置图片显示
     * @param imageView
     * @param lineObj
     */
    private void setBitmap(ImageView imageView,LineObj lineObj){
        String phoneNumStr = lineObj.getCallConnection().getOriginDialNumber();
        Bitmap bitmap = ContactsUtil.getInstance(MeetingActivity.this).getPhotoByPhone(phoneNumStr);
        if(bitmap == null){
            bitmap = ImageUtils.getBitmapByResId(R.mipmap.conf_incoming_default_pic,MeetingActivity.this);
            imageView.setImageBitmap(bitmap);
        }else {
            imageView.setImageBitmap(bitmap);
        }
    }



    /**
     * 通过已有的线路ＩＤ获取该线路在该位置的位置 -1为该线路不在座位上
     * @param lineId
     * @return
     */
    private int getPositionByHavingId(int lineId){
        int position = -1;
        for(int i=2;i<=7;i++){
            if(map.get(i) == lineId){
                position = i;
                break;
            }
        }
        return position;
    }


    private void setLineChange(int lineObjId,int status){
        int position = getPositionByHavingId(lineObjId);
        switch (position){
            case 2:{
                setLineChangeView(twoImage,twoTv,twoNameTv,2,status,twoBlockIv);
                break;
            }
            case 3:{
                setLineChangeView(threeImage,threeTv,threeNameTv,3,status,threeBlockIv);
                break;
            }
            case 4:{
                setLineChangeView(fourImage, fourTv,fourNameTv,4,status,fourBlockIv);
                break;
            }
            case 5:{
                setLineChangeView(fiveImage, fiveTv,fiveNameTv,5,status,fiveBlockIv);
                break;
            }
            case 6:{
                setLineChangeView(sixImage, sixTv,sixNameTv,6,status,sixBlockIv);
                break;
            }
            case 7:{
                setLineChangeView(sevenImage, sevenTv,sevenNameTv,7,status,sevenBlockIv);
                break;
            }
        }
    }

    /**
     * 初始化当前所有活跃线路
     */
    private void initLineObjes(){
        List<LineObj> lineObjs = StatusListenService.getLineObjs();
        if(lineObjs!=null&& lineObjs.size()>0){
            for(int i=0;i<lineObjs.size();i++){
                LineObj lineObj = lineObjs.get(i);
                if(lineObj.getIsInConf()){
                    int position = getEmptyPosition();
                    map.put(position,lineObj.getId());
                    setLineChange(lineObj.getId(),lineObj.getState());
                }
            }
        }
    }

    private void setConfChangeReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.BROADCAST_CONF);
        registerReceiver(confChangeReceiver,intentFilter);
    }

    private BroadcastReceiver confChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            isLock = bundle.getBoolean(Contants.IS_LOCK);
            isMute = bundle.getBoolean(Contants.IS_MUTE_KEY);
            isOnHold = bundle.getBoolean(Contants.IS_ONHOLD_KEY);

            lockBtn.setSelected(isLock);
            pauseBtn.setSelected(isOnHold);
        }
    };

    /**
     * 线路变化广播监听事件
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int lineObjId = bundle.getInt(Contants.LINEOBJ_KEY);
            int status = bundle.getInt(Contants.CALL_STATUS);
            Boolean isLocalMute = bundle.getBoolean(Contants.LINE_MUTE);

            if(map == null||map.size() == 0){
                initMap();
            }
            if(getPositionByLineId(lineObjId) ==-1) {
                if (map.get(position) != -1) {
                    map.put(getEmptyPosition(),lineObjId);
                } else {
                    map.put(position, lineObjId);
                }
            }

            setLineChange(lineObjId, status);
        }
    };

    /**
     *注册广播接收事件
     */
    private void setBroadCastRevice(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.BROADCAST_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 当按住返回时候将该页面放在通知栏中
     */
    private void setShowInNotification(){
        notificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setTicker(getResources().getString(R.string.touch_return_call));
        mBuilder.setSmallIcon(R.mipmap.line_talking_normal);
        mBuilder.setContentTitle(getResources().getString(R.string.call_state));
        mBuilder.setContentText(getResources().getString(R.string.having_conf));

        Intent notifyIntent = new Intent(this, MeetingActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
        notificationManager.notify(3, mBuilder.build());
    }





}
