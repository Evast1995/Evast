package com.example.hjiang.gactelphonedemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.base.module.call.CallManager;
import com.base.module.call.account.CallAccount;
import com.example.hjiang.gactelphonedemo.MyApplication;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.activity.AppointMentActivity;
import com.example.hjiang.gactelphonedemo.adapter.PopupAdatper;
import com.example.hjiang.gactelphonedemo.util.AccountUtils;
import com.example.hjiang.gactelphonedemo.weight.ImageOrTextMixed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-8.
 */
public class ModelFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button modelBtn;
    private Context context;
    /** 将当前话机所有本地账号Ｉｄ存储起来*/
    private List<Integer> list = new ArrayList<Integer>();

    private LinearLayout accountLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_model, container, false);
        modelBtn = (Button) view.findViewById(R.id.model_btn);
        accountLayout = (LinearLayout) view.findViewById(R.id.account_layout);
        modelBtn.setOnClickListener(this);
        view.findViewById(R.id.schedule_layout).setOnClickListener(this);
        setLineData();
        return view;
    }

    /**
     * 设置账号显示(动态添加账号控件)
     */
    private void setLineData() {
        List<CallAccount> accountList = AccountUtils.getInstance(context).getAllUsableCallAccounts();
        for (int i = 0; i < accountList.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,1);
            ImageOrTextMixed imageOrTextMixed = new ImageOrTextMixed(context);
            imageOrTextMixed.setLayoutParams(layoutParams);
            CallAccount callAccount = accountList.get(i);
            int spiUserId = callAccount.getLocalId();
            String localName = callAccount.getSipUserId();
            imageOrTextMixed.setTextOne(String.valueOf(spiUserId));
            imageOrTextMixed.setTextTwo(localName);
            imageOrTextMixed.setImageBackground(R.mipmap.account_icon);
            imageOrTextMixed.setBackgroundResource(R.drawable.select_account_bg);
            imageOrTextMixed.setPadding(2, 2, 2, 2);
            imageOrTextMixed.setId(callAccount.getLocalId());
            imageOrTextMixed.setTag(callAccount.getSipUserId());
            list.add(callAccount.getLocalId());
            imageOrTextMixed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.localId = v.getId();
                    MyApplication.localPhone = (String) v.getTag();
                    setAllNotSelect();
                    v.setSelected(!v.isSelected());
                }
            });
            accountLayout.addView(imageOrTextMixed);
        }
        /** 设置账号默认位第一个账号选中*/
        view.findViewById(list.get(0)).setSelected(true);
        MyApplication.localId = view.findViewById(list.get(0)).getId();
        MyApplication.localPhone = (String) view.findViewById(list.get(0)).getTag();
    }

    /**
     * 将所有账号设置为不选中
     */
    private void setAllNotSelect(){
        for(int i=0;i<list.size();i++){
            view.findViewById(list.get(i)).setSelected(false);
        }
    }

    /**
     * s设置模式按钮点击事件
     */
    public void showPopupWindow(){
        View view = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);
        ListView listView = (ListView) view.findViewById(R.id.popup_list);
        listView.setAdapter(new PopupAdatper(context));
        final PopupWindow popup=new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(true);
        popup.update();
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(modelBtn);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    /** 选择呼叫模式*/
                    case 0: {
                        popup.dismiss();
                        MyApplication.callModel = CallManager.CALLMODE_SIP;
                        modelBtn.setText(R.string.call);
                        break;
                    }
                    /** 选择Paging模式*/
                    case 1: {
                        popup.dismiss();
                        MyApplication.callModel = CallManager.CALLMODE_PAGING;
                        modelBtn.setText(R.string.paging);
                        break;
                    }
                    /** 选着IP呼叫模式*/
                    case 2: {
                        popup.dismiss();
                        MyApplication.callModel = CallManager.CALLMODE_IP;
                        modelBtn.setText(R.string.ip_call);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.model_btn:{
                showPopupWindow();
                break;
            }
            /** 打开预约会议界面*/
            case R.id.schedule_layout:{
                Intent intent = new Intent(context, AppointMentActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

}
