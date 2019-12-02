package com.xdynamics.share.bean;

import android.text.TextUtils;


/*
 * YouTube分享内容包装器
 * */
public class YouTubeContentWrapper extends ContentWrapper {

    private String title;

    private String tags;

    private String description;

    private String videoPath;

    public String getTitle() {
        return title;
    }

    public String getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public static class Builder {

        private String title;

        private String tags;

        private String description;

        private String videoPath;

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

        public Builder setVideoPath(String videoPath) {
            this.videoPath = videoPath;
            return this;
        }

        public YouTubeContentWrapper build() {

            if (TextUtils.isEmpty(videoPath))
                throw new IllegalArgumentException("videoPath != null");

            YouTubeContentWrapper wrapper = new YouTubeContentWrapper();

            wrapper.title = title;
            wrapper.tags = tags;
            wrapper.description = description;
            wrapper.videoPath = videoPath;

            return wrapper;
        }

    }

}
