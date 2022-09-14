package com.example.wuzhiming.myapplication

import androidx.appcompat.app.AppCompatActivity
import com.example.wuzhiming.myapplication.dialog.VerificationScanCodeOrderDialog
import android.text.style.ClickableSpan
import android.widget.TextView
import android.widget.Toast
import android.text.TextPaint
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.CharacterStyle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.WindowManager
import android.app.TimePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.wuzhiming.myapplication.databinding.ActivityDialogTest2Binding
import com.example.wuzhiming.myapplication.dialog.TestBottomDragDlg
import java.text.DateFormat
import java.util.*

class DialogTestActivity : AppCompatActivity() {
    private lateinit var mDataBind: ActivityDialogTest2Binding
    private val TAG = "DialogTestActivity"
    var format = DateFormat.getDateTimeInstance()
    var calendar = Calendar.getInstance(Locale.CHINA)
    private var mDialog: VerificationScanCodeOrderDialog? = null

    val  bottomDragDlg: TestBottomDragDlg by lazy {
        TestBottomDragDlg()
    }

    private inner class TextClick : ClickableSpan() {
        override fun onClick(widget: View) {
            //在此处理点击事件
            Log.e("------->", "点击了")
            val content = (widget as TextView).text.toString()
            Toast.makeText(this@DialogTestActivity, "点击了$content", Toast.LENGTH_SHORT).show()
        }

        override fun updateDrawState(ds: TextPaint) {
//            Toast.makeText(TextActivity.this,"点击了"+ds,Toast.LENGTH_SHORT).show();
//            ds.setColor(ds.linkColor);
//            ds.setUnderlineText(true);
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind=ActivityDialogTest2Binding.inflate(layoutInflater)
        mDataBind.lifecycleOwner = this
        setContentView(mDataBind.root)//必须set，否则无效
//        setContentView(R.layout.activity_dialog_test2)
        findViewById<View>(R.id.btn1).setOnClickListener { v: View? -> tipDialog() }
        findViewById<View>(R.id.btn7).setOnClickListener { v: View? -> tipDialog2() }
        findViewById<View>(R.id.btn2).setOnClickListener { v: View? -> itemListDialog() }
        findViewById<View>(R.id.btn3).setOnClickListener { v: View? -> singleChoiceDialog() }
        findViewById<View>(R.id.btn4).setOnClickListener { v: View? -> multiChoiceDialog() }
        findViewById<View>(R.id.btn5).setOnClickListener { v: View? -> showDateDialog() }
        findViewById<View>(R.id.btn6).setOnClickListener { v: View? -> showTimeDialog() }
        findViewById<View>(R.id.btn8).setOnClickListener { v: View? -> showCustomeDialog() }
        findViewById<View>(R.id.btn9).setOnClickListener { v: View? -> showWindowParamsDialog() }
        findViewById<View>(R.id.btn10).setOnClickListener { v: View? -> showDialogContentClick() }



        mDataBind.btn11.setOnClickListener {
            bottomDragDlg.show(supportFragmentManager,"")
        }
    }

    override fun onDestroy() {
        mDataBind.unbind()
        super.onDestroy()
    }

    private fun showDialogContentClick() {
        val message = TextView(this)
        // i.e.: R.string.dialog_message =>
        // "Test this dialog following the link to dtmilano.blogspot.com"
        val a = "1234567890abcdefghijklmn"
        val spannableBuilder = SpannableStringBuilder(a)
        // 单独设置字体颜色
        val colorSpan = ForegroundColorSpan(Color.parseColor("#3072F6"))

/*        spannableBuilder.setSpan(colorSpan, 14, a.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableBuilder.setSpan(colorSpan, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/spannableBuilder.setSpan(
            CharacterStyle.wrap(colorSpan), 14, a.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        spannableBuilder.setSpan(CharacterStyle.wrap(colorSpan), 1, 2,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        spannableBuilder.setSpan(TextClick(), 14, a.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        spannableBuilder.setSpan(TextClick(), 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        message.highlightColor = resources.getColor(android.R.color.transparent) //不设置会有背景色
        message.movementMethod = LinkMovementMethod.getInstance()
        message.text = spannableBuilder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示：")
        builder.setMessage(spannableBuilder)
        //        builder.setView(message);
        builder.setCancelable(false) //点击对话框以外的区域是否让对话框消失

        //设置正面按钮
        builder.setPositiveButton("确定") { dialog, which ->
            Toast.makeText(this@DialogTestActivity, "你点击了确定", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        val dialog = builder.create() //创建AlertDialog对象
        dialog.show() //显示对话框
        val textView = dialog.findViewById<TextView>(android.R.id.message)
        textView!!.highlightColor = resources.getColor(android.R.color.transparent) //不设置会有背景色
        textView.movementMethod = LinkMovementMethod.getInstance()
        //        textView.setText(spannableBuilder);
    }

    private fun showWindowParamsDialog() {
        val mWindowDialog = tipDialog()
        //调整明暗度的代码一定要在show()之后执行
        val lp = mWindowDialog.window!!.attributes
        //调整明暗度，float值，完全透明不变暗是0.0f，完全变暗不透明是1.0f
        lp.dimAmount = 0.65f
        //必须要设置回去
        mWindowDialog.window!!.attributes = lp
        //根据谷歌文档，给对应的Window添加FLAG_DIM_BEHIND标志位，dimAmount值才有效。
        mWindowDialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun showCustomeDialog() {
        if (null == mDialog) {
            mDialog = VerificationScanCodeOrderDialog(this,
                VerificationScanCodeOrderDialog.DialogType.MARKET, "content")
        }
        mDialog!!.show()
    }

    private fun showTimeDialog() {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        TimePickerDialog(this, 0,  // 绑定监听器
            { view, hourOfDay, minute ->
                Log.i(TAG, "hourOfDay：$hourOfDay; minute:$minute")
                Log.i(TAG, "您选择了：" + hourOfDay + "时" + minute + "分")
            } // 设置初始时间
            , calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE] // true表示采用24小时制
            , true).show()
    }

    private fun showDateDialog() {
// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        val listener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // 绑定监听器(How the parent is notified that the date is set.)
            // 此处得到选择的时间，可以进行你想要的操作
            Log.i(TAG, "year：$year; monthOfYear$monthOfYear; dayOfMonth$dayOfMonth")
            Log.i(TAG, "您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日")
        }
        DatePickerDialog(this, 0, listener // 设置初始日期
            , calendar[Calendar.YEAR], calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]).show()
    }

    /**
     * 提示对话框
     */
    fun tipDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示：")
        builder.setMessage("这是一个普通对话框，")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setCancelable(true) //点击对话框以外的区域是否让对话框消失

        //设置正面按钮
        builder.setPositiveButton("确定") { dialog, which ->
            Toast.makeText(this@DialogTestActivity, "你点击了确定", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        //设置反面按钮
        builder.setNegativeButton("取消") { dialog, which ->
            Toast.makeText(this@DialogTestActivity, "你点击了取消", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        //设置中立按钮
        builder.setNeutralButton("保密") { dialog, which ->
            Toast.makeText(this@DialogTestActivity, "你选择了中立", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        val dialog = builder.create() //创建AlertDialog对象
        //对话框显示的监听事件
        dialog.setOnShowListener { Log.e(TAG, "对话框显示了") }
        //对话框消失的监听事件
        dialog.setOnCancelListener { Log.e(TAG, "对话框消失了") }
        dialog.show() //显示对话框


        //改变dialog按钮颜色
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"))
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#fd8d99"))
        return dialog
    }

    fun tipDialog2() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示：")
        builder.setMessage("这是一个普通对话框，")
        builder.setCancelable(false) //点击对话框以外的区域是否让对话框消失

        //设置正面按钮
        builder.setPositiveButton("确定") { dialog, which ->
            Toast.makeText(this@DialogTestActivity, "你点击了确定", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        val dialog = builder.create() //创建AlertDialog对象
        dialog.show() //显示对话框
    }

    /**
     * 列表对话框
     */
    private fun itemListDialog() {
        val builder = AlertDialog.Builder(this@DialogTestActivity)
        builder.setTitle("选择你喜欢的课程：")
        builder.setCancelable(true)
        val lesson = arrayOf("语文", "数学", "英语", "化学", "生物", "物理", "体育")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setIcon(R.mipmap.ic_launcher_round)
            .setItems(lesson) { dialog, which ->
                Toast.makeText(applicationContext, "你选择了" + lesson[which], Toast.LENGTH_SHORT)
                    .show()
            }
            .create()
        //设置正面按钮
        builder.setPositiveButton("确定") { dialog, which -> dialog.dismiss() }
        //设置反面按钮
        builder.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
        val dialog = builder.create() //创建AlertDialog对象
        dialog.show() //显示对话框
    }

    /**
     * 单选对话框
     */
    fun singleChoiceDialog() {
        val builder = AlertDialog.Builder(this@DialogTestActivity)
        builder.setTitle("你现在居住地是：")
        val cities = arrayOf("北京", "上海", "广州", "深圳", "杭州", "天津", "成都")
        val chedkedItem = intArrayOf(2)
        builder.setSingleChoiceItems(cities, chedkedItem[0]) { dialog, which ->
            Toast.makeText(applicationContext, "你选择了" + cities[which], Toast.LENGTH_SHORT).show()
            chedkedItem[0] = which
        }
        builder.setPositiveButton("确认") { dialog, which -> dialog.dismiss() }
        builder.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
        val dialog = builder.create() //创建AlertDialog对象
        dialog.show() //显示对话框
    }

    /**
     * 复选对话框
     */
    fun multiChoiceDialog() {
        val builder = AlertDialog.Builder(this@DialogTestActivity)
        builder.setTitle("请选择你喜欢的颜色：")
        val colors = arrayOf("红色", "橙色", "黄色", "绿色", "蓝色", "靛色", "紫色")
        val myColors: MutableList<String> = ArrayList()
        builder.setMultiChoiceItems(colors, null) { dialog, which, isChecked ->
            if (isChecked) {
                myColors.add(colors[which])
            } else {
                myColors.remove(colors[which])
            }
        }
        builder.setPositiveButton("确认") { dialog, which ->
            var result = ""
            for (color in myColors) {
                result += "$color、"
            }
            Toast.makeText(applicationContext, "你选择了: $result", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        builder.setNegativeButton("取消") { dialog, which ->
            myColors.clear()
            dialog.dismiss()
        }
        val dialog = builder.create() //创建AlertDialog对象
        dialog.show() //显示对话框
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#D0021B"))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3072F6"))
    }
}