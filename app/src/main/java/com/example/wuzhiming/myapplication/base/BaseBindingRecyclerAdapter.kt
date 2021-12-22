package com.example.wuzhiming.myapplication.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * @Author:         wuzm
 * @CreateDate:     2021/12/22 4:01 下午
 * @Description:    类作用描述
 */
abstract class BaseBindingRecyclerAdapter<T, D : ViewDataBinding> :
    RecyclerView.Adapter<BaseBindingRecyclerAdapter<T,D>.VH>() {
    private val mData: MutableList<T>? = ArrayList<T>()
    fun setData(data: List<T>?) {
        if (data != null) {
            mData!!.clear()
            mData.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun setDataAll(data: List<T>?) {
        if (data != null) {
            val lastIndex = mData!!.size
            if (mData.addAll(data)) {
                notifyItemRangeInserted(lastIndex, data.size)
            }
        }
    }

    fun updateItem(position: Int) {
        if (position < mData!!.size) {
            this.notifyItemChanged(position)
        }
    }

    fun remove(position: Int) {
        if (position < mData!!.size) {
            mData.removeAt(position)
            notifyItemRemoved(position)
            if (position != mData.size) {
                this.notifyItemRangeChanged(position, mData.size - position)
            }
            if (mData.size == 0) {
                notifyDataSetChanged()
            }
        }
    }

    fun move(fromPosition: Int, toPosition: Int) {
        Collections.swap(mData, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        updateItem(toPosition)
    }

    fun clear() {
        mData!!.clear()
        notifyDataSetChanged()
    }

    fun clearOnly() {
        mData!!.clear()
    }

    fun getData(): List<T>? {
        return mData
    }

    abstract fun getLayoutId(var1: Int): Int
    abstract fun convert(var1: VH, var2: T, var3: Int)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): VH {
        val dataBind: D =
            DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), getLayoutId(viewType),
                viewGroup, false)
        return VH(dataBind)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        convert(holder, mData!![position], position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }
    inner class VH(var dataBind: D) : RecyclerView.ViewHolder(dataBind.getRoot())

}


