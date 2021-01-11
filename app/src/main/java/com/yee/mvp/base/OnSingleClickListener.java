package com.yee.mvp.base;

import android.view.View;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/9 9:57
 * 功能描述：特殊按键消除连续点击，保证一秒之内只响应一次点击事件
 */
public abstract class OnSingleClickListener implements View.OnClickListener {

    public abstract void onSingleClick(View view);

    //两次点击的时间间隔，默认1s
    private static final int CLICK_DELAY_TIME = 1000;
    private long lastClickTime;

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if (curClickTime - lastClickTime >= CLICK_DELAY_TIME){
            lastClickTime = curClickTime;
            onSingleClick(v);
        }
    }
}
