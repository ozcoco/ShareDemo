package com.xdynamics.share.bean;

import android.text.TextUtils;


/*
 * YouTube分享内容包装器
 * */
public class InstagramContentWrapper extends ContentWrapper {

    private String title;

    private String tags;

    private String description;

    private String mediaPath;

    public String getTitle() {
        return title;
    }

    public String getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public static class Builder {

        private String title;

        private String tags;

        private String description;

        private String mediaPath;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTags(String tags) {
            this.tags = tags;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setMediaPath(String mediaPath) {
            this.mediaPath = mediaPath;
            return this;
        }

        public InstagramContentWrapper build() {

            if (TextUtils.isEmpty(mediaPath))
                throw new IllegalArgumentException("mediaPath != null");

            InstagramContentWrapper wrapper = new InstagramContentWrapper();

            wrapper.title = title;
            wrapper.tags = tags;
            wrapper.description = description;
            wrapper.mediaPath = mediaPath;

            return wrapper;
        }

    }

}
