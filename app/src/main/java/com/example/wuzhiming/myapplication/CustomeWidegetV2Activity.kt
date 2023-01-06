package com.example.wuzhiming.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.wuzhiming.myapplication.databinding.ActivityCustomeWidegetV2Binding

/**
 * @Author:         wuzm
 * @CreateDate:     2022/12/28 4:15 PM
 * @Description:    类作用描述
 */
class CustomeWidegetV2Activity: AppCompatActivity()  {
    private lateinit var mDataBind: ActivityCustomeWidegetV2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind = DataBindingUtil.setContentView(this, R.layout.activity_custome_wideget_v2)
        mDataBind.lifecycleOwner = this

        mDataBind.clickPressTest.isPressCallBack={
            Log.i("clickPressTest",it.toString())
        }
    }
}