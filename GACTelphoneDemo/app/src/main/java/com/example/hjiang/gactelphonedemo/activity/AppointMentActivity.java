package com.example.hjiang.gactelphonedemo.activity;

import android.os.Bundle;
import android.view.View;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.weight.TabLayout;


/**
 * 预约会议
 * Created by hjiang on 16-1-27.
 */
public class AppointMentActivity extends BaseActivity implements View.OnClickListener{
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        initView();
    }


    private void initView(){
        tabLayout = (TabLayout) findViewById(R.id.my_tablayout);
        tabLayout.addTabs(R.array.alert_state_four);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
