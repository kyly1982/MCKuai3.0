package com.mckuai.until;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.List;

/**
 * Created by kyly on 2015/6/30.
 * 提供管理我的世界的相关功能，包括：
 * 检测游戏是否安装
 * 安装游戏
 * 检测游戏是否正在运行
 * 获取游戏版本号
 * 杀死游戏
 */
public class GameUntil {

    public static boolean detectionIsGameRunning(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> run = activityManager.getRunningAppProcesses();
        if (null != run && !run.isEmpty()){
            for (ActivityManager.RunningAppProcessInfo info:run){
                if (info.processName.equalsIgnoreCase("com.mojang.minecraftpe")){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean detectionIsGameInstalled(Context context){
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> applist = pm.getInstalledApplications(0);
        if (null != applist && !applist.isEmpty()){
            for (ApplicationInfo app:applist){
                if (app.packageName.equalsIgnoreCase("com.mojang.minecraftpe")){
                    return  true;
                }
            }
        }
        return  false;
    }

    public static  boolean installGame(Context context,String gameFile){
        File file = new File(gameFile) ;
        if (file.exists() && file.isFile()) {
            Intent intent = new Intent();
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            context.startActivity(intent);
            return  true;
        }
        else {
            return  false;
        }
    }

    public static  String detectionGameVersion(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packagelist = pm.getInstalledPackages(0);
        if (null != packagelist && !packagelist.isEmpty()){
            for (PackageInfo curpackage:packagelist){
                if (curpackage.packageName.equalsIgnoreCase("com.mojang.minecraftpe")){
                    return  curpackage.versionName;
                }
            }
        }
        return  null;
    }

    public static  boolean killGameTask(Context context){
        if (detectionIsGameRunning(context)) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses("com.mojang.minecraftpe");
        }
        return  !detectionIsGameRunning(context);
    }



    public static boolean  detectionIsGameHasProfile(Context context){
        String dir = getSDPath();
        if (null != dir){
            dir += "/games/com.mojang/";
        }

        File profileDir = new File(dir);
        if (profileDir.exists() && profileDir.isDirectory()) {
            dir+= "minecraftWorlds/";
            File mapFile = new File(dir);
            if (mapFile.exists() && mapFile.isDirectory() && null != mapFile.listFiles() && 0 < mapFile.listFiles().length){
                return  true;
            }
        }
        return  false;
    }

    private static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取根目录
        }
        return sdDir.toString();
    }


}
