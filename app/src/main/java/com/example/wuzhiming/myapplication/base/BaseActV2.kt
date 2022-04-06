package com.example.wuzhiming.myapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

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


    private val exceptionHandler = CoroutineExceptionHandlerImpl{context, exception, errCode ->
        coroutineError(context, exception, errCode)
    }

    open fun coroutineError(context: CoroutineContext, throwable: Throwable, errCode: Int){}

    /**
     *延迟任务
     */
    fun processDelay(delayTime: Long, errCode: Int = 0, block: suspend CoroutineScope.() -> Unit){
        exceptionHandler.errCode = errCode
        lifecycleScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) { delay(delayTime) }
            block.invoke(this)
        }
    }

    /**
     * 切换到主线程
     */
    fun processMain(errCode: Int = 0, block: suspend CoroutineScope.() -> Unit){
        exceptionHandler.errCode = errCode
        lifecycleScope.launch(exceptionHandler) {
            block.invoke(this)
        }
    }

}