package com.mango.bridge.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.databinding.ViewDataBinding
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.dialog.base.BaseDialogBindingFragment
import com.example.wuzhiming.myapplication.utils.DisplayUtil

/**
 * author：mango
 * date：2022/5/14 11:20
 * desc：
 * modify record :
 *  底部展示的dialog
 */
abstract class BottomDialogImpl<T : ViewDataBinding> : BaseDialogBindingFragment<T>() {

    override fun setDialogStyle() = R.style.dlg_ActionSheetDialogStyle

    override fun setParams() {
        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val attributes = window?.attributes
        attributes?.gravity = Gravity.BOTTOM
        attributes?.width = DisplayUtil.getScreenWidth(context)
        window?.attributes = attributes
    }
}