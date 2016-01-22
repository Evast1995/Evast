package com.evast.itrueface.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.evast.itrueface.R;
import com.evast.itrueface.bean.home.ContextVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72963 on 2015/12/13.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<MyHolder>{
    private Context context;
    private List<ContextVo> list = new ArrayList<>();
    public RecycleViewAdapter(Context context,List<ContextVo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_list_item_item,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, int i) {
        ContextVo contextVo = list.get(i);
        myHolder.teacherTv.setText(context.getResources().getString(R.string.speaker)+contextVo.getTeacherName());
        myHolder.courseTv.setText(context.getResources().getString(R.string.course_name)+contextVo.getCourseName());
        myHolder.imageView.setImageResource(R.mipmap.home_test_two);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class MyHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView courseTv;
    public TextView teacherTv;
    public MyHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.home_item_image);
        courseTv = (TextView) itemView.findViewById(R.id.home_item_course);
        teacherTv = (TextView) itemView.findViewById(R.id.home_item_teacher);
    }
}
