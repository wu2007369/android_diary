package com.example.wuzhiming.myapplication.utils


import android.graphics.Bitmap
import android.graphics.Matrix
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest


/**
 * 图片旋转处理类
 */
class RotateTransformation(private val rotateToVertical: Boolean = false, private var rotateRotationAngle: Float = 0f) :
    BitmapTransformation() {
    //当不生效时，要手动添加如下代码，进行适配
    private val ID = "com.example.wuzhiming.myapplication.utils.RotateTransformation"
    private val ID_BYTES = ID.toByteArray(CHARSET)

//    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
//    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        rotateRotationAngle = if (rotateToVertical) {
            if (toTransform.width > toTransform.height) 90f else 0f
        } else {
            rotateRotationAngle
        }
        val matrix = Matrix()
        matrix.postRotate(rotateRotationAngle)
        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.width, toTransform.height, matrix, true)
    }

    override fun equals(o: Any?): Boolean {
        return o is RotateTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }
}