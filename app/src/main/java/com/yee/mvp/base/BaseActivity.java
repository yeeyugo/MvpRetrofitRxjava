package com.yee.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/5 7:10
 * 功能描述：封装activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    private static Toast mToast;

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(getLayoutId());

        initView();
        initData();
    }

    //获取当前窗口的布局资源
    protected abstract int getLayoutId();
    //初始化view
    protected abstract void initView();
    //初始化数据
    protected abstract void initData();

    //封装toast
    public void showToast(String message){
        try {
            if (null == mToast){
                mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
            }else {
                mToast.setText(message);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast.show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    //消除连续点击，保证一秒之内只响应一次点击事件
    public abstract class OnSingleClickListener implements View.OnClickListener{
        //两次点击的时间间隔，默认1s
        private static final int CLICK_DELAY_TIME = 1000;
        private long lastClickTime;
        //
        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if (curClickTime - lastClickTime >= CLICK_DELAY_TIME){
                lastClickTime = curClickTime;
                onSingleClick(v);
            }
        }
    }
}
