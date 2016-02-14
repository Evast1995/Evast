package com.example.hjiang.gactelphonedemo.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hjiang.gactelphonedemo.R;
import com.example.hjiang.gactelphonedemo.activity.MainActivity;
import com.example.hjiang.gactelphonedemo.adapter.SearchAdapter;
import com.example.hjiang.gactelphonedemo.bean.SearchBean;
import com.example.hjiang.gactelphonedemo.util.ContactsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-19.
 */
public class SearchFragment extends Fragment{
    private Context context;
    private ListView listView;
    private SearchAdapter adapter;
    private TextView phoneTv;
    private LinearLayout headLayout;
    private TextView searchPhoneTv;
    private  List<SearchBean> list = new ArrayList<SearchBean>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        adapter = new SearchAdapter(context,list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgent_search,container,false);
        phoneTv = (TextView) view.findViewById(R.id.search_phoneTv);
        searchPhoneTv = (TextView) view.findViewById(R.id.search_phoneTv);
        headLayout = (LinearLayout) view.findViewById(R.id.head_layout);
        headLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(searchPhoneTv.getText())) {
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.setDelEdit(searchPhoneTv.getText().toString());
                }
            }
        });
        initListView(view);
        return view;
    }

    /**
     * 初始化listview 视图
     */
    private void initListView(View view){
        listView = (ListView) view.findViewById(R.id.search_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity mainActivity = (MainActivity) context;
                SearchBean searchBean = (SearchBean) adapter.getItem(position);
                mainActivity.setDelEdit(searchBean.getPhoneNum());
            }
        });
    }

    public void search(final String keyword) {
        phoneTv.setText(keyword);
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = setSearchData(keyword);
                handler.sendEmptyMessage(0x12);
            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        if(msg.what == 0x12){
            adapter.setListChange(list);
        }
        }
    };

    /**
     * 获取模糊匹配的数据
     * @param phoneNum
     * @return
     */
    private List<SearchBean> setSearchData(String phoneNum){
        List<SearchBean> list = new ArrayList<SearchBean>();
        addContacts(list,phoneNum);
        addHistory(list,phoneNum);
        return list;
    }

    /**
     * 添加差找到的联系人
     * @param list
     * @param phoneNum
     */
    private void addContacts(List<SearchBean> list,String phoneNum){
        /** 通过模糊匹配查找出来的联系人*/
        Cursor cursor = ContactsUtil.getInstance(context).getCursorByVaguePhoneNum(phoneNum);
        while (cursor.getCount()!=0&&cursor.moveToNext()) {
            SearchBean searchBean = new SearchBean();
            String displayNameStr = cursor.getString(cursor.getColumnIndex("display_name"));
            searchBean.setDisplayName(displayNameStr);
            if(displayNameStr!=null) {
                searchBean.setPhoneNum(ContactsUtil.getInstance(context).getPhoneNumsByDisplayName(displayNameStr).get(0));
            }
            Bitmap bitmap = ContactsUtil.getInstance(context).getPhotoByRawId(cursor.getString(cursor.getColumnIndex("_id")));
            searchBean.setBitmap(bitmap);
            list.add(searchBean);
        }
        cursor.close();
    }

    /**
     * 添加查找的历史记录
     */
    private void addHistory(List<SearchBean> list,String phoneNum){
        /** 通过模糊匹配查找出来的历史记录*/
        Cursor historyCursor =  ContactsUtil.getInstance(context).getCallHistroyByVaguePhoneNum(phoneNum);
        while(historyCursor.getCount()!=0&&historyCursor.moveToNext()){
            SearchBean searchBean = new SearchBean();
            String phoneNumStr = historyCursor.getString(historyCursor.getColumnIndex("origin_number"));
            searchBean.setPhoneNum(phoneNumStr);
            Integer type = historyCursor.getInt(historyCursor.getColumnIndex("type"));
            searchBean.setType(type);
            /** 去掉重复元素*/
            if(!isContain(list,searchBean)){
                list.add(searchBean);
            }
        }
        historyCursor.close();
    }


    private Boolean isContain(List<SearchBean> list,SearchBean object){
        Boolean isEquals = false;
        for(int i=0;i<list.size();i++){
            if (object.equals(list.get(i))){
                isEquals = true;
                break;
            }
        }
        return isEquals;
    }
}
