package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.sqrt

/**
 * @Author:         wuzm
 * @CreateDate:     2022/12/9 10:52 AM
 * @Description:    类作用描述
 */
class CircularRing @JvmOverloads constructor(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {
    var destValueArray: ArrayList<CircularRingData>? = null
        set(value) {
            field = value
            invalidate()
        }
    private val outerPaint: Paint = Paint()
    private val innerPaint: Paint = Paint()

    init {
        outerPaint.run {
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        innerPaint.run {
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        val outRectHalfWith = width / (2 * sqrt(2.0).toFloat())
        //画圆弧的宽度会有部分被裁切掉，所有额外乘以根号二
        outerPaint.strokeWidth = width / 4F
//        outerPaint.strokeWidth=width/(4* sqrt(2.0).toFloat())
//        outerPaint.strokeWidth=outRectHalfWith*(1+1/sqrt(2.0).toFloat())
        innerPaint.strokeWidth = outerPaint.strokeWidth / 5
//        val ionnerRectHalfWith=outRectHalfWith/2
//        val ionnerRectHalfWith=outRectHalfWith/2*sqrt(2.0).toFloat()
        val ionnerRectHalfWith = outRectHalfWith / 2 * 1.16F

        //由于画圆以及画圆环的，路径宽度问题。组件内的最大圆环的外边界方框，不能够贴边，要取组件的内切圆内部的最大正方形。
        //在上述条件成立情况的范围之内，画的圆和圆环是内切于外边界方框的。不会出现，路径宽度问题。
        val outRectF = RectF(width / 2 - outRectHalfWith, width / 2 - outRectHalfWith,
            width / 2 + outRectHalfWith, width / 2 + outRectHalfWith)
        val innerRectF = RectF(width / 2 - ionnerRectHalfWith, width / 2 - ionnerRectHalfWith,
            width / 2 + ionnerRectHalfWith, width / 2 + ionnerRectHalfWith)

        // 绘制背景矩形
        outerPaint.color = Color./*YELLOW*/TRANSPARENT
        canvas.drawRect(outRectF, outerPaint)

        innerPaint.color = Color./*YELLOW*/TRANSPARENT
        canvas.drawRect(innerRectF, innerPaint)

        // 绘制圆弧
        var startAngle = -90F//-90是时钟的12点位置
        destValueArray?.forEachIndexed { index, circularRingData ->
            val currentAngle = 360 * circularRingData.percent
            val destAngle = startAngle + currentAngle

            outerPaint.color = circularRingData.outerColorInt
            canvas.drawArc(outRectF, startAngle, currentAngle, false, outerPaint)

            innerPaint.color = circularRingData.innerColorInt
            canvas.drawArc(innerRectF, startAngle, currentAngle, false, innerPaint)

            startAngle = destAngle

        }
    }


}


data class CircularRingData(val percent: Float,
                            @ColorInt val outerColorInt: Int,
                            @ColorInt val innerColorInt: Int)