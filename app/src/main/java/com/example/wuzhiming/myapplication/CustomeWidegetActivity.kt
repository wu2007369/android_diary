package com.example.wuzhiming.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.wuzhiming.myapplication.adapter.CalendarListAdapter
import com.example.wuzhiming.myapplication.databinding.ActivityCustomeWidegetBinding
import com.example.wuzhiming.myapplication.entity.CalendarBean
import java.util.*


class CustomeWidegetActivity : AppCompatActivity() {
    private lateinit var mDataBind: ActivityCustomeWidegetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind = DataBindingUtil.setContentView(this, R.layout.activity_custome_wideget)
        mDataBind.lifecycleOwner = this

//        setContentView(R.layout.activity_custome_wideget)
        mDataBind.item2.isSelected = true

        mDataBind.item3.setOnClickListener { v -> v.isSelected = true }
        mDataBind.item4.setOnClickListener { v -> v.isSelected = !v.isSelected }

    /*    val adapter = CalendarListAdapter(getAllDay(7), this)

        mDataBind.recyCalendar.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        val mPagerSnapHelper = PagerSnapHelper()
        mPagerSnapHelper.attachToRecyclerView(mDataBind.recyCalendar)
        mDataBind.recyCalendar.adapter = adapter*/
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBind.unbind()
    }

}