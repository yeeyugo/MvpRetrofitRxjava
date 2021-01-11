package com.yee.mvp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.webkit.WebView;

import com.yee.mvp.MainActivity;
import com.yee.mvp.config.MyApp;

import java.util.Locale;

public class LanguageUtil {

    public static final String USER_LANGUAGE = "user_language";
    public static final String LOCALE_LANGUAGE = "locale_language";

    /**
     * 设置本地化语言
     */
    public static void setLocale(Context context, int type) {
        //解决webView所在的activity语言没有切换问题
        new WebView(context).destroy();
        //切换语言
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = getLocaleByType(type);
        resources.updateConfiguration(configuration, dm);
    }

    public static void setLocale(Context context){
        int type = getUserLanguageType(context);
        setLocale(context, type);
    }

    /**
     * 根据type获取locale
     */
    private static Locale getLocaleByType(int type) {
        Locale locale = null;
        //使用用户选择的语言
        switch (type) {
            case 0:
                //由于API仅支持7.0，需要判断，否则程序会crash(解决7.0以上系统不能跟随系统语言问题)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    LocaleList localeList = LocaleList.getDefault();
                    int saveType = getUserLanguageType(MyApp.getContext());
                    //如果app已选择不跟随系统语言，则取第二个数据为系统默认语言
                    if (saveType != 0 && localeList.size() > 1){
                        locale = localeList.get(1);
                    }else {
                        locale = localeList.get(0);
                    }
                }else {
                    locale = Locale.getDefault();
                }
                break;
            case 1:
                locale = Locale.ENGLISH;
                break;
            case 2:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case 3:
                locale = Locale.TRADITIONAL_CHINESE;
                break;
        }
        return locale;
    }

    /**
     * 判断语言是否相同
     */
    public static boolean isLanguageSame(Context context, int type){
        Locale locale = getLocaleByType(type);
        Locale appLocale = context.getResources().getConfiguration().locale;
        return appLocale.equals(locale);
    }

    public static boolean isLanguageSame(Context context){
        int type = getUserLanguageType(context);
        return isLanguageSame(context, type);
    }

    /**
     * 存储语言类型
     */
    public static void putLanguageType(Context context, int type){
        SharedPreferences sp = context.getSharedPreferences(USER_LANGUAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putInt(LOCALE_LANGUAGE, type);
        editor.apply();
    }

    /**
     * 获取存储的语言类型
     */
    private static int getUserLanguageType(Context context){
        SharedPreferences sp =context.getSharedPreferences(USER_LANGUAGE, Context.MODE_PRIVATE);
        return sp.getInt(LOCALE_LANGUAGE, 0);
    }

    /**
     * 重启MainActivity，如果是跨进程则杀掉当前进程
     */
    public static void restartMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        //杀掉进程
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }
}
