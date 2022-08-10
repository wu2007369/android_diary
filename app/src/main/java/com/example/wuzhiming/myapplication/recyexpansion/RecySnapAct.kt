package com.example.wuzhiming.myapplication.recyexpansion

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.adapter.TextItemAdapter
import com.example.wuzhiming.myapplication.base.BaseActV2
import com.example.wuzhiming.myapplication.databinding.ActivityRecySnapBinding
import com.example.wuzhiming.myapplication.recyexpansion.adapter.SnapTestAdapter
import java.util.*


/**
 * @Author:         wuzm
 * @CreateDate:     2022/6/24 10:02 上午
 * @Description:    类作用描述
 */
class RecySnapAct :BaseActV2<ActivityRecySnapBinding>(){
    override fun setLayoutId()= R.layout.activity_recy_snap

    var strings = arrayOf("sacasdc", "csdcsdcsdcsdc", "kxco;lkco", "ckalo;kcoask", "casjlclsa",
        "cjileajcliveververver", "casdc", "dcsdcsdc", "klkco", "ckalo;kcoask", "lclsa",
        "cjileajcliveververver", "cjileajcliveververver", "casdc", "dcsdcsdc", "klkco",
        "ckalo;kcoask", "lclsa", "cjileajcliveververver")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDataBind.recy.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        mDataBind.recy.adapter=
            SnapTestAdapter(strings.toList(),this)

        // 将SnapHelper attach 到RecyclrView
        LinearSnapHelper().attachToRecyclerView(mDataBind.recy)


        mDataBind.recy2.layoutManager=LinearLayoutManager(this)
        mDataBind.recy2.adapter=
            SnapTestAdapter(/*strings.toList()*/arrayListOf("sacasdc", "csdcsdcsdcsdc", "kxco;lkco"),this)

        // 将SnapHelper attach 到RecyclrView
        LinearSnapHelper().attachToRecyclerView(mDataBind.recy2)
    }
}