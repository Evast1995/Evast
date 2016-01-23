package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.base.module.call.account.CallAccount;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.weight.ImageOrTextMixed;

import java.util.List;

/**
 * Created by hjiang on 16-1-8.
 */
public class AccountAdapter extends BaseAdapter{
    private Context context;
    private List<CallAccount> accountList;
    public AccountAdapter(Context context, List<CallAccount> accountList){
        this.context =context;
        this.accountList =accountList;
    }
    @Override
    public int getCount() {
        return accountList.size();
    }

    @Override
    public Object getItem(int position) {
        return accountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.account_item_layout,null);
        }
        CallAccount callAccount = accountList.get(position);
        int spiUserId = callAccount.getLocalId();
        String localName = callAccount.getSipUserId();
        ((ImageOrTextMixed) convertView).setTextOne(String.valueOf(spiUserId));
        ((ImageOrTextMixed) convertView).setTextTwo(localName);

        if(position == 0){
            convertView.setSelected(true);
        }
        return convertView;
    }



}
