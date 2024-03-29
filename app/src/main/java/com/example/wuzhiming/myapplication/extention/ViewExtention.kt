package com.example.wuzhiming.myapplication.extention

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.utils.DisplayUtil

/**
 * @Author:         wuzm
 * @CreateDate:     2022/9/26 5:42 下午
 * @Description:    类作用描述
 */

/**
 * 着色
 */
fun AppCompatImageView?.tint(
    checked: Boolean,
    @ColorRes checkedColor: Int = R.color.black,
    @ColorRes normalColor: Int = R.color.color_1D81FF
) {
    this?.let {
        val drawable = it.drawable
        val wrap: Drawable = DrawableCompat.wrap(drawable).mutate()
        DrawableCompat.setTint(
            wrap, ContextCompat.getColor(
                context, if (checked) checkedColor else normalColor
            )
        )
    }
}



val Int.dp: Float
    get() = DisplayUtil.dip2px(this.toFloat())

val Float.dp: Float
    get() = DisplayUtil.dip2px(this)

val Int.sp:Float
    get() = DisplayUtil.sp2px(this.toFloat())

val Float.sp:Float
    get() = DisplayUtil.sp2px(this.toFloat())