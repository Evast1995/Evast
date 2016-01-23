package com.example.hjiang.gactelphonedemo.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    public void search(String keyword) {
        phoneTv.setText(keyword);
        new AsyncTask<String,Void,List<SearchBean>>(){
            @Override
            protected List<SearchBean> doInBackground(String... params) {
                list = setSearchData(params[0]);
                return list;
            }

            @Override
            protected void onPostExecute(List<SearchBean> aVoid) {
                adapter.setListChange(list);
                super.onPostExecute(aVoid);
            }
        }.execute(keyword);
    }


    /**
     * 获取模糊匹配的数据
     * @param phoneNum
     * @return
     */
    private List<SearchBean> setSearchData(String phoneNum){
        List<SearchBean> list = new ArrayList<SearchBean>();
        /** 通过模糊匹配查找出来的历史记录*/
        Cursor historyCursor =  ContactsUtil.getInstance(context).getCallHistroyByVaguePhoneNum(phoneNum);
        while(historyCursor.getCount()!=0&&historyCursor.moveToNext()){
            SearchBean searchBean = new SearchBean();
            String phoneNumStr = historyCursor.getString(historyCursor.getColumnIndex("origin_number"));
            searchBean.setPhoneNum(phoneNumStr);
            if(!TextUtils.isEmpty(phoneNumStr)) {
                List<String> displayNameList = ContactsUtil.getInstance(context).getDisplayNameByPhone(phoneNumStr);
                if(displayNameList.size()>0) {
                    searchBean.setDisplayName(displayNameList.toString());
                }else{
                    searchBean.setDisplayName(phoneNumStr);
                }
            }
            switch (historyCursor.getInt(historyCursor.getColumnIndex("type"))){
                case CallLog.Calls.INCOMING_TYPE:{
                    searchBean.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.line_ringing_normal));
                    break;
                }
                case CallLog.Calls.MISSED_TYPE:{
                    searchBean.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.miss_call));
                    break;
                }
                case CallLog.Calls.OUTGOING_TYPE:{
                    searchBean.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.line_calling_normal));
                    break;
                }
            }
            list.add(searchBean);
        }
        historyCursor.close();


        /** 通过模糊匹配查找出来的联系人*/
        Cursor cursor = ContactsUtil.getInstance(context).getCursorByVaguePhoneNum(phoneNum);
        while (cursor.getCount()!=0&&cursor.moveToNext()) {
            SearchBean searchBean = new SearchBean();
            String displayNameStr = cursor.getString(cursor.getColumnIndex("display_name"));
            searchBean.setDisplayName(displayNameStr);
            if(displayNameStr!=null) {
                searchBean.setPhoneNum(ContactsUtil.getInstance(context).getPhoneNumsByDisplayName(displayNameStr).toString());
            }
            long contactId = ContactsUtil.getInstance(context).getContactIdByRawId(cursor.getLong(cursor.getColumnIndex("_id")));
            Bitmap bitmap = ContactsUtil.getInstance(context).getPhotoByContactId(contactId);
            searchBean.setBitmap(bitmap);
            list.add(searchBean);
        }
        cursor.close();
        return list;
    }


}
