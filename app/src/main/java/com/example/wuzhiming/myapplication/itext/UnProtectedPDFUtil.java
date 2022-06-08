package com.example.wuzhiming.myapplication.itext;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.exceptions.InvalidPdfException;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: wuzm
 * @CreateDate: 2022/6/8 11:11 上午
 * @Description: itext5 破解owner密码，简直鸡肋
 * https://www.jianshu.com/p/b71d56df7b99
 */
public class UnProtectedPDFUtil {
    private static MyPdfReader pdfReader = null;

    public static byte[] unProtected(byte[] fileBytes) throws Exception {
        ByteArrayOutputStream bos = null;
        ByteArrayInputStream bis = null;

        try {
            bos = new ByteArrayOutputStream();
            bis = new ByteArrayInputStream(fileBytes);

            initPdfReader(bis);

            if (checkProtected()) {
                unProtectedPdf(bos);
                byte[] newBytes = bos.toByteArray();
                bos.flush();
                return newBytes;
            } else {
                return fileBytes;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != bos) {
                bos.close();
            }
            if (null != bis) {
                bis.close();
            }
        }
    }

    /**
     * Will check user password or owner password. If user password, will throws
     * BadPasswordException.
     *
     * @param is
     * @throws BadPasswordException
     */
    private static void initPdfReader(final InputStream is) throws BadPasswordException {
        try {
            pdfReader = new MyPdfReader(is);
        } catch (BadPasswordException e) {
            // if catch BadPasswordException. it should be user password.
            throw new BadPasswordException("Bad password. It should be user password.");
        } catch (IOException e) {
            return;
        }
    }

    private static boolean checkProtected() throws InvalidPdfException {
        if (null == pdfReader) {
            throw new InvalidPdfException("Invalid pdf.");
        }

        // check is owner password (protected).
        if (pdfReader.isEncrypted()) {
            return true;
        } else {
            return false;
        }
    }

    private static void unProtectedPdf(OutputStream os) {
        PdfStamper stamper = null;
        try {
            MyPdfReader.unethicalreading = true;
            pdfReader.decryptOnPurpose();

            stamper = new PdfStamper(pdfReader, os);
        } catch (DocumentException e) {
            return;
        } catch (IOException e) {
            return;
        } finally {
            try {
                if (null != stamper) {
                    stamper.close();
                }
            } catch (Exception e) {
                return;
            }
        }
    }
}
