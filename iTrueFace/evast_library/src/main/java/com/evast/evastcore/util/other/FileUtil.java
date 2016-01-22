package com.evast.evastcore.util.other;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by 72963 on 2015/11/18.
 */
public class FileUtil {

    /**
     * 检查目录是否存在，不存在则创建
     * @param filePath 文件路径
     * @return 是存在，如不存在是否创建成功
     */
    public static Boolean checkDir(String filePath){
        File f = new File(filePath);
        /** 文件不存在时创建文件夹路径 mkdirs（）创建多级目录 mkdir（）创建一级目录*/
        if(!f.exists()){
            return  f.mkdirs();
        }
        return true;
    }

    /**
     * 判断SD卡是否存在
     * @return 返回SD卡是否存在
     * (在mainfest中加入权限 android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
     */
    public static Boolean isExistSD(){
       if(android.os.Environment.getExternalStorageState().equals(
               Environment.MEDIA_MOUNTED)){
           return true;
       }else{
           return false;
       }
    }

    /**
     * 获取SD卡剩余容量
     * @return 返回MB单位
     */
    public static long getSDFreeSize(){
        /** 获取SD卡路径*/
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        /** 获取单个数据块的大小（Byte）*/
        long blockSize = sf.getBlockSize();
        /** 空闲的数据块数量*/
        long freeBlocks = sf.getAvailableBlocks();
        /** 单位Byte*/
        //return freeBlocks*blockSize;
        /** 单位KB*/
        //return (freeBlocks*blockSize)/1024;
        /** 单位MB*/
        return (freeBlocks *blockSize)/1024/1024;
    }

    /**
     * 获取所有数据块数
     * @return 返回SD卡的所有容量的大小单位（MB）
     */
    public static long getSDAllSize(){
        /**获取SD卡文件路径*/
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        /** 获取单个数据块大小（Byte）*/
        long blockSize = sf.getBlockSize();
        /** 获取所有数据块数*/
        long allBlocks = sf.getBlockCount();
        /** 单位Byte*/
        //return allBlocks*blockSize;
        /** 单位KB*/
        //return (allBlocks*blockSize)/1024;
        /** 单位MB*/
        return (allBlocks *blockSize)/1024/1024;
    }

}
