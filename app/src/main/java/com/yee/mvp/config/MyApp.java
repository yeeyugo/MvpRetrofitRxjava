package com.yee.mvp.config;

import android.app.Application;
import android.content.Context;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/4 19:53
 * 功能描述：
 */
public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
