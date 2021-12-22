package com.example.wuzhiming.myapplication.thirdwidget.adapter

import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.databinding.ItemBannerCustomeBinding
import com.example.wuzhiming.myapplication.thirdwidget.bean.DataBean

/**
 * @Author:         wuzm
 * @CreateDate:     2021/12/22 4:41 下午
 * @Description:    类作用描述
 */
class TestBannerAdapter():CustomeBannerAdapter<DataBean,ItemBannerCustomeBinding>(
    mutableListOf<DataBean>()) {
    override fun getLayoutId(var1: Int): Int = R.layout.item_banner_custome

    override fun convert(var1: CustomHolder, data: DataBean, position: Int, size: Int) {
        val bind=var1.bind as ItemBannerCustomeBinding
        bind.image.setImageResource(data.imageRes)
        bind.text.text="$position/$size"
    }
}