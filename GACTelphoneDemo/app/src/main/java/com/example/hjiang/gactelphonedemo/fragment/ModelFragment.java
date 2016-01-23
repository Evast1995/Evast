package com.example.hjiang.gactelphonedemo.fragment;

import android.content.Context;
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
import com.example.hjiang.gactelphonedemo.adapter.AccountAdapter;
import com.example.hjiang.gactelphonedemo.adapter.PopupAdatper;
import com.example.hjiang.gactelphonedemo.util.AccountUtils;

import java.util.List;

/**
 * Created by hjiang on 16-1-8.
 */
public class ModelFragment extends Fragment implements View.OnClickListener{

    private Button modelBtn;
    private ListView accountLv;
    private Context context;
    private AccountAdapter accountAdapter;
    private PopupWindow popupWindow;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model, container, false);
        modelBtn = (Button) view.findViewById(R.id.model_btn);
        accountLv = (ListView) view.findViewById(R.id.account_list);
        modelBtn.setOnClickListener(this);
        setLineSv();
        return view;
    }

    /**
     * 设置显示所有线路
     */
    private void setLineSv(){
        List<CallAccount> accountList = AccountUtils.getInstance(context).getAllUsableCallAccounts();
        accountAdapter = new AccountAdapter(context,accountList);
        accountLv.setAdapter(accountAdapter);
        accountLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                CallAccount callAccount = (CallAccount) accountAdapter.getItem(position);
                MyApplication.localId = callAccount.getLocalId();
                MyApplication.localPhone = callAccount.getSipUserId();
            }
        });


//        ImageOrTextMixed imageOrTextMixed = (ImageOrTextMixed) accountLv.getChildAt(0);
//        imageOrTextMixed.setSelected(true);
    }


    /**
     * 设置模式按钮点击事件
     */
    private void setModelBtnOnClick(){
        View view = LayoutInflater.from(context).inflate(R.layout.popup_layout,null);
        ListView listView = (ListView) view.findViewById(R.id.popup_list);
        listView.setAdapter(new PopupAdatper(context));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    /** 选择呼叫模式*/
                    case 0: {
                        setPopupDismissed();
                        MyApplication.callModel = CallManager.CALLMODE_SIP;
                        modelBtn.setText(R.string.call);
                        break;
                    }
                    /** 选择Paging模式*/
                    case 1: {
                        setPopupDismissed();
                        MyApplication.callModel = CallManager.CALLMODE_PAGING;
                        modelBtn.setText(R.string.paging);
                        break;
                    }
                    /** 选着IP呼叫模式*/
                    case 2: {
                        setPopupDismissed();
                        MyApplication.callModel = CallManager.CALLMODE_IP;
                        modelBtn.setText(R.string.ip_call);
                        break;
                    }
                }
            }
        });
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,false);
        /** 设置popupwindow点击外面消失*/
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(modelBtn);
    }

    /**
     * 设置popupWindow的消失
     */
    private void setPopupDismissed(){
        if(popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.model_btn:{
                setModelBtnOnClick();
                break;
            }
        }
    }

}
