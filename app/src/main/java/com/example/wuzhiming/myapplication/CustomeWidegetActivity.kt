package com.example.wuzhiming.myapplication

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.wuzhiming.myapplication.databinding.ActivityCustomeWidegetBinding
import com.example.wuzhiming.myapplication.utils.BitmapUtilKt
import com.example.wuzhiming.myapplication.utils.ScreenUtils
import com.example.wuzhiming.myapplication.wideget.CircularRingData
import java.util.*


class CustomeWidegetActivity : AppCompatActivity() {
    private lateinit var mDataBind: ActivityCustomeWidegetBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind = DataBindingUtil.setContentView(this, R.layout.activity_custome_wideget)
        mDataBind.lifecycleOwner = this

//        setContentView(R.layout.activity_custome_wideget)
        mDataBind.item2.isSelected = true

        mDataBind.item3.setOnClickListener { v -> v.isSelected = true }
        mDataBind.item4.setOnClickListener { v -> v.isSelected = !v.isSelected }
        mDataBind.item5.setOnClickListener { v -> mDataBind.item5.setColor(getColor(R.color.color_1D81FF)) }

        /*    val adapter = CalendarListAdapter(getAllDay(7), this)

            mDataBind.recyCalendar.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
            val mPagerSnapHelper = PagerSnapHelper()
            mPagerSnapHelper.attachToRecyclerView(mDataBind.recyCalendar)
            mDataBind.recyCalendar.adapter = adapter*/



        //TODO open or close
        //TODO test ,longImg 在没有照片的手机上，得注释，防止崩溃
//        mDataBind.longImg.test()

        mDataBind.changeColor.setOnClickListener {
            mDataBind.longImg.mPaint.color = ContextCompat.getColor(this, R.color.color_FF6633)
            mDataBind.longImg.paintTextSize = ScreenUtils.dp2px(this, 25f).toFloat()
//            mDataBind.longImg.mPaint.textSize = ScreenUtils.dp2px(this, 25f).toFloat()
//            mDataBind.longImg.refreshView()
            mDataBind.longImg.invalidate()
        }
        mDataBind.changeSize.setOnClickListener {
            mDataBind.longImg.mPaint.color = ContextCompat.getColor(this, R.color.color_1D81FF)
            mDataBind.longImg.paintTextSize = ScreenUtils.dp2px(this, 5f).toFloat()
//            mDataBind.longImg.mPaint.textSize = ScreenUtils.dp2px(this, 5f).toFloat()
//            mDataBind.longImg.refreshView()
            mDataBind.longImg.invalidate()
        }

        mDataBind.clear.setOnClickListener {
            mDataBind.longImg.waterMarkText=""
            mDataBind.longImg.invalidate()
        }

        mDataBind.cover2local.setOnClickListener {
            mDataBind.longImg.createFinalBitmap()?.let { it1 ->
//                mDataBind.finalImg.setImageBitmap(it1)
                BitmapUtilKt.cover2Local(it1, this, BitmapUtilKt.SUFFIX_PNG)
            }
        }

        mDataBind.drawSeal.setOnClickListener {
            mDataBind.longImg.sealText="scjilsdjcilosdjloclsd"
            mDataBind.longImg.invalidate()

        }

        mDataBind.radarView.circleColor=ContextCompat.getColor(this,R.color.color_7651FF)

        mDataBind.circularRing.destValueArray= arrayListOf(CircularRingData(0.1F,getColor(R.color.color_7651FF),getColor(R.color.color_8100FF_40_percent)),
            CircularRingData(0.3F,getColor(R.color.color_3480FF_10_percent),getColor(R.color.color_87888C)),
            CircularRingData(0.3F,getColor(R.color.colorPrimaryDark),getColor(R.color.color_1D81FF)),
            CircularRingData(0.3F,getColor(R.color.colorAccent),getColor(R.color.color_FF6633)))

    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBind.unbind()
    }

}