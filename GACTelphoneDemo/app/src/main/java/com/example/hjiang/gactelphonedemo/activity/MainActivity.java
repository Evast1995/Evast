package com.example.hjiang.gactelphonedemo.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hjiang.gactelphonedemo.MyApplication;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.adapter.DialsAdapter;
import com.example.hjiang.gactelphonedemo.fragment.ModelFragment;
import com.example.hjiang.gactelphonedemo.fragment.SearchFragment;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
import com.example.hjiang.gactelphonedemo.util.Contants;
import com.example.hjiang.gactelphonedemo.util.OtherUtils;
import com.example.hjiang.gactelphonedemo.weight.DelEdit;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {



    /** 用于测试git 无用的注释   */

    /** 用于测试git 无用的注释   */

    /** 用于测试git 无用的注释   */

    /** 用于测试git 无用的注释   */
    /** 用于测试git 无用的注释   */
    /** 用于测试git 无用的注释   */

    /** 用于测试git 无用的注释   *//** 用于测试git 无用的注释   */
    /** 用于测试git 无用的注释   */
    /** 用于测试git 无用的注释   */
    /** 用于测试git 无用的注释   */



    /** 用于测试git    */
    /** 用于测试git    */
    /** 用于测试git    */
    /** 用于测试git    */   /** 用于测试git    */   /** 用于测试git    */   /** 用于测试git    */
    /** 用于测试git    */
    /** 用于测试git    */
    /** 用于测试git    */
    /** 用于测试git    */








    private GridView gridView;
    private DelEdit delEdit;
    private ImageView callImage;
    public static final int RESULT_CODE = 101;
    /** 判断当前　搜索页面是否展开*/
    private Boolean isSearchVisiable = false;
    private ModelFragment modelFragment = null;
    private SearchFragment searchFragment = null;
    private static final int CONTACTS_REQUEST_CODE=102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setEditChange();
    }
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        // add a fragment to current activity window.
        // fragment instance or args.
        // which fragment it is.

        if (fragment instanceof SearchFragment) {
            searchFragment = (SearchFragment) fragment;
        } else if (fragment instanceof ModelFragment) {
            modelFragment = (ModelFragment) fragment;
        }

    }
    /**
     * 初始化视图
     */
    private void initView(){
        /** 初始化拨号盘相关视图*/
        setDials();
        findViewById(R.id.meeting_btn).setOnClickListener(this);
        findViewById(R.id.contact_btn).setOnClickListener(this);
    }

    /**
     * 对外提供一个改变DelEdit的方法
     */
    public void setDelEdit(String phoneStr){
        delEdit.setAddTextView(phoneStr);
    }
    /**
     * 设置拨号盘，拨号编辑框
     */
    private void setDials(){
        gridView = (GridView) findViewById(R.id.dials);
        delEdit = (DelEdit) findViewById(R.id.eidt_layout);
        callImage = (ImageView) findViewById(R.id.call_icon);
        callImage.setOnClickListener(this);
        final DialsAdapter adapter = new DialsAdapter(MainActivity.this, OtherUtils.getDialsNum());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) adapter.getItem(position);
                delEdit.addString(str);
            }
        });
        modelFragment = new ModelFragment();
        searchFragment = new SearchFragment();
        switchFragmentContent(R.id.lefl_slip_layout, modelFragment);
    }

    /**
     * 拨打电话
     */
    private void makeCall(){
        String callStr = delEdit.getEditText();
        List<String> list = delEdit.getImagesText();
        int position = list.size();
        if(position>1||(position == 1&&!TextUtils.isEmpty(callStr))){//表示成员有两人及其以上 则开启会议
            while(position>0){
                String phoneNum =list.get(position-1);
                CallUtils.getInstance(this).confCall(MyApplication.localId,phoneNum,phoneNum,MyApplication.callModel);
                position--;
            }
            if(!TextUtils.isEmpty(callStr)) {
                CallUtils.getInstance(this).confCall(MyApplication.localId, callStr, callStr,MyApplication.callModel);
            }
        }else {//无会议成员则拨打单路线路
            if(!TextUtils.isEmpty(callStr)) {
                CallUtils.getInstance(this).makeCall(MyApplication.localId, callStr, callStr, MyApplication.callModel);
            }else if(position == 1){
                CallUtils.getInstance(this).makeCall(MyApplication.localId, list.get(0), list.get(0), MyApplication.callModel);
            } else{
                Toast.makeText(this,getResources().getString(R.string.phone_isnull),Toast.LENGTH_SHORT).show();
            }
        }
        list.clear();
        delEdit.removeEditText();
    }

    /**
     * 转拨
     */
    private void makeTransfer(){
        String callStr = delEdit.getEditText();
        MyApplication.isTransfer = true;
        CallUtils.getInstance(this).transferCall(MyApplication.localId, callStr);
    }

    /**
     * 是否是会议线路
     * @return
     */
    private Boolean isInConfLine(){
        return getIntent().getBooleanExtra(Contants.IS_CONFLINE,false);
    }

    /**
     * 判断当前电弧是否是通话转移
     * @return
     */
    private Boolean isTransfer(){
        Boolean isTransfer = getIntent().getBooleanExtra(Contants.IS_TRANSFER, false);
        return  isTransfer;
    }

    /**
     * 创建会议线路
     */
    private void makeConf(){
        String callStr = delEdit.getEditText();
        Intent intent = new Intent();
        intent.putExtra(Contants.PHONE_NUM,callStr);
        setResult(RESULT_CODE,intent);
        finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /** 按下拨打电话*/
            case R.id.call_icon:{
                if(isTransfer()) {//转拨线路
                    makeTransfer();
                }else if(isInConfLine()){//会议线路
                    makeConf();
                }else{//拨号
                    makeCall();
                }
                break;
            }
            /** 按下会议按钮*/
            case R.id.meeting_btn:{
                openMeetingRoom();
                break;
            }
            /** 按下联系人按钮*/
            case R.id.contact_btn:{
                startActivityForResult(new Intent(this, ContactsActivity.class), CONTACTS_REQUEST_CODE);
//                openContactView();
                break;
            }
        }
    }

    /**
     * 解决singleTask和intent传值的问题
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /**
     * 打开会议界面
     */
    private void openMeetingRoom(){
        Intent intent = new Intent(this,MeetingActivity.class);
        startActivity(intent);
    }

    /**
     * 打开联系人视图
     */
    private void openContactView() {
//        ArrayList<ContentValues> data = new ArrayList<ContentValues>();
//
//        ContentValues row1 = new ContentValues();
//        row1.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE);
//        row1.put(ContactsContract.CommonDataKinds.Organization.COMPANY, "Android");
//        data.add(row1);
//
//        ContentValues row2 = new ContentValues();
//        row2.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
//        row2.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM);
//        row2.put(ContactsContract.CommonDataKinds.Email.LABEL, "Green Bot");
//        row2.put(ContactsContract.CommonDataKinds.Email.ADDRESS, "android@android.com");
//        data.add(row2);
//
//        Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
//        intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);
//
//        startActivity(intent);

    }


    /**
     * 编辑框变动时事件
     */
    private void setEditChange(){
        delEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {//当编辑框为空时显示modelfragment页面
                    switchFragmentContent(R.id.lefl_slip_layout, modelFragment);
                    isSearchVisiable = false;
                } else if (!isSearchVisiable&&s.length()!=0) {//当编辑框里的内容不为空的时候，并且SearchFragment没有显示时切换到SearchFragment
                    switchFragmentContent(R.id.lefl_slip_layout, searchFragment);
                    isSearchVisiable = true;
                    setEditChangeSearch(s.toString());
                }else if(isSearchVisiable&&s.length()!=0) {//当编辑框内容不为空并且SearchFragment正在显示时
                    setEditChangeSearch(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 文本有变化的时候
     * @param str
     */
    private void setEditChangeSearch(final String str){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                searchFragment.search(str);
            }
        });
    }

    private Handler mHandler = new Handler();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == CONTACTS_REQUEST_CODE){
            Bundle bundle = data.getExtras();
            List<String> phoneList = bundle.getStringArrayList(Contants.PHONE_LIST_KEY);
            Log.e("--main--",phoneList.toString());
            for(int i = 0;i<phoneList.size();i++){
                delEdit.setAddTextView(phoneList.get(i));
            }
        }
    }
}