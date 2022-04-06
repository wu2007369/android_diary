package com.example.wuzhiming.myapplication.base

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlin.coroutines.CoroutineContext

/**
 * author：mango
 * date：6/15/21 09:48
 * desc：
 * modify record :
 */
class CoroutineExceptionHandlerImpl(var errCode: Int = 0,
                                    var block: (context: CoroutineContext, exception: Throwable, errCode: Int) -> Unit) : CoroutineExceptionHandler {

    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.i("CoroutineException"," ${context[CoroutineName].toString()} 处理异常 ：$exception")
        block.invoke(context, exception, errCode)
    }

}