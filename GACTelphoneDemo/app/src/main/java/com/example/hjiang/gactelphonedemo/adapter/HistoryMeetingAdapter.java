package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;
import com.example.hjiang.gactelphonedemo.util.OtherUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-2-14.
 */
public class HistoryMeetingAdapter extends BaseAdapter{
    private Context context;
    private List<MeetingBean> list = new ArrayList<MeetingBean>();
    public HistoryMeetingAdapter(Context context,List<MeetingBean> list){
        this.context = context;
        this.list = list;
    }


    /**
     * 对外提供数据变更操作
     * @param list
     */
    public void setListChange(List<MeetingBean> list){
        this.list = list;
        notifyDataSetChanged();
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.historymeeting_item_layout,null);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.meeting_name_tv);
            viewHolder.startTimeTv = (TextView) convertView.findViewById(R.id.meeting_time_tv);
            viewHolder.stateTv = (TextView) convertView.findViewById(R.id.meeting_state_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MeetingBean meetingBean = list.get(position);
        /** 开始时间*/
        String startTime = OtherUtils.getTimeStrByStamp(meetingBean.getStartTime());
        viewHolder.startTimeTv.setText(context.getString(R.string.starttime)+" "+startTime);
        /** 会议状态*/
        int state = meetingBean.getState();
        if(state == 2){
            viewHolder.stateTv.setText(context.getString(R.string.miss_meeting));
            viewHolder.stateTv.setTextColor(context.getResources().getColor(R.color.red));
        }else if(state == 1){
            viewHolder.stateTv.setText(context.getString(R.string.close_meeting));
            viewHolder.stateTv.setTextColor(context.getResources().getColor(R.color.blue));
        }
        /** 会议名称*/
        viewHolder.nameTv.setText(meetingBean.getName());
        return convertView;
    }
    class ViewHolder{
        TextView nameTv;
        TextView startTimeTv;
        TextView stateTv;
    }
}
