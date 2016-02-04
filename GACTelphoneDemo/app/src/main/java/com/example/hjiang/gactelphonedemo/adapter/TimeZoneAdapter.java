package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by hjiang on 16-1-29.
 */
public class TimeZoneAdapter extends ArrayAdapter<String>{
    
    public TimeZoneAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
