package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.utils.DisplayUtil

/**
 * @author: guxxxxd
 * @Time: 2021/7/23 13:42
 * @Version:
 * @Desc: 自定义圆角textview
 */
class CornersTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var solidColorInt: Int = 0
    set(value) {
        field = value
        background = generateGradientDrawable(cornersOrientation,colors)
    }

    private var cornersRadius: Float = 0f
    private var cornersBottomLeftRadius: Float = 0f
    private var cornersBottomRightRadius: Float = 0f
    private var cornersTopLeftRadius: Float = 0f
    private var cornersTopRightRadius: Float = 0f

    private var cornersStrokeColor: Int = 0
    private var cornersStrokeWidth: Int = 0

    private var cornersStartColor: Int = 0
    private var cornersCenterColor: Int = 0
    private var cornersEndColor: Int = 0
    private var cornersOrientation: GradientDrawable.Orientation =
        GradientDrawable.Orientation.TOP_BOTTOM

    val colors =
        if (cornersStartColor == 0 && cornersEndColor == 0) {
            if (cornersCenterColor != 0)
                intArrayOf(cornersStartColor, cornersCenterColor, cornersEndColor)
            else intArrayOf(cornersStartColor, cornersEndColor)
        } else null

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.CornersTextView)
        solidColorInt = array.getColor(R.styleable.CornersTextView_ctv_solidColor, currentTextColor)

        cornersRadius = array.getDimension(R.styleable.CornersTextView_ctv_radius, 0f)
        cornersBottomLeftRadius =
            array.getDimension(R.styleable.CornersTextView_ctv_bottomLeftRadius, 0f)
        cornersBottomRightRadius =
            array.getDimension(R.styleable.CornersTextView_ctv_bottomRightRadius, 0f)
        cornersTopLeftRadius =
            array.getDimension(R.styleable.CornersTextView_ctv_topLeftRadius, 0f)
        cornersTopRightRadius =
            array.getDimension(R.styleable.CornersTextView_ctv_topRightRadius, 0f)

        cornersStrokeColor = array.getColor(R.styleable.CornersTextView_ctv_strokeColor, 0)
        cornersStrokeWidth =
            array.getDimensionPixelSize(R.styleable.CornersTextView_ctv_strokeWidth, 0)

        cornersStartColor = array.getColor(R.styleable.CornersTextView_ctv_startColor, 0)
        cornersCenterColor = array.getColor(R.styleable.CornersTextView_ctv_centerColor, 0)
        cornersEndColor = array.getColor(R.styleable.CornersTextView_ctv_endColor, 0)

        cornersOrientation = GradientDrawable.Orientation.values()[array.getInt(
            R.styleable.CornersTextView_ctv_orientation,
            0
        )]

        array.recycle()

//        val colors: IntArray? =
//            if (cornersStartColor == 0 && cornersEndColor == 0) {
//                if (cornersCenterColor != 0)
//                    intArrayOf(cornersStartColor, cornersCenterColor, cornersEndColor)
//                else intArrayOf(cornersStartColor, cornersEndColor)
//            } else null

        val gradientDrawable = generateGradientDrawable(cornersOrientation, colors)
        background = gradientDrawable
    }

    private fun generateGradientDrawable(
        orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
        colors: IntArray? = null
    ): GradientDrawable {
        val drawable = background as? GradientDrawable ?: GradientDrawable(orientation, colors)
        drawable.apply {
            solidColorInt.takeIf { it != 0 }?.let { setColor(it) }
            if (this@CornersTextView.cornersRadius != 0f) {
                cornerRadius = this@CornersTextView.cornersRadius
            } else {
                cornerRadii = getCornerRadii(
                    cornersTopLeftRadius, cornersTopRightRadius,
                    cornersBottomLeftRadius, cornersBottomRightRadius
                )
            }
            setStroke(cornersStrokeWidth, cornersStrokeColor)
        }

        return drawable
    }


    /**
     * 必须返回一个含有8个元素的float数组
     */
    private fun getCornerRadii(
        leftTop: Float,
        rightTop: Float,
        leftBottom: Float,
        rightBottom: Float
    ): FloatArray {
        return floatArrayOf(
            leftTop,
            leftTop,
            rightTop,
            rightTop,
            rightBottom,
            rightBottom,
            leftBottom,
            leftBottom
        )

    }

    fun refreshSolid(@ColorInt color: Int){
        solidColorInt = color
        val drawable = background as? GradientDrawable
        drawable?.setColor(solidColorInt)
    }

    fun refreshStroke(strokeWidthDp:Float,@ColorInt strokeColor:Int){
        cornersStrokeWidth = DisplayUtil.dip2px(strokeWidthDp).toInt()
        cornersStrokeColor = strokeColor
        val drawable = background as? GradientDrawable
        drawable?.setStroke(cornersStrokeWidth,cornersStrokeColor)
    }
}