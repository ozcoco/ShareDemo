package com.xdynamics.share;

import android.graphics.Bitmap;

import com.xdynamics.share.bean.ContentWrapper;

import java.util.ArrayList;
import java.util.List;

public class ShareContent {

    public enum Type {
        LINK,  //连接分享
        IMAGE,  //图片分享
        VIDEO,  //视频分享
        MEDIA,   //图片和视频分享
    }

    private String postId;

    private ShareContent.Type type;  //分享类型

    private String link;    //连接

    private String quote;   //摘要

    private List<Bitmap> imageBitmapList;

    private List<String> videoPathList;

    private List<String> imagePathList;

    private ContentWrapper contentWrapper;

    private ShareContent() {
    }

    public String getPostId() {
        return postId;
    }

    public Type getType() {
        return type;
    }

    public String getLink() {
        return link;
    }

    public String getQuote() {
        return quote;
    }

    public List<Bitmap> getImageBitmapList() {
        return imageBitmapList;
    }

    public List<String> getVideoPathList() {
        return videoPathList;
    }

    public List<String> getImagePathList() {
        return imagePathList;
    }

    public ContentWrapper getContentWrapper() {
        return contentWrapper;
    }

    public static class Builder {

        private String postId;

        private ShareContent.Type type;  //分享类型

        private String link;    //连接

        private String quote;   //摘要

        private List<Bitmap> imageBitmapList = new ArrayList<>();

        private List<String> videoPathList = new ArrayList<>();

        private List<String> imagePathList = new ArrayList<>();

        private ContentWrapper contentWrapper;

        public Builder setPostId(String postId) {
            this.postId = postId;
            return this;
        }

        public Builder setType(ShareContent.Type type) {

            this.type = type;

            return this;
        }

        public Builder setLink(String link) {

            this.link = link;

            return this;
        }

        public Builder setQuote(String quote) {
            this.quote = quote;
            return this;
        }

        public Builder addImage(Bitmap bmp) {

            imageBitmapList.add(bmp);

            return this;
        }

        public Builder addImage(List<Bitmap> bmps) {

            imageBitmapList.addAll(bmps);

            return this;
        }

        public Builder addVideo(String filename) {

            videoPathList.add(filename);

            return this;
        }

        public Builder addImage(String filename) {

            imagePathList.add(filename);

            return this;
        }

        public Builder addVideo(List<String> filenames) {

            videoPathList.addAll(filenames);

            return this;
        }

        public Builder addImagePath(List<String> filenames) {

            imagePathList.addAll(filenames);

            return this;
        }

        public Builder setContentWrapper(ContentWrapper contentWrapper) {
            this.contentWrapper = contentWrapper;
            return this;
        }

        public ShareContent build() {

            if (type == null) throw new NullPointerException(" type != null");

            if (type == ShareContent.Type.LINK && link == null)
                throw new NullPointerException(" link != null");

            ShareContent content = new ShareContent();

            content.postId = postId;

            content.type = type;

            content.link = link;

            content.quote = quote;

            content.imageBitmapList = imageBitmapList;

            content.videoPathList = videoPathList;

            content.imagePathList = imagePathList;

            content.contentWrapper = contentWrapper;

            return content;

        }


    }

}
