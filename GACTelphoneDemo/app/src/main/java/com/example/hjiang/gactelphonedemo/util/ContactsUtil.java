package com.example.hjiang.gactelphonedemo.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.example.hjiang.gactelphonedemo.bean.ChangeContactsBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-19.
 */
public class ContactsUtil {
    private static ContentResolver contentResolver;
    private ContactsUtil(Context context){
        contentResolver = context.getContentResolver();
    }
    private static ContactsUtil contactsUtil;
    public synchronized static ContactsUtil getInstance(Context context){
        if(contactsUtil == null) {
            contactsUtil = new ContactsUtil(context);
        }
        return contactsUtil;
    }

    /**
     * 根据电话号码返回对应的raw_contact_id的数组
     * @param phoneNumStr
     * @return
     */
    public List<Integer> getRawIdsByPhoneNum(String phoneNumStr){
        List<Integer> list = new ArrayList<Integer>();
        int i=0;
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String whereStr = "mimetype_id = '5' and data1 = ?";
        Cursor cursor = null;
        cursor = contentResolver.query(uri, new String[]{"raw_contact_id"},
                whereStr, new String[]{phoneNumStr}, null);
        while(cursor!=null&&cursor.moveToNext()){
            list.add(cursor.getInt(cursor.getColumnIndex("raw_contact_id")));
        }
        cursor.close();
        return list;
    }

    /**
     * 通过rawId获取ContactId
     * @param rawId
     * @return
     */
    public int getContactIdByRawId(long rawId){
        int contactId;
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        String whereStr = "_id = ?";
        Cursor cursor = contentResolver.query(uri, new String[]{"contact_id"},
                whereStr, new String[]{String.valueOf(rawId)}, null);
        cursor.moveToNext();
        contactId = cursor.getInt(cursor.getColumnIndex("contact_id"));
        cursor.close();
        return contactId;
    }

    /**
     * 通过raw_contacts_id来获取姓名
     * @param rawId
     * @return
     */
    public String getDisplayNameByRawId(int rawId){
        String displayName;
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        String whereStr = "_id = ?";
        Cursor cursor = contentResolver.query(uri,new String[]{"display_name"},whereStr
        ,new String[]{String.valueOf(rawId)},null);
        cursor.moveToNext();
        displayName = cursor.getString(cursor.getColumnIndex("display_name"));
        cursor.close();
        return displayName;
    }

    /**
     * 通过电话号码获取相应的名字
     * @param phoneNumStr
     * @return
     */
    public List<String> getDisplayNameByPhone(String phoneNumStr){
        List<String> list = new ArrayList<String>();
        List<Integer> rawIds = getRawIdsByPhoneNum(phoneNumStr);
        for(int i=0;i<rawIds.size();i++){
            list.add(getDisplayNameByRawId(rawIds.get(i)));
        }
        return list;
    }

    /**
     * 通过raw_contact_id获取该表的游标
     * @param rawId
     * @return
     */
    public Cursor getCursorByRawId(String rawId){
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        String whereStr = "_id = ?";
        Cursor cursor = contentResolver.query(uri, null, whereStr, new String[]{rawId}, null);
        return cursor;
    }

    /**
     * 通过模糊查找　前面存在phoneNum字符串的游标
     * @param phoneNum
     * @return
     */
    public Cursor getCursorByVaguePhoneNum(String phoneNum){
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        String whereStr = "_id in" +
                "(select raw_contact_id from data where mimetype_id='5' and data1 like '"+phoneNum+"%')";
        Cursor cursor = contentResolver.query(uri,null,whereStr,null,null);
        return cursor;
    }

