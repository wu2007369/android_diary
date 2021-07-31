package com.example.wuzhiming.myapplication.entity

data class CalendarBean(
    val week: String,
    val dateNum: Int,
    val isToday: Boolean,
    val isSelected: Boolean = false,
    val isCurrentMount: Boolean = false
) {
}