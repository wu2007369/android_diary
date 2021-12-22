package com.example.wuzhiming.myapplication.itext

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.events.PdfDocumentEvent
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState
import com.itextpdf.layout.Canvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.VerticalAlignment
import java.io.*
import java.util.*


/**
 * @Author:         wuzm
 * @CreateDate:     2021/12/20 5:32 下午
 * @Description:    类作用描述
 */
object Itext7Utils {
    /**
     * 在指定目录等分pdf
     * @param fileName  要分割的文档
     * @param pageNum   分割尺寸
     * @param desDir    分割后存储路径
     * @throws IOException
     */
    @Throws(IOException::class)
     fun pdfSplitter(fileName: String, pageNum: Int, desDir: String) {
        val pdfReader = PdfReader(fileName)
        val pdf = PdfDocument(pdfReader)
        var name: String
        var pdfWriter: PdfWriter? = null
        var pdfWriterDoc: PdfDocument? = null
        var i = 1
        while (i <= pdf.numberOfPages) {
            name = "$desDir/$i.pdf"
            pdfWriter = PdfWriter(name)
            pdfWriterDoc = PdfDocument(pdfWriter)
            val start = i
            val end = Math.min(start + pageNum - 1, pdf.numberOfPages)
            //从页数第一页开始，
            pdf.copyPagesTo(start, end, pdfWriterDoc)
            pdfWriterDoc.close()
            pdfWriter.close()
            i += pageNum
        }
        //关闭
        pdf.close()
        pdfReader.close()
    }

    /**
     * 分割文档，分割后文仔默认存储在原来的文档路径下。
     * @param fileName
     * @param pageNum
     * @throws IOException
     */
    @Throws(IOException::class)
     fun pdfSplitter(fileName: String, pageNum: Int) {
        val desDir = File(fileName).parentFile.absolutePath
        pdfSplitter(fileName, pageNum, desDir)
    }


    /**
     * 返回自定义片段大小的文件，UUID名称命名。
     * @param fileName
     * @param startPage
     * @param endPage
     * @throws IOException
     */
    @Throws(IOException::class)
    fun pdfSplitter(fileName: String?, startPage: Int, endPage: Int) {
        //源文档
        val pdfReader = PdfReader(fileName)
        val pdf = PdfDocument(pdfReader)
        //目标文档名
        val desDir =
            (File(fileName).parentFile.absolutePath + "/" + UUID.randomUUID().toString() + ".pdf")
        //生成目标文档
        val pdfWriter = PdfWriter(desDir)
        val outPdfDocument = PdfDocument(pdfWriter)
        //从页数第一页开始，
        pdf.copyPagesTo(startPage, endPage, outPdfDocument)
        //关闭
        outPdfDocument.close()
        pdfWriter.close()
        pdf.close()
        pdfReader.close()
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun addWatermark2(srcPdfPath: String?, destPdfPath: String?, watermarkText: String?) {
        val pdfDoc = PdfDocument(PdfReader(srcPdfPath), PdfWriter(destPdfPath))
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE) { event ->
            val docEvent = event as PdfDocumentEvent
            val pdfDoc = docEvent.document
            val page = docEvent.page
            var font: PdfFont? = null
            try {
                font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true)
//                font = PdfFontFactory.createFont("STSong-Light", PdfEncodings.IDENTITY_H, false)
//                val font = PdfFontFactory.createFont("C:\\Users\\xx\\Desktop\\d\\songti.TTF", PdfEncodings.IDENTITY_H, false)

//                                    font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD); // 要显示中文水印的话，需要设置中文字体，这里可以动态判断
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val canvas = PdfCanvas(page)
            val gs1 = PdfExtGState()
            gs1.setFillOpacity(1f) // 水印透明度
            canvas.setExtGState(gs1)
            Canvas(canvas, pdfDoc, page.pageSize).setFontColor(ColorConstants.LIGHT_GRAY)
                .setFontSize(60f)
                .setFont(font)
                .showTextAligned(Paragraph(watermarkText), 298f, 421f, pdfDoc.getPageNumber(page),
                    TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0f)
        }
        pdfDoc.close()
    }

