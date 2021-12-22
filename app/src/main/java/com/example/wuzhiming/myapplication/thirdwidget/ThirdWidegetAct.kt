package com.example.wuzhiming.myapplication.thirdwidget

import android.os.Bundle
import com.example.wuzhiming.myapplication.base.BaseActV2
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.databinding.ActivityThirdWidegetBinding
import com.example.wuzhiming.myapplication.thirdwidget.adapter.ImageAdapter
import com.example.wuzhiming.myapplication.thirdwidget.adapter.TestBannerAdapter
import com.example.wuzhiming.myapplication.thirdwidget.bean.DataBean
import com.youth.banner.indicator.CircleIndicator

class ThirdWidegetAct : BaseActV2<ActivityThirdWidegetBinding>() {

    private var adapter2: TestBannerAdapter?=null

    override fun setLayoutId()= R.layout.activity_third_wideget

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 画廊效果
         */
        mDataBind.banner1.setAdapter(
            ImageAdapter(
                DataBean.getTestData2()))
        mDataBind.banner1.setIndicator(CircleIndicator(this))
        //添加画廊效果
        mDataBind.banner1.setBannerGalleryEffect(50, 5,0.75f);
        //(可以和其他PageTransformer组合使用，比如AlphaPageTransformer，注意但和其他带有缩放的PageTransformer会显示冲突)
        //添加透明效果(画廊配合透明效果更棒)
//        mDataBind.banner1.addPageTransformer(AlphaPageTransformer())


        adapter2=TestBannerAdapter()
        mDataBind.banner2.setStartPosition(0)
        mDataBind.banner2.setAdapter(adapter2)
//        mDataBind.banner1.setIndicator(CircleIndicator(this))
        //添加画廊效果
        mDataBind.banner2.setBannerGalleryEffect(50, 5,0.75f)

        adapter2?.setDatas(DataBean.getTestData2())
    }

}