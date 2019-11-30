package com.xdynamics.share;

import android.graphics.Bitmap;

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

    public static class Builder {

        private String postId;

        private ShareContent.Type type;  //分享类型

        private String link;    //连接

        private String quote;   //摘要

        private List<Bitmap> imageBitmapList = new ArrayList<>();

        private List<String> videoPathList = new ArrayList<>();

        private List<String> imagePathList = new ArrayList<>();


        public ShareContent.Builder setPostId(String postId) {
            this.postId = postId;
            return this;
        }

        public ShareContent.Builder setType(ShareContent.Type type) {

            this.type = type;

            return this;
        }

        public ShareContent.Builder setLink(String link) {

            this.link = link;

            return this;
        }

        public ShareContent.Builder setQuote(String quote) {
            this.quote = quote;
            return this;
        }

        public ShareContent.Builder addImage(Bitmap bmp) {

            imageBitmapList.add(bmp);

            return this;
        }

        public ShareContent.Builder addImage(List<Bitmap> bmps) {

            imageBitmapList.addAll(bmps);

            return this;
        }

        public ShareContent.Builder addVideo(String filename) {

            videoPathList.add(filename);

            return this;
        }

        public ShareContent.Builder addImage(String filename) {

            imagePathList.add(filename);

            return this;
        }

        public ShareContent.Builder addVideo(List<String> filenames) {

            videoPathList.addAll(filenames);

            return this;
        }

        public ShareContent.Builder addImagePath(List<String> filenames) {

            imagePathList.addAll(filenames);

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

            return content;

        }


    }

}
