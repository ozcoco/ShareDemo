package com.xdynamics.share;

import android.content.Intent;

public abstract class ShareCallback {

    public interface OnActivityResultCallback {
        boolean onActivityResult(int requestCode, int resultCode, Intent data);
    }

    private OnActivityResultCallback onActivityResult;

    public OnActivityResultCallback getOnActivityResult() {
        return onActivityResult;
    }

    public void setOnActivityResult(OnActivityResultCallback onActivityResult) {
        this.onActivityResult = onActivityResult;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

        if (onActivityResult != null)
            return onActivityResult.onActivityResult(requestCode, resultCode, data);

        return false;
    }

    public abstract void onSuccess(String postId);

    public abstract void onCancel();

    public abstract void onError(ShareException error);

}
