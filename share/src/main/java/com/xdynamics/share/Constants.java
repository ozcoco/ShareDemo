package com.xdynamics.share;

public interface Constants {

    int STATE_SUCCESS = 0;

    int STATE_CANCEL = 1;

    int STATE_ERROR = 2;

    String INTENT_EXTRA_DATA = "INTENT_EXTRA_DATA";

    String INTENT_STATE = "INTENT_STATE";

    String INTENT_ERROR = "INTENT_ERROR";

    String ERROR_UNKNOWN = "ERROR_UNKNOWN";


    interface Twitter {

        String CALLBACK_RECEIVER_ACTION = "com.xdynamics.share.TwitterShare.CALLBACK_RECEIVER_ACTION";

        short TWEET_COMPOSER_REQUEST_CODE = 0x123;

        String ERROR_DUPLICATE_TWEET = "ERROR_DUPLICATE_TWEET";
    }

}
