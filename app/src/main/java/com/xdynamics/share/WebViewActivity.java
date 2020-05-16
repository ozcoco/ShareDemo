package com.xdynamics.share;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.xdynamics.share.webview.ShareWebChromeClient;
import com.xdynamics.share.webview.ShareWebViewClient;
import com.xdynamics.share.webview.ShareWebViewSetting;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share
 * @ClassName: WebViewActivity
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/16 15:27
 * @UpdateUser:
 * @UpdateDate: 2020/5/16 15:27
 * @UpdateRemark:
 * @Version: 1.0
 */
public class WebViewActivity extends AppCompatActivity {

    private String URL_BASE = "https://m.facebook.com/";

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new WebView(this) {

            /*  WebView load url   */ {

                mWebView = this;

                new ShareWebViewSetting(this).load();

                setWebChromeClient(new ShareWebChromeClient());

                setWebViewClient(new ShareWebViewClient());

                setWebContentsDebuggingEnabled(true);

                loadUrl(URL_BASE);

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
            mWebView.resumeTimers();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
            mWebView.pauseTimers();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            ((ViewGroup) mWebView.getRootView()).removeView(mWebView);
            mWebView.destroy();
        }
    }
}
