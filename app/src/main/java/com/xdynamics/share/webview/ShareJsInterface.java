package com.xdynamics.share.webview;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.orhanobut.logger.Logger;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share.webview
 * @ClassName: ShareJsInterface
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/19 15:31
 * @UpdateUser:
 * @UpdateDate: 2020/5/19 15:31
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ShareJsInterface {

    private Activity mActivity;

    public ShareJsInterface(Activity activity) {
        this.mActivity = activity;
    }

    @JavascriptInterface
    public void finish() {

        Logger.d("---ShareJsInterface---> %s", "finish");

        if (mActivity != null)
            mActivity.finish();

    }


    @JavascriptInterface
    public void filePickerCallback() {

        Logger.d("---ShareJsInterface---> %s", "filePickerCallback");

    }

}
