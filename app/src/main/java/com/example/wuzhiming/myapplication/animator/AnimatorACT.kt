package com.example.wuzhiming.myapplication.animator

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.base.BaseActV2
import com.example.wuzhiming.myapplication.databinding.ActivityAnimatorBinding

/**
 * @Author:         wuzm
 * @CreateDate:     2022/9/19 7:17 下午
 * @Description:    动画
 */
class AnimatorACT:BaseActV2<ActivityAnimatorBinding>() {
    override fun setLayoutId()= R.layout.activity_animator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        mDataBind.laser.animate().translationYBy(500F).setDuration(2*1000)

        val animator = ObjectAnimator.ofFloat(mDataBind.laser,"translationY",0f,1000f)
        animator.repeatCount = ValueAnimator.INFINITE
//        animator.repeatMode=
        animator.duration = 2000
        animator.interpolator = LinearInterpolator()
        animator.start()

        mDataBind.btn.setOnClickListener {
            animator.cancel()
        }
    }
}