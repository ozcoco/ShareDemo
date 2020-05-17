package com.xdynamics.share.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
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

        Logger.d("Progress: %d", newProgress);

    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
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
        Logger.d(callback);
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        Logger.d("resultMsg");
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        Logger.d("resultMsg");
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
        Logger.d("onRequestFocus");
    }

    @Override
    public void onCloseWindow(WebView window) {
        Logger.d(window);
        super.onCloseWindow(window);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Logger.d(result);
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Logger.d(result);
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Logger.d(result);
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        Logger.d(result);
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
        Logger.d(request);
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        super.onPermissionRequestCanceled(request);
        Logger.d(request);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {


        Logger.d(consoleMessage);

        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

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
}
