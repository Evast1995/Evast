package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;

/**
 * Created by hjiang on 16-1-11.
 */
public class PopupAdatper extends BaseAdapter{
    private Context context;
    private String[] arrays;
    public PopupAdatper(Context context){
        this.context = context;
        arrays =context.getResources().getStringArray(R.array.call_model);
    }

    @Override
    public int getCount() {
        return arrays.length;
    }

    @Override
    public Object getItem(int position) {
        return arrays[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.popup_layout_item,null);
            textView = (TextView) convertView.findViewById(R.id.popup_item_text);
            convertView.setTag(textView);
        }else {
            textView = (TextView) convertView.getTag();
        }
        textView.setText(arrays[position]);
        return convertView;
    }
}
