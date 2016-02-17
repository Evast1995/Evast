package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;
import com.example.hjiang.gactelphonedemo.bean.MemberBean;
import com.example.hjiang.gactelphonedemo.util.CallUtils;
import com.example.hjiang.gactelphonedemo.util.ContactsUtil;
import com.example.hjiang.gactelphonedemo.util.OtherUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-2-4.
 */
public class OpenMeetingAdapter extends BaseAdapter{
    private Context context;
    List<MeetingBean> list = new ArrayList<MeetingBean>();
    public OpenMeetingAdapter(Context context,List<MeetingBean> list){
        this.list = list;
        this.context = context;
    }

    /**
     * 对外提供list变更操作
     */
    public void changeList(List<MeetingBean> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.openmeeting_layout_item,null);
            viewHolder.nameTv = (TextView)convertView.findViewById(R.id.openmeeting_name);
            viewHolder.memberTv = (TextView) convertView.findViewById(R.id.member_layout);
            viewHolder.timeTv = (TextView) convertView.findViewById(R.id.meeting_time);
            viewHolder.stateTv = (TextView) convertView.findViewById(R.id.meeting_state);
            viewHolder.isCycleIv = (ImageView) convertView.findViewById(R.id.loop_icon);
            viewHolder.startBtn = (Button) convertView.findViewById(R.id.meeting_startbtn);
            viewHolder.startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MeetingBean meetingBean = (MeetingBean) v.getTag();
                    CallUtils.getInstance(context).setScheduleInf(meetingBean);
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MeetingBean meetingBean = list.get(position);
        viewHolder.nameTv.setText(meetingBean.getName());
        viewHolder.memberTv.setText(getMemberNameStr(meetingBean));
        viewHolder.timeTv.setText(getTimeStr(meetingBean));
        if(meetingBean.getCycleTime() == 0){
            viewHolder.isCycleIv.setVisibility(View.GONE);
        }else{
            viewHolder.isCycleIv.setVisibility(View.VISIBLE);
        }
        viewHolder.startBtn.setTag(meetingBean);
        return convertView;
    }

    /**
     * 获取会议成员电话号码字符串
     */
    private String getMemberNameStr(MeetingBean meetingBean){
        List<MemberBean> list = meetingBean.getMemberList();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<list.size();i++){
            String phoneNumStr = list.get(i).getPhone();
            String displayNameStr = ContactsUtil.getInstance(context).getDisplayNameByPhone(phoneNumStr).get(0);
            stringBuilder.append(displayNameStr+",");
        }
        stringBuilder = stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
        return stringBuilder.toString();
    }

    /**
     * 获取时间字符串
     * @param meetingBean
     * @return
     */
    private String getTimeStr(MeetingBean meetingBean){
        long startTime = meetingBean.getStartTime();
        return OtherUtils.getTimeStrByTimeStamp(startTime,context,meetingBean.getDuration());
    }

    class ViewHolder{
        TextView nameTv;
        TextView memberTv;
        TextView timeTv;
        TextView stateTv;
        ImageView isCycleIv;
        Button startBtn;
    }
}
