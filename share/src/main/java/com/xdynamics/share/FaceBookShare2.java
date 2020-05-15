package com.xdynamics.share;

import android.net.Uri;
import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;

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

    public void shareLink() {

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .setQuote("Connect on a global scale.")
                .setPageId(UUID.randomUUID().toString())
                .build();

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

    }


}
