package com.example.wuzhiming.myapplication.itext;


import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.ReaderProperties;
import com.itextpdf.kernel.pdf.WriterProperties;

import java.nio.charset.StandardCharsets;

/**
 * @Author: wuzm
 * @CreateDate: 2022/6/8 11:22 上午
 * @Description: 类作用描述
 */
public class Itext7UnEncriptUtil {
    private static final String source = "./source/1.pdf";

    private static final String dest = "./security/1.pdf";
    private static final String dest2 = "./security/2.pdf";
    private static final String dest3 = "./security/3.pdf";

    public static void fun() throws Exception{  //设置文件拥有者、用户密码，
        //读取文件时需使用文件拥有者、或者用户密码
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(source),
                new PdfWriter(dest, new WriterProperties()
                        .setStandardEncryption(
                                "123456".getBytes(StandardCharsets.UTF_8),
                                "1qaz2w".getBytes(StandardCharsets.UTF_8),
                                EncryptionConstants.ALLOW_PRINTING,
                                EncryptionConstants.ENCRYPTION_AES_128
                        )
                ));

        pdfDocument.close();
    }

    public static void fun2() throws Exception{  //只设置文件拥有者密码，不设置用户密码，
        //读取文件时不需要使用密码
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(source),
                new PdfWriter(dest2, new WriterProperties()
                        .setStandardEncryption(
                                null,
                                "1qaz2w".getBytes(StandardCharsets.UTF_8),
                                EncryptionConstants.ALLOW_PRINTING,
                                EncryptionConstants.ENCRYPTION_AES_128
                        )
                ));

        pdfDocument.close();
    }

    public static void fun3() throws Exception{  //通过文件拥有密码获取用户密码
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(dest,
                new ReaderProperties().setPassword("1qaz2w".getBytes(StandardCharsets.UTF_8))));

        byte[] passwordBytes = pdfDocument.getReader().computeUserPassword();
        System.out.println(passwordBytes!=null?new String(passwordBytes):"null");
    }

    public static void fun3(String dest,String dest2,String password) throws Exception{  //复制 加密文件到新文件
        PdfReader pdfReader = new PdfReader(dest,
                new ReaderProperties().setPassword(password.getBytes(StandardCharsets.UTF_8)));
        PdfDocument pdfDocumentRead = new PdfDocument(pdfReader);
        PdfDocument pdfDocumentWriter=new PdfDocument(new PdfWriter(dest2));
        int numberOfPages = pdfDocumentRead.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            PdfPage page = pdfDocumentRead.getPage(i);
            //复制每页内容添加到新的文件中
            pdfDocumentWriter.addPage(page.copyTo(pdfDocumentWriter));
        }

        byte[] passwordBytes = pdfDocumentRead.getReader().computeUserPassword();
        System.out.println(passwordBytes!=null?new String(passwordBytes):"null");
        pdfDocumentRead.close();
        pdfDocumentWriter.close();
        pdfReader.close();
    }

    public static void fun4() throws Exception{  //不设置用户密码，可直接读取文件
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(dest2)
                .setUnethicalReading(true), new PdfWriter(dest3));

        pdfDocument.close();
    }

    public static void main(String[] args) throws Exception{
//        fun();
//        fun2();
//
//        fun3();
//        fun4();
    }
}
