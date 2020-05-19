package com.xdynamics.share.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;

import java.util.Arrays;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share.webview
 * @ClassName: ShareWebChromeClient
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/16 17:00
 * @UpdateUser:
 * @UpdateDate: 2020/5/16 17:00
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ShareWebChromeClient extends WebChromeClient {


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Logger.d("onProgressChanged");
        Logger.d("Progress: %d", newProgress);

    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        Logger.d("onReceivedTitle");
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
        Logger.d("onReceivedIcon");
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        super.onReceivedTouchIconUrl(view, url, precomposed);
        Logger.d("onReceivedTouchIconUrl");
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        Logger.d("onShowCustomView");
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        Logger.d("onHideCustomView");
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        Logger.d("onCreateWindow");
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
        Logger.d("onRequestFocus");
    }

    @Override
    public void onCloseWindow(WebView window) {
        Logger.d("onCloseWindow");
        super.onCloseWindow(window);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Logger.d("----onJsAlert----> url: %s \n message: %s", url, message);

        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Logger.d("onJsConfirm");
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Logger.d("onJsPrompt");
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        Logger.d("onJsBeforeUnload");
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        super.onGeolocationPermissionsShowPrompt(origin, callback);
        Logger.d("onGeolocationPermissionsShowPrompt");
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
        Logger.d("onGeolocationPermissionsHidePrompt");
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        super.onPermissionRequest(request);
        Logger.d("onPermissionRequest");
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        super.onPermissionRequestCanceled(request);
        Logger.d("onPermissionRequestCanceled");
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {


        Logger.d("onConsoleMessage");

        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

        Logger.d("onShowFileChooser");

        Logger.d("AcceptTypes: %s \n FilenameHint: %s \n Mode: %d \n Title: %s \n isCaptureEnabled: %d",
                Arrays.toString(fileChooserParams.getAcceptTypes()),
                fileChooserParams.getFilenameHint(),
                fileChooserParams.getMode(),
                fileChooserParams.getTitle(),
                fileChooserParams.isCaptureEnabled() ? 1 : 0);

        Logger.d(fileChooserParams.createIntent());

//        filePathCallback.onReceiveValue();

        return true;
//        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }


    @Nullable
    @Override
    public Bitmap getDefaultVideoPoster() {

        Logger.d("getDefaultVideoPoster");
        return super.getDefaultVideoPoster();
    }

    @Nullable
    @Override
    public View getVideoLoadingProgressView() {
        Logger.d("getVideoLoadingProgressView");
        return super.getVideoLoadingProgressView();
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        super.getVisitedHistory(callback);
        Logger.d("getVisitedHistory");
    }
}
