package com.xdynamics.share;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;
import com.xdynamics.share.webview.ShareJsInterface;
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

    private String URL_BASE = ShareConfig.Facebook.URL_HOME;

    private WebView mWebView;

    private int uploadCount = 0;

    public ValueCallback<Uri[]> uploadMessage;
    private ValueCallback<Uri> mUploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;

    private void progress(int progress) {

        setProgress(progress);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_FILE) {
            if (uploadMessage == null)
                return;

            Logger.d(data);

            assert data != null;
            Logger.d("data: %s", data.toString());

            uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            uploadMessage = null;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 101);

        setContentView(new SwipeRefreshLayout(this) {

            {
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

                addView(new WebView(getContext()) {

                    /*  WebView load url   */ {

                        mWebView = this;

                        new ShareWebViewSetting(this).load();

                        setWebChromeClient(new ShareWebChromeClient() {
                            @Override
                            public void onProgressChanged(WebView view, int newProgress) {
                                super.onProgressChanged(view, newProgress);

                                if (uploadCount == 0) {

                                    if (newProgress == 100) {
//                                        view.evaluateJavascript("javascript:document.querySelector('#composer-main-view-id div div div ._4wqq').addEventListener('click', function(){ it_share.finish(); });", null);
//                                        view.evaluateJavascript("javascript:document.querySelectorAll('#composer-main-view-id ._vbz')[0].addEventListener('click', function(){ it_share.filePickerCallback(); });", null);
                                    }

//                                    view.evaluateJavascript("javascript:document.querySelector('._6be8 span').onanimationiteration = function(){ console.log('-----> openCallback');  var picker = document.querySelectorAll('#composer-main-view-id ._vbz')[0];  if(picker){ picker.click();  this.onanimationiteration='none' };", null);

//                                    view.evaluateJavascript("javascript:if(window.bgUploadInlineComposerCallback && window.bgUploadInlineComposerCallback()) { };document.getElementById('composer-placeholder-form').style.display = '';document.getElementById('viewport').style.display = 'none';", null);
                                    view.evaluateJavascript("javascript:document.querySelector('#u_0_1v').click();", null);

                                    view.evaluateJavascript("javascript:document.querySelector('#structured_composer_form div div span div[role]').click();", null);

                                }

                            }

                            @Override
                            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                                result.confirm();

                                if (ShareConfig.Facebook.URL_HOME.contains(url)) {

                                    ++uploadCount;

                                }

                                return true;
//                                return super.onJsAlert(view, url, message, result);
                            }


                            //For Android 4.1 only
                            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                                mUploadMessage = uploadMsg;
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
                            }

                            protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
                                mUploadMessage = uploadMsg;
                                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                                i.addCategory(Intent.CATEGORY_OPENABLE);
                                i.setType("image/*");
                                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
                            }


                            // For Lollipop 5.0+ Devices
                            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                                if (uploadMessage != null) {
                                    uploadMessage.onReceiveValue(null);
                                    uploadMessage = null;
                                }

                                uploadMessage = filePathCallback;
                                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                                i.addCategory(Intent.CATEGORY_OPENABLE);
                                i.setType("*/*");
                                startActivityForResult(Intent.createChooser(i, "Image/Video"), REQUEST_SELECT_FILE);

                                if (getVisibility() == View.INVISIBLE)
                                    setVisibility(View.VISIBLE);

                                return true;
                            }

                        });


                        setWebViewClient(new ShareWebViewClient() {


                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);

                                setRefreshing(false);

                            }
                        });

                        setWebContentsDebuggingEnabled(true);


                        addJavascriptInterface(new ShareJsInterface(WebViewActivity.this) {

                            @Override
                            public void filePickerCallback() {
                                super.filePickerCallback();

//                                loadUrl("javascript:document.getElementById('header').innerHTML=''");

                            }
                        }, "it_share");

//                        setVisibility(View.INVISIBLE);

                        loadUrl(URL_BASE);
                    }

                    {

                        setOnRefreshListener(new OnRefreshListener() {
                            @Override
                            public void onRefresh() {

                                if (mWebView != null)
                                    mWebView.reload();

                            }
                        });

                    }

                }, layoutParams);

            }

        }, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //改写物理返回键的逻辑
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
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
