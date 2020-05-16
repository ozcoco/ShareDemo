package com.xdynamics.share;

import android.app.Activity;
import android.net.Uri;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.UUID;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share
 * @ClassName: FaceBookShare2
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/15 13:44
 * @UpdateUser:
 * @UpdateDate: 2020/5/15 13:44
 * @UpdateRemark:
 * @Version: 1.0
 */
public class FaceBookShare2 {

    public final static String TAG = FaceBookShare2.class.getCanonicalName();

    private Activity mActivity;

    public FaceBookShare2(Activity activity) {
        mActivity = activity;
    }

    public void shareLink() {

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .setQuote("Connect on a global scale.")
                .setPageId(UUID.randomUUID().toString())
                .build();

        ShareDialog.show(mActivity, content);



/*
        ShareApi.share(content, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {

                Log.d(TAG, String.format("---------> onSuccess ---> post id: %s", result.getPostId()));

            }

            @Override
            public void onCancel() {

                Log.d(TAG, "---------> onCancel");
            }

            @Override
            public void onError(FacebookException error) {

                Log.d(TAG, "---------> onError");

            }
        });
*/

    }


}
