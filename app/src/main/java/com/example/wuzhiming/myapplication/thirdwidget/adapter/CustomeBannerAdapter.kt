package com.example.wuzhiming.myapplication.thirdwidget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter

/**
 * @Author:         wuzm
 * @CreateDate:     2021/12/22 4:05 下午
 * @Description:    类作用描述
 */
abstract class CustomeBannerAdapter<T,D : ViewDataBinding>(datas:MutableList<T>): BannerAdapter<T, CustomeBannerAdapter<T,D>.CustomHolder>(datas) {
    abstract fun getLayoutId(var1: Int): Int
    abstract fun convert(var1: CustomeBannerAdapter<T,D>.CustomHolder, data: T, position: Int, size: Int)

    //更新数据
    fun setData(data: List<T>) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear()
        mDatas.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val dataBind: D =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayoutId(viewType),
                parent, false)
        return CustomHolder(dataBind)

    }

    override fun onBindView(holder: CustomHolder, data: T, position: Int, size: Int) {
        convert(holder,data,position,size)
    }

    inner class CustomHolder(var bind: D): RecyclerView.ViewHolder(bind.root)
}