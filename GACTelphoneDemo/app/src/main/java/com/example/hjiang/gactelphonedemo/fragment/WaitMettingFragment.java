package com.example.hjiang.gactelphonedemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.activity.ScheduleDetailActivity;
import com.example.hjiang.gactelphonedemo.adapter.OpenMeetingAdapter;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;
import com.example.hjiang.gactelphonedemo.util.ContactsUtil;
import com.example.hjiang.gactelphonedemo.util.Contants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-2-4.
 */
public class WaitMettingFragment extends Fragment{
    private ListView listView;
    private Context context;
    private List<MeetingBean> list = new ArrayList<MeetingBean>();
    private OpenMeetingAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context  = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        list = ContactsUtil.getInstance(context).getMeetingsByState(0);
        adapter.changeList(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waitingmeeting,container,false);
        listView = (ListView) view.findViewById(R.id.openmeeting_list);
        initListView();
        return view;
    }


    /**
     * 初始化listview
     */
    private void initListView(){
        list = ContactsUtil.getInstance(context).getMeetingsByState(0);
        adapter = new OpenMeetingAdapter(context,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MeetingBean meetingBean = (MeetingBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent(context, ScheduleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Contants.MEETING_BEAN,meetingBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
