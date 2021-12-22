package com.example.wuzhiming.myapplication.itext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Author: wuzm
 * @CreateDate: 2021/12/20 5:41 下午
 * @Description: 类作用描述
 */
public class ItextUtilJava {
    /*    *//**
     * 在指定目录等分pdf
     * @param fileName  要分割的文档
     * @param pageNum   分割尺寸
     * @param desDir    分割后存储路径
     * @throws IOException
     *//*
    private void pdfSplitter(String fileName,Integer pageNum,String desDir) throws IOException {
        PdfReader pdfReader = new PdfReader(fileName);
        PdfDocument pdf = new PdfDocument(pdfReader);
        String name;
        PdfWriter pdfWriter=null;
        PdfDocument pdfWriterDoc = null;
        for (int i = 1; i <= pdf.getNumberOfPages() ; i +=  pageNum) {
            name = desDir+"/"+i+".pdf";
            pdfWriter = new PdfWriter(name);
            pdfWriterDoc = new PdfDocument(pdfWriter);
            int start = i;
            int end = Math.min((start + pageNum-1), pdf.getNumberOfPages());
            //从页数第一页开始，
            pdf.copyPagesTo(start,end,pdfWriterDoc);
            pdfWriterDoc.close();
            pdfWriter.close();
        }
        //关闭
        pdf.close();
        pdfReader.close();

    }

    *//**
     * 分割文档，分割后文仔默认存储在原来的文档路径下。
     * @param fileName
     * @param pageNum
     * @throws IOException
     *//*
    private void pdfSplitter(String  fileName,Integer pageNum) throws IOException {
        String desDir = new File(fileName).getParentFile().getAbsolutePath();
        pdfSplitter(fileName,pageNum,desDir);
    }

    *//**
     * 返回自定义片段大小的文件，UUID名称命名。
     * @param fileName
     * @param startPage
     * @param endPage
     * @throws IOException
     *//*
    public void pdfSplitter(String fileName,int startPage, int endPage) throws IOException {
        //源文档
        PdfReader pdfReader = new PdfReader(fileName);
        PdfDocument pdf = new PdfDocument(pdfReader);
        //目标文档名
        String desDir = new File(fileName).getParentFile().getAbsolutePath()
                +"/"+ UUID.randomUUID().toString()+".pdf";
        //生成目标文档
        PdfWriter pdfWriter = new PdfWriter(desDir);
        PdfDocument outPdfDocument = new PdfDocument(pdfWriter);
        //从页数第一页开始，
        pdf.copyPagesTo(startPage,endPage,outPdfDocument);
        //关闭
        outPdfDocument.close();
        pdfWriter.close();
        pdf.close();
        pdfReader.close();
    }*/


    /**
     * 读取文件 添加水印
     *
     * @param outName  生成的新的文件     例如：src/main/resources/static/test1.pdf
     * @param readName 读取的文件    例如：src/main/resources/static/test.pdf
     * @throws Exception
     */
    public static void readPdf(String outName, String readName) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(new File(outName));
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument outDocument = new PdfDocument(pdfWriter);
        PdfReader pdfReader = new PdfReader(readName);
        PdfDocument redDocument = new PdfDocument(pdfReader);
        // 添加事件，该事件拥有添加水印
        WaterMark waterMark = new WaterMark();
        outDocument.addEventHandler(PdfDocumentEvent.INSERT_PAGE, waterMark);
        //获取总页数
        int numberOfPages = redDocument.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            PdfPage page = redDocument.getPage(i);
            //复制每页内容添加到新的文件中
            outDocument.addPage(page.copyTo(outDocument));
        }
        outDocument.close();
        redDocument.close();
        pdfReader.close();
    }

    /**
     * 监听事件 添加水印
     *
     * @author Administrator
     */
    protected static class WaterMark implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent documentEvent = (PdfDocumentEvent) event;
            PdfDocument document = documentEvent.getDocument();
            PdfPage page = documentEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfFont pdfFont = null;
            try {
                // 将字体包拖到路径下
                pdfFont = PdfFontFactory.createFont("src/main/resources/static/simkai.ttf", PdfEncodings.IDENTITY_H,
                        false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), document);
