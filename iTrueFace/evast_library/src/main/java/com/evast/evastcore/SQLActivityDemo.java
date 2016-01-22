package com.evast.evastcore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.evast.evastcore.bean.User;
import com.evast.evastcore.core.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 72963 on 2015/11/15.
 */
public class SQLActivityDemo extends BaseActivity{

    private List<User> users;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_sql);

    }

    @Override
    protected void init() {
        users = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new SqlDemoAdapter());
    }


    /**
     * sqlDemo List的适配器
     */
    class SqlDemoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /** listView 的优化处理缓存处理*/
            ViewHolder viewHolder = null;
            if(convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(SQLActivityDemo.this).inflate(R.layout.core_sql_item, null);
                viewHolder.idTv = (TextView) convertView.findViewById(R.id.tv_id);
                viewHolder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.ageTv = (TextView) convertView.findViewById(R.id.tv_age);
                viewHolder.sexTv = (TextView) convertView.findViewById(R.id.tv_sex);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            User user = users.get(position);
            int age = user.getAge();
            int id = user.getId();
            String name = user.getName();
            int sex = user.getSex();

            viewHolder.idTv.setText(String.valueOf(id));
            viewHolder.nameTv.setText(name);
            viewHolder.ageTv.setText(String.valueOf(age));
            if(sex == 1){
                viewHolder.sexTv.setText("男");
            }else if(sex == 0){
                viewHolder.sexTv.setText("女");
            }
            return convertView;
        }
        class ViewHolder{
            TextView idTv;
            TextView nameTv;
            TextView ageTv;
            TextView sexTv;
        }
    }
}
