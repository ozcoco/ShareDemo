package com.xdynamics.share;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.xdynamics.share.databinding.ActivityMainBinding;

import org.oz.utils.Ts;

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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        shareCallback.onActivityResult(requestCode, resultCode, data);

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
        } else if (v.equals(mBinding.btnUi)) {

            share2();
        } else {
            share();
        }
    }


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
}
