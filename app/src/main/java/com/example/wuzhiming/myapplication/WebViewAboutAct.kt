package com.example.wuzhiming.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.webkit.WebView
import android.widget.LinearLayout
import com.example.wuzhiming.myapplication.base.BaseActV2
import com.example.wuzhiming.myapplication.databinding.ActivityWebviewAboutBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @Author:         wuzm
 * @CreateDate:     2022/3/14 12:22 下午
 * @Description:    类作用描述
 */
class WebViewAboutAct : BaseActV2<ActivityWebviewAboutBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_webview_about
    var currenIndex = 0
    var currenIndex3 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val content1 =
            "<div style=\\\"display:flex;justify-content: space-between;\\\"><div style='width:100%;'><img src='https://cdn-h.gongfudou.com/Leviathan/user/const/2021/1/19/53640744-d5a4-4d66-958c-ef2ef1694432' style='width:100%;' /></div></div>"
        val content2 =
            "\n\n<div class=\"qml-stem\"><div class=\"qml-stem\"><p style=\"text-align: left;\">下列加点的字注音都正确的一项是（ ）</p></div></div>\n\n"
        val content3 =
            "<div style=\"display:flex;justify-content: space-between;\"><div style='width:48%;'><img src='https://cdn-h.gongfudou.com/Leviathan/user/const/2021/1/19/f3dc7730-4691-47d3-ac2d-1da06809fd8c' style='width:100%;' /></div></div>"
        val content4 =
            "<div style=\"display:flex;justify-content: space-between;\"><div style='width:100%;'><img src='https://cdn-h.gongfudou.com/Leviathan/user/const/2021/1/19/f3dc7730-4691-47d3-ac2d-1da06809fd8c' style='width:100%;' /></div></div>"
        val content6 =
            "<div style=\"display:flex;justify-content: space-between;\"><div style='width:100%;height:100%;'><img src='https://cdn-h.gongfudou.com/Leviathan/user/const/2021/1/19/f3dc7730-4691-47d3-ac2d-1da06809fd8c' style='width:100%;height:100%;' /></div></div>"
        val content5 =
            "<div style=\"display:flex;justify-content: space-between;\"><div style='width:100px;height:100px;'><img src='https://cdn-h.gongfudou.com/Leviathan/user/const/2021/1/19/f3dc7730-4691-47d3-ac2d-1da06809fd8c' style='width:100px;height:100px;' /></div></div>"
        val content7="<div style=\"display:flex;justify-content: space-between;\"><div style=\"width:100%;\"><img src='https://micro.obs.cn-east-2.myhuaweicloud.com/Leviathan/user/const/2022/3/28/04fa4ccd-6ef8-4eea-8845-a5d87c237645.jpg' style='width:100%;' /></div></div>"
        val list = arrayListOf<String>(content1, content1, content2, content3,content4,content5,content6,content7)
//        val list= arrayListOf<String>(content2,content3,content1,content1)

        val webSettings = mDataBind.webview2.settings
        webSettings.setJavaScriptEnabled(true)
        webSettings?.useWideViewPort = true
        webSettings?.loadWithOverviewMode = true
        webSettings?.loadsImagesAutomatically = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings?.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

//        val params=mDataBind.webview2.layoutParams
//                    params.height=DisplayUtil.dip2px(mDataBind.webview2.contentHeight.toFloat()).toInt()
//        params.height=DisplayUtil.dip2px(50f).toInt()
//        mDataBind.webview2.layoutParams=params

