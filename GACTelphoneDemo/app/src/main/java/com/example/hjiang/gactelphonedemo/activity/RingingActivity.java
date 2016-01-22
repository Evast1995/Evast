package com.example.hjiang.gactelphonedemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.module.call.CallManager;
import com.base.module.call.line.LineObj;
import com.base.module.call.line.LineObjManager;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
import com.example.hjiang.gactelphonedemo.util.Contants;

/**
 * Created by hjiang on 16-1-8.
 */
public class RingingActivity extends BaseActivity implements View.OnClickListener{
    private TextView localNameTv;
    private TextView remoteNumTv;
    private TextView titleView;
    private ImageView headIv;
    private LineObj lineObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);
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
        findViewById(R.id.accept).setOnClickListener(this);
        findViewById(R.id.reject).setOnClickListener(this);
    }

    private void initData(){
        Bundle bundle = getIntent().getExtras();
        lineObj = (LineObj) bundle.getSerializable(Contants.INTENT_LINEOBJ);
        String localName = lineObj.getBaseAccount().getLocalName();
        String remoteNum = lineObj.getCallConnection().getOriginDialNumber();

        localNameTv.setText(getResources().getString(R.string.local_account) + localName);
        remoteNumTv.setText(getResources().getString(R.string.remote_num) + remoteNum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accept:{
                CallUtils.getInstance(this).accept(lineObj.getLocalId(),
                        CallManager.CALL_AUDIO_MODE_SPEAKER,false);
                break;
            }
            case R.id.reject:{
                CallUtils.getInstance(this).reject(lineObj.getLocalId());
                break;
            }
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
     * 设置相应状态的对应事件(如果不是ringing状态则销毁该activity)
     * @param status
     */
    private void setLineChange(int status){
       if(status != LineObjManager.STATUS_RINGING){
           this.finish();
       }
    }
    /**
     *注册广播接收事件
     */
    private void setBroadCastRevice(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.BROADCAST_ACTION);
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
