package com.example.hjiang.gactelphonedemo.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.bean.ChangeHistoryBean;
import com.example.hjiang.gactelphonedemo.bean.SearchBean;
import com.example.hjiang.gactelphonedemo.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-2-1.
 */
public class ChangeHistoryAdapter extends BaseAdapter{
    private Context context;
    private List<ChangeHistoryBean> list;
    private Uri uri;
    private ContentResolver contentResolver;
    public ChangeHistoryAdapter(Context context ,List<ChangeHistoryBean> list){
        this.context = context;
        this.list = list;
        uri = ContactsContract.Data.CONTENT_URI;
        contentResolver = context.getContentResolver();
    }

    /**
     * 对外提供刷新数据的方法
     * @param list
     */
    public void changeData(List<ChangeHistoryBean> list){
        this.list = list;
    }

    /**
     * 对外提供获取选中电话号码的集合
     * @return
     */
    public List<String> getChangeList(){
        List<String> selectedList = new ArrayList<String>();
        for (int i=0;i<list.size();i++){
            ChangeHistoryBean changeHistoryBean = list.get(i);
            if(changeHistoryBean.getIsSelected() == true){
                selectedList.add(changeHistoryBean.getPhoneNumStr());
            }
        }
        return selectedList;
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
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.changhistory_item_layout,null);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.changehistory_name_text);
            viewHolder.phoneTv = (TextView) convertView.findViewById(R.id.change_history_phone_text);
            viewHolder.accountTv = (TextView) convertView.findViewById(R.id.change_history_account);
            viewHolder.headView = (ImageView) convertView.findViewById(R.id.head_image);
            viewHolder.typeView = (ImageView) convertView.findViewById(R.id.changehistory_image);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
            viewHolder.itemLayout = (LinearLayout) convertView.findViewById(R.id.item_layout);
            viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer position = (Integer) v.getTag();
                    list.get(position).setIsSelected(!list.get(position).getIsSelected());
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChangeHistoryBean changeHistoryBean = list.get(position);
        String phoneNumStr =changeHistoryBean.getPhoneNumStr();
        viewHolder.phoneTv.setText(phoneNumStr);
        if(changeHistoryBean.getNameStr() == null){
            getAllInfomation(changeHistoryBean,phoneNumStr);
        }
        viewHolder.nameTv.setText(changeHistoryBean.getNameStr().toString());
        String accountStr = context.getResources().getString(R.string.account);
        accountStr = String.format(accountStr,changeHistoryBean.getAccount()+1);
        viewHolder.accountTv.setText(accountStr);

        /** 设置头像*/
        Bitmap headBitmap = changeHistoryBean.getBitmap();
        if(headBitmap == null){
            Bitmap bitmap = ImageUtils.getBitmapByResId(R.mipmap.conf_incoming_default_pic,context);
            viewHolder.headView.setImageBitmap(bitmap);
        }else{
            viewHolder.headView.setImageBitmap(headBitmap);
        }

        /** 设置历史记录的电话类型*/
        viewHolder.typeView.setImageBitmap(setCallType(changeHistoryBean.getType()));
        viewHolder.checkBox.setSelected(changeHistoryBean.getIsSelected());

        viewHolder.itemLayout.setTag(position);
        return convertView;
    }
    class ViewHolder{
        private TextView nameTv;
        private TextView phoneTv;
        private TextView accountTv;
        private CheckBox checkBox;
        private ImageView headView;
        private ImageView typeView;
        private LinearLayout itemLayout;
    }

    /**
     * 设置历史记录的电话类型
     * @param type
     * @return
     */
    private Bitmap setCallType(int type){
        Bitmap bitmap;
        switch (type){
            case SearchBean.INCOMING_TYPE:{
                bitmap = ImageUtils.getBitmapByResId(R.mipmap.line_ringing_normal,context);
                break;
            }
            case SearchBean.OUTGOING_TYPE:{
                bitmap = ImageUtils.getBitmapByResId(R.mipmap.miss_call,context);
                break;
            }
            case SearchBean.MISSED_TYPE:{
                bitmap = ImageUtils.getBitmapByResId(R.mipmap.line_calling_normal,context);
                break;
            }
            default:{
                bitmap = ImageUtils.getBitmapByResId(R.mipmap.conf_incoming_default_pic, context);
            }
        }
        return bitmap;
    }

    /**
     * 补全所有信息(从data表中获取电话号码对应的相关联系人的信息)
     * @param changeHistoryBean
     * @param phoneNumStr
     */
    private void getAllInfomation(ChangeHistoryBean changeHistoryBean,String phoneNumStr){

        List<String> nameList = new ArrayList<String>();
        if(phoneNumStr.equals("")){
            nameList.add("");
            changeHistoryBean.setNameStr(nameList);
        }else if(phoneNumStr!=null){
            String nameWhereStr ="raw_contact_id in(select raw_contact_id from data where mimetype_id='5' and data1 = ?) and mimetype_id = '7'";
            String imgWhereStr = "raw_contact_id in(select raw_contact_id from data where mimetype_id='5' and data1 = ?) and mimetype_id = '10'";
            Cursor nameCursor = contentResolver.query(uri,new String[]{"data1"},nameWhereStr,new String[]{phoneNumStr},null);
            Cursor imgCursor = contentResolver.query(uri,new String[]{"data15"},imgWhereStr,new String[]{phoneNumStr},null);
            while(nameCursor.getCount()>0&&nameCursor.moveToNext()){
                nameList.add(nameCursor.getString(nameCursor.getColumnIndex("data1")));
            }

            while(imgCursor.getCount()>0&&imgCursor.moveToNext()){
                byte[] bytes = imgCursor.getBlob(imgCursor.getColumnIndex("data15"));
                Bitmap bitmap = ImageUtils.getBitmapByBytes(bytes);
                if (bitmap!=null) {
                    changeHistoryBean.setBitmap(bitmap);
                }
            }

            if(nameList.size()>0) {
                changeHistoryBean.setNameStr(nameList);
            }else{
                nameList.add(phoneNumStr);
                changeHistoryBean.setNameStr(nameList);
            }
            nameCursor.close();
            imgCursor.close();
        }
    }
}
