package com.evast.itrueface.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 72963 on 2015/12/10.
 */
public class NavigationAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragment;
    public NavigationAdapter(FragmentManager fm,List<Fragment> fragment){
        super(fm);
        this.fragment=fragment;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragment.size();
    }
}
