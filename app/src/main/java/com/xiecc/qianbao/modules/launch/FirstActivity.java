package com.xiecc.qianbao.modules.launch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.umeng.analytics.MobclickAgent;
import com.xiecc.qianbao.base.BaseApplication;
import com.xiecc.qianbao.common.utils.SPUtils;
import com.xiecc.qianbao.common.utils.SharedPreferencesUtil;
import com.xiecc.qianbao.modules.main.ui.GuideActivity;
import com.xiecc.qianbao.modules.main.ui.MainActivity;

import java.lang.ref.WeakReference;

/**
 * Created by hugo on 2015/10/25 0025.
 * 闪屏页
 * @see <a herf="http://www.androiddesignpatterns.com/2013/01/inner-class-handler-memory-leak.html">How to Leak a Context: Handlers & Inner Classes</a>
 */
public class FirstActivity extends Activity {
    private static  final String URL="http://www.shoujiweidai.com/android/app2001.html";
    private SharedPreferences sp;
    private SwitchHandler mHandler = new SwitchHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //activity切换的淡入淡出效果
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        boolean url1 = SPUtils.contains(this, "url");
        if(!url1){
            setUrl();
            setWelcome();
        }else {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }
    private void setUrl() {
      StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              SPUtils.put(FirstActivity.this,"url",URL);

          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
      });
        BaseApplication.getVolleyRequestQueue().add(request);

    }
    private void setWelcome(){
        boolean isFirstOpen = SharedPreferencesUtil.getBoolean(FirstActivity.this, SharedPreferencesUtil.FIRST_OPEN, true);
        if (isFirstOpen) {
            Intent intent = new Intent(FirstActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }else {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }
    private static class SwitchHandler extends Handler {
        private WeakReference<FirstActivity> mWeakReference;

        SwitchHandler(FirstActivity activity) {
            mWeakReference = new WeakReference<FirstActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FirstActivity activity = mWeakReference.get();
            if (activity != null) {
                MainActivity.launch(activity);
                activity.finish();
            }
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private long mLastBackTime = 0;
    @Override
    public void onBackPressed() {
        // finish while click back key 2 times during 1s.
        if ((System.currentTimeMillis() - mLastBackTime) < 1000) {
            finish();
        } else {
            mLastBackTime = System.currentTimeMillis();
            Toast.makeText(this, "请再确认一次", Toast.LENGTH_SHORT).show();
        }

    }
}