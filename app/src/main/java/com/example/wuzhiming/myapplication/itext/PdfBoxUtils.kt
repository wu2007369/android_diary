package com.example.wuzhiming.myapplication.itext

//import com.tom_roush.pdfbox.pdmodel.PDDocument
//import org.apache.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDDocument
import java.io.File
import java.io.IOException

/**
 * @Author:         wuzm
 * @CreateDate:     2022/6/9 10:06 上午
 * @Description:    类作用描述
 */
object PdfBoxUtils {
    /**
     * 解除保护
     * @param filepath 文件路径
     * @param filepath password 密码
     */
    fun unPdfEncrypt(filepath: String?, password: String?): Boolean {
        val file = File(filepath)
        return try {
            val document: PDDocument = PDDocument.load(file, password)
            document.isAllSecurityToBeRemoved = true
            document.save(filepath)
            document.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun isEncrypted(filepath: String):Boolean{
        var isEncrypted=false
         try {
            val document: PDDocument = PDDocument.load(File(filepath))
            isEncrypted=document.isEncrypted
            document.close()
        } catch (e: IOException) {
            e.printStackTrace()
             isEncrypted=true
        }
        return isEncrypted
    }
}