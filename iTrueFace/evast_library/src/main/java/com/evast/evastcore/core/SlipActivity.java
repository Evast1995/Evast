package com.evast.evastcore.core;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.evast.evastcore.R;
import com.evast.evastcore.adapter.ThemeAdapter;
import com.evast.evastcore.util.other.SharedPreferenceUtil;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by 72963 on 2015/12/2.
 */
public abstract class SlipActivity extends BaseActivity{

    public static final int[] THEMECOLOR = {R.drawable.theme_blue_round, R.drawable.theme_brown_round, R.drawable.theme_red_round,
            R.drawable.theme_blue_grey_round, R.drawable.theme_yellow_round, R.drawable.theme_deep_purple_round,
            R.drawable.theme_pink_round, R.drawable.theme_green_round};
    public static final String THEME_COLOR_KEY = "theme_color";//保存主题的key值
    private ResideMenu resideMenu;
    private SlipActivity mContext;
    /** 侧滑菜单的变更监听事件*/
    private ResideMenu.OnMenuListener menuListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setResideMenu();
        setBackGround((Integer) SharedPreferenceUtil.get(this, THEME_COLOR_KEY, 0));
    }


    private void setResideMenu(){
        /** 创建侧滑菜单变量*/
        resideMenu = new ResideMenu(this);
        /** 设置3D显示*/
        resideMenu.setUse3D(true);
        /** 设置背景*/
        resideMenu.setBackground(R.mipmap.menu_background);

        resideMenu.attachToActivity(this);

        if(menuListener != null) {
            /** 设置侧滑菜单的变更监听事件*/
            resideMenu.setMenuListener(menuListener);
        }
//        /** 创建侧滑菜单的menuItem*/
//        ResideMenuItem homeItem = new ResideMenuItem(this, R.mipmap.icon_home, "主页");
//        /** 将menuItem放在哪一边*/
//        resideMenu.addMenuItem(homeItem, ResideMenu.DIRECTION_LEFT);

        /** 侧滑菜单滑动幅度（0.0f-1.0f）*/
        resideMenu.setScaleValue(0.8f);
//        /** 向右边添加一个View视图*/
//        View view = LayoutInflater.from(this).inflate(R.layout.test_layout,null);
//        resideMenu.addRightMenuView(view);
    }


    /**
     * 设置主题切换
     */
    protected void setChanegeStyle(final Context context){
        ResideMenuItem themeItem = new ResideMenuItem(this,R.mipmap.icon_home,R.string.theme_name);
        resideMenu.addMenuItem(themeItem,ResideMenu.DIRECTION_RIGHT);
        themeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(context);
            }
        });
    }

    /**
     * 设置主题背景
     * @param position
     */
    private void setBackGround(int position) {
        switch (position) {
            case 0:
                resideMenu.setBackground(R.mipmap.blue_background);
                break;
            case 1:
                resideMenu.setBackground(R.mipmap.brown_background);
                break;
            case 2:
                resideMenu.setBackground(R.mipmap.red_background);
                break;
            case 3:
                resideMenu.setBackground(R.mipmap.gray_background);
                break;
            case 4:
                resideMenu.setBackground(R.mipmap.yellow_background);
                break;
            case 5:
                resideMenu.setBackground(R.mipmap.purse_background);
                break;
            case 6:
                resideMenu.setBackground(R.mipmap.red_background);
                break;
            case 7:
                resideMenu.setBackground(R.mipmap.grenn_background);
                break;
            default:
                resideMenu.setBackground(R.mipmap.blue_background);
                break;
        }
    }

        /**
         * 设置切换主题
         */
    public void setDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.theme_change);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_theme,null);
        GridView gridView = (GridView) view.findViewById(R.id.theme_gridview);
        int witchChange = (int) SharedPreferenceUtil.get(this, THEME_COLOR_KEY, 0);
        final ThemeAdapter adapter = new ThemeAdapter(this,THEMECOLOR,witchChange);
        gridView.setAdapter(adapter);
        builder.setView(view);
        final Dialog dialog = builder.show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//选择主题的监听事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.findViewById(R.id.grid_item_theme_right).setVisibility(View.VISIBLE);
                int witchCheck = (int) SharedPreferenceUtil.get(SlipActivity.this, THEME_COLOR_KEY, 0);
                dialog.dismiss();
                if (witchCheck != position) {
                    SharedPreferenceUtil.put(SlipActivity.this, THEME_COLOR_KEY, position);//保存主题
                    finish();
                    startActivity(new Intent(context, context.getClass()));//重启Actity
                }
            }
        });
    }
    /**将触摸事件分发给侧滑菜单处理*/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    /**
     * 设置侧滑菜单监听事件的回掉接口
     * @param menuListener
     */
    protected void setResideMenuListener(ResideMenu.OnMenuListener menuListener){
        this.menuListener = menuListener;
    }

    /**
     * 添加侧滑菜单的菜单项
     * @param resideMenuItem 侧滑菜单的侧滑菜单项
     * @param menuDirection 侧滑菜单菜单项的方向 左还是右（例如 ResideMenu.DIRECTION_LEFT）
     */
    protected void addMenuItem(ResideMenuItem resideMenuItem,int menuDirection){
        resideMenu.addMenuItem(resideMenuItem,menuDirection);
    }

    /**
     * 添加侧滑菜单视图
     * @param view 侧滑菜单视图
     * @param menuDirection 侧滑菜单菜单项的方向 左还是右（例如 ResideMenu.DIRECTION_LEFT）
     */
    protected void addMenuView(View view,int menuDirection){
        if(menuDirection == ResideMenu.DIRECTION_LEFT){
            resideMenu.addLeftMenuView(view);
        }else if(menuDirection == ResideMenu.DIRECTION_RIGHT){
            resideMenu.addRightMenuView(view);
        }
    }



}
