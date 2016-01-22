package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.base.module.call.account.CallAccount;
import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.weight.ImageOrTextMixed;

import java.util.List;

/**
 * Created by hjiang on 16-1-8.
 */
public class AccountAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private List<CallAccount> accountList;
    /** 被选择中的位置 默认为0*/
    private int selectedPosition = 0;
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
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.account_item_layout,null);
            viewHolder.accountLayout = (ImageOrTextMixed) convertView.findViewById(R.id.account_layout);
//            viewHolder.accountBody = (LinearLayout) convertView.findViewById(R.id.account_item_body);
//            viewHolder.accountLayout.setOnClickListener(this);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CallAccount callAccount = accountList.get(position);
        int spiUserId = callAccount.getLocalId();
        String localName = callAccount.getSipUserId();

        viewHolder.accountLayout.setTextOne(String.valueOf(spiUserId));
        viewHolder.accountLayout.setTextTwo(localName);
        viewHolder.accountLayout.setTag(position);
//
//        if(selectedPosition == position){
//            viewHolder.accountBody.setSelected(true);
//        }
        return convertView;
    }
    class ViewHolder{
        public ImageOrTextMixed accountLayout;
        public LinearLayout accountBody;
    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.account_layout){
//            int position  = (Integer)v.getTag();
//            selectedPosition = position;
//        }
    }
}
