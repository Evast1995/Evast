package com.example.hjiang.gactelphonedemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.base.module.call.account.CallAccount;
import com.example.hjiang.gactelphonedemo.MyApplication;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.adapter.ChangeContactAdapter;
import com.example.hjiang.gactelphonedemo.adapter.ChangeHistoryAdapter;
import com.example.hjiang.gactelphonedemo.bean.ChangeContactsBean;
import com.example.hjiang.gactelphonedemo.bean.ChangeHistoryBean;
import com.example.hjiang.gactelphonedemo.util.AccountUtils;
import com.example.hjiang.gactelphonedemo.util.ContactsUtil;
import com.example.hjiang.gactelphonedemo.util.Contants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-18.
 */
public class ContactsActivity extends BaseActivity implements View.OnClickListener{
    private EditText editText;
    private Button modelBut;
    private ImageView extendIv;
    private ListView contactLv;
    private ListView historyLv;
    private List<ChangeContactsBean> contactList;
    private List<ChangeHistoryBean> historyList;
    private ChangeContactAdapter adapter;
    private TextView gourpTv;
    private TextView historyGroupTv;
    private ChangeHistoryAdapter historyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
    }

    private void initView(){
        findViewById(R.id.extend_history_layout).setOnClickListener(this);
        findViewById(R.id.contact_back_icon).setOnClickListener(this);
        findViewById(R.id.contact_sure_icon).setOnClickListener(this);
        findViewById(R.id.extend_contact_layout).setOnClickListener(this);

        historyGroupTv = (TextView) findViewById(R.id.group_history_people);
        historyLv = (ListView) findViewById(R.id.history_list);
        gourpTv = (TextView) findViewById(R.id.group_contact_people);
        extendIv = (ImageView) findViewById(R.id.open_contact_extend);
        contactLv = (ListView) findViewById(R.id.change_listview);
        contactLv.setAdapter(adapter);
        modelBut = (Button) findViewById(R.id.contactchange_account_btn);
        modelBut.setOnClickListener(this);
        modelBut.setText(MyApplication.localPhone);
        editText = (EditText) findViewById(R.id.contactchange_edit);


        contactList = ContactsUtil.getInstance(this).getAllContacts();
        adapter = new ChangeContactAdapter(this,contactList);
        contactLv.setAdapter(adapter);

        showHistory();
        String historyGourpStr = getResources().getString(R.string.group_people);
        historyGourpStr =  String.format(historyGourpStr, historyList.size());
        historyGroupTv.setText(historyGourpStr);


        String gourpStr = getResources().getString(R.string.group_people);
        gourpStr = String.format(gourpStr, contactList.size());
        gourpTv.setText(gourpStr);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contact_back_icon:{//点击返回图标
                finish();
                break;
            }
            case R.id.contact_sure_icon:{//点击确认图标
                setActivityResultData();
                break;
            }
            case R.id.contactchange_account_btn:{//点击账号变更按钮
                changeAccount();
                break;
            }
            case R.id.extend_contact_layout:{
                if(contactLv.getVisibility() == View.VISIBLE){
                    contactLv.setVisibility(View.GONE);
                }else {
                    contactLv.setVisibility(View.VISIBLE);
                    showContacts();
                }
                break;
            }
            case R.id.extend_history_layout:{
                if(historyLv.getVisibility() == View.VISIBLE){
                    historyLv.setVisibility(View.GONE);
                }else{
                    historyLv.setVisibility(View.VISIBLE);
                    historyAdapter.changeData(historyList);
                }
                break;
            }
        }
    }

    /**
     * 点击切换账号弹出dialog
     */
    private void changeAccount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.change_account));
        List<CallAccount> list = AccountUtils.getInstance(this).getAllUsableCallAccounts();
        final CharSequence[] charSequences = new CharSequence[list.size()];
        for(int i=0;i<list.size();i++){
            CallAccount callAccount = list.get(i);
            charSequences[i] = callAccount.getSipUserId();
        }
        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                modelBut.setText(charSequences[which]);
                MyApplication.localPhone = (String) charSequences[which];
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 显示联系人
     */
    private void showContacts(){
        adapter.setChangeList(contactList);
    }

    /**
     * 显示历史记录
     */
    private void showHistory(){
        historyList = new ArrayList<ChangeHistoryBean>();
        /** 通过模糊匹配查找出来的历史记录*/
        Cursor historyCursor =  ContactsUtil.getInstance(this).getAllCallHistory();
        while(historyCursor.getCount()>0&&historyCursor.moveToNext()){
            ChangeHistoryBean changeHistoryBean = new ChangeHistoryBean();
            String phoneNumStr = historyCursor.getString(historyCursor.getColumnIndex("origin_number"));
            changeHistoryBean.setPhoneNumStr(phoneNumStr);
            int type = historyCursor.getInt(historyCursor.getColumnIndex("type"));
            changeHistoryBean.setType(type);
            int account = historyCursor.getInt(historyCursor.getColumnIndex("account"));
            changeHistoryBean.setAccount(account);
            changeHistoryBean.setIsSelected(false);
            if(!isRetainHistory(historyList,changeHistoryBean)) {
                historyList.add(changeHistoryBean);
            }
        }
        historyCursor.close();
        historyAdapter = new ChangeHistoryAdapter(this, historyList);
        historyLv.setAdapter(historyAdapter);
    }

    /**
     * 判断该记录是否类似　类似则去掉
     */
    private Boolean isRetainHistory(List<ChangeHistoryBean> list,ChangeHistoryBean changeHistoryBean){
        Boolean isRetain = false;
        for(int i=0;i<list.size();i++){
            ChangeHistoryBean bean = list.get(i);
            if(bean.equals(changeHistoryBean)){
                isRetain = true;
                break;
            }
        }
        return isRetain;
    }

    /**
     * 设置点击确认返回相应的信息
     */
    private void setActivityResultData(){
        String callStr = editText.getText().toString();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        List<String> list = adapter.getCheckedList();
        list.addAll(historyAdapter.getChangeList());
        if(!callStr.isEmpty()){
            list.add(callStr);
        }
        bundle.putStringArrayList(Contants.PHONE_LIST_KEY, (ArrayList<String>) list);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


}
