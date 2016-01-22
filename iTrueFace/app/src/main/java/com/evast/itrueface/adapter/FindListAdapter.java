package com.evast.itrueface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.evast.itrueface.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72963 on 2015/12/11.
 */
public class FindListAdapter extends BaseAdapter{
    private Context context;
    private List<String> list = new ArrayList<>();
    public FindListAdapter(Context context,List<String> list){
        this.context=context;
        this.list =list;
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
        ImageView imageView;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.find_list_item,null);
            imageView = (ImageView) convertView.findViewById(R.id.find_item_image);
            convertView.setTag(imageView);
        }else{
            imageView = (ImageView) convertView.getTag();
        }
        imageView.setImageResource(R.mipmap.home_test_one);
        return convertView;
    }
}
