package com.xdynamics.share;

import android.content.Intent;
import android.content.PeriodicSync;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.xdynamics.share.databinding.ActivityMainBinding;

import org.oz.utils.Ts;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        mBinding.setLifecycleOwner(this);

        mBinding.setHandleClick(this);

        setContentView(mBinding.getRoot());

        requestPermission();
    }


    private void requestPermission() {

        PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied() {

            }
        }).request();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        shareCallback.onActivityResult(requestCode, resultCode, data);

        twitterLoginButton.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(mBinding.btnShareLink)) {

            shareLink();

        } else if (v.equals(mBinding.btnShareImage)) {

            shareImage();

        } else if (v.equals(mBinding.btnShareVideo)) {
            shareVideo();

        } else if (v.equals(mBinding.btnShareMedia)) {

            shareMedia();
        } else if (v.equals(mBinding.btnShareTweet)) {

            shareToTwitter();
        } else if (v.equals(mBinding.btnUi)) {

            share2();
        } else {
            share();
        }
    }


    /**********************************  facebook  *************************************/

    private void shareLink() {

        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.LINK)
                .setLink("https://developers.facebook.com")
                .setQuote("Connect on a global scale.")
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }

    private void shareImage() {

        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.IMAGE)
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.q))
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.w))
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.e))
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.r))
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }

    private void shareMedia() {

        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.MEDIA)
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.q))
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.w))
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.e))
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.r))
                .addVideo("/storage/emulated/0/DCIM/Camera/VID_20190227_073108.mp4")
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }


    private void shareVideo() {

        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.VIDEO)
                .addVideo("/storage/emulated/0/DCIM/Camera/VID_20190227_073108.mp4")
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }


    private void share() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }


    private void share2() {

        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.LINK)
                .setLink("https://developers.facebook.com")
                .setQuote("Connect on a global scale.")
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }


    private ShareCallback shareCallback = new ShareCallback() {
        @Override
        public void onSuccess(String postId) {

            Ts.i("分享成功");
        }

        @Override
        public void onCancel() {

            Ts.w("取消分享");
        }

        @Override
        public void onError(ShareException error) {

            Ts.e("分享发生错误");

        }
    };


    /**********************************  分享到twitter  *************************************/


    /**
     * 分享到twitter
     * 若未安装twitter客户端，则会跳转到浏览器
     */

    TwitterLoginButton twitterLoginButton;

    public void shareToTwitter() {

    /*    twitterLoginButton = new TwitterLoginButton(this);

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

//                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
//                        .getActiveSession();

                final Intent intent = new ComposerActivity.Builder(MainActivity.this)
                        .session(result.data)
                        .image(Uri.fromFile(new File("/storage/sdcard0/DCIM/Camera/IMG_20181124_190200.jpg")))
                        .text("Tweet from TwitterKit!")
                        .hashtags("#twitter")
                        .createIntent();
                startActivity(intent);

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

        twitterLoginButton.performClick();*/

        TweetComposer.Builder builder = null;

 /*       Uri uri = FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName() + ".provider",
                new File("/storage/sdcard0/DCIM/Camera/IMG_20181124_190200.jpg"));*/

        try {
            builder = new TweetComposer.Builder(this)
                    .text("just setting up my Twitter Kit.")
                    .url(new URL("http://www.twitter.com"))
                    .image(Uri.fromFile(new File("/storage/sdcard0/DCIM/Camera/IMG_20181124_190200.jpg")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        builder.show();

    }

}



