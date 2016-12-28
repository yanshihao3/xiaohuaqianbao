package com.xiecc.qianbao.modules.main.ui;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.xiecc.qianbao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {


    @Bind(R.id.mWebView)
    WebView mWebView;
    private SharedPreferences sp;
    private boolean flag = false;

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        TextNet();
        sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        String url = sp.getString("url", "");
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCachePath(getActivity().getCacheDir().getPath());
        if (flag) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(flag);

        mWebView.getSettings().setSupportZoom(false);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return false;
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                    mWebView.goBack();
                    mWebView.canGoBack();
                    return true;
                }
                return false;
            }
        });
    }

    private void TextNet() {
        ConnectivityManager con = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if (wifi | internet) {
            //执行相关操作
            flag = true;

        } else {
            Toast.makeText(getActivity(),
                    "亲，网络连接失败咯！", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
