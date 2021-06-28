package com.example.wuzhiming.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
/*import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfWriter*/
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class PdfAboutActivity : AppCompatActivity() {

    companion object{
        const val TAG="PdfAboutActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfabout)

        findViewById<Button>(R.id.btn).setOnClickListener {
//            splitPdf("/storage/emulated/0/缓存/典型时评文章50篇.pdf",50)
        }
    }

    /**
     * 将filename文件切分成多个自定义页数大小的文件
     * @param filename 文件路径
     * @param splitSize  切分后的小文件的页数
     * @return 将filename文件划分成的子文件数目 >0则是成功
     */
    /*fun splitPdf(filename: String, splitSize: Int): Int {
        // String filename = "1904.08394.pdf";
        var reader: PdfReader? = null
        try {
            reader = PdfReader(filename)
        } catch (e: IOException) {
            return -1
        }
        val numberOfPages: Int = reader.getNumberOfPages()
        var numberOfNewFiles = 0
        var pageNumber = 1
        while (pageNumber <= numberOfPages) {
            val doc = Document()
            val outputFilename = String.format(
                filename.substring(0, filename.length - 4) + "_%02d" + ".pdf",
                numberOfNewFiles
            )
            var writer = try {
                PdfWriter.getInstance(doc, FileOutputStream(outputFilename))
            } catch (e: FileNotFoundException) {
                return -2 - numberOfNewFiles * 10
            } catch (e: DocumentException) {
                return -3 - numberOfNewFiles * 10
            }
            doc.open()
            val cb: PdfContentByte = writer.getDirectContent()
            // 这里判断加到了循环里不好，有优化空间
            var j = 1
            while (pageNumber <= numberOfPages && j <= splitSize) {
                doc.newPage()
                // 查看源码得知pageNumber是从1开始计数的
                cb.addTemplate(writer.getImportedPage(reader, pageNumber), 0F, 0F)
                ++j
                pageNumber++
            }
            doc.close()
            numberOfNewFiles++
            writer.close()
        }
        return numberOfNewFiles
    }*/
}