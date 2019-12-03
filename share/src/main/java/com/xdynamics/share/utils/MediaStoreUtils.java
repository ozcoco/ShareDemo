package com.xdynamics.share.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MediaStoreUtils {


    /**
     * 查找视频文件对应于MediaStore的Uri
     *
     * @param path 视频文件
     * @return
     */

    public static Uri queryUriForVideo(Context context, String path) {

        if (context == null) throw new IllegalArgumentException("context != null");

        int id = getVideoId(context, path);
        if (id == -1) {
            return insertVideo(context, path);
        }
        return Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
    }

    /**
     * 插入视频到MediaStore
     */
    private static Uri insertVideo(Context context, String mediaPath) {
        ContentValues content = new ContentValues(4);
        content.put(MediaStore.Video.VideoColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
        content.put(MediaStore.Video.Media.MIME_TYPE, "video/*");
        content.put(MediaStore.Video.Media.DATA, mediaPath);
        ContentResolver resolver = context.getContentResolver();
        return resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);
    }


    private static int getVideoId(Context context, String path) {

        int id = -1;

        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DISPLAY_NAME};

        final String where = MediaStore.Video.Media.DATA + "=" + "?";

        ContentResolver contentResolver = context.getContentResolver();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, mediaColumns, where, new String[]{path}, null);

        if (cursor == null) {
            return -1;
        }
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));
            } while (cursor.moveToNext());
        }
        return id;
    }


    /**
     * 查找图片文件对应于MediaStore的Uri
     *
     * @param path 图片文件
     * @return
     */

    public static Uri queryUriForImage(Context context, String path) {

        if (context == null) throw new IllegalArgumentException("context != null");

        int id = getImageId(context, path);
        if (id == -1) {
            return insertImage(context, path);
        }
        return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
    }


    /**
     * 插入图片到MediaStore
     */
    private static Uri insertImage(Context context, String mediaPath) {
        ContentValues content = new ContentValues(4);
        content.put(MediaStore.Images.ImageColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
        content.put(MediaStore.Images.Media.MIME_TYPE, "video/*");
        content.put(MediaStore.Images.Media.DATA, mediaPath);
        ContentResolver resolver = context.getContentResolver();
        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content);
    }

    private static int getImageId(Context context, String path) {

        int id = -1;

        String[] mediaColumns = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DISPLAY_NAME};

        final String where = MediaStore.Images.Media.DATA + "=" + "?";

        ContentResolver contentResolver = context.getContentResolver();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, mediaColumns, where, new String[]{path}, null);

        if (cursor == null) {
            return -1;
        }
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Images.Media._ID));
            } while (cursor.moveToNext());
        }
        return id;
    }

}
