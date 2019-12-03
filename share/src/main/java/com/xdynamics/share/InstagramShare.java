package com.xdynamics.share;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.xdynamics.share.bean.InstagramContentWrapper;
import com.xdynamics.share.services.ShareNotificationHandle;
import com.xdynamics.share.utils.MediaStoreUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

public class InstagramShare implements Destroyable {

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


    public InstagramShare(Activity activity, ShareContent content, ShareCallback callback) throws IllegalArgumentException {

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

    public InstagramShare(Fragment fragment, ShareContent content, ShareCallback callback) throws IllegalArgumentException {

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

                    if (requestCode == Constants.Instagram.REQUEST_CODE) {

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

        if (content.getType() == ShareContent.Type.VIDEO) {

            shareVideo();

        } else if (content.getType() == ShareContent.Type.IMAGE) {
            shareImage();
        }

    }


    private void shareImage() {

        if (activity != null && activity.get() != null) {

            Intent share = new Intent(Intent.ACTION_SEND);

            share.setType(Constants.MEDIA_TYPE_IMAGE);

            final InstagramContentWrapper wrapper = (InstagramContentWrapper) content.getContentWrapper();

            share.putExtra(android.content.Intent.EXTRA_STREAM, MediaStoreUtils.queryUriForImage(activity.get().getApplication(), wrapper.getMediaPath()));

            share.setClassName(Constants.Instagram.packageName, Constants.Instagram.shareVideoActivityName);//注意这里Activity名不能写死，因为有些app升级后分享页面的路径或者名称会更改(比如Instagram)

            if (!TextUtils.isEmpty(wrapper.getTitle()))
                share.putExtra(Intent.EXTRA_TITLE, wrapper.getTitle());

            if (!TextUtils.isEmpty(wrapper.getTags()))
                share.putExtra(Intent.EXTRA_TEXT, wrapper.getTags());

            if (!TextUtils.isEmpty(wrapper.getDescription()))
                share.putExtra(Intent.EXTRA_SUBJECT, wrapper.getDescription());

            activity.get().startActivityForResult(Intent.createChooser(share, wrapper.getTitle()), Constants.Instagram.REQUEST_CODE);

        } else {

            Intent share = new Intent(Intent.ACTION_SEND);

            share.setType(Constants.MEDIA_TYPE_VIDEO);

            final InstagramContentWrapper wrapper = (InstagramContentWrapper) content.getContentWrapper();

            share.putExtra(android.content.Intent.EXTRA_STREAM, MediaStoreUtils.queryUriForImage(activity.get().getApplication(), wrapper.getMediaPath()));

            share.setClassName(Constants.Instagram.packageName, Constants.Instagram.shareVideoActivityName);//注意这里Activity名不能写死，因为有些app升级后分享页面的路径或者名称会更改(比如Instagram)

            if (!TextUtils.isEmpty(wrapper.getTitle()))
                share.putExtra(Intent.EXTRA_TITLE, wrapper.getTitle());

            if (!TextUtils.isEmpty(wrapper.getTags()))
                share.putExtra(Intent.EXTRA_TEXT, wrapper.getTags());

            if (!TextUtils.isEmpty(wrapper.getDescription()))
                share.putExtra(Intent.EXTRA_SUBJECT, wrapper.getDescription());

            fragment.get().startActivityForResult(Intent.createChooser(share, wrapper.getTitle()), Constants.Instagram.REQUEST_CODE);

        }

    }

    private void shareVideo() {

        if (activity != null && activity.get() != null) {

            Intent share = new Intent(Intent.ACTION_SEND);

            share.setType(Constants.MEDIA_TYPE_VIDEO);

            PackageManager packageManager = activity.get().getPackageManager();

            @SuppressLint("WrongConstant") List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(share, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

            ResolveInfo resolveInfo = null;

            for (ResolveInfo info : resolveInfos) {
                if (Constants.Instagram.packageName.equals(info.activityInfo.packageName)) {
                    resolveInfo = info;
                    break;
                }
            }

            final InstagramContentWrapper wrapper = (InstagramContentWrapper) content.getContentWrapper();

            share.putExtra(android.content.Intent.EXTRA_STREAM, MediaStoreUtils.queryUriForVideo(activity.get().getApplication(), wrapper.getMediaPath()));

            assert resolveInfo != null;
            share.setClassName(Constants.Instagram.packageName, resolveInfo.activityInfo.name);//注意这里Activity名不能写死，因为有些app升级后分享页面的路径或者名称会更改(比如Instagram)

            if (!TextUtils.isEmpty(wrapper.getTitle()))
                share.putExtra(Intent.EXTRA_TITLE, wrapper.getTitle());

            if (!TextUtils.isEmpty(wrapper.getTags()))
                share.putExtra(Intent.EXTRA_TEXT, wrapper.getTags());

            if (!TextUtils.isEmpty(wrapper.getDescription()))
                share.putExtra(Intent.EXTRA_SUBJECT, wrapper.getDescription());

            activity.get().startActivityForResult(Intent.createChooser(share, wrapper.getTitle()), Constants.Instagram.REQUEST_CODE);

        } else {

            Intent share = new Intent(Intent.ACTION_SEND);

            share.setType(Constants.MEDIA_TYPE_VIDEO);

            PackageManager packageManager = fragment.get().getContext().getPackageManager();

            @SuppressLint("WrongConstant") List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(share, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

            ResolveInfo resolveInfo = null;

            for (ResolveInfo info : resolveInfos) {
                if (Constants.Instagram.packageName.equals(info.activityInfo.packageName)) {
                    resolveInfo = info;
                    break;
                }
            }

            final InstagramContentWrapper wrapper = (InstagramContentWrapper) content.getContentWrapper();

            share.putExtra(android.content.Intent.EXTRA_STREAM, MediaStoreUtils.queryUriForVideo(fragment.get().getContext(), wrapper.getMediaPath()));

            assert resolveInfo != null;
            share.setClassName(Constants.Instagram.packageName, resolveInfo.activityInfo.name);//注意这里Activity名不能写死，因为有些app升级后分享页面的路径或者名称会更改(比如Instagram)

            if (!TextUtils.isEmpty(wrapper.getTitle()))
                share.putExtra(Intent.EXTRA_TITLE, wrapper.getTitle());

            if (!TextUtils.isEmpty(wrapper.getTags()))
                share.putExtra(Intent.EXTRA_TEXT, wrapper.getTags());

            if (!TextUtils.isEmpty(wrapper.getDescription()))
                share.putExtra(Intent.EXTRA_SUBJECT, wrapper.getDescription());

            fragment.get().startActivityForResult(Intent.createChooser(share, wrapper.getTitle()), Constants.Instagram.REQUEST_CODE);

        }
    }

    @Override
    public void destroy() {
        unregister();
    }

}
