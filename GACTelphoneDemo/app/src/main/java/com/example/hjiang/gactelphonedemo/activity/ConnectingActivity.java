package com.example.hjiang.gactelphonedemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.module.call.line.LineObj;
import com.base.module.call.line.LineObjManager;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
import com.example.hjiang.gactelphonedemo.util.Contants;

/**
 * Created by hjiang on 16-1-11.
 */
public class ConnectingActivity extends BaseActivity implements View.OnClickListener{
    private TextView localNameTv;
    private TextView remoteNumTv;
    private TextView titleView;
    private ImageView headIv;
    private LineObj lineObj;
    private Button addMeetingBtn;
    private Button recordBtn;
    private Button handUpBtn;
    private Button pauseBtn;
    private Button transferBtn;
    public static int REQUESTCODE = 1;
    /**是否在录音*/
    private Boolean isRecord = false;
    /** 是否在暂停状态*/
    private Boolean isPause = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);
        initView();
        initData();
        setBroadCastRevice();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        localNameTv = (TextView) findViewById(R.id.local_name);
        remoteNumTv = (TextView) findViewById(R.id.remote_num);
        headIv = (ImageView) findViewById(R.id.call_head);
        titleView = (TextView) findViewById(R.id.tilte_text);
        addMeetingBtn = (Button) findViewById(R.id.add_meeting);
        recordBtn = (Button) findViewById(R.id.record);
        handUpBtn = (Button) findViewById(R.id.hand_up);
        pauseBtn = (Button) findViewById(R.id.pause);
        transferBtn = (Button) findViewById(R.id.transfer_call);
        addMeetingBtn.setOnClickListener(this);
        recordBtn.setOnClickListener(this);
        handUpBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        transferBtn.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        Bundle bundle = getIntent().getExtras();
        lineObj = (LineObj) bundle.getSerializable(Contants.INTENT_LINEOBJ);
        String localName = lineObj.getBaseAccount().getLocalName();
        String remoteNum = lineObj.getCallConnection().getOriginDialNumber();

        localNameTv.setText(getResources().getString(R.string.local_account) + localName);
        remoteNumTv.setText(String.format(getResources().getString(R.string.call_with), remoteNum));
        titleView.setText(getResources().getString(R.string.calling));

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /** 加入会议*/
            case R.id.add_meeting:{
                break;
            }
            /** 接听*/
            case R.id.hand_up:{
                CallUtils.getInstance(this).endCall(lineObj.getId());
                break;
            }
            /** 录音*/
            case R.id.record:{
                setRecord();
                break;
            }
            /** 暂停*/
            case R.id.pause:{
                setPauseCall();
                break;
            }
            /** 呼叫转移*/
            case R.id.transfer_call:{
                setTransFerCall();
                break;
            }
        }
    }

    /**
     * 设置盲转移通话
     */
    private void setTransFerCall(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(Contants.IS_TRANSFER,true);
        startActivity(intent);
    }


    /**
     * 设置通话暂停
     */
    private void setPauseCall(){
        if(!isPause) {
            CallUtils.getInstance(this).startPauseCall(lineObj.getId(), false);
            pauseBtn.setText(R.string.stop_pause);
        }else{
            CallUtils.getInstance(this).stopPauseCall(lineObj.getId(), false);
            pauseBtn.setText(R.string.start_pause);
        }
        isPause = !isPause;
    }

    /**
     * 设置录音
     */
    private void setRecord(){
        if(isRecord == true){
            isRecord = false;
            recordBtn.setText(R.string.start_record);
            CallUtils.getInstance(this).stopRecord();
        }else{
            isRecord = true;
            recordBtn.setText(R.string.stop_record);
            CallUtils.getInstance(this).startRecord(lineObj.getId());
        }
    }

    /**
     * 线路变化广播监听事件
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int status = bundle.getInt(Contants.CALL_STATUS);
            setLineChange(status);
        }
    };


    /**
     * 设置相应状态的对应事件
     * @param status
     */
    private void setLineChange(int status){
        /** 状态为连接和暂停时候不去结束activity*/
        if(status != LineObjManager.STATUS_CONNECTED && status!=LineObjManager.STATUS_ONHOLD){
            finish();
        }
    }

    /**
     *注册广播接收事件
     */
    private void setBroadCastRevice(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.BROADCAST_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
