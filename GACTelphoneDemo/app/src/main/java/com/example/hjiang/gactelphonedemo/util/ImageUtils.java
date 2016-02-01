package com.example.hjiang.gactelphonedemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by hjiang on 16-1-25.
 */
public class ImageUtils {

    /**
     * 获取资源文件中的bitmap
     * @param resId
     * @param context
     * @return
     */
    public static Bitmap getBitmapByResId(int resId,Context context){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize=2;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 获取bitmap通过byte[]
     * @return
     */
    public static Bitmap getBitmapByBytes(byte[] bytes){
        Bitmap bitmap = null;
        if(bytes.length>0) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        return bitmap;
    }
}
