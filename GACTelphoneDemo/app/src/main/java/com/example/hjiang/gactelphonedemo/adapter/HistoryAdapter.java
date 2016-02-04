package com.example.hjiang.gactelphonedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.ChangeContactsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-29.
 */
public class HistoryAdapter extends BaseAdapter{
    private Context context;
    private List<ChangeContactsBean> list = new ArrayList<ChangeContactsBean>();
    public HistoryAdapter(Context context,List<ChangeContactsBean> list){
        this.list =list;
        this.context = context;
    }

    /** 对外提供一个获取选中的item中号码集合的方法*/
    public List<String> getCheckedList(){
        List<String> checkedList = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getIsChecked()){
                checkedList.add(list.get(i).getPhoneStr().get(0));
            }
        }
        return checkedList;
    }

    /**
     * 对外提供一个改变数据的方法
     * @param list
     */
    public void setChangeList(List<ChangeContactsBean> list){
        this.list =list;
        notifyDataSetChanged();
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
        ChangeContactsBean changeContactsBean = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.changcontact_item_layout,null);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.changecontact_name_text);
            viewHolder.phoneTv = (TextView) convertView.findViewById(R.id.change_contact_phone_text);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
            viewHolder.headIv = (ImageView) convertView.findViewById(R.id.head_image);
            viewHolder.itemLayout = (LinearLayout) convertView.findViewById(R.id.item_layout);
            viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer position = (Integer) v.getTag();
                    list.get(position).setIsChecked(!list.get(position).getIsChecked());
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap =changeContactsBean.getBitmap();
        if(bitmap!=null) {
            viewHolder.headIv.setImageBitmap(bitmap);
        }else {
            viewHolder.headIv.setImageResource(R.mipmap.conf_incoming_default_pic);
        }
        viewHolder.itemLayout.setTag(position);
        viewHolder.checkBox.setSelected(changeContactsBean.getIsChecked());
        viewHolder.nameTv.setText(changeContactsBean.getNameStr());
        viewHolder.phoneTv.setText(changeContactsBean.getPhoneStr().toString());
        return convertView;
    }
    class ViewHolder{
        TextView nameTv;
        TextView phoneTv;
        ImageView headIv;
        CheckBox checkBox;
        LinearLayout itemLayout;
    }
}
