package com.xdynamics.share.twitter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

import org.oz.utils.Ts;

public class MyResultReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success Twitter分享成功的回调
            final Long tweetId = intent.getExtras().getLong(TweetUploadService.EXTRA_TWEET_ID);

            Ts.i("分享成功");

        } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {
            // failure
            final Intent retryIntent = intent.getExtras().getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);

            Ts.e("分享成功");

        } else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {
            // cancel

            Ts.e("取消分享");

        }
    }
}
