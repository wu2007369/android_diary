package com.mango.bridge.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.utils.DisplayUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @author: guxxxxd
 * @Time: 2022/9/14
 * @Version:
 * @Desc:
 */
abstract class BaseBottomSheetDialogBindingFragment<T : ViewDataBinding> : BottomSheetDialogFragment() {

    protected lateinit var mDataBind: T

    var isSaveInState = false

    abstract fun setLayoutId(): Int
    abstract fun initView(root: View?)
    abstract fun setPeekHeight(): Int

    open fun setDialogStyle(): Int = R.style.dlg_ActionSheetDialogStyle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, setDialogStyle())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBind = DataBindingUtil.inflate(inflater, setLayoutId(), container, false)
        val root = mDataBind.root
        initView(root)
        return root
    }

    override fun onStart() {
        super.onStart()
        setParams()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (isSaveInState) {
            return
        }
        try {
            manager.executePendingTransactions()
            if (!isAdded) {
                super.show(manager, tag)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        if (isSaveInState) {
            return
        }
        if (fragmentManager == null) {
            return
        }
        super.dismiss()
    }

    open fun setParams() {
        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val attributes = window?.attributes
        attributes?.gravity = Gravity.BOTTOM
        attributes?.width = DisplayUtil.getScreenWidth(context)
        window?.attributes = attributes
        getBehavior()?.peekHeight = setPeekHeight()
    }

    fun getBehavior(): BottomSheetBehavior<View>? =
        dialog?.window?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.let { BottomSheetBehavior.from(it) }
}