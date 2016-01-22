package com.evast.evastcore.util.other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 72963 on 2015/11/18.
 */
public class ImageUtil {
    /**
     * 将图片保存到本地时进行压缩，即将图片从Bitmap形式变为File形式时进行压缩
     * 特点是：File形式的图片确实被压缩了，但是当你重新读取压缩后的file为Bitmap是，它占用的内存并没有改变
     * @param bmp 要压缩的图片
     * @param file 压缩图片放在那个File中
     * 方法说明：该方法是压缩图片的质量，注意它不会减少图片的像素，比方说,你的图片是300K的，1280*700像素的，经过该方法压缩后，
     *         File形式的图片是100以下,以方便上传到服务器，但是你BItmapFactory。decodeFile到内存中，编程Bitmap时，它的
     *         像素仍然是1280*700,计算图片像素的方法 bitmap。getWidth（）和bitmap。getHeight（）
     * 官方文档也解释说，它会让图片重新构造，但是有可能图片的位深（色深）和每个像素的透明度会变化，JPEG（不透明）,也就是说以jpeg格式
     * 压缩后，原来图片中透明的元素将会消失，所以这种格式很可能造成失真
     * 它是改变了图片的显示质量,达到了对File形式的图片进行压缩，图片像素没有改变的话，那重新读取经过压缩的file为Bitmap时，它占用
     * 的内存不会少。bitmap.getByteCount()计算它的像素所占用的内存
     */
    public static void compressBmpToFile(Bitmap bmp,File file){
        /** */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /** 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中*/
        int options = 80;
        bmp.compress(Bitmap.CompressFormat.JPEG,options,baos);
        /** 压缩到100KB以下*/
        while(baos.toByteArray().length/1024>100){
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG,options,baos);
        }
        try{
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 该方法就是对Bitmap形式的图片进行压缩，也就是通过设置采样率，减少Bitmap的像素，从而减少了它所占用的内存
     * @param srcPath 图片保存的路径
     * @return 返回Bitmap
     */
    public static Bitmap compressImageFromFile(String srcPath){
        BitmapFactory.Options newOpts= new BitmapFactory.Options();
        /** 只读边,不读内容 */
        newOpts.inJustDecodeBounds = true;
//        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;
        float ww = 480f;
        int be = 1;

        /** 如果宽高中大的进行压缩*/
        if(w>h && w>ww){
            be = (int)(newOpts.outWidth/ww);
        }else if(w<h&&h>hh){
            be = (int)(newOpts.outHeight/hh);
        }
        if (be<=0){
            be = 1;
        }

        /** 设置采样率*/
        newOpts.inSampleSize = be;
        /** 该模式是默认的，可不设*/
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        /** 同时设置才会有效*/
        newOpts.inPurgeable = true;
        /** 当系统内存不够的时候图片制动被回收*/
        newOpts.inInputShareable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);
        return bitmap;
    }
}
