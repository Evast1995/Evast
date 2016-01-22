package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-19.
 */
public class SearchAdapter extends BaseAdapter{
    private Context context;
    private List<SearchBean> list = new ArrayList<SearchBean>();

    public SearchAdapter(Context context,List<SearchBean> list){
        this.context = context;
        this.list = list;
    }

    /**
     * 对外提供一个list改变的接口
     * @param list
     */
    public void setListChange(List<SearchBean> list){
        this.list = list;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.imageHead = (ImageView) convertView.findViewById(R.id.search_head_image);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.search_nameTv);
            viewHolder.phoneTv = (TextView) convertView.findViewById(R.id.search_phoneTv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SearchBean searchBean = list.get(position);
        if(searchBean.getBitmap()!=null){
            viewHolder.imageHead.setImageBitmap(searchBean.getBitmap());
        }else if(searchBean!=null){
            viewHolder.imageHead.setImageResource(R.mipmap.conf_incoming_default_pic);
        }
        viewHolder.nameTv.setText(searchBean.getDisplayName());
        viewHolder.phoneTv.setText(searchBean.getPhoneNum());
        return convertView;
    }
    class ViewHolder{
        private TextView nameTv;
        private TextView phoneTv;
        private ImageView imageHead;
    }
}
