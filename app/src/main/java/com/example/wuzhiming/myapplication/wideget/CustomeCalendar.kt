package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.wuzhiming.myapplication.adapter.CalendarListAdapter
import com.example.wuzhiming.myapplication.entity.CalendarBean
import java.util.*


class CustomeCalendar(context : Context, attrs: AttributeSet?) :FrameLayout(context,attrs) {
    private var adapter: CalendarListAdapter= CalendarListAdapter(getAllDay(7), context)
    private var content: RecyclerView = RecyclerView(context)

    init {

        content.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        addView(content)


        content.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        val mPagerSnapHelper = PagerSnapHelper()
        mPagerSnapHelper.attachToRecyclerView(content)
        content.adapter = adapter

        val index=adapter.mDataList.indexOfFirst { it.isToday }
        if (index!=-1){
            content.smoothScrollToPosition(index/7)
        }
    }

    fun refreshData(month: Int){
        adapter.mDataList=getAllDay(month)
        adapter.notifyDataSetChanged()
    }

    private fun getAllDay(month: Int): List<CalendarBean> {
        var list = ArrayList<CalendarBean>()
        val cal = Calendar.getInstance()
        val today = cal.get(Calendar.DAY_OF_MONTH)
        val toMonth = cal.get(Calendar.MONTH)
        cal.clear()
        cal.set(Calendar.MONTH, month - 1)
        cal.set(Calendar.DAY_OF_MONTH, 1);
        var maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        list = addHead(list, cal)

        cal.clear()
        cal.set(Calendar.MONTH, month - 1)
        cal.set(Calendar.DAY_OF_MONTH, 1)
        for (i in 0 until maxDate) {
            if (month == toMonth) {
                list.add(
                    CalendarBean(
                        getWeek(cal),
                        cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.DAY_OF_MONTH) == today,
                        cal.get(Calendar.DAY_OF_MONTH) == today,
                        true
                    )
                )
            } else {
                list.add(
                    CalendarBean(
                        getWeek(cal),
                        cal.get(Calendar.DAY_OF_MONTH),
                        false,
                        isCurrentMount = true
                    )
                )
            }
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        list = addTail(list, cal)

        return list
    }

    private fun getWeek(cal: Calendar): String {
        val week = when (cal.get(Calendar.DAY_OF_WEEK)) {
            1 -> "天"
            2 -> "一"
            3 -> "二"
            4 -> "三"
            5 -> "四"
            6 -> "五"
            7 -> "六"
            else -> ""
        }
        return "周:$week"
    }

    fun addHead(list: ArrayList<CalendarBean>, cal: Calendar): ArrayList<CalendarBean> {
        return if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
            list
        } else {
            while (cal.get(Calendar.DAY_OF_WEEK) != 2) {
                cal.add(Calendar.DAY_OF_MONTH, -1)
                list.add(0, CalendarBean(getWeek(cal), cal.get(Calendar.DAY_OF_MONTH), false))
            }
            list
        }
    }

    private fun addTail(list: ArrayList<CalendarBean>, cal: Calendar): ArrayList<CalendarBean> {
        return if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            list
        } else {
            while (cal.get(Calendar.DAY_OF_WEEK) != 1) {
                list.add(CalendarBean(getWeek(cal), cal.get(Calendar.DAY_OF_MONTH), false))
                cal.add(Calendar.DAY_OF_MONTH, 1)
            }
            list.add(CalendarBean(getWeek(cal), cal.get(Calendar.DAY_OF_MONTH), false))
            list
        }
    }
}