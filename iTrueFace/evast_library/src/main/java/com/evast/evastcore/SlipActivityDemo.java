package com.evast.evastcore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.evast.evastcore.core.BaseActivity;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by 72963 on 2015/11/16.
 */
public class SlipActivityDemo extends BaseActivity {
    private ResideMenu resideMenu;
    private SlipActivityDemo mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_slip);
        mContext = this;
    }

    @Override
    protected void init() {
        setResideMenu();
    }

    private void setResideMenu(){
        /** 创建侧滑菜单变量*/
        resideMenu = new ResideMenu(this);
        /** 设置3D显示*/
        resideMenu.setUse3D(true);
        /** 设置背景*/
        resideMenu.setBackground(R.mipmap.menu_background);

        resideMenu.attachToActivity(this);
        /** 设置侧滑菜单的变更监听事件*/
        resideMenu.setMenuListener(menuListener);
        /** 创建侧滑菜单的menuItem*/
        ResideMenuItem homeItem = new ResideMenuItem(this, R.mipmap.icon_home, "主页");
        /** 侧滑菜单滑动幅度（0.0f-1.0f）*/
        resideMenu.setScaleValue(0.8f);
        /** 将menuItem放在哪一边*/
        resideMenu.addMenuItem(homeItem, ResideMenu.DIRECTION_LEFT);
        /** 向右边添加一个View视图*/
        View view = LayoutInflater.from(this).inflate(R.layout.test_layout,null);
        resideMenu.addRightMenuView(view);
    }
    /** 侧滑菜单的变更监听事件*/
    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    /** */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

}


