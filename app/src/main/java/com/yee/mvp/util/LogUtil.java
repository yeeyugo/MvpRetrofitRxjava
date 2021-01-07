package com.yee.mvp.util;

import android.util.Log;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/7 8:38
 * 功能描述：封装日志工具
 */
public final class LogUtil {
    private static boolean DEBUG = true;

    public static void v(String tag, String message){
        logger("v", tag, message);
    }

    public static void d(String tag, String message){
        logger("d", tag, message);
    }

    public static void i(String tag, String message){
        logger("i", tag, message);
    }

    public static void w(String tag, String message){
        logger("w", tag, message);
    }

    public static void e(String tag, String message){
        logger("e", tag, message);
    }

    private static void logger(String priority, String tag, String message){
        if (!DEBUG){
            return;
        }

        switch (priority){
            case "v":
                Log.v(tag, message);
                break;
            case "d":
                Log.d(tag, message);
                break;
            case "i":
                Log.i(tag, message);
                break;
            case "w":
                Log.w(tag, message);
                break;
            default:
                Log.e(tag, message);
        }
    }
}
