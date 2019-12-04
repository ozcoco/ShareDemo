package com.xdynamics.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.widget.ShareDialog;
import com.xdynamics.share.bean.ShareContent;

import java.io.File;
import java.lang.ref.WeakReference;

public class SystemShare {

    private CallbackManager callbackManager;

    private ShareDialog shareDialog;

    private final ShareContent content;

    private WeakReference<AppCompatActivity> activity;

    private WeakReference<Fragment> fragment;

    public SystemShare(AppCompatActivity activity, ShareContent content, ShareCallback callback) throws NullPointerException {

        if (activity == null)
            throw new NullPointerException("activity != null ");
        if (content == null)
            throw new NullPointerException("content != null ");
        if (callback == null)
            throw new NullPointerException("callback != null ");

        this.activity = new WeakReference<>(activity);
        this.content = content;
        init(callback);
    }

    public SystemShare(Fragment fragment, ShareContent content, ShareCallback callback) throws NullPointerException {

        if (fragment == null)
            throw new NullPointerException("fragment != null ");
        if (content == null)
            throw new NullPointerException("content != null ");
        if (callback == null)
            throw new NullPointerException("callback != null ");

        this.fragment = new WeakReference<>(fragment);
        this.content = content;
        init(callback);
    }

    private void init(final ShareCallback callback) {

        callbackManager = CallbackManager.Factory.create();

        if (callback != null)
            callback.setOnActivityResult(new ShareCallback.OnActivityResultCallback() {
                @Override
                public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                    return callbackManager.onActivityResult(requestCode, resultCode, data);
                }
            });

        if (activity != null && activity.get() != null)
            shareDialog = new ShareDialog(activity.get());
        else
            shareDialog = new ShareDialog(fragment.get());

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {


                if (callback != null)
                    callback.onSuccess(result.getPostId());

            }

            @Override
            public void onCancel() {

                if (callback != null)
                    callback.onCancel();

            }

            @Override
            public void onError(FacebookException error) {

                if (callback != null)
                    callback.onError(new ShareException(error));

                error.printStackTrace();
            }
        });

    }


    public void show() {

        switch (content.getType()) {

            case LINK:
                shareLink();
                break;
            case IMAGE:
                shareImage();
                break;
            case VIDEO:
                shareVideo();
                break;
            case MEDIA:
                shareMedia();
                break;
        }

    }


    private void shareLink() {

        if (ShareDialog.canShow(ShareLinkContent.class)) {

            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(this.content.getLink()))
                    .setQuote(this.content.getQuote())
                    .setPageId(this.content.getPostId())
                    .build();

            shareDialog.show(content);
        }

    }

    private void shareImage() {

        if (ShareDialog.canShow(ShareMediaContent.class)) {

            ShareMediaContent.Builder builder = new ShareMediaContent.Builder();

            for (Bitmap bmp : this.content.getImageBitmapList()) {

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bmp)
                        .build();

                builder.addMedium(photo);

            }

            builder.setPageId(this.content.getPostId());

            shareDialog.show(builder.build(), ShareDialog.Mode.AUTOMATIC);
        }


    }

    private void shareMedia() {

        if (ShareDialog.canShow(ShareMediaContent.class)) {

            ShareMediaContent.Builder builder = new ShareMediaContent.Builder();

            for (Bitmap bmp : this.content.getImageBitmapList()) {

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bmp)
                        .build();

                builder.addMedium(photo);

            }

            for (String path : this.content.getVideoPathList()) {

                ShareVideo photo = new ShareVideo.Builder()
                        .setLocalUrl(Uri.fromFile(new File(path)))
                        .build();

                builder.addMedium(photo);
            }
            builder.setPageId(this.content.getPostId());

            shareDialog.show(builder.build(), ShareDialog.Mode.AUTOMATIC);
        }

    }


    private void shareVideo() {

        if (ShareDialog.canShow(ShareMediaContent.class)) {

            ShareMediaContent.Builder builder = new ShareMediaContent.Builder();

            for (String path : this.content.getVideoPathList()) {

                ShareVideo photo = new ShareVideo.Builder()
                        .setLocalUrl(Uri.fromFile(new File(path)))
                        .build();

                builder.addMedium(photo);
            }

            builder.setPageId(this.content.getPostId());

            shareDialog.show(builder.build(), ShareDialog.Mode.AUTOMATIC);
        }

    }

}