//        mDataBind.webview.loadDataWithBaseURL(null, content2, "text/html", "UTF-8", "")


        mDataBind.webview2.webViewClient = object : com.tencent.smtt.sdk.WebViewClient() {
            override fun onPageFinished(p0: com.tencent.smtt.sdk.WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
//                Log.i("WebViewAboutAct", "onPageFinished p1=" + p1)

                processDelay(200) {
                    Log.i("WebViewAboutAct",
                        "onPageFinished contentHeight=" + mDataBind.webview2.contentHeight)

                    mDataBind.webview2.invalidate()
                    val bitmap = mDataBind.webview2.convert2Bitmap()
                    coverBitmap2Local(this@WebViewAboutAct, bitmap)
                    currenIndex++
                    covertListJpg(list)
                }
            }
        }


        mDataBind.btn.setOnClickListener {
//            val bitmap=mDataBind.webview.convert2Bitmap()
            val bitmap = mDataBind.webview2.convert2Bitmap()
            coverBitmap2Local(this, bitmap)
        }

        mDataBind.btn2.setOnClickListener {
            covertListJpg(list)
        }

        mDataBind.btn3.setOnClickListener {
            covertListJpg3(list)
        }
    }

    private fun covertListJpg(list: List<String>) {
        if (currenIndex < list.size) {
            Log.i("WebViewAboutAct", "covertListJpg currenIndex=" + currenIndex)
            mDataBind.webview2.loadDataWithBaseURL(null, list.get(currenIndex), "text/html",
                "UTF-8", "")
        } else {

        }
    }

    private fun covertListJpg3(list: List<String>) {
        if (currenIndex3<list.size){
            val content=list.get(currenIndex3)
            var web = com.tencent.smtt.sdk.WebView(this).apply {
                val params = LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
                mDataBind.webview2.layoutParams = params
            }
            mDataBind.bg.addView(web)
            web.webViewClient = object : com.tencent.smtt.sdk.WebViewClient() {

                override fun onPageFinished(p0: com.tencent.smtt.sdk.WebView?, p1: String?) {
                    super.onPageFinished(p0, p1)
                    processDelay(100) {
                        val bitmap = web.convert2Bitmap()
                        coverBitmap2Local(this@WebViewAboutAct, bitmap)
                        mDataBind.bg.removeView(web)
                        web.removeAllViews()
                        currenIndex3++
                        covertListJpg3(list)
                    }
                }
            }
            web.loadDataWithBaseURL(null,
                content, "text/html", "UTF-8", "")
        }

    }


}


/**
 * webview转bitmap
 */
fun com.tencent.smtt.sdk.WebView.convert2Bitmap(): Bitmap {
    val picture: Picture = this.capturePicture()
    val b = Bitmap.createBitmap(picture.width, picture.height, Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    picture.draw(c)

    return b
}

/**
 * webview转bitmap
 */
fun WebView.convert2Bitmap(): Bitmap {
    val picture: Picture = this.capturePicture()
    val b = Bitmap.createBitmap(picture.width, picture.height, Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    picture.draw(c)

    return b
}

/**
 * bitmap存储到本地文件
 */
fun coverBitmap2Local(context: Context, bitmap: Bitmap, desFileName: String? = null): String {
    val parent = getPhotoTempParent(context)
    val fileName = if (desFileName.isNullOrEmpty()) {
        "mmexport" + System.currentTimeMillis() + ".jpg"
    } else {
        desFileName
    }
    val avaterFile = File(parent, fileName)//设置文件名称

    if (avaterFile.exists()) {
        avaterFile.delete()
    }
//    FileStorageUtil.getInstance().putBitmapToExternalStorage(avaterFile.parentFile, fileName, bitmap)
//    return ""
    return try {
        avaterFile.createNewFile()
        val fos = FileOutputStream(avaterFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()
        avaterFile.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }
}

/**
 * 图片临时目录
 */
fun getPhotoTempParent(context: Context): File {
    val parent: File = File(context.getExternalFilesDir(null as String?), "/t_pic")
    if (!parent.exists()) {
        parent.mkdirs()
    }
    return parent
}

private fun captureWebViewLollipop(webView: com.tencent.smtt.sdk.WebView): Bitmap {
    val scale = webView.scale
    val width = webView.width
    val height = (webView.contentHeight * scale + 0.5).toInt()
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    val canvas = Canvas(bitmap)
    webView.draw(canvas)
    return bitmap
}