    /**
     * 通过contactId来获取图片
     * @param contactID
     * @return
     */
    public Bitmap getPhotoByContactId(long contactID){
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactID);
        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                contentResolver, uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    /**
     * 通过电话号码获取头像
     * @param phoneNum
     * @return
     */
    public Bitmap getPhotoByPhone(String phoneNum){
        Bitmap bitmap = null;
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String whereStr = "raw_contact_id in(select raw_contact_id from data where mimetype_id='5' and data1=?)";
        Cursor cursor = contentResolver.query(uri, new String[]{"data15"}, whereStr, new String[]{phoneNum}, null);
        if(cursor.getCount()>0) {
            cursor.moveToNext();
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex("data15"));
            InputStream inputStream = new ByteArrayInputStream(bytes);
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        return bitmap;
    }

    /**
     * 通过displayName获取电话号码
     * @param displayName
     * @return
     */
    public List<String> getPhoneNumsByDisplayName(String displayName){
        List<String> list = new ArrayList<String>();
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String where = "raw_contact_id in(select raw_contact_id from data where mimetype_id='7' and data1 =?) and mimetype_id='5'";
        Cursor cursor = contentResolver.query(uri,new String[]{"data1"},where,new String[]{displayName},null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("data1")));
        }
        cursor.close();
        return list;
    }

    /**
     * 通过电话号码模糊查找匹配前几位的历史记录
     * @param phoneNum
     * @return
     */
    public Cursor getCallHistroyByVaguePhoneNum(String phoneNum){
        Uri uri = CallLog.Calls.CONTENT_URI;
        String whereStr = "origin_number like '"+phoneNum+"%'";
        Cursor cursor = contentResolver.query(uri, new String[] {"origin_number","type"}, whereStr, null, null);
        // select * from calls where origin_number like '%';
        return cursor;
    }

    /**
     * 获取所有联系人
     * @return 返回联系人bean
     */
    public List<ChangeContactsBean> getAllContacts(){
        List<ChangeContactsBean> list = new ArrayList<ChangeContactsBean>();
        Uri rawUri = ContactsContract.RawContacts.CONTENT_URI;
        Uri dataUri = ContactsContract.Data.CONTENT_URI;
        Cursor cursor = contentResolver.query(rawUri,new String[]{"display_name","_id"},null,null,null);

        String whereStr;
        String nameStr;

        while (cursor.moveToNext()){
            ChangeContactsBean changeContactsBean = new ChangeContactsBean();

            /** 查找姓名*/
            nameStr = cursor.getString(cursor.getColumnIndex("display_name"));
            changeContactsBean.setNameStr(nameStr);

            long id = cursor.getLong(cursor.getColumnIndex("_id"));

            /** 通过raw_contact_id 查找电话号码*/
            whereStr = "raw_contact_id = ? and mimetype_id = ?";
            Cursor phoneCursor = contentResolver.query(dataUri, new String[]{"data1"},
                    whereStr, new String[]{String.valueOf(id),"5"}, null);
            List<String> phoneList = new ArrayList<String>();
            while(phoneCursor.getCount()>0&&phoneCursor.moveToNext()){
                phoneList.add(phoneCursor.getString(phoneCursor.getColumnIndex("data1")));
            }
            changeContactsBean.setPhoneStr(phoneList);
            phoneCursor.close();

            /** 通过raw_contact_id 查找联系人头像*/
            whereStr = "raw_contact_id=? and mimetype_id=?";
            Cursor bitmapCursor = contentResolver.query(dataUri,new String[]{"data15"},
                    whereStr,new String[]{String.valueOf(id),"10"},null);
            if(bitmapCursor.getCount()>0) {
                bitmapCursor.moveToNext();
                byte[] bytes = bitmapCursor.getBlob(bitmapCursor.getColumnIndex("data15"));
                InputStream inputStream = new ByteArrayInputStream(bytes);
                changeContactsBean.setBitmap(BitmapFactory.decodeStream(inputStream));
            }
            bitmapCursor.close();

            /** 此项只有在 打开选着联系人时才有用 表示是否选中*/
            changeContactsBean.setIsChecked(false);
            list.add(changeContactsBean);
        }
        return list;
    }
}

