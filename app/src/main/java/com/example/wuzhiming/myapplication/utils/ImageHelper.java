package com.example.wuzhiming.myapplication.utils;

import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: mango
 * @time: 2020/3/3 11:07
 * @desc:
 * @修订历史：
 *      https://blog.csdn.net/liu_jing_hui/article/details/62416876
 *     光圈值        String TAG_APERTURE = exifInterface.getAttribute(ExifInterface.TAG_APERTURE);
 *     拍摄时间      String TAG_DATETIME = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
 *     曝光时间      String TAG_EXPOSURE_TIME = exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
 *     闪光灯        String TAG_FLASH = exifInterface.getAttribute(ExifInterface.TAG_FLASH);
 *     焦距          String TAG_FOCAL_LENGTH = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
 *     图片高度      String TAG_IMAGE_LENGTH = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
 *     图片宽度      String TAG_IMAGE_WIDTH = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
 *     ISO           String TAG_ISO = exifInterface.getAttribute(ExifInterface.TAG_ISO);
 *     设备品牌      String TAG_MAKE = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
 *     设备型号      String TAG_MODEL = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
 *     旋转角度      String TAG_ORIENTATION = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
 *     白平衡        String TAG_WHITE_BALANCE = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
 */
public class ImageHelper {

    /**
     * 获取图片的旋转角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    @Deprecated
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static int getBitmapDegree(InputStream stream) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(stream);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getBitmapDegree(Context context, Uri uri) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd == null) {
                return degree;
            }
            ExifInterface exifInterface = new ExifInterface(pfd.getFileDescriptor());
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
            }
            pfd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}