//            new Canvas(canvas, document, page.getPageSize())
//                    .setFontColor(Color.LIGHT_GRAY)   //颜色
//                    .setFontSize(100)				//字体大小
//                    .setFont(pdfFont)               //字体的格式   即导入的字体包
//                    //水印的内容（中英文都支持）    坐标（例如：298f, 421f）  当前页数     最后的值为倾斜度（170）
//                    .showTextAligned(new Paragraph("你好中文a"), 298f, 421f, document.getPageNumber(page),
//                            TextAlignment.CENTER, VerticalAlignment.MIDDLE, 170);
        }

    }


    public static final void addWatermark(String srcPdfPath, String destPdfPath, String watermarkText)
            throws FileNotFoundException, IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(srcPdfPath), new PdfWriter(destPdfPath));

        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new IEventHandler() {
            @Override
            public void handleEvent(Event event) {
                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                PdfDocument pdfDoc = docEvent.getDocument();
                PdfPage page = docEvent.getPage();
                PdfFont font = null;
                try {
                    font=PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
//                    font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD); // 要显示中文水印的话，需要设置中文字体，这里可以动态判断
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PdfCanvas canvas = new PdfCanvas(page);
                PdfExtGState gs1 = new PdfExtGState();
                gs1.setFillOpacity(0.5f); // 水印透明度
                canvas.setExtGState(gs1);
                new Canvas(canvas, pdfDoc, page.getPageSize()).setFontColor(ColorConstants.LIGHT_GRAY).setFontSize(60)
                        .setFont(font).showTextAligned(new Paragraph(watermarkText), 298, 421,
                        pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
            }
        });
        pdfDoc.close();
    }

    public void addWaterMark(String pdfIS,String pdfOS,String text) throws IOException{
        File sourceFile = new File(pdfIS);
        File pdfFile = new File(pdfOS);
        addTestMarkForPDF(new FileInputStream(sourceFile), new FileOutputStream(pdfFile), text);
    }

    /**
     * 82     * 给pdf的每一页添加注释文字
     * 83     * @param pdfIS 需要增加文字前的pdf输入流
     * 84     * @param pdfOS 增加文字后的pdf输出流
     * 85     * @param text 需要增加的说明文字
     * 86     * @throws WriterException
     * 87     * @throws IOException
     * 88
     */
    public void addTestMarkForPDF(InputStream pdfIS, OutputStream pdfOS, String text) throws IOException {


        int textLeft = 0;
        int textWidth = 0;
        int textBottom = 0;
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfIS), new PdfWriter(pdfOS));
        Document doc = null;
        try {
            doc = new Document(pdfDoc);
            int n = pdfDoc.getNumberOfPages();
            Rectangle pagesize = pdfDoc.getPage(n).getPageSize();

            for (int i = 0; i < n; i++) {

                textLeft = (int) (pagesize.getWidth() / 5) * 4;
                textWidth = 50;
                textBottom = (int) (pagesize.getHeight() / 10);
                PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);

                Paragraph para = new Paragraph().setFont(font).setFontSize(50);
                DeviceRgb rgbColor = new DeviceRgb(192, 192, 192);
                //          para.setFontColor(DeviceRgb.makeLighter(rgbColor));    // 定制字体颜色(偏亮)
                para.setFontColor(DeviceRgb.makeDarker(rgbColor)); // 定制字体颜色(偏暗)
                para.setRotationAngle(120f);   // 设置文字倾斜度
                para.setOpacity(0.5f); // 设置文字透明化度
                para.setFixedPosition(i + 1, textLeft, textBottom, textWidth);   // 设置文字的绝对位置
                para.setTextAlignment(TextAlignment.JUSTIFIED);    // 设置文字排序方式
                para.add(text);

                doc.add(para);
            }

        } finally {
            if (doc != null) {
                doc.close();
            }
        }

    }


    public static void createTempPdf(String filePath,String image){
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(new File(filePath)));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            Paragraph title = new Paragraph("标题")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(16);
            title.setFont(font);
            document.add(title);
            document.add(new Paragraph("模板内容").setFont(font));
            Image codeQrImage = new Image(ImageDataFactory.create("F:/"+image),450,600);
            //图片大小
            codeQrImage.scaleAbsolute(70, 30);
            document.add(codeQrImage);
            document.close();
            writer.close();
            pdf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void coverpdf2img(String src,String dest) throws IOException {
        PdfDocument origPdf = new PdfDocument(new PdfReader(src));
        PdfPage origPage = origPdf.getPage(1);
//        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
//        PdfFormXObject pageCopy = origPage.copyAsFormXObject(pdf);
//        Image image = new Image(pageCopy);

        PdfFormXObject pageCopy = origPage.copyAsFormXObject(origPdf);
        Image image = new Image(pageCopy);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image.getXObject().getPdfObject().getBytes(), 0, image.getXObject().getPdfObject().getBytes().length);
        bitmap.getByteCount();
    }


/*    public static Image makeBlackAndWhitePng(PdfImageXObject image) throws IOException {
        BufferedImage bi = image.getBufferedImage();
        BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        newBi.getGraphics().drawImage(bi, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBi, "png", baos);
        return new Image(ImageDataFactory.create(baos.toByteArray()));
    }

    public static void replaceStream(PdfStream orig, PdfStream stream) throws IOException {
        orig.clear();
        orig.setData(stream.getBytes());
        for (PdfName name : stream.keySet()) {
            orig.put(name, stream.get(name));
        }
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(dest));
        PdfDictionary page = pdfDoc.getFirstPage().getPdfObject();
        PdfDictionary resources = page.getAsDictionary(PdfName.Resources);
        PdfDictionary xobjects = resources.getAsDictionary(PdfName.XObject);
        PdfName imgRef = xobjects.keySet().iterator().next();
        PdfStream stream = xobjects.getAsStream(imgRef);
        Image img = makeBlackAndWhitePng(new PdfImageXObject(stream));
        replaceStream(stream, img.getXObject().getPdfObject());
        pdfDoc.close();
    }*/

}
