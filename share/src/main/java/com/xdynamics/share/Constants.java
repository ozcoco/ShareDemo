package com.xdynamics.share;

public interface Constants {

    int STATE_SUCCESS = 0;

    int STATE_CANCEL = 1;

    int STATE_ERROR = 2;

    String INTENT_EXTRA_DATA = "INTENT_EXTRA_DATA";

    String INTENT_STATE = "INTENT_STATE";

    String INTENT_ERROR = "INTENT_ERROR";

    String ERROR_UNKNOWN = "ERROR_UNKNOWN";

    String MEDIA_TYPE_IMAGE = "image/*";

    String MEDIA_TYPE_VIDEO = "video/*";


    interface Twitter {

        String CALLBACK_RECEIVER_ACTION = "com.xdynamics.share.TwitterShare.CALLBACK_RECEIVER_ACTION";

        short REQUEST_CODE = 0x123;

        String ERROR_DUPLICATE_TWEET = "ERROR_DUPLICATE_TWEET";
    }


    interface YouTube {

        String packageName = "com.google.android.youtube";

        short REQUEST_CODE = 0x124;

    }

    interface Instagram {

        String packageName = "com.instagram.android";

        String shareImageActivityName = "com.instagram.share.handleractivity.ShareHandlerActivity";
        
        String shareVideoActivityName = "com.instagram.share.handleractivity.ShareHandlerActivity";

        short REQUEST_CODE = 0x125;

    }


}
