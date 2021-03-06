package com.example.hjiang.gactelphonedemo.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.base.module.call.CallManager;
import com.base.module.call.settings.CallSettingsManager;
import com.example.hjiang.gactelphonedemo.MyApplication;
import com.example.hjiang.gactelphonedemo.bean.ChangeContactsBean;
import com.example.hjiang.gactelphonedemo.bean.ChangeHistoryBean;
import com.example.hjiang.gactelphonedemo.bean.MeetingBean;
import com.example.hjiang.gactelphonedemo.bean.MemberBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjiang on 16-1-19.
 */
public class ContactsUtil {
    private static Context context;
    private static ContentResolver contentResolver;
    private ContactsUtil(Context context){
        contentResolver = context.getContentResolver();
        this.context=  context;
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
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String whereStr = "raw_contact_id in (select raw_contact_id from data where mimetype_id = '5' " +
                "and data1=?) and mimetype_id = '7'";
        Cursor cursor = contentResolver.query(uri,new String[]{"data1"},whereStr,new String[]{phoneNumStr},null);
        while(cursor.moveToNext()){
            list.add(cursor.getString(0));
        }
        cursor.close();
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
        Cursor cursor = contentResolver.query(uri, null, whereStr, null, null);
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
     * 通过rawID获bitmap
     * @param rawID
     * @return
     */
    public Bitmap getPhotoByRawId(String rawID){
        Bitmap bitmap = null;
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String whereStr = "raw_contact_id = ? and mimetype_id = '10'";
        Cursor cursor = contentResolver.query(uri, new String[]{"data15"}, whereStr,
                new String[]{rawID}, null);
        if(cursor.getCount()>0){
            cursor.moveToNext();
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex("data15"));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        cursor.close();
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
        String whereStr =
                "raw_contact_id in(select raw_contact_id from data where mimetype_id='5' and data1=?) and mimetype_id='10'";
        Cursor cursor = contentResolver.query(uri, new String[]{"data15"}, whereStr, new String[]{phoneNum}, null);
        if(cursor.getCount()>0) {
            cursor.moveToNext();
            byte[] bytes = cursor.getBlob(cursor.getColumnIndex("data15"));
            if(bytes!=null) {
                InputStream inputStream = new ByteArrayInputStream(bytes);
                BitmapFactory.Options options = new BitmapFactory.Options();
                bitmap = BitmapFactory.decodeStream(inputStream,null,options);
            }
        }
        cursor.close();
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
        return cursor;
    }

    /**
     * 获取所有通话记录
     * 去掉会议记录
     * @return
     */
    public Cursor getAllCallHistory(){
        ChangeHistoryBean changeHistoryBean = new ChangeHistoryBean();
        Uri historyUri = CallLog.Calls.CONTENT_URI;
        String whereStr = "is_conference = '0'";
        Cursor cursor = contentResolver.query(historyUri,new String[]{"origin_number","type","account"},whereStr,null,null);
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
        cursor.close();
        return list;
    }





    /** ---------------------------------------会议工具--------------------------------------------------------**/


    /**
     * 添加预约会议
     * @param meetingBean
     */
    public void insertMeetings(MeetingBean meetingBean,List<String> phoneNumStr){
        String meetingId = insertMeetingsTable(meetingBean);
        addMeetingMemberBean(phoneNumStr, Integer.parseInt(meetingId));
    }

    /**
     * 添加预约会议中　会议表中的数据
     * @param meetingBean
     * @return 会议ID
     */
    public String insertMeetingsTable(MeetingBean meetingBean){
        if(meetingBean == null){
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",meetingBean.getName());
        contentValues.put("state",meetingBean.getState());
        contentValues.put("cycle_time",meetingBean.getCycleTime());
        contentValues.put("haspwd",meetingBean.getHaspwd());
        contentValues.put("password",meetingBean.getPswStr());
        contentValues.put("time_zone",meetingBean.getTimeZone());
        contentValues.put("start_time",meetingBean.getStartTime());
        contentValues.put("duration",meetingBean.getDuration());
        contentValues.put("reminder_time",meetingBean.getReminderTime());
        contentValues.put("has_reminded",0);
        contentValues.put("auto_call",meetingBean.getAutoCall());
        contentValues.put("auto_answer",meetingBean.getAutoAnswer());
        contentValues.put("intercept",meetingBean.getIntercept());
        contentValues.put("auto_record",meetingBean.getAutoRecord());
        contentValues.put("enter_mute",meetingBean.getEnterMute());
        contentValues.put("build_time",0);
        Uri uri = Uri.parse("content://com.base.conference.provider/meetings");
        Uri resultUri =  contentResolver.insert(uri, contentValues);
        String returnStr = resultUri.getEncodedPath();
        String meetingIdStr = returnStr.replace("/meetings/", "");
        return meetingIdStr;
    }

    /**
     * 通过电话号码集合添加会议成员到bean
     * @param list
     */
    private void addMeetingMemberBean(List<String> list,int meetingId){
        List<MemberBean> memList = new ArrayList<MemberBean>();
        for(int i=0;i<list.size();i++){
            MemberBean memberBean = new MemberBean();
            String phoneNum = list.get(i);
            memberBean.setAccount(MyApplication.localId);
            memberBean.setOrigin(CallSettingsManager.SOURCE_FLAG_DIAL);
            memberBean.setOriginNum(phoneNum);
            memberBean.setPhone(phoneNum);
            memberBean.setCallMode(CallManager.CALLMODE_SIP);
            memberBean.setMeetingId(meetingId);
            memList.add(memberBean);
        }
        Uri uri = Uri.parse("content://com.base.conference.provider/members");
        for(int i=0;i<memList.size();i++){
            MemberBean memberBean = memList.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put("phone", memberBean.getPhone());
            contentValues.put("origin_number", memberBean.getOriginNum());
            contentValues.put("account", memberBean.getAccount());
            contentValues.put("origin", memberBean.getOrigin());
            contentValues.put("call_mode", memberBean.getCallMode());
            contentValues.put("meeting_id", memberBean.getMeetingId());
            contentResolver.insert(uri, contentValues);
        }
    }

    /**
     * 通过会议ID获取该会议的成员
     * @param meetingId
     */
    public List<MemberBean> getMemeberByMeetingId(int meetingId){
        List<MemberBean> list = new ArrayList<MemberBean>();
        Uri uri = Uri.parse("content://com.base.conference.provider/members");
        String whereStr = "meeting_id = ?";
        Cursor cursor = contentResolver.query(uri,null,whereStr,new String[]{String.valueOf(meetingId)},null);
        while(cursor.getCount()>0&&cursor.moveToNext()){
            MemberBean memberBean = new MemberBean();
            memberBean.setMeetingId(meetingId);
            memberBean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            memberBean.setOriginNum(cursor.getString(cursor.getColumnIndex("origin_number")));
            memberBean.setAccount(cursor.getInt(cursor.getColumnIndex("account")));
            memberBean.setOrigin(cursor.getInt(cursor.getColumnIndex("origin")));
            memberBean.setCallMode(cursor.getInt(cursor.getColumnIndex("call_mode")));
            list.add(memberBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 通过会议状态获取会议信息
     * @param state 0:no start 1:closed 2:missed
     * @return
     */
    public List<MeetingBean> getMeetingsByState(int state){
        List<MeetingBean> list = new ArrayList<MeetingBean>();
        Uri uri = Uri.parse("content://com.base.conference.provider/meetings");
        String whereStr = "state = ?";
        Cursor cursor = contentResolver.query(uri, null, whereStr, new String[]{String.valueOf(state)}, null);
        while(cursor.getCount()>0&&cursor.moveToNext()){
            MeetingBean meetingBean = new MeetingBean();
            meetingBean.setMeetingId(cursor.getInt(cursor.getColumnIndex("_id")));
            meetingBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            meetingBean.setCycleTime(cursor.getInt(cursor.getColumnIndex("cycle_time")));
            meetingBean.setHaspwd(cursor.getInt(cursor.getColumnIndex("haspwd")));
            meetingBean.setPswStr(cursor.getString(cursor.getColumnIndex("password")));
            meetingBean.setTimeZone(cursor.getString(cursor.getColumnIndex("time_zone")));
            meetingBean.setStartTime(cursor.getLong(cursor.getColumnIndex("start_time")));
            meetingBean.setDuration(cursor.getInt(cursor.getColumnIndex("duration")));
            meetingBean.setReminderTime(cursor.getLong(cursor.getColumnIndex("reminder_time")));
            meetingBean.setAutoCall(cursor.getInt(cursor.getColumnIndex("auto_call")));
            meetingBean.setAutoAnswer(cursor.getInt(cursor.getColumnIndex("auto_answer")));
            meetingBean.setIntercept(cursor.getInt(cursor.getColumnIndex("intercept")));
            meetingBean.setAutoRecord(cursor.getInt(cursor.getColumnIndex("auto_record")));
            meetingBean.setEnterMute(cursor.getInt(cursor.getColumnIndex("enter_mute")));
            meetingBean.setIsRead(cursor.getInt(cursor.getColumnIndex("is_read")));
            meetingBean.setState(cursor.getInt(cursor.getColumnIndex("state")));
            meetingBean.setMemberList(getMemeberByMeetingId(meetingBean.getMeetingId()));
            list.add(meetingBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 删除某条会议记录通过会议Id
     * @param meetingId
     */
    public void delScheduleById(int meetingId){
        Uri meetingUri = Uri.parse("content://com.base.conference.provider/meetings");
        Uri memberUri = Uri.parse("content://com.base.conference.provider/members");

        String meetingWhereStr = "_id = ?";
        contentResolver.delete(meetingUri,meetingWhereStr,new String []{String.valueOf(meetingId)});
        String memberWhereStr = "meeting_id = ?";
        contentResolver.delete(memberUri, memberWhereStr, new String[]{String.valueOf(meetingId)});
    }

    public void updataScheduleByMeetingId(MeetingBean meetingBean,List<String> list){
        Uri meetingUri = Uri.parse("content://com.base.conference.provider/meetings");
        Uri memberUri = Uri.parse("content://com.base.conference.provider/members");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",meetingBean.getName());
        contentValues.put("state",meetingBean.getState());
        contentValues.put("cycle_time",meetingBean.getCycleTime());
        contentValues.put("haspwd",meetingBean.getHaspwd());
        contentValues.put("password",meetingBean.getPswStr());
        contentValues.put("time_zone",meetingBean.getTimeZone());
        contentValues.put("start_time",meetingBean.getStartTime());
        contentValues.put("duration",meetingBean.getDuration());
        contentValues.put("reminder_time",meetingBean.getReminderTime());
        contentValues.put("has_reminded",0);
        contentValues.put("auto_call",meetingBean.getAutoCall());
        contentValues.put("auto_answer",meetingBean.getAutoAnswer());
        contentValues.put("intercept",meetingBean.getIntercept());
        contentValues.put("auto_record",meetingBean.getAutoRecord());
        contentValues.put("enter_mute", meetingBean.getEnterMute());
        contentValues.put("build_time", 0);
        String meetingWhere = "_id = ?";
        contentResolver.update(meetingUri, contentValues, meetingWhere,
                new String[]{String.valueOf(meetingBean.getMeetingId())});

        String memberWhere = "meeting_id = ?";
        contentResolver.delete(memberUri,memberWhere,
                new String[]{String.valueOf(meetingBean.getMeetingId())});
        addMeetingMemberBean(list,meetingBean.getMeetingId());
    }

}

