package com.example.wuzhiming.myapplication.dialog

import android.view.View
import android.widget.Toast
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.databinding.DialogBottomDragBinding
import com.example.wuzhiming.myapplication.utils.DisplayUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mango.bridge.dialog.BaseBottomSheetDialogBindingFragment


/**
 * @Author:         wuzm
 * @CreateDate:     2022/9/14 4:33 下午
 * @Description:    类作用描述
 */
class TestBottomDragDlg : BaseBottomSheetDialogBindingFragment<DialogBottomDragBinding>() {
    override fun setLayoutId() = R.layout.dialog_bottom_drag

    override fun initView(root: View?) {
        mDataBind.clickTest.setOnClickListener {
            Toast.makeText(context,"Vdfvdfv",Toast.LENGTH_LONG).show()
        }
    }

    override fun setParams() {
        super.setParams()
        val view: View? =
            dialog?.window?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        view?.let { BottomSheetBehavior.from(it).peekHeight = DisplayUtil.dip2px(200f).toInt() }
    }
}