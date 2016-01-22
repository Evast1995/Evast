package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-7.
 * 拨号盘的适配器
 */
public class DialsAdapter extends BaseAdapter{
    private List<String> list = new ArrayList<String>();
    private Context context;
    public DialsAdapter(Context context,List<String> list){
        this.context =context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView keyBtn;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.dials_item_layout, null);
            keyBtn = (TextView) convertView.findViewById(R.id.dails_btn);
            convertView.setTag(keyBtn);
        }else{
            keyBtn = (TextView) convertView.getTag();
        }
        keyBtn.setText(list.get(position));
        return convertView;
    }
}
