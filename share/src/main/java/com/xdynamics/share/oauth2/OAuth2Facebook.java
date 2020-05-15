package com.xdynamics.share.oauth2;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * @ProjectName: ShareDemo
 * @Package: com.xdynamics.share.oauth2
 * @ClassName: OAuth2Utils
 * @Description:
 * @Author: oz
 * @CreateDate: 2020/5/12 19:32
 * @UpdateUser:
 * @UpdateDate: 2020/5/12 19:32
 * @UpdateRemark:
 * @Version: 1.0
 */
public class OAuth2Facebook implements OAuth2able, ActivityResultCallback {

    final static String TAG = OAuth2Facebook.class.getCanonicalName();

    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";

    private CallbackManager mCallbackManager;

    private LoginManager mLoginManager;

    private Activity mActivity;

    public OAuth2Facebook(Activity activity) {

        mActivity = activity;
        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();
        mLoginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        mLoginManager.setAuthType(AUTH_TYPE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallbackManager != null)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void oauth2() {

        if (mCallbackManager != null)
            mLoginManager.registerCallback(mCallbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // App code
                            AccessToken accessToken = loginResult.getAccessToken();

                            Log.d(TAG, String.format("---------------》 accessToken: %s", accessToken.getToken()));

                            Log.d(TAG, String.format("---------------》 %s", accessToken.toString()));

                        }

                        @Override
                        public void onCancel() {
                            // App code

                            mLoginManager.unregisterCallback(mCallbackManager);

                            Log.d(TAG, "---------------》 onCancel");

                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code

                            mLoginManager.unregisterCallback(mCallbackManager);

                            Log.d(TAG, String.format("---------------》 onError: %s", exception.toString()));
                        }
                    });

//        mLoginManager.logInWithPublishPermissions(mActivity, Collections.singletonList("publish_actions"));

        mLoginManager.logInWithReadPermissions(mActivity, Arrays
                .asList("email", "user_likes", "user_status", "user_photos", "user_videos", "user_birthday", "public_profile", "user_friends"));

//        mLoginManager.logIn(mActivity, Arrays
//                .asList("publish_actions", "email", "user_likes", "user_status", "user_photos", "user_birthday", "public_profile", "user_friends"));

    }

}
