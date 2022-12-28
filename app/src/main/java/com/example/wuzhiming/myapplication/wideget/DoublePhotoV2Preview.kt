package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.utils.BitmapUtils

/**
 * @Author:         wuzm
 * @CreateDate:     2022/12/28 3:21 PM
 * @Description:    老照片修复自定义view
 */
class DoublePhotoV2Preview @JvmOverloads constructor(context: Context, attrs: AttributeSet?) :
    View(context, attrs), View.OnTouchListener {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var backgroundBitmap: Bitmap =
        BitmapFactory.decodeResource(getContext().getResources(), R.drawable.image2)
    private var destBitmap: Bitmap =
        BitmapFactory.decodeResource(getContext().getResources(), R.drawable.image3)
    private var lineBitmap: Bitmap =
        BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ph_icon_fix_line)
    private var lineCenterLocationY = 0F//中间线的y坐标
    private var canMoveLine: Boolean = false//是否可以移动中间线

    //按下点的坐标
    var lastY = 0f

    init {
        paint.style = Paint.Style.FILL

        setLayerType(LAYER_TYPE_SOFTWARE, null)
        setOnTouchListener(this)

        post {
            //待view绘制完后，初始化横线坐标
            lineCenterLocationY = height / 2F
            invalidate()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (backgroundBitmap.width != width) {
            backgroundBitmap =
                BitmapUtils.resizeImage(backgroundBitmap, width.toFloat(), height.toFloat())
        }
        // 设置背景
        canvas.drawBitmap(backgroundBitmap, 0F, 0F, paint)

        if (destBitmap.width != width) {
            destBitmap = BitmapUtils.resizeImage(destBitmap, width.toFloat(), height.toFloat())
        }

        if (lineBitmap.width != width) {
            lineBitmap = BitmapUtils.resizeImage(lineBitmap, width.toFloat(),
                lineBitmap.height.toFloat() * width / lineBitmap.width)
        }


        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        val saveCount =
            canvas.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        // 绘制 dest
        canvas.drawBitmap(destBitmap, 0F, 0F, paint)
        // 设置 xfermode
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
        // 绘制 src
        canvas.drawRect(0F, lineCenterLocationY.toFloat(), width.toFloat(), height.toFloat(), paint)
        paint.xfermode = null
        canvas.restoreToCount(saveCount)

        canvas.drawBitmap(lineBitmap, 0F, lineCenterLocationY - lineBitmap.height / 2F, paint)

    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = event.y
                //这里需要判断是否点在了中间线的热区上
                canMoveLine =
                    lastY >= (lineCenterLocationY - lineBitmap.height / 2) && lastY <= (lineCenterLocationY + lineBitmap.height / 2)
            }
            MotionEvent.ACTION_MOVE -> {
                val tempY = event.y
                val diffY = tempY - lastY
                if (canMoveLine) {
                    lineCenterLocationY += diffY
                    if (lineCenterLocationY <= 0) {
                        lineCenterLocationY = 0F
                    }
                    if (lineCenterLocationY >= height) {
                        lineCenterLocationY = height.toFloat()
                    }
                    invalidate()
                }
                lastY = tempY
            }
            MotionEvent.ACTION_UP -> {
                canMoveLine = false
            }
        }
        return true
    }
}