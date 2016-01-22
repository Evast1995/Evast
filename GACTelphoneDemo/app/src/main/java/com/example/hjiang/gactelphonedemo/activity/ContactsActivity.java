package com.example.hjiang.gactelphonedemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.hjiang.gactelphonedemo.bean.ChangeContactsBean;
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
    private ListView listView;
    private ChangeContactAdapter adapter;
    private TextView gourpTv;
    private List<ChangeContactsBean> list = new ArrayList<ChangeContactsBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
    }

    private void initView(){
        findViewById(R.id.contact_back_icon).setOnClickListener(this);
        findViewById(R.id.contact_sure_icon).setOnClickListener(this);
        findViewById(R.id.extend_layout).setOnClickListener(this);
        gourpTv = (TextView) findViewById(R.id.group_people);
        extendIv = (ImageView) findViewById(R.id.open_extend);
        listView = (ListView) findViewById(R.id.change_listview);
        adapter = new ChangeContactAdapter(this,list);
        listView.setAdapter(adapter);
        modelBut = (Button) findViewById(R.id.contactchange_account_btn);;
        modelBut.setOnClickListener(this);
        modelBut.setText(MyApplication.localPhone);
        editText = (EditText) findViewById(R.id.contactchange_edit);

        String gourpStr = getResources().getString(R.string.group_people);
        gourpStr = String.format(gourpStr,ContactsUtil.getInstance(this).getAllContacts().size());
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
            case R.id.extend_layout:{
                if(listView.getVisibility() == View.VISIBLE){
                    listView.setVisibility(View.GONE);
                }else {
                    listView.setVisibility(View.VISIBLE);
                    showContacts();
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

    private void showContacts(){
        List<ChangeContactsBean> list = ContactsUtil.getInstance(this).getAllContacts();
        adapter.setChangeList(list);
    }

    /**
     * 设置点击确认返回相应的信息
     */
    private void setActivityResultData(){
        String callStr = editText.getText().toString();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        List<String> list = adapter.getCheckedList();
        if(!callStr.isEmpty()){
            list.add(callStr);
        }
        bundle.putStringArrayList(Contants.PHONE_LIST_KEY, (ArrayList<String>) list);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
