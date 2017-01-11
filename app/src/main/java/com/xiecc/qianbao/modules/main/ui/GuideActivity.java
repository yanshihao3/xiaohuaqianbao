package com.xiecc.qianbao.modules.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiecc.qianbao.R;
import com.xiecc.qianbao.common.utils.SharedPreferencesUtil;

import cn.bingoogolapple.bgabanner.BGABanner;

public class GuideActivity extends AppCompatActivity {
    private BGABanner mBackgroundBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        setListener();
        processLogic();
    }
    private void initView() {
        mBackgroundBanner = (BGABanner) findViewById(R.id.banner_guide_background);
    }
    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                Intent intent = new Intent(GuideActivity.this,
                        MainActivity.class);
                startActivity(intent);
                SharedPreferencesUtil.putBoolean(GuideActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);
                finish();
            }
        });
    }
    private void processLogic() {
        // 设置数据源
        mBackgroundBanner.setData(R.mipmap.loding_01, R.mipmap.loding_02, R.mipmap.loding_03);

    }
}
