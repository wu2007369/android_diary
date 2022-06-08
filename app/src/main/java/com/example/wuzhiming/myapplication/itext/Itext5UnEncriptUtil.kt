package com.example.wuzhiming.myapplication.itext


import android.util.Log
import com.itextpdf.text.Document
import com.itextpdf.text.Rectangle
import com.itextpdf.text.exceptions.BadPasswordException
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfSmartCopy
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

/**
 * @Author:         wuzm
 * @CreateDate:     2022/6/8 2:52 下午
 * @Description:    类作用描述
 */
object Itext5UnEncriptUtil {

    fun fun2(dest: String?) {  //检查是否加密
        try {
            val pdfReader = PdfReader(dest)
        Log.i("Itext5UnEncriptUtil","is isEncrypted:"+pdfReader.isEncrypted)
        }catch (e: BadPasswordException){
            Log.i("Itext5UnEncriptUtil","is isEncrypted BadPasswordException :"+ true)
        }
    }
    fun fun3(dest: String?, dest2: String?, password: String) {  //复制 加密文件到新文件
        val pdfReader = PdfReader(dest, password.toByteArray(StandardCharsets.UTF_8))
        val pageSize: Rectangle = pdfReader.getPageSize(1)
        val numberOfPages = pdfReader.numberOfPages
        val pdfDocument = Document(pageSize)
        val outStream=FileOutputStream(dest2)
        val pdfWriter = PdfWriter.getInstance(pdfDocument, FileOutputStream(dest2))

        val copy=PdfSmartCopy(pdfDocument,outStream)
        pdfDocument.open()
        for (i in 1..numberOfPages) {
            pdfDocument.newPage()
            val imported = copy.getImportedPage(pdfReader, i)
            copy.addPage(imported)
        }
//        pdfDocument.close()
        pdfReader.close()
        copy.close()
        outStream.close()
    }

}