package com.example.wuzhiming.myapplication.itext;

import com.itextpdf.text.pdf.PdfReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: wuzm
 * @CreateDate: 2022/6/8 11:12 上午
 * @Description: itext5 破解owner密码，简直鸡肋
 */
public class MyPdfReader extends PdfReader {
    public MyPdfReader(final String filename) throws IOException {
        super(filename);
    }

    public MyPdfReader(final byte[] bytes) throws IOException {
        super(bytes);
    }

    public MyPdfReader(final InputStream is) throws IOException {
        super(is);
    }

    public void decryptOnPurpose() {
        encrypted = false;
    }
}
