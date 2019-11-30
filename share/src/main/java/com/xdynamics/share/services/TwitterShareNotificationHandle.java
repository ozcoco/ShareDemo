package com.xdynamics.share.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.xdynamics.share.Constants;

public class TwitterShareNotificationHandle extends ShareNotificationHandle {


    public static TwitterShareNotificationHandle create(Context context) {

        return new TwitterShareNotificationHandle(context);
    }

    public TwitterShareNotificationHandle(Context context) {
        super(context, "com.twitter.android");
    }


    @Override
    public void onHandle() {

        Intent intent = new Intent(Constants.Twitter.CALLBACK_RECEIVER_ACTION);

        intent.putExtra(Constants.INTENT_EXTRA_DATA, this);

        if (TextUtils.isEmpty(getTickerText())) {
            //null网络异常
            intent.putExtra(Constants.INTENT_STATE, Constants.STATE_ERROR);

            intent.putExtra(Constants.INTENT_ERROR, Constants.ERROR_UNKNOWN);

        } else if (TextUtils.equals("推文已发送", getTickerText()) || TextUtils.equals("Tweet sent", getTickerText())) {
            //Tweet Sent
            intent.putExtra(Constants.INTENT_STATE, Constants.STATE_SUCCESS);

        } else if (TextUtils.equals("重复的推文", getTickerText()) || TextUtils.equals("Duplicate Tweet", getTickerText())) {
            //error
            intent.putExtra(Constants.INTENT_STATE, Constants.STATE_ERROR);

            intent.putExtra(Constants.INTENT_ERROR, Constants.Twitter.ERROR_DUPLICATE_TWEET);
        }
        else {
//            intent.putExtra(Constants.INTENT_STATE, Constants.STATE_ERROR);
//
//            intent.putExtra(Constants.INTENT_ERROR, Constants.ERROR_UNKNOWN);
        }

        LocalBroadcastManager.getInstance(getContext()).sendBroadcastSync(intent);

    }

}
