package com.example.wuzhiming.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.wuzhiming.myapplication.databinding.ActivityCustomeWidegetBinding

class CustomeWidegetActivity : AppCompatActivity() {
    private lateinit var mDataBind: ActivityCustomeWidegetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         mDataBind = DataBindingUtil.setContentView(this, R.layout.activity_custome_wideget)
        mDataBind.lifecycleOwner = this

//        setContentView(R.layout.activity_custome_wideget)
        mDataBind.item2.isSelected=true

        mDataBind.item3.setOnClickListener { v -> v.isSelected=true }
        mDataBind.item4.setOnClickListener { v -> v.isSelected=!v.isSelected }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBind.unbind()
    }
}