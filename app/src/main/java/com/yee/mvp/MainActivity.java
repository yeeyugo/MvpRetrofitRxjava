package com.yee.mvp;

import android.view.View;
import android.widget.Button;

import com.yee.mvp.base.BaseActivity;
import com.yee.mvp.util.LogUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btLog, btToast;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btLog=findViewById(R.id.bt_log);
        btLog.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                LogUtil.d(TAG, "click log...");
            }
        });

        btToast=findViewById(R.id.bt_toast);
        btToast.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_toast:
                showToast("show toast!");
                LogUtil.e(TAG, "show toast...");
                break;
        }
    }
}