package com.yee.mvp;

import android.view.View;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.yee.mvp.base.BaseActivity;
import com.yee.mvp.tab.home.HomeFragment;
import com.yee.mvp.tab.image.ImageFragment;
import com.yee.mvp.tab.mine.MineFragment;
import com.yee.mvp.tab.play.PlayFragment;

public class MainActivity extends BaseActivity {

    private Fragment[] fragments = new Fragment[4];
    private int curPageIndex = -1;

    @Override
    protected int getTitleBarType() {
        return TITLE_BAR_GONE;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        RadioGroup tabs = findViewById(R.id.rg_tab);
        tabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        showFragment(0);
                        break;
                    case R.id.rb_image:
                        showFragment(1);
                        break;
                    case R.id.rb_play:
                        showFragment(2);
                        break;
                    case R.id.rb_mine:
                        showFragment(3);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        showFragment(0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

        }
    }

    @Override
    protected void onDestroy() {
        removeAllFragment();
        super.onDestroy();
    }

    //fragment显示管理
    private void showFragment(int index){
        //如果点击的页面就是当前页面不做处理，否则按点击位置切换显示
        if (curPageIndex != index){
            //获取fragment管理器
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //隐藏当前显示的界面
            if (curPageIndex != -1){
                transaction.detach(fragments[curPageIndex]);
            }
            //切换新的界面显示
            if (fragments[index] == null){
                switch (index){
                    case 0:
                        fragments[0] = new HomeFragment();
                        break;
                    case 1:
                        fragments[1] = new ImageFragment();
                        break;
                    case 2:
                        fragments[2] = new PlayFragment();
                        break;
                    case 3:
                        fragments[3] = new MineFragment();
                        break;
                }
                //添加新的fragment到容器中
                transaction.add(R.id.main_content, fragments[index]);
            }else {
                //复用，直接显示
                transaction.attach(fragments[index]);
            }
            //提交
            transaction.commit();
            //记录index
            curPageIndex = index;
        }
    }

    //fragment销毁管理
    private void removeAllFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null){
                transaction.remove(fragments[i]);
                fragments[i] = null;
            }
        }
        transaction.commitAllowingStateLoss();
    }
}