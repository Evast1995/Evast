package com.evast.evastcore.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.evast.evastcore.bean.User;
import com.evast.evastcore.util.other.L;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite中需要注意事项
 * 1.SQLite中没有Boolean型用int的0，1替代
 * 2.insert参数说明 第一个参数表名，第二个参数一般为null，第三个参数ContentValues类似于Map的形式传递要加的数据
 * 3.
 * Created by 72963 on 2015/11/12.
 */
public class MyDateBase extends SQLiteOpenHelper{

    /** 例子User 该List为数据库中一张表的数据（多张表多张数据创立多个List）此处以一张表为例子*/
    private List<User> users=  new ArrayList<>();
    /** SQLite数据库名称*/
    private static final String DB_NAME = "app_sql_name";
    /** SQLite数据库版本号*/
    private static final int VERSION = 1;

    /** SQLite创建表的语句（此处以表user为例，如要创建多表则添加多个String即可）*/
    private static final String[]  CREATE_TABLES={"create table if not exists user(" +
            "       id integer primary key AUTOINCREMENT,       " +
            "       name text,                " +
            "       age integer,  " +
            "       sex text" +
            ");"};
    /** SQL删除表的语句（此处以user表为例）*/
    private static final String DROP_TABLE = "drop if table exitsts user;";
    /**
     * MyDateBase的构造函数
     */
    public MyDateBase(Context context) {
        super(context, DB_NAME, null,VERSION);
    }

    /**
     * 在调getReadableDatabase或getWritableDatabase时，
     * 会判断指定的数据库是否存在，
     * 不存在则调SQLiteDatabase.create创建，
     * onCreate只在数据库第一次创建时才执行
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 创建表的语句放在了CREATE_TABLES中，循环创建所有表
         */
        for(int i=0;i<CREATE_TABLES.length;i++){
            db.execSQL(CREATE_TABLES[i]);
        }
        /** 当数据库创建时判断users集合为不为空，为空说明第一次创建，不为空说明为版本更新是onUpgrade中调用*/
        if(users.size()>0){
            /** 设置开始事物事务（保证所有数据都能同时插入）*/
            db.beginTransaction();
            try {
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    /** Map的形式添加要加入数据的信息*/
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", user.getName());
                    contentValues.put("age", user.getAge());
                    contentValues.put("sex", user.getSex());
                    /** 第一个参数表名，第二个参数一般为null，第三个参数ContentValues类似于Map的形式传递要加的数据*/
                    db.insert("user", null, contentValues);
                    /** 事务操作成功*/
                    db.setTransactionSuccessful();
                }
            }catch (Exception e){
                L.e("SQL insert Execption!!!");
            }finally {
                /**结束事务*/
                db.endTransaction();
            }
        }
    }

    /**
     * 数据库版本更新是调用
     * @param db
     * @param oldVersion 老版本号
     * @param newVersion 新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /** 此处用循环是为了防止用户一直不升级，一次跳很多级升级，通过这种迭代方式一次次升级版本*/
        for(int j = oldVersion;j<=newVersion;j++) {
            switch (j) {
                case 2:{//版本为二时的升级情况
                    /** 将老版本的数据导出到List集合中*/
                    upDateToNewVersion(db);
                    /** 老版本的数据导入到新版本中*/
                    onCreate(db);
                    break;
                }
            }
        }

    }


    /**
     * 查询所有数据并放入List集合中（在版本更新时用到了）
     * @param db
     */
    private void upDateToNewVersion(SQLiteDatabase db){
        /** query参数说明，table 表名，columns 数据库列名称数组 写入后最后返回的Cursor中只能查到这里的列的内容
             selection 查询条件, selectionArgs:查询结果，groupBy:分组列，having:分组条件
             ，orderBy:排序列，limit:分页查询限制 */
        Cursor cursor = db.query("user",null,null,null,null,null,"id desc");
        while (cursor.moveToNext()){
            User user = new User();
            user.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
            users.add(user);
        }
    }

    /**
     * 删除表
     */
    private void dropTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            /** 删除表操作*/
            db.execSQL(DROP_TABLE);
            db.setTransactionSuccessful();
        }catch (Exception e){
            L.e("dropTable Execption!!!");
        }finally {
            db.endTransaction();
        }
    }
}
