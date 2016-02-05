package com.example.hjiang.gactelphonedemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by hjiang on 16-1-8.
 */
public class BaseActivity extends ActionBarActivity{

    public Fragment mFragmentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 切换Fragment
     * @param resId 需要将Fragment放入那个容器中的id
     * @param to 需要切换的Fragment对象（v4包下的Fragment）
     */
    protected void switchFragmentContent(int resId,Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(mFragmentContent!=null){
            if (mFragmentContent != to) {
                if (!to.isAdded()) { // 先判断是否被add过
                    transaction.hide(mFragmentContent).add(resId, to); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mFragmentContent).show(to); // 隐藏当前的fragment，显示下一个
                }
            }
        }else{
            transaction.add(resId, to);
        }
        /**
         * Can not perform this action after onSaveInstanceState
         * onSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
         * 再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()就行了，其效果是一样的。
         */
        transaction.commitAllowingStateLoss();
        mFragmentContent = to;
    }
}
