package com.example.wuzhiming.myapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author:         wuzm
 * @CreateDate:     2021/12/22 3:41 下午
 * @Description:    类作用描述
 */
abstract class BaseActV2 <T : ViewDataBinding> : AppCompatActivity(){
    lateinit var mDataBind: T
    protected abstract fun setLayoutId(): Int

    protected open fun createDataBind() {
        mDataBind = DataBindingUtil.setContentView(this, setLayoutId())
        mDataBind.lifecycleOwner = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDataBind()
    }

    override fun onDestroy() {
        mDataBind.unbind()

        super.onDestroy()
    }
}