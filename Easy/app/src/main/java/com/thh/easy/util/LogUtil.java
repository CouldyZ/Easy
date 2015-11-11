package com.thh.easy.util;

import android.app.Activity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cloud on 2015/11/11.
 */
public class LogUtil {

    public static final String TAG = "日志： ";

    // 控制打印日志的输出
    public static boolean DEBUG = true;
    public static boolean DEBUG_E = true;
    public static boolean DEBUG_I = true;
    public static boolean DEBUG_D = true;
    public static boolean DEBUG_V = true;



    /**
     * 错误信息
     * @param activity
     * @param message
     * @param linenum
     */
    public static void e(Activity activity, String message, String linenum){
        if(DEBUG && DEBUG_E)
        Log.e(getActivityName(activity), message + getDate() + getLineNumber(linenum));
    }

    public static void e(Activity activity, String message){
        if(DEBUG && DEBUG_E)
        Log.e(getActivityName(activity), message + getDate());
    }

    public static void e(String message){
        if(DEBUG && DEBUG_E)
            Log.e(TAG, message + getDate());
    }



    public static void i(Activity activity, String message, String linenum){
        if(DEBUG && DEBUG_I)
            Log.i(getActivityName(activity), message + getDate() + getLineNumber(linenum));

    }

    public static void i(Activity activity, String message){
        if(DEBUG && DEBUG_I)
            Log.i(getActivityName(activity), message + getDate());

    }
    public static void i(String message){

        if(DEBUG && DEBUG_I)
            Log.i(TAG, message + getDate());
    }


    public static void d(Activity activity, String message, String linenum){
        if(DEBUG && DEBUG_D)
            Log.d(getActivityName(activity), message + getDate() + getLineNumber(linenum));

    }

    public static void d(Activity activity, String message){
        if(DEBUG && DEBUG_D)
            Log.d(getActivityName(activity), message + getDate());

    }
    public static void d(String message){

        if(DEBUG && DEBUG_D)
            Log.d(TAG, message + getDate());
    }

    public static void v(Activity activity, String message, String linenum){
        if(DEBUG && DEBUG_V)
            Log.v(getActivityName(activity), message + getDate() + getLineNumber(linenum));

    }

    public static void v(Activity activity, String message){
        if(DEBUG && DEBUG_V)
            Log.v(getActivityName(activity), message + getDate());

    }
    public static void v(String message){

        if(DEBUG && DEBUG_V)
            Log.v(TAG, message + getDate());
    }


    /**
     * 获得Activity的名字
     * @param activity
     * @return
     */
    public static String getActivityName(Activity activity){

        String activityString = activity.toString();
        String activityContent =
                activityString.substring(activityString.lastIndexOf(".") + 1, activityString.indexOf("@"));
        return activityContent;
    }


    /**
     * 获得当前系统时间
     * @return
     */
    public static String getDate(){
        SimpleDateFormat formatter = new  SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new  Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return "  当前系统时间------>" + str;
    }


    /**
     * 获得当前行数
     * @return
     */
    public static String getLineNumber(String linenum){
        return "  当前行数------>"+linenum;
    }
}
