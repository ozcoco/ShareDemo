package com.xdynamics.share.webview;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share.webview
 * @ClassName: WebViewSetting
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/16 16:43
 * @UpdateUser:
 * @UpdateDate: 2020/5/16 16:43
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ShareWebViewSetting {

    private WebView mWebView;


    public ShareWebViewSetting(WebView view) {
        mWebView = view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void load() {

        if (mWebView == null) return;

        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

//声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
//支持插件
//                webSettings.setPluginsEnabled(true);
        webSettings.setSupportMultipleWindows(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setMediaPlaybackRequiresUserGesture(false);

        cookie();
    }

    private void cookie() {

        if (mWebView == null) return;
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptThirdPartyCookies(mWebView);
        cookieManager.flush();

    }

}