    @Throws(IOException::class)
    fun addWaterMark(pdfIS: String?, pdfOS: String?, text: String?) {
        val sourceFile = File(pdfIS)
        val pdfFile = File(pdfOS)
        addTestMarkForPDF(FileInputStream(sourceFile), FileOutputStream(pdfFile), text)
    }

    /**
     * 82     * 给pdf的每一页添加注释文字
     * 83     * @param pdfIS 需要增加文字前的pdf输入流
     * 84     * @param pdfOS 增加文字后的pdf输出流
     * 85     * @param text 需要增加的说明文字
     * 86     * @throws WriterException
     * 87     * @throws IOException
     * 88
     */
    @Throws(IOException::class)
    fun addTestMarkForPDF(pdfIS: InputStream?, pdfOS: OutputStream?, text: String?) {
        var textLeft = 0
        var textWidth = 0
        var textBottom = 0
        val pdfDoc = PdfDocument(PdfReader(pdfIS), PdfWriter(pdfOS))
        var doc: Document? = null
        try {
            doc = Document(pdfDoc)
            val n = pdfDoc.numberOfPages
            val pagesize = pdfDoc.getPage(n).pageSize
            for (i in 0 until n) {
                textLeft = (pagesize.width / 5).toInt() * 4
                textWidth = 500
                textBottom = (pagesize.height / 10).toInt()
//                val font = PdfFontFactory.createFont()
//                val font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true)
                val font = PdfFontFactory.createFont(StandardFonts.HELVETICA, PdfEncodings.UTF8)
                val para = Paragraph().setFont(font).setFontSize(50f)
                val rgbColor = DeviceRgb(192, 192, 192)
                //          para.setFontColor(DeviceRgb.makeLighter(rgbColor));    // 定制字体颜色(偏亮)
                para.setFontColor(DeviceRgb.makeDarker(rgbColor)) // 定制字体颜色(偏暗)
                para.setRotationAngle(0f) // 设置文字倾斜度
                para.setOpacity(1f) // 设置文字透明化度
                para.setFixedPosition(i + 1, textLeft.toFloat(), textBottom.toFloat(),
                    textWidth.toFloat()) // 设置文字的绝对位置
                para.setTextAlignment(TextAlignment.JUSTIFIED) // 设置文字排序方式
                para.add(text)
                doc.add(para)
            }
        } finally {
            doc?.close()
        }
    }


    fun createImgPdf(filePath: String?, image: String) {
        try {
            val writer = PdfWriter(FileOutputStream(File(filePath)))
            val pdf = PdfDocument(writer)
            val document = Document(pdf)
//            val font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false)
//            val title = Paragraph("标题").setTextAlignment(TextAlignment.CENTER).setFontSize(16f)
//            title.setFont(font)
//            document.add(title)
//            document.add(Paragraph("模板内容").setFont(font))
//            val codeQrImage: Image = Image(ImageDataFactory.create(image), 450f, 600f)
            val codeQrImage: Image = Image(ImageDataFactory.create(image), 0f, 0f)
            //图片大小
//            codeQrImage.scaleAbsolute(70f, 30f)
            codeQrImage.scale(1f, 0.8f)
            document.add(codeQrImage)
            document.close()
            writer.close()
            pdf.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun pdf2Img(ORIG: String?, OUTPUT_FILE: String?) {
        val pdfFile: File = File(ORIG)
        var descriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_WRITE)
        var render= PdfRenderer(descriptor)
        val mCurrentPage = render.openPage(0)
        //空位图的大小，Config是图片ARGB通道，8888是最清晰的了
        val bitmap = Bitmap.createBitmap(
            mCurrentPage.width,
            mCurrentPage.height,
            Bitmap.Config.ARGB_8888
        )
        //render支持裁切和旋转参数
        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_PRINT)

        descriptor?.close()
        render?.close()
    }
}