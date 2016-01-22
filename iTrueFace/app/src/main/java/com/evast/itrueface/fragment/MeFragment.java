package com.evast.itrueface.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evast.evastcore.core.BaseFragment;
import com.evast.itrueface.R;

/**
 * Created by 72963 on 2015/12/10.
 */
public class MeFragment extends BaseFragment{

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,container,false);
    }

    @Override
    protected void init(View rootView) {

    }
}
