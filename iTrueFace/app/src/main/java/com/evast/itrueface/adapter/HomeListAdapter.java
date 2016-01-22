package com.evast.itrueface.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.evast.itrueface.R;
import com.evast.itrueface.bean.home.ContextVo;
import com.evast.itrueface.bean.home.HomeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72963 on 2015/12/11.
 */
public class HomeListAdapter extends BaseAdapter{
    private Context context;
    private List<HomeVo> homeVos = new ArrayList<>();
    public HomeListAdapter(Context context,List<HomeVo> homeVos){
        this.context = context;
        this.homeVos = homeVos;
    }
    @Override
    public int getCount() {
        return homeVos.size();
    }

    @Override
    public Object getItem(int position) {
        return homeVos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeVo homeVo = homeVos.get(position);
        List<ContextVo> list = new ArrayList<>();
        ContextVo[] contextVos = homeVo.getContextVos();
        for(int i=0;i<contextVos.length;i++){
            list.add(contextVos[i]);
        }
//        HomeHorizontalAdapter adapter;
        RecycleViewAdapter adapter = null;
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item,null);
            viewHolder.topTv = (TextView) convertView.findViewById(R.id.top_name);
            viewHolder.recyclerView = (RecyclerView) convertView.findViewById(R.id.recylerview);
            adapter = new RecycleViewAdapter(context,list);
            viewHolder.recyclerView.setTag(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.recyclerView.setLayoutManager(linearLayoutManager);

//            viewHolder.horizontalListView = (HorizontalListView) convertView.findViewById(R.id.horizontal_list);
//            adapter = new HomeHorizontalAdapter(context,list);
//            viewHolder.horizontalListView.setTag(adapter);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.topTv.setText(homeVo.getTop());
        adapter = (RecycleViewAdapter) viewHolder.recyclerView.getTag();
        viewHolder.recyclerView.setAdapter(adapter);
//        adapter = (HomeHorizontalAdapter) viewHolder.horizontalListView.getTag();
//        viewHolder.horizontalListView.setAdapter(adapter);
        return convertView;
    }
    class ViewHolder{
        TextView topTv;
//        HorizontalListView horizontalListView;
        RecyclerView recyclerView;
    }
}
