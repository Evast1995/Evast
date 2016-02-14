package com.example.hjiang.gactelphonedemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.adapter.FragmentAdapter;
import com.example.hjiang.gactelphonedemo.fragment.HistoryMeetingFragment;
import com.example.hjiang.gactelphonedemo.fragment.WaitMettingFragment;
import com.example.hjiang.gactelphonedemo.weight.TabLayout;


/**
 * 预约会议
 * Created by hjiang on 16-1-27.
 */
public class AppointMentActivity extends BaseActivity implements View.OnClickListener{
    private TabLayout tabLayout;
    private HistoryMeetingFragment historyMeetingFragment;
    private WaitMettingFragment waitMettingFragment;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        initView();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof HistoryMeetingFragment){
            historyMeetingFragment = (HistoryMeetingFragment) fragment;
        }else if(fragment instanceof WaitMettingFragment){
            waitMettingFragment = (WaitMettingFragment) fragment;
        }
        super.onAttachFragment(fragment);
    }

    private void initView(){
        initTitle();
        initTabLayout();
        initViewPager();
    }

    /**
     * 初始化标题
     */
    private void initTitle(){
        findViewById(R.id.add_schedule_icon).setOnClickListener(this);
        findViewById(R.id.back_icon).setOnClickListener(this);
    }

    /**
     * 初始化标签栏
     */
    private void initTabLayout(){
        tabLayout = (TabLayout) findViewById(R.id.my_tablayout);
        tabLayout.setTabListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                viewPager.setCurrentItem(position,true);
            }
        });
        tabLayout.addTabs(R.array.schedule);
    }

    /**
     * 设置fragment　viewpage切换
     */
    private void initViewPager(){
        viewPager = (ViewPager) findViewById(R.id.schedule_viewpage);
        FragmentManager fm = getSupportFragmentManager();
        if(waitMettingFragment == null){waitMettingFragment = new WaitMettingFragment();}
        if(historyMeetingFragment == null){historyMeetingFragment = new HistoryMeetingFragment();}
        Fragment[] fragments = new Fragment[]{waitMettingFragment,historyMeetingFragment};
        viewPager.setAdapter(new FragmentAdapter(fm, fragments));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setCurrentTab(position+1,positionOffset);
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_schedule_icon:{
                startActivity(new Intent(this,AddMeetingActivity.class));
                break;
            }
            case R.id.back_icon:{
                finish();
                break;
            }
        }
    }
}
