package com.evast.itrueface.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.evast.evastcore.core.BaseFragment;
import com.evast.itrueface.R;
import com.evast.itrueface.adapter.FindListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72963 on 2015/12/10.
 */
public class FindFragment extends BaseFragment{

    private List<String> list = new ArrayList<>();
    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find,container,false);
    }

    @Override
    protected void init(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.find_list);
        initData();
        listView.setAdapter(new FindListAdapter(getContext(),list));
    }
    private void initData(){
        list.add("test1");
        list.add("test1");
        list.add("test1");
        list.add("test1");
    }

}
