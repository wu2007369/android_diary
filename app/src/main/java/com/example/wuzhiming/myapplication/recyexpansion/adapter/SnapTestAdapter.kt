package com.example.wuzhiming.myapplication.recyexpansion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.adapter.TextItemAdapter

/**
 * @Author:         wuzm
 * @CreateDate:     2022/6/24 10:50 上午
 * @Description:    类作用描述
 */
class SnapTestAdapter( mdata:List<String>,  mContext: Context): TextItemAdapter(mdata,mContext) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_snap_test_item, viewGroup, false)
        return ViewHolder(v)
    }
}