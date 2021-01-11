package com.yee.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.yee.mvp.R;
import com.yee.mvp.util.LanguageUtil;
import com.yee.mvp.util.LogUtil;

/**
 * 开发人员：叶斌一
 * 创建时间：2021/1/5 7:10
 * 功能描述：封装activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();

    private static Toast mToast;

    protected Context mContext;

    //封装titleBar
    private RelativeLayout titleBarLayout;
    private TextView tvTitleBarTitle, tvTitleBarBack, tvTitleBarMore;
    //titleBar不显示
    protected static final int TITLE_BAR_GONE = 0;
    //titleBar只以文字的方式显示back
    protected static final int TITLE_BAR_BACK_TEXT = 1;
    //titleBar只以图片的方式显示back
    protected static final int TITLE_BAR_BACK_IMAGE = 2;
    //titleBar只以文字的方式显示more
    protected static final int TITLE_BAR_MORE_TEXT = 3;
    //titleBar只以图片的方式显示more
    protected static final int TITLE_BAR_MORE_IMAGE = 4;
    //titleBar以文字的方式同时显示back和more
    protected static final int TITLE_BAR_BOTH_TEXT = 5;
    //titleBar以图片的方式同时显示back和more
    protected static final int TITLE_BAR_BOTH_IMAGE = 6;
    //titleBar以图片的方式显示back同时以文字的方式显示more
    protected static final int TITLE_BAR_IMAGE_TEXT = 7;
    //titleBar以文字的方式显示back同时以图片的方式显示more
    protected static final int TITLE_BAR_TEXT_IMAGE = 8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        //设置语言
        if (!LanguageUtil.isLanguageSame(this)) {
            LanguageUtil.setLocale(this);
            LanguageUtil.restartMainActivity(this);
        }

        setContentView(R.layout.activity_base);

        //设置titleBar
        setTitleBar(getTitleBarType());

        //动态加载baseContent
        addBaseContent();

        //初始化
        initView();
        initData();
    }

    /**
     * 子类必须实现的方法
     *     1.getTitleBarType()实现设置titleBar的类型
     *     2.getLayoutId()获取当前窗口的布局资源
     *     3.initView()初始化view
     *     4.initData()初始化数据
     */
    //子类实现设置titleBar的类型
    protected abstract int getTitleBarType();
    //获取当前窗口的布局资源
    protected abstract int getLayoutId();
    //初始化view
    protected abstract void initView();
    //初始化数据
    protected abstract void initData();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_left:
                LogUtil.d(TAG, "Click titleBar's back.");
                this.finish();
                break;
        }
    }

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

    //动态加载BaseContent
    private void addBaseContent(){
        LinearLayout baseLayout = findViewById(R.id.base_layout);
        View baseContent = getLayoutInflater().inflate(getLayoutId(), null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0
        );
        layoutParams.weight = 1;
        baseLayout.addView(baseContent, layoutParams);
    }

    //设置titleBar入口
    private void setTitleBar(int titleBarType){
        if (titleBarType < TITLE_BAR_BACK_TEXT || titleBarType > TITLE_BAR_TEXT_IMAGE){
            return;
        }

        ViewStub viewStub = findViewById(R.id.vs_title_bar);
        viewStub.setVisibility(View.VISIBLE);
        titleBarLayout = findViewById(R.id.title_bar_layout);

        tvTitleBarTitle = findViewById(R.id.tv_title_center);
        tvTitleBarBack = findViewById(R.id.tv_title_left);
        tvTitleBarMore = findViewById(R.id.tv_title_right);

        //设施titleBar的标题
        tvTitleBarTitle.setText(getTitle());
        //设置back和more点击监听
        tvTitleBarBack.setOnClickListener(this);
        tvTitleBarMore.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                LogUtil.d(TAG, "Click titleBar's more.");
                setTitleBarMoreClick(view);
            }
        });

        switch (titleBarType){
            case TITLE_BAR_BACK_TEXT:
                titleBarTypeBackText(setTitleBarBackText());
                break;
            case TITLE_BAR_BACK_IMAGE:
                titleBarTypeBackImage(setTitleBarBackImage());
                break;
            case TITLE_BAR_MORE_TEXT:
                titleBarTypeMoreText(setTitleBarMoreText());
                break;
            case TITLE_BAR_MORE_IMAGE:
                titleBarTypeMoreImage(setTitleBarMoreImage());
                break;
            case TITLE_BAR_BOTH_TEXT:
                titleBarTypeBothText(setTitleBarBackText(), setTitleBarMoreText());
                break;
            case TITLE_BAR_BOTH_IMAGE:
                titleBarTypeBothImage(setTitleBarBackImage(), setTitleBarMoreImage());
                break;
            case TITLE_BAR_IMAGE_TEXT:
                titleBarTypeImageText(setTitleBarBackImage(), setTitleBarMoreText());
                break;
            case TITLE_BAR_TEXT_IMAGE:
                titleBarTypeTextImage(setTitleBarBackText(), setTitleBarMoreImage());
                break;
        }
    }

    //设置具体titleBar类型：backText
    private void titleBarTypeBackText(@StringRes int resId){
        tvTitleBarBack.setVisibility(View.VISIBLE);
        tvTitleBarMore.setVisibility(View.GONE);

        tvTitleBarBack.setText(resId);
    }

    //设置具体titleBar类型：backImage
    private void titleBarTypeBackImage(@DrawableRes int resId){
        tvTitleBarBack.setVisibility(View.VISIBLE);
        tvTitleBarMore.setVisibility(View.GONE);

        tvTitleBarBack.setText(null);
        tvTitleBarBack.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
    }

    //设置具体titleBar类型：moreText
    private void titleBarTypeMoreText(@StringRes int resId){
        tvTitleBarBack.setVisibility(View.GONE);
        tvTitleBarMore.setVisibility(View.VISIBLE);

        tvTitleBarMore.setText(resId);
    }

    //设置具体titleBar类型：moreImage
    private void titleBarTypeMoreImage(@DrawableRes int resId){
        tvTitleBarBack.setVisibility(View.GONE);
        tvTitleBarMore.setVisibility(View.VISIBLE);

        tvTitleBarMore.setText(null);
        tvTitleBarMore.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
    }

    //设置具体titleBar类型：bothText
    private void titleBarTypeBothText(@StringRes int backId, @StringRes int moreId){
        tvTitleBarBack.setVisibility(View.VISIBLE);
        tvTitleBarMore.setVisibility(View.VISIBLE);

        tvTitleBarBack.setText(backId);
        tvTitleBarMore.setText(moreId);
    }

    //设置具体titleBar类型：bothImage
    private void titleBarTypeBothImage(@DrawableRes int backId, @DrawableRes int moreId){
        tvTitleBarBack.setVisibility(View.VISIBLE);
        tvTitleBarMore.setVisibility(View.VISIBLE);

        tvTitleBarBack.setText(null);
        tvTitleBarBack.setCompoundDrawablesWithIntrinsicBounds(backId, 0, 0, 0);
        tvTitleBarMore.setText(null);
        tvTitleBarMore.setCompoundDrawablesWithIntrinsicBounds(moreId, 0, 0, 0);
    }

    //设置具体titleBar类型：imageText
    private void titleBarTypeImageText(@DrawableRes int backId, @StringRes int moreId){
        tvTitleBarBack.setVisibility(View.VISIBLE);
        tvTitleBarMore.setVisibility(View.VISIBLE);

        tvTitleBarBack.setText(null);
        tvTitleBarBack.setCompoundDrawablesWithIntrinsicBounds(backId, 0, 0, 0);
        tvTitleBarMore.setText(moreId);
    }

    //设置具体titleBar类型：TextImage
    private void titleBarTypeTextImage(@StringRes int backId, @DrawableRes int moreId){
        tvTitleBarBack.setVisibility(View.VISIBLE);
        tvTitleBarMore.setVisibility(View.VISIBLE);

        tvTitleBarBack.setText(backId);
        tvTitleBarMore.setText(null);
        tvTitleBarMore.setCompoundDrawablesWithIntrinsicBounds(moreId, 0, 0, 0);
    }

    /**
     * 设置titleBar的具体属性，根据需要选择性重写
     */
    //设置titleBar背景颜色
    public void setTitleBarBackground(@ColorRes int resId){
        titleBarLayout.setBackgroundColor(getResources().getColor(resId, null));
    }
    //设置titleBar标题文字颜色
    public void setTitleBarTextColor(@ColorRes int resId){
        tvTitleBarTitle.setTextColor(getResources().getColor(resId, null));
        if (tvTitleBarBack.getText() != null){
            tvTitleBarBack.setTextColor(getResources().getColor(resId, null));
        }
        if (tvTitleBarMore.getText() != null){
            tvTitleBarMore.setTextColor(getResources().getColor(resId, null));
        }
    }
    //设置titleBar返回键文字
    public int setTitleBarBackText(){
        return 0;
    }
    //设置titleBar返回键图片
    public int setTitleBarBackImage(){
        return 0;
    }
    //设置titleBar更多键文字
    public int setTitleBarMoreText(){
        return 0;
    }
    //设置titleBar更多键图片
    public int setTitleBarMoreImage(){
        return 0;
    }
    //设置titleBar的more点击监听
    public void setTitleBarMoreClick(View view){}
}
