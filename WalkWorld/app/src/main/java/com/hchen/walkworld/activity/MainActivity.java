package com.hchen.walkworld.activity;

import android.os.Bundle;

import com.hchen.walkworld.R;
import com.hchen.walkworld.weight.NavigationBar;

public class MainActivity extends BaseActivity {

    private NavigationBar navigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        navigationBar = (NavigationBar) findViewById(R.id.navigationBar);
        initNavigationBar();
    }

    /**
     * 初始化底部导航条
     */
    private void initNavigationBar(){
    }

}
