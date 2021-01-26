package com.yee.mvp.tab.home;

import android.util.Log;
import android.widget.RadioGroup;

import com.yee.mvp.R;
import com.yee.mvp.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    public static final String TAG = "yee";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        RadioGroup rg1 = mActivity.findViewById(R.id.rg_choose_coin);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_usdt:
                        Log.e(TAG, "checked usdt");
                        break;
                    case R.id.rb_btc:
                        Log.e(TAG, "checked btc");
                        break;
                    case R.id.rb_eth:
                        Log.e(TAG, "checked eth");
                        break;
                }
            }
        });

        RadioGroup rg2 = mActivity.findViewById(R.id.rg_pay);
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_card:
                        Log.e(TAG, "checked card");
                        break;
                    case R.id.rb_chat:
                        Log.e(TAG, "checked chat");
                        break;
                    case R.id.rb_ali:
                        Log.e(TAG, "checked ali");
                        break;
                }
            }
        });
    }
}