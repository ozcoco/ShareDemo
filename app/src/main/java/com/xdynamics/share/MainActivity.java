package com.xdynamics.share;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.xdynamics.share.bean.ShareContent;
import com.xdynamics.share.databinding.ActivityMainBinding;
import com.xdynamics.share.oauth2.OAuth2Facebook;
import com.xdynamics.share.services.NotificationCollectorService;

import org.oz.utils.Ts;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final short TWEET_COMPOSER_REQUEST_CODE = 0x123;
    ActivityMainBinding mBinding;
    OAuth2Facebook oauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());

        mBinding.setLifecycleOwner(this);

        mBinding.setHandleClick(this);

        setContentView(mBinding.getRoot());

        requestPermission();

        oauth = new OAuth2Facebook(this);
    }


    private void requestPermission() {

        if (!NotificationCollectorService.Permission.notificationListenerEnable(getApplicationContext())) {

            NotificationCollectorService.Permission.gotoNotificationAccessSetting(getApplicationContext());
        }

        PermissionUtils.permission(PermissionConstants.STORAGE,PermissionConstants.CAMERA).callback(new PermissionUtils.SimpleCallback() {
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

        oauth.onActivityResult(requestCode, resultCode, data);

//        twitterLoginButton.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TWEET_COMPOSER_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                Ts.i("分享成功");

            } else if (resultCode == Activity.RESULT_CANCELED) {

                Ts.w("取消分享");
            }
        }

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

            shareToTwitter2();

//            shareToTwitter();
        } else if (v.equals(mBinding.btnShareInstagram)) {

//            shareImageToInstagram();

            shareVideoToInstagram();

        } else if (v.equals(mBinding.btnUi)) {

            share2();
        } else if (v.equals(mBinding.btnShareYoutube)) {
            shareToYoutube();
        } else if (v.equals(mBinding.btnOauth)) {
            oauth2();
        } else {
            share();
        }

        switch (v.getId()) {

            case R.id.btn_graph_share_link:

                new FaceBookShare2(this).shareLink();

                break;

            case R.id.btn_web_view:

                ContextCompat.startActivity(this, new Intent(this, WebViewActivity.class), null);

                break;

        }


    }

    /******************************  facebook oauth2 ************************************/


    private void oauth2() {

        oauth.oauth2();

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

        String img = "test2.jpg";
        String img2 = "test.jpg";
        String mediaPath = Environment.getExternalStorageDirectory() + img;
        String mediaPath2 = Environment.getExternalStorageDirectory() + img2;

        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.IMAGE)
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.q))
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.w))
                .addImage(mediaPath)
                .addImage(mediaPath2)
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }

    private void shareMedia() {

        String img = "test2.jpg";
        String img2 = "test.jpg";
        String mediaPath = Environment.getExternalStorageDirectory() + img;
        String mediaPath2 = Environment.getExternalStorageDirectory() + img2;

        String v = "vtest.mp4";
        String mediaPath3 = Environment.getExternalStorageDirectory() + v;
        String v2 = "v.mp4";
        String mediaPath4 = Environment.getExternalStorageDirectory() + v2;

        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.MEDIA)
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.q))
                .addImage(BitmapFactory.decodeResource(getResources(), R.mipmap.w))
                .addImage(mediaPath)
                .addImage(mediaPath2)
                .addVideo(mediaPath3)
                .addVideo(mediaPath4)
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }


    private void shareVideo() {

        String v = "vtest.mp4";
        String mediaPath3 = Environment.getExternalStorageDirectory() + v;
        String v2 = "v.mp4";
        String mediaPath4 = Environment.getExternalStorageDirectory() + v2;

        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.VIDEO)
                .addVideo(mediaPath3)
                .addVideo(mediaPath4)
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


            error.printStackTrace();

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
                        .image(Uri.fromFile(new File("/storage/sdcard0/DCIM/Camera/test2.jpg")))
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

        String img = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575021002547&di=4e6f5fdcea945e08516738f44b976fea&imgtype=0&src=http%3A%2F%2Fimg1.replays.net%2Fwww.replays.net%2Fuploads%2Fbody%2Fzhuzhanyezizhuwyxw%2F1441%2F212b2c283141.jpg";


