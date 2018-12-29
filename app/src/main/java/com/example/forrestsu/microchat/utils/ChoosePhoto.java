package com.example.forrestsu.microchat.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class ChoosePhoto {

    private static final String TAG = "ChoosePhoto";

    public static final int TAKE_PHOTO = 0;
    public static final int CHOOSE_PHOTO = 1;

    /**
     * 拍摄照片
     * @param mActivity 请求拍照的活动
     * @param savePath 保存的路径
     * @param saveName 保存的文件名
     */
    public static void takePhoto(Activity mActivity, String savePath, String saveName) {
        if (savePath == null) {
            savePath = Environment.DIRECTORY_DCIM;
        }
        if (saveName == null) {
            saveName = TimeUtils.stampToDate(System.currentTimeMillis()) + ".jpg";
        }
        //创建File对象，用于存储拍摄后的照片
        File outPutImage =  new File(savePath, saveName);
        try {
            if (outPutImage.exists()) {
                outPutImage.delete();
            } else {
                outPutImage.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri imageUri;
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(mActivity,
                    "com.example.forrestsu.task10.fileprovider", outPutImage);
        } else {
            imageUri = Uri.fromFile(outPutImage);
        }
        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        mActivity.startActivityForResult(intent, TAKE_PHOTO);
    }


    /**
     * 打开相册选择图片
     * @param mActivity 请求打开相册的活动
     */
    public static void openAlbum(Activity mActivity) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     *获取图片路径
     * @param context
     * @param data
     */
    public static String getImagePath(Context context, Intent data) {
        String imagePath="";
        //判断手机版本号
        if (Build.VERSION.SDK_INT >= 19) {
            imagePath = handleImageOnKitKat(context, data);
            Log.i(TAG, "onActivityResult: path:" + imagePath);
        } else {
            imagePath = handleImageBeforeKitKat(context, data);
        }
        return imagePath;
    }


    /**获取图片路径
     * 获取图片路径，Android_4.4以上系统
     * @param context
     * @param data
     * @return
     */
    @RequiresApi(api = 19)
    private static String handleImageOnKitKat(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的uri，通过document id来处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.document".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse(
                        "content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则用普通方式处理
            imagePath = getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }


    /**
     * 获取图片路径，Android_4.4以下的系统
     * @param context
     * @param data
     * @return
     */
    private static String handleImageBeforeKitKat(Context context, Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(context, uri, null);
        return imagePath;
    }


    /**
     * 获取图片路径
     * @param context
     * @param uri
     * @param selection
     * @return
     */
    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取图片的真实路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
