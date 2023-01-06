package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration


/**
 * @Author:         wuzm
 * @CreateDate:     2023/1/5 10:42 AM
 * @Description:    识别按压操作
 */
class PressView @JvmOverloads constructor(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs), View.OnTouchListener {
    private var touchSlop: Int = 0
    private var lastY: Float = 0F
    private var lastX: Float = 0F

    var isPressCallBack: ((isPressed: Boolean) -> Unit)? = null

    init {

        setOnTouchListener(this)

    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                isPressCallBack?.invoke(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val dx: Float = event.x - lastX
                val dy: Float = event.y - lastY
                if (touchSlop === 0) {
                    touchSlop = ViewConfiguration.get(context).scaledTouchSlop
                }
                if (dx * dx + dy * dy > touchSlop * touchSlop) {
                    isPressCallBack?.invoke(false)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isPressCallBack?.invoke(false)
            }
        }
        return true
    }
}