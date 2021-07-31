package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.wuzhiming.myapplication.R

class CustomRoundAngleImageView(context :Context,attrs: AttributeSet?):AppCompatImageView(context,attrs) {


    var defaultRadius = 0
    var radius=0f
    var leftTopRadius=0f
    var rightTopRadius=0f
    var rightBottomRadius=0f
    var leftBottomRadius=0f

    init {
        // 读取xml自定义属性配置
        val array = context.obtainStyledAttributes(attrs, R.styleable.Custom_Round_Image_View);
        radius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_radius, defaultRadius).toFloat()
        leftTopRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_left_top_radius, defaultRadius).toFloat()
        rightTopRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_right_top_radius, defaultRadius).toFloat()
        rightBottomRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_right_bottom_radius, defaultRadius).toFloat()
        leftBottomRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_left_bottom_radius, defaultRadius).toFloat()


        //如果四个角的值没有设置，那么就使用通用的radius的值。
        if (defaultRadius == leftTopRadius.toInt()) {
            leftTopRadius = radius;
        }
        if (defaultRadius == rightTopRadius.toInt()) {
            rightTopRadius = radius;
        }
        if (defaultRadius == rightBottomRadius.toInt()) {
            rightBottomRadius = radius;
        }
        if (defaultRadius == leftBottomRadius.toInt()) {
            leftBottomRadius = radius;
        }
        array.recycle()
    }

    override fun  onDraw(canvas: Canvas) {
        if (width >= 12 && height > 12) {
            val  path =  Path().apply {
                //四个角：右上，右下，左下，左上
                moveTo(leftTopRadius, 0f);
                lineTo(width - rightTopRadius, 0f);
                quadTo(width.toFloat(), 0f, width.toFloat(), rightTopRadius);

                lineTo(width.toFloat(), height - rightBottomRadius);
                quadTo(width.toFloat(), height.toFloat(), width - rightBottomRadius, height.toFloat());

                lineTo(leftBottomRadius, height.toFloat());
                quadTo(0f, height.toFloat(), 0f, height - leftBottomRadius);

                lineTo(0f, leftTopRadius);
                quadTo(0f, 0f, leftTopRadius, 0f);
            }

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}