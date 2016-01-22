package com.evast.evastcore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.evast.evastcore.R;


/**
 * 主题Gallery的适配器
 * Created by 72963 on 2015/10/20.
 */
public class ThemeAdapter extends BaseAdapter {

    private Context context;
    private int[] data;
    private int witchCheck = 0;
    public ThemeAdapter(Context context, int[] data, int witchCheck){
        this.context = context;
        this.data = data;
        this.witchCheck = witchCheck;
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.dialog_theme_grid_item, null);
        ImageView circleImageView = (ImageView) convertView.findViewById(R.id.grid_item_theme_color);
        ImageView rightImageView = (ImageView) convertView.findViewById(R.id.grid_item_theme_right);
        circleImageView.setImageResource(data[position]);
        if(position == witchCheck){
            rightImageView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
