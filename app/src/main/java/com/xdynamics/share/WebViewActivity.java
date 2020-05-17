package com.xdynamics.share;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
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

        setContentView(new SwipeRefreshLayout(this) {
            {
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

                addView(new WebView(getContext()) {

                    /*  WebView load url   */ {

                        mWebView = this;

                        new ShareWebViewSetting(this).load();

                        setWebChromeClient(new ShareWebChromeClient() {

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

                                Intent intent = fileChooserParams.createIntent();
                                try {
                                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                                } catch (ActivityNotFoundException e) {
                                    uploadMessage = null;
                                    Toast.makeText(view.getContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                                    return false;
                                }
                                return true;
                            }

                        });


                        setWebViewClient(new ShareWebViewClient() {


                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                                URL_BASE = request.getUrl().getPath();

                                return super.shouldOverrideUrlLoading(view, request);
                            }

                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);

                                setRefreshing(false);

                            }
                        });

                        setWebContentsDebuggingEnabled(true);

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
    public void onBackPressed() {
//        super.onBackPressed();

        if (mWebView != null) {

            if (mWebView.canGoBack())
                mWebView.goBack();
            else
                finish();
        }

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
