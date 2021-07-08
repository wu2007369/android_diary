package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

class Circle(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private lateinit var paint: Paint
    private lateinit var paint2: Paint

    init {
        paint = Paint()
        //background包括color和Drawable,这里分开取值
        if (background is ColorDrawable) {
            //        给画笔设置颜色
            paint.color = (background as ColorDrawable).color
            setBackgroundDrawable(null);
        }

        paint2 = Paint()
        paint2.style = Paint.Style.FILL;//画笔属性是实心圆
        paint2.color=Color.WHITE
    }
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        //        设置画笔属性
        paint.style = Paint.Style.FILL;//画笔属性是实心圆
//        paint.setStyle(Paint.Style.STROKE) //画笔属性是空心圆
//        paint.strokeWidth = 8F //设置画笔粗细

        /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔
                */
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paint)
        if (isSelected){
            canvas.drawCircle(width / 2f, height / 2f, width / 2.5f, paint2)
            canvas.drawCircle(width / 2f, height / 2f, width / 4f, paint)
        }
    }
}