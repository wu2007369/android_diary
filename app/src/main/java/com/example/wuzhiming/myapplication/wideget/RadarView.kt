package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class RadarView @JvmOverloads constructor(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {

    @ColorInt
    var circleColor: Int = 0
    private var arrayXLayer: Array<Float>? = null
    private var arrayYLayer: Array<Float>? = null
    private val backGroundPaint: Paint
    private val foreGroundPaint: Paint

    var destValue: Array<Int>? = null

    init {
        backGroundPaint = Paint()
        foreGroundPaint = Paint()
        backGroundPaint.style = Paint.Style.FILL
        foreGroundPaint.style = Paint.Style.FILL

        //background包括color和Drawable,这里分开取值
        if (background is ColorDrawable) {
            //        给画笔设置颜色
            backGroundPaint.color = (background as ColorDrawable).color
            background = null
        }
        if (foreground is ColorDrawable) {
            foreGroundPaint.color = (foreground as ColorDrawable).color
            foreground = null
        }

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val realWidth=width-paddingLeft-paddingRight
        val realHeight=height-paddingTop-paddingBottom
        val interval = realWidth / 15f
        for (index in 0..6) {
            arrayXLayer = arrayOf(paddingLeft + interval * index, paddingLeft+realWidth / 2f,
                paddingLeft+realWidth.toFloat() - interval * index - paddingRight, paddingLeft+realWidth / 2f)
            arrayYLayer = arrayOf(paddingTop+realHeight / 2f, paddingTop + interval * index, paddingTop+realHeight / 2f,
                paddingTop+realHeight.toFloat() - interval * index - paddingBottom)
            drawLayer(arrayXLayer!!, arrayYLayer!!, canvas, backGroundPaint)
        }


        // test，手动设置数据
//        destValue = arrayOf(0, 0, 100, 90)
        destValue = arrayOf(60, 70, 100, 90)


        if ((destValue?.size ?: 0) >= 4) {
            val arrayXValue =
                arrayOf(paddingLeft + realWidth / 2f - realWidth / 200f * destValue!![0], paddingLeft+realWidth / 2f,
                    paddingLeft+ realWidth / 2f + realWidth / 200f * destValue!![2] - paddingRight, paddingLeft+realWidth / 2f)
            val arrayYValue =
                arrayOf(paddingTop+realHeight / 2f, paddingTop + realHeight / 2f - realHeight / 200f * destValue!![1],
                    paddingTop+realHeight / 2f, paddingTop+realHeight / 2f + realHeight / 200f * destValue!![3] - paddingBottom)
            drawLayer(arrayXValue, arrayYValue, canvas, foreGroundPaint)

            if (circleColor != 0) {
                drawCircle(arrayXValue, arrayYValue, canvas, foreGroundPaint)
            }
        }


        //test
//        canvas.drawCircle(realWidth / 2f, realHeight / 2f, realWidth / 2f, backgroundPaint)
    }

    private fun drawCircle(arrayXLayer: Array<Float>,
                           arrayYLayer: Array<Float>,
                           canvas: Canvas,
                           mPaint: Paint) {
        mPaint.color = Color.WHITE
        val radiusLength = (10f)
        for (index in arrayXLayer.indices) {
            canvas.drawCircle(arrayXLayer[index], arrayYLayer[index], radiusLength, mPaint)
        }

        mPaint.color = circleColor
        val radiusContentLength = (7f)
        for (index in arrayXLayer.indices) {
            canvas.drawCircle(arrayXLayer[index], arrayYLayer[index], radiusContentLength, mPaint)
        }

    }

    private fun drawLayer(arrayX: Array<Float>,
                          arrayY: Array<Float>,
                          canvas: Canvas,
                          mPaint: Paint) {
        val path = Path()
        for (index in arrayX.indices) {
            if (index == 0) {
                path.moveTo(arrayX[index], arrayY[index])
            } else {
                path.lineTo(arrayX[index], arrayY[index])
            }
        }
        //闭合覆盖区域
        path.close()
//        path.lineTo(arrayX[0], arrayY[0])
        canvas.drawPath(path, mPaint)
    }


}