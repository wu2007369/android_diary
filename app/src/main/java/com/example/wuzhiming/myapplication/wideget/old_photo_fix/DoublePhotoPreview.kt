package com.example.wuzhiming.myapplication.wideget.old_photo_fix

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.utils.BitmapUtils


/**
 * @Author:         wuzm
 * @CreateDate:     2022/12/28 10:16 AM
 * @Description:    双层照片 的xfermode 试验
 */
class DoublePhotoPreview @JvmOverloads constructor(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var destBitmap: Bitmap =
        BitmapFactory.decodeResource(getContext().getResources(), R.drawable.image2)
    private var srcBitmap: Bitmap =
        BitmapFactory.decodeResource(getContext().getResources(), R.drawable.image3)

    init {
        paint.style = Paint.Style.FILL;

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

/*    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 设置背景
        canvas.drawColor(Color.DKGRAY)

        destBitmap=BitmapUtils.resizeImage(destBitmap,width.toFloat(),height.toFloat())

        srcBitmap=BitmapUtils.resizeImage(srcBitmap,width.toFloat(),height.toFloat())
        srcBitmap= Bitmap.createBitmap(srcBitmap,0,0,width/2,height)
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        val saveCount =
            canvas.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        // 绘制 dest
        canvas.drawBitmap(destBitmap, 0F, 0F, paint)
        // 设置 xfermode
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
        // 绘制 src
        canvas.drawBitmap(srcBitmap, 0F, 0F, paint)
        paint.xfermode = null
        canvas.restoreToCount(saveCount)
    }*/

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 设置背景(虽然叫做destBitmap。其实是作backgroundBitmap)
        canvas.drawBitmap(destBitmap, 0F, 0F, paint)


        srcBitmap=BitmapUtils.resizeImage(srcBitmap,width.toFloat(),height.toFloat())

        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        val saveCount =
            canvas.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        // 绘制 dest（虽然叫做srcBitmap。其实是作DestBitmap）
        canvas.drawBitmap(srcBitmap, 0F, 0F, paint)
        // 设置 xfermode
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
        // 绘制 src
        canvas.drawRect( 0F, 0F,width.toFloat()/2,height.toFloat(), paint)
        paint.xfermode = null
        canvas.restoreToCount(saveCount)
    }
}