//        String filename = "/DCIM/Camera/test2.jpg";
        String filename = "test2.jpg";
        String filename2 = "test.jpg";
        String filename3 = "vtest.mp4";
        String mediaPath = Environment.getExternalStorageDirectory() + filename;
        String mediaPath2 = Environment.getExternalStorageDirectory() + filename2;
        String mediaPath3 = Environment.getExternalStorageDirectory() + filename3;

        Uri sharedFileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", new File(mediaPath));
        Uri sharedFileUri2 = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", new File(mediaPath2));
        Uri sharedFileUri3 = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", new File(mediaPath3));


        Ts.i(sharedFileUri.toString());

        Log.d("----->", sharedFileUri.toString());
        Log.d("----->", sharedFileUri.getPath());

        TweetComposer.Builder builder = null;

        try {
            builder = new TweetComposer.Builder(this)
//                    .image(sharedFileUri3)
//                    .image(Uri.parse(img))
//                    .image(sharedFileUri)
//                    .image(sharedFileUri2)
                    .text("just setting up my Twitter Kit.")
                    .url(new URL("http://www.twitter.com"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

//        builder.show();

        assert builder != null;
        Intent intent = builder.createIntent();

//        ArrayList<Uri> imgs = new ArrayList<>();
//
//        imgs.add(sharedFileUri);
//        imgs.add(sharedFileUri2);
//
//        intent.putExtra(Intent.EXTRA_STREAM, imgs);
//        intent.setType("image/jpeg");

//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

//        startActivity(intent);

        startActivityForResult(intent, TWEET_COMPOSER_REQUEST_CODE);



        /* Tween */

  /*      final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();

        final Intent intent = new ComposerActivity.Builder(MainActivity.this)
                .session(session)
                .image(sharedFileUri)
//                .image(sharedFileUri2)
                .text("Tweet from TwitterKit!")
                .hashtags("#twitter")
                .createIntent();

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

        startActivity(intent);*/

    }

    public void shareToTwitter2() {

        String filename = "test2.jpg";
        String filename2 = "test.jpg";
        String filename3 = "vtest.mp4";
        String mediaPath = Environment.getExternalStorageDirectory() + filename;
        String mediaPath2 = Environment.getExternalStorageDirectory() + filename2;
        String mediaPath3 = Environment.getExternalStorageDirectory() + filename3;


        ShareContent content = new ShareContent.Builder()
                .setType(ShareContent.Type.VIDEO)
                .addImage(mediaPath)
                .addImage(mediaPath2)
                .addVideo(mediaPath3)
                .setTitle("海鸟")
                .setSubject("海鸟视频")
                .setText("#海鸟")
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }


    public void shareImageToInstagram() {

        String filename = "test2.jpg";

        String mediaPath = Environment.getExternalStorageDirectory() + filename;

        ShareContent content = new ShareContent.Builder()
                .addImage(mediaPath)
                .setTitle("海鸟")
                .setSubject("海鸟视频")
                .setText("#海鸟")
                .setType(ShareContent.Type.IMAGE)
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }


    public void shareVideoToInstagram() {

        String filename = "vtest.mp4";

        String mediaPath = Environment.getExternalStorageDirectory() + filename;

        ShareContent content = new ShareContent.Builder()
                .addVideo(mediaPath)
                .setTitle("海鸟")
                .setSubject("海鸟视频")
                .setText("#海鸟")
                .setType(ShareContent.Type.VIDEO)
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

    }

    private void createInstagramIntent(String type, String mediaPath) {

        // Create the URI from the media
        Uri sharedFileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", new File(mediaPath));

        Ts.i(sharedFileUri.toString());

      /*  // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);
        // Set the MIME type
        share.setType(type);
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, sharedFileUri);
        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));*/

        ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this);

//        intentBuilder.addStream(sharedFileUri);

        intentBuilder.setType(type);

        intentBuilder.setText("12324153546765");

        intentBuilder.setStream(sharedFileUri);

        Intent intent = intentBuilder.createChooserIntent();

        intent.setPackage("com.instagram.android");

//        intent.setComponent(new ComponentName("com.instagram.android", "com.instagram.share.handleractivity.ShareHandlerActivity"));

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

//        intentBuilder.startChooser();

        startActivity(intent);

    }


    private void shareToYoutube() {

        String filename = "vtest.mp4";

        String mediaPath = Environment.getExternalStorageDirectory() + filename;

        ShareContent content = new ShareContent.Builder()
                .addVideo(mediaPath)
                .setTitle("海鸟")
                .setSubject("海鸟视频")
                .setText("#海鸟")
                .setType(ShareContent.Type.VIDEO)
                .setPostId(UUID.randomUUID().toString())
                .build();

        ShareUtils.showShare(this, content, shareCallback);

//        toYouTube(MediaStoreUtils.queryUriForVideo(this, mediaPath));

    }


    void toYouTube(Uri uri) {

        Log.e("uri----->", uri.toString());

        final String type = "video/*";

        final String pkg = "com.google.android.youtube";

        Intent share = new Intent(Intent.ACTION_SEND);

        share.setType(type);

        PackageManager packageManager = getPackageManager();

        @SuppressLint("WrongConstant") List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(share, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        ResolveInfo resolveInfo = null;

        for (ResolveInfo info : resolveInfos) {
            if (pkg.equals(info.activityInfo.packageName)) {
                resolveInfo = info;
                break;
            }
        }

        share.putExtra(android.content.Intent.EXTRA_STREAM, uri);

        assert resolveInfo != null;
        share.setClassName("com.google.android.youtube", resolveInfo.activityInfo.name);
        share.putExtra(Intent.EXTRA_TITLE, "video share");

        share.putExtra(Intent.EXTRA_TEXT, "video share112376787775576856545675566557656");

        share.putExtra(Intent.EXTRA_SUBJECT, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        startActivityForResult(Intent.createChooser(share, "share to"), TWEET_COMPOSER_REQUEST_CODE);
    }


}


