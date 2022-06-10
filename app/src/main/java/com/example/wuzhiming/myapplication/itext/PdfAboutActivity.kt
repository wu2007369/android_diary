package com.example.wuzhiming.myapplication.itext

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.wuzhiming.myapplication.R
import com.example.wuzhiming.myapplication.itext.Itext7UnEncriptUtil.fun3


class PdfAboutActivity : AppCompatActivity() {

    companion object {
        const val TAG = "PdfAboutActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfabout)

        findViewById<Button>(R.id.btn).setOnClickListener {
            var result = 0
            Thread {
/*                result=ItextUtils.splitPdf("/storage/emulated/0/缓存/南京功夫豆企业介绍.pdf",50
                ) { index, length -> Log.e(TAG, "currtrent progress $index/$length") }

                if (result>0){
                    runOnUiThread{Toast.makeText(this,"success",Toast.LENGTH_LONG).show()}
                    Log.e(TAG, "success result = $result")
                }else{
                    runOnUiThread{Toast.makeText(this,"fail",Toast.LENGTH_LONG).show()}
                    Log.e(TAG, "fail result = $result")
                }*/
            }.start()

        }

        findViewById<Button>(R.id.btn2).setOnClickListener {
            Itext7Utils.pdfSplitter("/storage/emulated/0/缓存/南京功夫豆企业介绍.pdf", 20)
        }
        findViewById<Button>(R.id.btn3).setOnClickListener {
//            Itext7Utils.addWaterMark("/storage/emulated/0/缓存/1.pdf","/storage/emulated/0/缓存/11.pdf","漩涡鸣人")
            Itext7Utils.addWatermark3("/storage/emulated/0/缓存/1.pdf",
                "/storage/emulated/0/缓存/11.pdf", "漩涡鸣人sf123")
        }

        findViewById<Button>(R.id.btn4).setOnClickListener {
            Itext7Utils.createImgPdf("/storage/emulated/0/缓存/12.pdf",
                "/storage/emulated/0/缓存/A.jpg")
        }

        findViewById<Button>(R.id.btn5).setOnClickListener {
            Itext7Utils.pdf2Img("/storage/emulated/0/缓存/12.pdf", "/storage/emulated/0/缓存/")
        }

        findViewById<Button>(R.id.btn6).setOnClickListener {
            Itext7UnEncriptUtil.fun3("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf",
                "/storage/emulated/0/缓存/南京功夫豆企业介绍解密.pdf", "111111");
//            UnProtectedPDFUtil.unProtected(Files.readAllBytes(Paths.get("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf")));
        }
        findViewById<Button>(R.id.btn7).setOnClickListener {
//            Itext5UnEncriptUtil.fun2("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf")
//            Itext5UnEncriptUtil.fun2("/storage/emulated/0/缓存/南京功夫豆企业介绍.pdf")
//            Itext5UnEncriptUtil.fun3("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf","/storage/emulated/0/缓存/南京功夫豆企业介绍解密.pdf","111111")
            ItextUtils.checkPdfEncrypted("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf")
            ItextUtils.checkPdfEncrypted("/storage/emulated/0/缓存/南京功夫豆企业介绍.pdf")
//            ItextUtils.UnEncryptPdf2NewFile("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf","/storage/emulated/0/缓存/南京功夫豆企业介绍解密.pdf","111111")
            ItextUtils.UnEncryptPdf2NewFileV2("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf",
                "/storage/emulated/0/缓存/南京功夫豆企业介绍解密.pdf", "111111")
        }

        findViewById<Button>(R.id.btn8).setOnClickListener {
            var isEncrypted=PdfBoxUtils.isEncrypted("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf")
            Log.i("isEncrypted","isEncrypted:"+isEncrypted)
//            PdfBoxUtils.unPdfEncrypt("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf", "111111")
            ItextUtils.unPdfEncrypt("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf", "111111")

            isEncrypted=PdfBoxUtils.isEncrypted("/storage/emulated/0/缓存/南京功夫豆企业介绍加密111111.pdf")
            Log.i("isEncrypted","isEncrypted:"+isEncrypted)
        }



    }
}