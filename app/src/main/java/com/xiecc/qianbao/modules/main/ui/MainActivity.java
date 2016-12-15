package com.xiecc.qianbao.modules.main.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

import com.xiecc.qianbao.R;
import com.xiecc.qianbao.base.BaseActivity;
import com.xiecc.qianbao.common.utils.DoubleClickExit;
import com.xiecc.qianbao.common.utils.SharedPreferenceUtil;
import com.xiecc.qianbao.common.utils.ToastUtil;

public class MainActivity extends BaseActivity  {
    private PagerBottomTabLayout bottomTabLayout;
    private Controller mController;
    private FragmentManager mFragmentManager;

    private Fragment mCurrentFragment;
    //int[] testColors1 = {0xFF7BA3A8,0xFFF4F3DE,0xFFBEAD92,0xFFF35A4A,0xFF5B4947};
       int[] testColors2 = {0xFF00796B,0xFF8D6E63,0xFF2196F3,0xFF607D8B,0xFFF57C00};
    int[] testColors = {0xFF00796B,0xFF5B4947,0xFF607D8B,0xFFF57C00,0xFFF57C00};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initIcon();
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        initIcon();
    }



    /**
     * 初始化基础View
     */
    private void initView() {
        bottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);
        bottomTabLayout.builder().build().setBackgroundColor(getResources().getColor(R.color.windows_color));
        mCurrentFragment=new MainFragment();
        mFragmentManager=getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.app_item, mCurrentFragment).commit();
       /* TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultIcon(R.mipmap.home)
                .setText("首页")
                .build();*/
        mController=bottomTabLayout.builder()
                .addTabItem(R.mipmap.home, "首页")
                .addTabItem(R.drawable.bug,"主页",testColors[0])
                .addTabItem(android.R.drawable.btn_star, "帮助",testColors[3])
                .build();
        mController.addTabItemClickListener(listener);

        //mViewPager.setOffscreenPageLimit(2);

        // Glide 加载本地 GIF 图的方法
        //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(mErroImageView);
        //Glide.with(this).load(R.raw.loading).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewTarget);
        //mFab
       // mFab.setOnClickListener(v -> showFabDialog());
    }

    OnTabItemSelectListener listener=new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            switchMenu(getFragmentName(index+1));
        }

        @Override
        public void onRepeatClick(int index, Object tag) {

        }
    };
    private String getFragmentName(int menuId) {
        switch (menuId) {
            case 1:
                return MainFragment.class.getName();
           case 2:
                return SecondFragment.class.getName();
            case 3:
                return ThirdFragment.class.getName();
            default:
                return null;
        }
    }
    private void switchMenu(String fragmentName) {

        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentName);

        if (fragment != null) {
            if (fragment == mCurrentFragment) return;

            mFragmentManager.beginTransaction().show(fragment).commit();
        } else {
            fragment = Fragment.instantiate(this, fragmentName);
            mFragmentManager.beginTransaction().add(R.id.app_item, fragment, fragmentName).commit();
        }

        if (mCurrentFragment != null) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).commit();
        }
        mCurrentFragment = fragment;
    }
    /**
     * 初始化Icon
     */
    private void initIcon() {
        if (SharedPreferenceUtil.getInstance().getIconType() == 0) {
            SharedPreferenceUtil.getInstance().putInt("未知", R.mipmap.none);
            SharedPreferenceUtil.getInstance().putInt("晴", R.mipmap.type_one_sunny);
            SharedPreferenceUtil.getInstance().putInt("阴", R.mipmap.type_one_cloudy);
            SharedPreferenceUtil.getInstance().putInt("多云", R.mipmap.type_one_cloudy);
            SharedPreferenceUtil.getInstance().putInt("少云", R.mipmap.type_one_cloudy);
            SharedPreferenceUtil.getInstance().putInt("晴间多云", R.mipmap.type_one_cloudytosunny);
            SharedPreferenceUtil.getInstance().putInt("小雨", R.mipmap.type_one_light_rain);
            SharedPreferenceUtil.getInstance().putInt("中雨", R.mipmap.type_one_light_rain);
            SharedPreferenceUtil.getInstance().putInt("大雨", R.mipmap.type_one_heavy_rain);
            SharedPreferenceUtil.getInstance().putInt("阵雨", R.mipmap.type_one_thunderstorm);
            SharedPreferenceUtil.getInstance().putInt("雷阵雨", R.mipmap.type_one_thunder_rain);
            SharedPreferenceUtil.getInstance().putInt("霾", R.mipmap.type_one_fog);
            SharedPreferenceUtil.getInstance().putInt("雾", R.mipmap.type_one_fog);
        } else {
            SharedPreferenceUtil.getInstance().putInt("未知", R.mipmap.none);
            SharedPreferenceUtil.getInstance().putInt("晴", R.mipmap.type_two_sunny);
            SharedPreferenceUtil.getInstance().putInt("阴", R.mipmap.type_two_cloudy);
            SharedPreferenceUtil.getInstance().putInt("多云", R.mipmap.type_two_cloudy);
            SharedPreferenceUtil.getInstance().putInt("少云", R.mipmap.type_two_cloudy);
            SharedPreferenceUtil.getInstance().putInt("晴间多云", R.mipmap.type_two_cloudytosunny);
            SharedPreferenceUtil.getInstance().putInt("小雨", R.mipmap.type_two_light_rain);
            SharedPreferenceUtil.getInstance().putInt("中雨", R.mipmap.type_two_rain);
            SharedPreferenceUtil.getInstance().putInt("大雨", R.mipmap.type_two_rain);
            SharedPreferenceUtil.getInstance().putInt("阵雨", R.mipmap.type_two_rain);
            SharedPreferenceUtil.getInstance().putInt("雷阵雨", R.mipmap.type_two_thunderstorm);
            SharedPreferenceUtil.getInstance().putInt("霾", R.mipmap.type_two_haze);
            SharedPreferenceUtil.getInstance().putInt("雾", R.mipmap.type_two_fog);
            SharedPreferenceUtil.getInstance().putInt("雨夹雪", R.mipmap.type_two_snowrain);
        }
    }




    @Override
    public void onBackPressed() {
            if (!DoubleClickExit.check()) {
                ToastUtil.showShort(getString(R.string.double_exit));
            } else {
                finish();
            }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //OrmLite.getInstance().close();
    }
}
