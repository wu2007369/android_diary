package com.example.wuzhiming.myapplication.itext;

/*import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;*/

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ItextUtils {
/*    *//**
     * 将filename pdf文件切分成多个 自定义页数大小的 pdf文件
     *
     * @param filename      pdf文件路径 "/storage/emulated/0/缓存/南京功夫豆企业介绍.pdf"
     * @param splitSize     切分后的每个小文件的页数
     * @param destFilesName 切分后的小文件的路径名 "/storage/emulated/0/缓存/Test/南京功夫豆企业介绍"
     * @return 将filename文件划分成的子文件数目 >0成功 <0异常
     *//*
    public static int splitPdf(String filename, int splitSize, String destFilesName,ItextCallBack itextCallBack) {
        PdfReader reader = null;
        try {
            reader = new PdfReader(filename);
        } catch (IOException e) {
            return -1;
        }
        int numberOfPages = reader.getNumberOfPages();
        Rectangle pageSize = reader.getPageSize(1);
        int numberOfNewFiles = 0, pageNumber = 1;
        while (pageNumber <= numberOfPages) {
            Document doc = new Document(pageSize);
            String outputFilename = String.format(destFilesName + "_%02d" + ".pdf", numberOfNewFiles);
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
            for (int j = 1; pageNumber <= numberOfPages && j <= splitSize; j++, pageNumber++) {
                doc.newPage();
                // 查看源码得知pageNumber是从1开始计数的
                cb.addTemplate(writer.getImportedPage(reader, pageNumber), 0, 0);
                if (itextCallBack!=null){
                    itextCallBack.onProgress(pageNumber,numberOfPages);
                }
            }
            doc.close();
            numberOfNewFiles++;
            writer.close();
        }
        return numberOfNewFiles;
    }

    //默认为输出的小文件与输入文件在同一目录
    public static int splitPdf(String filename, int splitSize,ItextCallBack itextCallBack) {
        return splitPdf(filename, splitSize, filename.substring(0, filename.length() - 4),itextCallBack);
    }*/


    public static Boolean checkPdfEncrypted(String filePath){
        try {
            PdfReader pdfReader = new PdfReader(filePath);
            return pdfReader.isEncrypted();
        }catch (BadPasswordException e){
            return true;
        }catch (IOException ioException){
            return true;
        }
    }

    //解密输出到新pdf文件
    public static void UnEncryptPdf2NewFile(String source,String destFilePath, String password) {
        PdfReader pdfReader=null;
        PdfSmartCopy copy=null;
        FileOutputStream outStream=null;
        try{
            pdfReader = new PdfReader(source, password.getBytes(StandardCharsets.UTF_8));
            Rectangle pageSize = pdfReader.getPageSize(1);
            int numberOfPages = pdfReader.getNumberOfPages();
            Document pdfDocument = new Document(pageSize);
            outStream = new FileOutputStream(destFilePath);
            PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument, new FileOutputStream(destFilePath));
            copy = new PdfSmartCopy(pdfDocument, outStream);
            pdfDocument.open();
            for (int i=1;i<=numberOfPages; i++) {
                pdfDocument.newPage();
                PdfImportedPage imported = copy.getImportedPage(pdfReader, i);
                copy.addPage(imported);
            }
            pdfReader.close();
            copy.close();
            outStream.close();
        }catch (IOException ioException){
        }catch (Exception e){
        }finally {
            if (pdfReader!=null){
                pdfReader.close();
            }
            if (copy!=null){
                copy.close();
            }
        }
//        pdfDocument.close()
    }
}
