package com.example.wuzhiming.myapplication.wideget

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.text.TextUtils
import android.util.AttributeSet
import com.example.wuzhiming.myapplication.utils.*
import java.io.File
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * @Author:         wuzm
 * @CreateDate:     2021/12/30 6:36 下午
 * @Description:    长图view
 */
class LongPictureView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    companion object {
        const val DEFAULT_PAINT_TEXT_DP_SIZE = 15f
        const val DEFAULT_PAINT_TEXT_STROKE_WIDTH = 5f
    }

    var sealText: String?=null
    var sealTimeText: String?=null

    var mPaint: Paint
    var imageUrls: MutableList<String> = mutableListOf()
    var imageBitmaps: MutableList<Pair<String, Bitmap>> = mutableListOf()
    var sortedBitmaps: MutableList<Bitmap> = mutableListOf()
    var waterMarkText = "Hello,安卓!"
    var paintTextSize: Float = 0.0f
        set(value) {
            field = value
            mPaint.textSize = value
        }

    init {
        adjustViewBounds = true
        scaleType = ScaleType.FIT_XY

        mPaint = Paint()
        //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        mPaint.isAntiAlias = true
        //绘图样式，对于设文字和几何图形都有效
        mPaint.style = Paint.Style.FILL
        //设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
        mPaint.textAlign = Paint.Align.LEFT
        mPaint.strokeWidth = DEFAULT_PAINT_TEXT_STROKE_WIDTH
        mPaint.color = Color.DKGRAY
        paintTextSize = ScreenUtils.dp2px(context, DEFAULT_PAINT_TEXT_DP_SIZE).toFloat()
    }

    fun test() {
//        imageUrls.add("/storage/emulated/0/Android/data/com.example.wuzhiming.myapplication/files/t_pic/IMG_20211223_145315.jpg")
//        imageUrls.add("/storage/emulated/0/Android/data/com.example.wuzhiming.myapplication/files/t_pic/A.jpg")
//        imageUrls.add("/storage/emulated/0/Android/data/com.example.wuzhiming.myapplication/files/t_pic/IMG_20211118_210235.jpg")


        imageUrls.add("/storage/emulated/0/缓存/files/IMG_20211223_145315.jpg")
        imageUrls.add("/storage/emulated/0/缓存/files/A.jpg")
        imageUrls.add("/storage/emulated/0/缓存/files/IMG_20211118_210235.jpg")

        sealText="你大爷的"
        sealTimeText="略略略"
        refreshView()
    }

    fun refreshView() {

        //判断是否有对应bitamp,没有则构造.有则塞入sortedBitmaps、
        //处理排序
        sortedBitmaps.clear()
        imageUrls.forEachIndexed() { index, imgUrl ->
            val pair = imageBitmaps.firstOrNull { item -> TextUtils.equals(item.first, imgUrl) }
            pair?.let {
                sortedBitmaps.add(pair.second)
            } ?: let {
                var source = deocodeAndCompressBitmap(imgUrl, context)
                //处理照片旋转
                source = dealWithRotate(imgUrl, source)
                imageBitmaps.add(Pair(imgUrl, source))
                sortedBitmaps.add(source)
            }
        }
        //绘制大bitmap
        var newBit = drawLongBitmap(sortedBitmaps)
        setImageBitmap(newBit)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (waterMarkText.isNullOrEmpty()) {
            //清除水印
//            canvas.drawColor(0,PorterDuff.Mode.CLEAR)
        } else {
            //绘制水印
            drawWaterMarkOnView(canvas)
        }

        drawSeal(canvas)

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }


    private fun drawLongBitmap(sortedBitmaps: MutableList<Bitmap>): Bitmap? {
        var maxBtmWidth: Int = sortedBitmaps.maxBy { btm -> btm.width }?.width ?: 0
        var height = 0f
        var totalBit = Bitmap.createBitmap(maxBtmWidth,
            sortedBitmaps.sumBy { bitmap -> (bitmap.height * (maxBtmWidth / bitmap.width.toFloat())).toInt() },
            Bitmap.Config.ARGB_8888)
        var canvas = Canvas(totalBit)

        sortedBitmaps.forEach { btm ->
//                val newsizeBitmap = getNewSizeBitmap(btm, maxBtmWidth)
            val newsizeBitmap = if (btm.width == maxBtmWidth) {
                btm
            } else {
                BitmapUtil.scaleBitmap(btm, maxBtmWidth / btm.width.toFloat())
            }
            canvas.drawBitmap(newsizeBitmap, 0f, height, null)
            height += newsizeBitmap.height
        }
        return totalBit
    }


    private fun drawLongBitmapAndWaterMark(sortedBitmaps: MutableList<Bitmap>): Bitmap? {
        var maxBtmWidth: Int = sortedBitmaps.maxBy { btm -> btm.width }?.width ?: 0
        var height = 0f
        var totalBit = Bitmap.createBitmap(maxBtmWidth,
            sortedBitmaps.sumBy { bitmap -> (bitmap.height * (maxBtmWidth / bitmap.width.toFloat())).toInt() },
            Bitmap.Config.ARGB_8888)
        var canvas = Canvas(totalBit)

        sortedBitmaps.forEach { btm ->
//                val newsizeBitmap = getNewSizeBitmap(btm, maxBtmWidth)
            val newsizeBitmap = if (btm.width == maxBtmWidth) {
                btm
            } else {
                BitmapUtil.scaleBitmap(btm, maxBtmWidth / btm.width.toFloat())
            }
            canvas.drawBitmap(newsizeBitmap, 0f, height, null)
            height += newsizeBitmap.height
        }
        //在bitmap上绘制水印
        canvas?.let {
            totalBit?.let { it1 ->
                drawWaterMarkOnBitmap(it, it1)
            }
        }
        return totalBit
    }

    //    画水印字体到view上
    fun drawWaterMarkOnView(canvas: Canvas) {
        drawWaterMark4(canvas, height, width)
    }

    fun drawWaterMark(canvas: Canvas, height: Int, width: Int) {
        if (waterMarkText.isNullOrEmpty()) {
            return
        }
        for (i in 100..height step (paintTextSize * 2).toInt()) {
            for (j in 0..width step ((waterMarkText.length * 0.8) * paintTextSize).toInt()) {
                val xPoint = paintTextSize + j.toFloat()
                val yPoint = i.toFloat()
                canvas.rotate(-30f, xPoint, yPoint)
                canvas.drawText(waterMarkText, xPoint, yPoint, mPaint)
                canvas.rotate(30f, xPoint, yPoint)
            }
        }
    }


    fun drawWaterMark2(canvas: Canvas, height: Int, width: Int) {
        if (waterMarkText.isNullOrEmpty()) {
            return
        }
        val diagonal = sqrt((height * height + width * width).toDouble()).toInt()
        val textWidth = mPaint.measureText(waterMarkText)
//        val ratio= tan(15/Math.PI)
        val ratio = sqrt(3.0)
        canvas.rotate(-30f)
        for (i in 100..diagonal step (textWidth / 2).toInt()) {
            val xLfet = (i / ratio).toInt()
            val xRight = (i * ratio).toInt()
            for (j in -xLfet..xRight step (textWidth * 1.5).toInt()) {
                val xPoint = j.toFloat()
                val yPoint = i.toFloat()
                canvas.drawText(waterMarkText, xPoint, yPoint, mPaint)
            }
        }
        canvas.rotate(30f)
    }

    fun drawWaterMark3(canvas: Canvas, height: Int, width: Int) {
        if (waterMarkText.isNullOrEmpty()) {
            return
        }
        val ratio = sqrt(3.0)
        val textWidth = mPaint.measureText(waterMarkText)
        val spaceY = (textWidth / 1.7).toInt()
        val spaceX = (textWidth * 1.5).toInt()
        for (i in 100..height step spaceY) {
            val deltaY = (i / ratio) % width
            for (j in 0..width step spaceX) {
                var xPoint = if (j.toFloat() + deltaY < width) {
                    (j.toFloat() + deltaY).toFloat()
                } else {
                    val newPoint = (j.toFloat() + deltaY - width).toFloat()
                    if ((deltaY - newPoint) > spaceX) {
                        newPoint
                    } else {
                        continue
                    }
                }
/*                val xPoint =
                    (j.toFloat()+deltY).toFloat()*/
                val yPoint = i.toFloat()
                canvas.rotate(-30f, xPoint, yPoint)
                canvas.drawText(waterMarkText, xPoint, yPoint, mPaint)
                canvas.rotate(30f, xPoint, yPoint)
                if ((width - xPoint) < spaceX) {
                    xPoint -= width
                    if ((deltaY - xPoint) > spaceX) {
                        canvas.rotate(-30f, xPoint, yPoint)
                        canvas.drawText(waterMarkText, xPoint, yPoint, mPaint)
                        canvas.rotate(30f, xPoint, yPoint)
                    }
                }
/*                Log.i("csdcsdcsd",
                    "yPoint=${yPoint};deltY=${deltaY};xPoint=${xPoint};j.toFloat()=${j.toFloat()}")*/
            }
        }
    }

    fun drawWaterMark4(canvas: Canvas, height: Int, width: Int) {
        if (waterMarkText.isNullOrEmpty()) {
            return
        }
        val ratio = sqrt(3.0)
        val textWidth = mPaint.measureText(waterMarkText)
        val spaceY = (textWidth / 1.7).toInt()
        val spaceX = (textWidth * 1.5).toInt()
        for (i in 100..height step spaceY) {
            val deltaY = (i / ratio) % width
            val yPoint = i.toFloat()
            var xPoint = deltaY.toFloat()
            while (xPoint < width) {
                canvas.rotate(-30f, xPoint, yPoint)
                canvas.drawText(waterMarkText, xPoint, yPoint, mPaint)
                canvas.rotate(30f, xPoint, yPoint)
                xPoint += spaceX
            }
            xPoint = (deltaY - spaceX).toFloat()
            while (xPoint + textWidth > 0) {
                canvas.rotate(-30f, xPoint, yPoint)
                canvas.drawText(waterMarkText, xPoint, yPoint, mPaint)
                canvas.rotate(30f, xPoint, yPoint)
                xPoint -= spaceX
            }
/*                Log.i("csdcsdcsd",
                    "yPoint=${yPoint};deltY=${deltaY};xPoint=${xPoint};j.toFloat()=${j.toFloat()}")*/
        }
    }


    //    等比放大水印字体到真实bitmap上
    fun drawWaterMarkOnBitmap(canvas: Canvas, bitmap: Bitmap) {
        val scale = bitmap.width.toFloat() / width
        val oldPaintTextSize = paintTextSize
        if (abs(scale - 1) > 0.01) {
            paintTextSize *= scale
            mPaint.strokeWidth = DEFAULT_PAINT_TEXT_STROKE_WIDTH * scale
        }
        drawWaterMark(canvas, bitmap.height, bitmap.width)
        paintTextSize = oldPaintTextSize
        mPaint.strokeWidth = DEFAULT_PAINT_TEXT_STROKE_WIDTH
    }

    /* fun newBitmap(bit1: Bitmap, bit2: Bitmap): Bitmap? {
         var newBit: Bitmap? = null
         val width = bit1.width
         if (bit2.width !== width) {
             val h2 = bit2.height * width / bit2.width
             newBit = Bitmap.createBitmap(width, bit1.height + h2, Bitmap.Config.ARGB_8888)
             val canvas = Canvas(newBit)
             val newSizeBitmap2: Bitmap = getNewSizeBitmap(bit2, width, h2)
             canvas.drawBitmap(bit1, 0f, 0f, null)
             canvas.drawBitmap(newSizeBitmap2, 0f, bit1.height.toFloat(), null)
         } else {
             newBit = Bitmap.createBitmap(width, bit1.height + bit2.height, Bitmap.Config.ARGB_8888)
             val canvas = Canvas(newBit)
             canvas.drawBitmap(bit1, 0f, 0f, null)
             canvas.drawBitmap(bit2, 0f, bit1.height.toFloat(), null)
         }
         return newBit
     }*/

    fun getNewSizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val scaleWidth = newWidth.toFloat() / bitmap.width
        val scaleHeight = newHeight.toFloat() / bitmap.height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun getNewSizeBitmap(bitmap: Bitmap, desWidth: Int): Bitmap {
        val scaleWidth = desWidth.toFloat() / bitmap.width
        val scaleHeight = scaleWidth
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    @Throws(Exception::class)
    private fun deocodeAndCompressBitmap(path: String, mContext: Context): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        if (OSUtils.isAndroidQ()) {
            val inputStream = mContext.contentResolver.openInputStream(Uri.fromFile(File(path)))
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()
        } else {
            BitmapFactory.decodeFile(path, options)
        }
        val source: Bitmap
        val inSampleSize: Int = if (options.outWidth > 7182 || options.outHeight > 7182) {
            4
        } else if (options.outWidth > 3564 || options.outHeight > 3564) {
            2
        } else {
            1
        }
        if (OSUtils.isAndroidQ()) {
            val `is` = mContext.contentResolver.openInputStream(Uri.fromFile(File(path)))
            source = BitmapUtil.decodeStreamBitmap(`is`, inSampleSize)
            `is`?.close()
        } else {
            source = BitmapUtil.decodeFileBitmap(path, inSampleSize)
        }
        return source
    }

    @Throws(Exception::class)
    private fun decodeBitmap(path: String, mContext: Context): Bitmap {
        val source: Bitmap
        if (OSUtils.isAndroidQ()) {
            val `is` = mContext.contentResolver.openInputStream(Uri.fromFile(File(path)))
            source = BitmapUtil.decodeStreamBitmap(`is`, 1)
            `is`?.close()
        } else {
            source = BitmapUtil.decodeFileBitmap(path, 1)
        }
        return source
    }

    //处理照片是否要旋转
    private fun dealWithRotate(path: String, source: Bitmap): Bitmap {
        val bitmapDegree = if (OSUtils.isAndroidQ()) {
            ImageHelper.getBitmapDegree(context, Uri.fromFile(File(path)))
        } else {
            ImageHelper.getBitmapDegree(path)
        }
        return if (bitmapDegree == 0) {
            source
        } else {
            BitmapUtil.rotateBitmap(source, bitmapDegree.toFloat())
        }
    }


    //生成最终的 高清bitmap
    fun createFinalBitmap(): Bitmap? {
        //处理排序
        sortedBitmaps.clear()
        imageUrls.forEachIndexed { index, imgUrl ->
            var source = decodeBitmap(imgUrl, context)
            //处理照片旋转
            source = dealWithRotate(imgUrl, source)
            sortedBitmaps.add(source)
        }
        //绘制大bitmap
        return drawLongBitmapAndWaterMark(sortedBitmaps)
    }

    //画个印章
    fun drawSeal(canvas: Canvas) {
        if (sealText.isNullOrEmpty()) {
            return
        }
        val paint = Paint()
        //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.isAntiAlias = true
        //绘图样式，对于设文字和几何图形都有效
        paint.style = Paint.Style.STROKE
        //设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
        paint.textAlign = Paint.Align.CENTER
        paint.strokeWidth = DisplayUtil.dip2px(1f)
        paint.color = Color.RED
        paint.textSize = DisplayUtil.dip2px(12f)

        val start=50f
        val top=50f
        val interval=20

        val rect1 = Rect()
        val rect2 = Rect()
        paint.getTextBounds(sealText, 0, sealText?.length ?: 0, rect1)
        if (!sealTimeText.isNullOrEmpty()){
            paint.getTextBounds(sealText, 0, sealText?.length ?: 0, rect2)
        }
        val rectWidth = rect1.width().coerceAtLeast(rect2.width())
        var reactHeight =
            rect1.height()+rect2.height()+interval*2


        canvas.rotate(30f)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = DisplayUtil.dip2px(15f)
        canvas.drawText(sealText!!,(rectWidth+start*2+interval*2)/2,(rect1.height()*0.75+top+interval).toFloat(),paint)
        if (!sealTimeText.isNullOrEmpty()){
            reactHeight+=interval
            canvas.drawText(sealTimeText!!,(rectWidth+start*2+interval*2)/2,((reactHeight)*0.75+top).toFloat(),paint)
        }
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = DisplayUtil.dip2px(1f)
        canvas.drawRect( RectF(start,top,rectWidth+start+interval*2,top+reactHeight),paint)
        canvas.rotate(-30f)

    }

}