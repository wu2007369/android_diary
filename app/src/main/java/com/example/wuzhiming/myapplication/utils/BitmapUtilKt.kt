package com.example.wuzhiming.myapplication.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import java.io.File

/**
 * @Author:         wuzm
 * @CreateDate:     2022/1/4 5:51 下午
 * @Description:    类作用描述
 */
object BitmapUtilKt {
    const val SUFFIX_JPG = "jpg"
    const val SUFFIX_PNG = "png"

    /*--------------------------------图片保存到相册----------------------------------------------*/

    /**
     * bitmap导出到相册
     */
    fun cover2Local(bitmap: Bitmap, activity: Activity, picUrl: String): Boolean {
        //将文件保存到公共目录
        val currentMills = System.currentTimeMillis()
        val fileName = "mmexport$currentMills"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "this is xiaobai`s image")
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, getMimeType(picUrl))
        contentValues.put(MediaStore.Images.Media.SIZE, bitmap.byteCount)
        contentValues.put(MediaStore.Images.Media.WIDTH, bitmap.width)
        contentValues.put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        if (OSUtils.isAndroidQ()) {
            contentValues.put(MediaStore.Images.Media.DATE_TAKEN, currentMills)
            //TODO 待恢复
//            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/XiaoBai")
            //设置true，禁止第三方app访问
//                                  contentValues.put(MediaStore.Images.Media.IS_PENDING,true);
        } else {
//            val parent = File(
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                "XiaoBai"
//            )
            val parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

            if (!parent.exists()) {
                parent.mkdirs()
            }
            val temp = File(parent, fileName)
            contentValues.put(MediaStore.Images.Media.DATE_ADDED, currentMills)
            contentValues.put(MediaStore.Images.Media.DATA, temp.absolutePath)
        }
        // content://media/external/images/media/31
        val contentResolver: ContentResolver = activity.getContentResolver()
        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                ?: return false
        val outputStream = contentResolver.openOutputStream(uri)
        bitmap.compress(getCompressType(picUrl), 100, outputStream)
        return true
    }

    private fun getCompressType(imageUrl: String?): Bitmap.CompressFormat {
        return if (imageUrl?.endsWith("jpg") == true || imageUrl?.endsWith("jpeg") == true) {
            Bitmap.CompressFormat.JPEG
        } else {
            Bitmap.CompressFormat.PNG
        }
    }

    private fun getMimeType(imageUrl: String?): String {
        return if (imageUrl?.endsWith("jpg") == true || imageUrl?.endsWith("jpeg") == true) {
            "image/JPEG"
        } else {
            "image/PNG"
        }
    }
}