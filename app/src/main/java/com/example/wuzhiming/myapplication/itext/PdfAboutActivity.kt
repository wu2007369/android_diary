package com.example.wuzhiming.myapplication.itext

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wuzhiming.myapplication.R
/*import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfWriter*/

class PdfAboutActivity : AppCompatActivity() {

    companion object{
        const val TAG="PdfAboutActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfabout)

        findViewById<Button>(R.id.btn).setOnClickListener {
            var  result=0
            Thread{
                result=ItextUtils.splitPdf("/storage/emulated/0/缓存/南京功夫豆企业介绍.pdf",50
                ) { index, length -> Log.e(TAG, "currtrent progress $index/$length") }

                if (result>0){
                    runOnUiThread{Toast.makeText(this,"success",Toast.LENGTH_LONG).show()}
                    Log.e(TAG, "success result = $result")
                }else{
                    runOnUiThread{Toast.makeText(this,"fail",Toast.LENGTH_LONG).show()}
                    Log.e(TAG, "fail result = $result")
                }
            }.start()

        }
    }
}