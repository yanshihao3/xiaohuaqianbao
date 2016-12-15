package com.xiecc.qianbao.modules.main.ui;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.xiecc.qianbao.R;
import com.xiecc.qianbao.base.BaseApplication;
import com.xiecc.qianbao.base.BaseFragment;
import com.xiecc.qianbao.common.utils.SharedPreferenceUtil;
import com.xiecc.qianbao.common.utils.SimpleSubscriber;
import com.xiecc.qianbao.common.utils.ToastUtil;
import com.xiecc.qianbao.common.utils.Util;
import com.xiecc.qianbao.component.RetrofitSingleton;
import com.xiecc.qianbao.component.RxBus;
import com.xiecc.qianbao.modules.main.adapter.WeatherAdapter;
import com.xiecc.qianbao.modules.main.domain.ChangeCityEvent;
import com.xiecc.qianbao.modules.main.domain.Weather;
import com.yalantis.phoenix.PullToRefreshView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class MainFragment extends BaseFragment implements AMapLocationListener {
    private static final String TAG = MainFragment.class.getSimpleName();
    public static final String HTML_URL = "http://www.shoujiweidai.com/android/app1.html?app=4&chanel=android";
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.iv_erro)
    ImageView mIvError;
    private static Weather mWeather = new Weather();
    @Bind(R.id.swiprefresh)
    PullToRefreshView swiprefresh;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.mWebview)
    WebView mWebview;
    private WeatherAdapter mAdapter;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.content_main, container, false);
            ButterKnife.bind(this, view);
        }
        mIsCreateView = true;
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StringRequest request=new StringRequest(HTML_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                toolbar.setVisibility(View.GONE);
                mFab.setVisibility(View.GONE);
                mWebview.setVisibility(View.VISIBLE);
                mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
                mWebview.setWebChromeClient(new WebChromeClient());
                mWebview.getSettings().setJavaScriptEnabled(true);
                mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                mWebview.getSettings().setDomStorageEnabled(true);
                mWebview.loadUrl(HTML_URL);
                mWebview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                mWebview.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mWebview.setVisibility(View.GONE);
                loadDate();
                if (error.toString().equals("com.android.volley.ServerError")) {
                    Toast.makeText(getActivity(), "请检查网络连接", Toast.LENGTH_SHORT).show();
                }

            }
        });
        BaseApplication.getVolleyRequestQueue().add(request);
    }

    private void loadDate() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        mFab.setImageResource(R.drawable.ic_add_24dp);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        initView();
        RxPermissions.getInstance(getActivity()).request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        location();
                    } else {
                        load();
                    }
                });
        RxBus.getDefault().toObservable(ChangeCityEvent.class).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new SimpleSubscriber<ChangeCityEvent>() {
                    @Override
                    public void onNext(ChangeCityEvent changeCityEvent) {
                        if (swiprefresh != null) {
                            swiprefresh.setRefreshing(true);
                        }
                        load();
                    }
                });

    }


    private void initView() {
        if (swiprefresh != null) {

            swiprefresh.setOnRefreshListener(
                    () -> swiprefresh.postDelayed(this::load, 1000));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WeatherAdapter(mWeather);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void load() {
        fetchDataByNetWork()
                .doOnRequest(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        swiprefresh.setRefreshing(true);
                    }
                })
                .doOnError(throwable -> {
                    SharedPreferenceUtil.getInstance().setCityName("北京");
                    safeSetTitle("北京");
                })
                .doOnNext(weather -> {
                    mIvError.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                })
                .doOnTerminate(() -> {
                    swiprefresh.setRefreshing(false);
                    mProgressBar.setVisibility(View.GONE);
                }).subscribe(new Subscriber<Weather>() {
            @Override
            public void onCompleted() {
                ToastUtil.showShort(getString(R.string.complete));
            }

            @Override
            public void onError(Throwable e) {
                RetrofitSingleton.disposeFailureInfo(e);

            }

            @Override
            public void onNext(Weather weather) {
                mWeather.status = weather.status;
                mWeather.aqi = weather.aqi;
                mWeather.basic = weather.basic;
                mWeather.suggestion = weather.suggestion;
                mWeather.now = weather.now;
                mWeather.dailyForecast = weather.dailyForecast;
                mWeather.hourlyForecast = weather.hourlyForecast;
                //mActivity.getToolbar().setTitle(weather.basic.city);
                safeSetTitle(weather.basic.city);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 从网络获取
     */
    private Observable<Weather> fetchDataByNetWork() {
        String cityName = SharedPreferenceUtil.getInstance().getCityName();
        return RetrofitSingleton.getInstance()
                .fetchWeather(cityName)
                .compose(this.bindToLifecycle());
    }

    /**
     * 高德定位
     */
    private void location() {
        swiprefresh.setRefreshing(true);
        //初始化定位
        mLocationClient = new AMapLocationClient(BaseApplication.getAppContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔 单位毫秒

        int tempTime = SharedPreferenceUtil.getInstance().getAutoUpdate();
        if (tempTime == 0) {
            tempTime = 100;
        }
        mLocationOption.setInterval(tempTime * SharedPreferenceUtil.ONE_HOUR);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.e(TAG, aMapLocation.getErrorCode() + "");
        Log.e(TAG, aMapLocation.getCity() + "");

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                Log.e(TAG, aMapLocation.getErrorCode() + "");

                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                SharedPreferenceUtil.getInstance().setCityName(Util.replaceCity(aMapLocation.getCity()));
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                if (isAdded()) {
                    ToastUtil.showShort(getString(R.string.errorLocation));
                }
            }

            load();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient = null;
        mLocationOption = null;
    }

    /**
     * 加载数据操作,在视图创建之前初始化
     */
    @Override
    protected void lazyLoad() {

    }


}
