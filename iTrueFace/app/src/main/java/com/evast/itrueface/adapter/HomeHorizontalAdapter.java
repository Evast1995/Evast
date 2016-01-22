package com.evast.itrueface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evast.itrueface.R;
import com.evast.itrueface.bean.home.ContextVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72963 on 2015/12/11.
 */
public class HomeHorizontalAdapter extends BaseAdapter{
    private Context context;
    private List<ContextVo> list = new ArrayList<>();
    public HomeHorizontalAdapter(Context context,List<ContextVo> list){
        this.context = context;
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
        ContextVo contextVo = list.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.home_item_image);
            viewHolder.courseTv = (TextView) convertView.findViewById(R.id.home_item_course);
            viewHolder.teacherTv = (TextView) convertView.findViewById(R.id.home_item_teacher);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.teacherTv.setText(context.getResources().getString(R.string.speaker)+contextVo.getTeacherName());
        viewHolder.courseTv.setText(context.getResources().getString(R.string.course_name)+contextVo.getCourseName());
        viewHolder.imageView.setImageResource(R.mipmap.home_test_two);
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView courseTv;
        TextView teacherTv;
    }
}
