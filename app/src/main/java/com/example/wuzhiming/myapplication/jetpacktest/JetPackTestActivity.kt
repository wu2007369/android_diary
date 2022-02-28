package com.example.wuzhiming.myapplication.jetpacktest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.databinding.ActivityJetPackTestBinding

class JetPackTestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityJetPackTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 绑定工作
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jet_pack_test)
        // 建立感应
        binding.lifecycleOwner = this

//        setContentView(R.layout.activity_jet_pack_test)
        findViewById<View>(R.id.btn).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn -> startActivity(Intent(this, NormalUseActivity::class.java))
        }
    }

    fun changeBool(v:View?){
        //binding中的变量取出来，是可空的。
        binding.boolTest=(binding.boolTest?:false).not()
    }
}