package com.example.wuzhiming.myapplication

import android.os.Bundle
import android.widget.Toast
import com.example.wuzhiming.myapplication.base.BaseActV2
import com.example.wuzhiming.myapplication.databinding.ActEventTestBinding

/**
 * @Author:         wuzm
 * @CreateDate:     2022/9/16 10:58 上午
 * @Description:    类作用描述
 */
class EventTestAct:BaseActV2<ActEventTestBinding>() {
    override fun setLayoutId()=R.layout.act_event_test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind.bottomBg.setOnClickListener {
            Toast.makeText(this,"这证明了,事件具有穿透性",Toast.LENGTH_SHORT).show()
        }


        mDataBind.topListenItem2.setOnClickListener {
            Toast.makeText(this,"滑动事件能穿透，点击事件呢",Toast.LENGTH_SHORT).show()
        }


        //设置了clicklistener，需要isClickable=false，才能取消点击事件的消费
        mDataBind.topCoordinator.setOnClickListener(null)
        mDataBind.topCoordinator.isClickable=false
    }
}