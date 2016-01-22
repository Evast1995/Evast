package com.evast.itrueface.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.evast.evastcore.core.BaseFragment;
import com.evast.evastcore.util.net.RequestNet;
import com.evast.evastcore.util.other.L;
import com.evast.itrueface.weight.ImageScorll;
import com.evast.itrueface.R;
import com.evast.itrueface.adapter.HomeListAdapter;
import com.evast.itrueface.bean.home.ContextVo;
import com.evast.itrueface.bean.home.HomeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72963 on 2015/12/10.
 */
public class HomeFragment extends BaseFragment{
    private int[] imageIds=new int[]{R.mipmap.home_test_one,R.mipmap.home_test_two,R.mipmap.home_test_three};
    private ListView listView;
    private List<HomeVo> list = new ArrayList<>();
    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    protected void init(View rootView) {
        RelativeLayout imageScorllContainer = (RelativeLayout) rootView.findViewById(R.id.image_scroll_container);
        ImageScorll  imageScorll = new ImageScorll(getContext(),imageIds);
        imageScorll.setLayoutParams(new ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
        imageScorllContainer.addView(imageScorll);
        listView = (ListView) rootView.findViewById(R.id.home_list);
        initData();
        setListData();
    }
    private void initData(){
        new RequestNet(getContext()){

        }.get("http://flash.weather.com.cn/wmaps/xml/china.xml", null, new RequestNet.ResponseResult() {
            @Override
            public void successful(String response) {
                L.i(response);
            }

            @Override
            public void exception() {
                L.i("error");
            }
        });
        HomeVo homeVo1 = new HomeVo();
        homeVo1.setTop("热门Top10");
        ContextVo contextVo1 = new ContextVo();
        contextVo1.setCourseName("吉他");
        contextVo1.setTeacherName("Learn");

        ContextVo contextVo2 = new ContextVo();
        contextVo2.setCourseName("足球");
        contextVo2.setTeacherName("Jeason");
        homeVo1.setContextVos(new ContextVo[]{contextVo1, contextVo2});
        list.add(homeVo1);

        HomeVo homeVo2 = new HomeVo();
        homeVo2.setTop("兴趣Top10");
        ContextVo contextVo12 = new ContextVo();
        contextVo12.setCourseName("吉他");
        contextVo12.setTeacherName("Learn");

        ContextVo contextVo22 = new ContextVo();
        contextVo22.setCourseName("足球");
        contextVo22.setTeacherName("Jeason");
        homeVo2.setContextVos(new ContextVo[]{contextVo12, contextVo22});
        list.add(homeVo2);

    }
    private void setListData(){
        HomeListAdapter adapter = new HomeListAdapter(getContext(),list);
        listView.setAdapter(adapter);
    }
}
