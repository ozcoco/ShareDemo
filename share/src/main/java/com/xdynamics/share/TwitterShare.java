package com.xdynamics.share;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.xdynamics.share.services.ShareNotificationHandle;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TwitterShare implements Destroyable {

    private final ShareContent content;

    private WeakReference<Activity> activity;

    private WeakReference<Fragment> fragment;

    private ShareCallback callback;

    private BroadcastReceiver callbackReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (callback == null) return;

            if (Constants.Twitter.CALLBACK_RECEIVER_ACTION.equals(intent.getAction())) {

                final ShareNotificationHandle handle = (ShareNotificationHandle) intent.getSerializableExtra(Constants.INTENT_EXTRA_DATA);

                final int state = intent.getIntExtra(Constants.INTENT_STATE, -1);

                switch (state) {

                    case Constants.STATE_CANCEL:

                        if (callback != null)
                            callback.onCancel();

                        break;

                    case Constants.STATE_ERROR:

                        final String error = intent.getStringExtra(Constants.INTENT_ERROR);

                        if (callback != null)
                            callback.onError(new ShareException(error));

                        break;

                    case Constants.STATE_SUCCESS:

                        if (callback != null)
                            callback.onSuccess(content.getPostId());

                        break;

                }

            }


        }
    };


    private void register() {

        IntentFilter filter = new IntentFilter();

        filter.addAction(Constants.Twitter.CALLBACK_RECEIVER_ACTION);

        if (activity != null && activity.get() != null) {

            LocalBroadcastManager.getInstance(activity.get()).registerReceiver(callbackReceiver, filter);

        } else {
            LocalBroadcastManager.getInstance(Objects.requireNonNull(fragment.get().getContext())).registerReceiver(callbackReceiver, filter);
        }


    }

    private void unregister() {

        if (activity != null && activity.get() != null) {

            IntentFilter filter = new IntentFilter();

            LocalBroadcastManager.getInstance(activity.get()).registerReceiver(callbackReceiver, filter);

        } else {
            LocalBroadcastManager.getInstance(Objects.requireNonNull(fragment.get().getContext())).unregisterReceiver(callbackReceiver);
        }
    }


    public TwitterShare(Activity activity, ShareContent content, ShareCallback callback) throws NullPointerException {

        if (activity == null)
            throw new IllegalArgumentException("activity != null ");
        if (content == null)
            throw new IllegalArgumentException("content != null ");
        if (callback == null)
            throw new IllegalArgumentException("callback != null ");

        this.activity = new WeakReference<>(activity);
        this.content = content;
        this.callback = callback;
        init(callback);
    }

    public TwitterShare(Fragment fragment, ShareContent content, ShareCallback callback) throws NullPointerException {

        if (fragment == null)
            throw new IllegalArgumentException("fragment != null ");
        if (content == null)
            throw new IllegalArgumentException("content != null ");
        if (callback == null)
            throw new IllegalArgumentException("callback != null ");

        this.fragment = new WeakReference<>(fragment);
        this.content = content;
        this.callback = callback;
        init(callback);
    }


    private void init(final ShareCallback callback) {

        if (callback != null)
            callback.setOnActivityResult(new ShareCallback.OnActivityResultCallback() {
                @Override
                public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

                    if (requestCode == Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE) {

                        if (resultCode == Activity.RESULT_CANCELED) {
                            callback.onCancel();
                        }
                    }

                    return false;
                }
            });

        register();

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

        try {
            if (activity != null && activity.get() != null) {

                TweetComposer.Builder builder = new TweetComposer.Builder(activity.get())
                        .text(content.getQuote())
                        .url(new URL(content.getLink()));

                final Intent intent = builder.createIntent();


                activity.get().startActivityForResult(intent, Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE);

            } else {

                TweetComposer.Builder builder = new TweetComposer.Builder(Objects.requireNonNull(fragment.get().getContext()))
                        .text(content.getQuote())
                        .url(new URL(content.getLink()));

                final Intent intent = builder.createIntent();

                fragment.get().startActivityForResult(intent, Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void shareImage() {

        if (activity != null && activity.get() != null) {

            List<Uri> uris = new ArrayList<>();

            for (String path : content.getImagePathList()) {

                uris.add(FileProvider.getUriForFile(activity.get(), activity.get().getPackageName() + ".fileProvider", new File(path)));
            }

            TweetComposer.Builder builder = new TweetComposer.Builder(activity.get());

            if (!TextUtils.isEmpty(content.getQuote()))
                builder.text(content.getQuote());

            if (!TextUtils.isEmpty(content.getLink())) {
                try {
                    builder.url(new URL(content.getLink()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if (!uris.isEmpty()) {
                builder.image(uris.get(0));
            }


            final Intent intent = builder.createIntent();

            activity.get().startActivityForResult(intent, Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE);

        } else {

            List<Uri> uris = new ArrayList<>();

            for (String path : content.getImagePathList()) {

                uris.add(FileProvider.getUriForFile(fragment.get().getContext(), fragment.get().getContext().getPackageName() + ".fileProvider", new File(path)));
            }

            TweetComposer.Builder builder = new TweetComposer.Builder(fragment.get().getContext());

            if (!TextUtils.isEmpty(content.getQuote()))
                builder.text(content.getQuote());

            if (!TextUtils.isEmpty(content.getLink())) {
                try {
                    builder.url(new URL(content.getLink()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if (!uris.isEmpty()) {
                builder.image(uris.get(0));
            }

            final Intent intent = builder.createIntent();

            fragment.get().startActivityForResult(intent, Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE);

        }

    }

    private void shareMedia() {

        if (activity != null && activity.get() != null) {

            List<Uri> uris = new ArrayList<>();

            for (String path : content.getImagePathList()) {

                uris.add(FileProvider.getUriForFile(activity.get(), activity.get().getPackageName() + ".fileProvider", new File(path)));
            }

            TweetComposer.Builder builder = new TweetComposer.Builder(activity.get());

            if (!TextUtils.isEmpty(content.getQuote()))
                builder.text(content.getQuote());

            if (!TextUtils.isEmpty(content.getLink())) {
                try {
                    builder.url(new URL(content.getLink()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if (!uris.isEmpty()) {
                builder.image(uris.get(0));
            }


            final Intent intent = builder.createIntent();

            activity.get().startActivityForResult(intent, Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE);

        } else {

            List<Uri> uris = new ArrayList<>();

            for (String path : content.getImagePathList()) {

                uris.add(FileProvider.getUriForFile(fragment.get().getContext(), fragment.get().getContext().getPackageName() + ".fileProvider", new File(path)));
            }

            TweetComposer.Builder builder = new TweetComposer.Builder(fragment.get().getContext());

            if (!TextUtils.isEmpty(content.getQuote()))
                builder.text(content.getQuote());

            if (!TextUtils.isEmpty(content.getLink())) {
                try {
                    builder.url(new URL(content.getLink()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if (!uris.isEmpty()) {
                builder.image(uris.get(0));
            }

            final Intent intent = builder.createIntent();

            fragment.get().startActivityForResult(intent, Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE);

        }
    }


    private void shareVideo() {

        if (activity != null && activity.get() != null) {

            List<Uri> uris = new ArrayList<>();

            for (String path : content.getImagePathList()) {

                uris.add(FileProvider.getUriForFile(activity.get(), activity.get().getPackageName() + ".fileProvider", new File(path)));
            }

            TweetComposer.Builder builder = new TweetComposer.Builder(activity.get());

            if (!TextUtils.isEmpty(content.getQuote()))
                builder.text(content.getQuote());

            if (!TextUtils.isEmpty(content.getLink())) {
                try {
                    builder.url(new URL(content.getLink()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if (!uris.isEmpty()) {
                builder.image(uris.get(0));
            }


            final Intent intent = builder.createIntent();

            activity.get().startActivityForResult(intent, Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE);

        } else {

            List<Uri> uris = new ArrayList<>();

            for (String path : content.getVideoPathList()) {

                uris.add(FileProvider.getUriForFile(fragment.get().getContext(), fragment.get().getContext().getPackageName() + ".fileProvider", new File(path)));
            }

            TweetComposer.Builder builder = new TweetComposer.Builder(fragment.get().getContext());

            if (!TextUtils.isEmpty(content.getQuote()))
                builder.text(content.getQuote());

            if (!TextUtils.isEmpty(content.getLink())) {
                try {
                    builder.url(new URL(content.getLink()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if (!uris.isEmpty()) {
                builder.image(uris.get(0));
            }

            final Intent intent = builder.createIntent();

            fragment.get().startActivityForResult(intent, Constants.Twitter.TWEET_COMPOSER_REQUEST_CODE);

        }
    }

    @Override
    public void destroy() {
        unregister();
    }
}
