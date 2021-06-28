package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfAboutActivity2 extends AppCompatActivity {

    private static final String TAG = "PdfAboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_about2);

//        findViewById(R.id.btn).setOnClickListener(v->splitPdf("/storage/emulated/0/缓存/典型时评文章50篇.pdf",50));
        findViewById(R.id.btn).setOnClickListener(v->{
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    int num=splitPdf("/storage/emulated/0/缓存/Test/建国以来毛泽东文稿 [第一到第十一册].pdf",500);
//                    int num=splitPdf("/storage/emulated/0/缓存/Test/建国以来毛泽东文稿[第十二册](1966.1-1968.12).pdf",5);
                    int num=splitPdf("/storage/emulated/0/缓存/Test/1-南京功夫豆企业介绍.pdf",3);
//                    int num=splitPdf("/storage/emulated/0/缓存/Test/1-南京功夫豆企业介绍.pdf",50);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PdfAboutActivity2.this,"共有"+num+"本",Toast.LENGTH_LONG).show();
                            Log.e(TAG,"共有"+num+"本");
                        }
                    });
                }
            }).start();

        });
    }

    /**
     * 将filename文件切分成多个自定义页数大小的文件
     * @param filename 文件路径
     * @param splitSize  切分后的小文件的页数
     * @return 将filename文件划分成的子文件数目 >0则是成功
     */
    public static int splitPdf(String filename,int splitSize) {
        // String filename = "1904.08394.pdf";
        PdfReader reader = null;
        try {
            reader = new PdfReader(filename);
        } catch (IOException e) {
            return -1;
        }
        int numberOfPages = reader.getNumberOfPages();
        Rectangle pageSize = reader.getPageSize(1);
        Log.e(TAG,"pagesize="+pageSize.toString());
        int numberOfNewFiles = 0, pageNumber = 1;
        while (pageNumber <= numberOfPages) {
            Document doc = new Document(pageSize);
            String outputFilename = String.format(filename.substring(0, filename.length()-4) + "_%02d" + ".pdf", numberOfNewFiles);
            PdfWriter writer = null;
            try {
                writer = PdfWriter.getInstance(doc, new FileOutputStream(outputFilename));
            } catch (FileNotFoundException e) {
                return -2 - numberOfNewFiles * 10;
            } catch (DocumentException e) {
                return -3 - numberOfNewFiles * 10;
            }
            doc.open();
            PdfContentByte cb = writer.getDirectContent();
//            PdfContentByte cb = writer.getDirectContentUnder();
            // 这里判断加到了循环里不好，有优化空间
            for (int j = 1; pageNumber <= numberOfPages && j <= splitSize; ++j, pageNumber++) {
                doc.newPage();
                // 查看源码得知pageNumber是从1开始计数的
                cb.addTemplate(writer.getImportedPage(reader, pageNumber), 0, 0);
            }
            doc.close();
            numberOfNewFiles++;
            writer.close();
        }
        return numberOfNewFiles;
    }
}