package com.xiecc.qianbao.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.xiecc.qianbao.BuildConfig;

import im.fir.sdk.FIR;

public class BaseApplication extends Application {

    private static String sCacheDir;
    public static Context sAppContext;
    public static RequestQueue requestQueue;

    public static RequestQueue getVolleyRequestQueue(){
        return requestQueue;
    }

    // TODO: 16/8/1 这里的夜间模式 UI 有些没有适配好 暂时放弃夜间模式
    static {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        if (!BuildConfig.DEBUG) {
            FIR.init(this);
        }
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            sCacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            sCacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static String getAppCacheDir() {
        return sCacheDir;
    }
}
