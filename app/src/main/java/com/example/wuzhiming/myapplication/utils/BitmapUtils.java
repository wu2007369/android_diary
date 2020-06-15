package com.example.wuzhiming.myapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.view.View;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * Bitmap解析类
 *
 * @author Richard.Ma
 */
public class BitmapUtils {
    public static final String TAG = "** BitmapUtils";
    public static final int MAX_SIZE = 1500;

    /**
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {

        Bitmap bitmap = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
        }
        return bitmap;

    }

    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }

    /**
     * 图片透明度处理
     *
     * @param bitmap 原始图片
     * @param number 透明度
     * @return
     */
    public static Bitmap setAlpha(Bitmap bitmap, int number) {
        if (null == bitmap)
            throw new RuntimeException("setAlpha:the source of bitmap is null");
        int[] argb = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(argb, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),
                bitmap.getHeight());
        // 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            // 修改最高2位的值
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
        }
        return bitmap;
    }

    /**
     * 图片缩放(非等比例)
     *
     * @param bitmap  :资源图片
     * @param mWidth  ：新图片的宽
     * @param mHeight ：新图片的高
     * @return Bitmap
     */
    public static Bitmap resizeImage(Bitmap bitmap, float mWidth, float mHeight) {
        if (null == bitmap)
            throw new RuntimeException("zoomImage:the source of bm is null");
        // 获取这个图片的宽和高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放率，新尺寸除原始尺寸
        float scaleWidth = (mWidth) / width;
        float scaleHeight = (mHeight) / height;
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建新的图片
        try {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
            return bitmap;
        } catch (OutOfMemoryError e) {
        }
        return null;
    }

    /**
     * 图片缩放(等比例)（2个边长都不高于maxSize）
     *
     * @param bitmap
     * @param maxSize
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int maxSize) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float newWidth = maxSize;
        float newHeight = maxSize;
        if (width >= height) {
            float temp = ((float) height) / ((float) width);
            newWidth = maxSize;
            newHeight = (int) ((maxSize) * temp);
        } else {
            float temp = ((float) width) / ((float) height);
            newWidth = (int) ((maxSize) * temp);
            newHeight = maxSize;
        }
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    /**
     * 等比例缩放（长和宽都不小于指定的长和宽）
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float scale = (float) width / (float) w >= (float) height / (float) h ? (float) width
                / (float) w
                : (float) height / (float) h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap cuteBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
                false);
        // Bitmap cuteBitmap = Bitmap.createBitmap(bitmap, (int)((w * scale -
        // width) / 2), (int)((h * scale - height) /
        // 2), width, height, matrix, true);
        // bitmap.recycle();
        return cuteBitmap;
    }

    /**
     * 图片切割（先压缩大小 然后切割适合尺寸部分）
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap cuteBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            return null;
        }
        Bitmap resizeBitmap = resizeBitmap(bitmap, width, height);
        Bitmap cuteBitmap = Bitmap.createBitmap(resizeBitmap, 0, 0, width,
                height);
        // resizeBitmap.recycle();
        return cuteBitmap;
    }

    /**
     * [ 图像圆角处理]<BR>
     * [功能详细描述]
     *
     * @param bitmap
     * @param roundPX
     * @return
     */
    public static Bitmap getRCB(Bitmap bitmap, float roundPX) // RCB means
    // Rounded Corner
    // Bitmap
    {
        Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(dstbmp);

        final int color = 0xff000000;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return dstbmp;
    }

    public static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 50;
        while (baos.toByteArray().length / 1024 > 200) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩80%，把压缩后的数据存放到baos中
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 800f;//这里设置高度为800f  
        float ww = 480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
//        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
        return bitmap;
    }

    public static void compressImage(Bitmap image, String dstPath) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int preSize = baos.toByteArray().length / 1024;
        int maxSize = 450;  //默认最大500k，取450
        if (preSize < maxSize) {
            saveImage(image, dstPath);
            return;
        }

        float scale = 1.0f;
        if (preSize > maxSize) {
            scale = 500 * 1.0f / preSize;
        }

        if (scale > 1 || scale <= 0) {
            scale = 0.6f;
        }

        int options = (int) (scale * 10) * 10;       //只保留小数点后一位

        FileOutputStream out = new FileOutputStream(new File(dstPath));
        image.compress(Bitmap.CompressFormat.JPEG, options, out);
        if (null != out) {
            out.close();
        }

        if (!image.isRecycled()) {
            image.recycle();
        }
        return;
    }

    public static void compressImage(String srcPath, String dstPath) throws IOException {
        Bitmap srcBitmap = getBitmap(srcPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        byte[] array = baos.toByteArray();
        while (array.length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            array = baos.toByteArray();

        }

        FileOutputStream fos = new FileOutputStream(dstPath);
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();
        srcBitmap.recycle();
    }



    /**
     * @param resource 二维码图片
     * @param b1       添加在中间的图片
     */
    private static Bitmap addImage(Bitmap resource, Bitmap b1, int QR_WIDTH,
                                   int QR_HEIGHT) {
        // 缩放指定的图片
        Bitmap result = Bitmap.createScaledBitmap(b1, QR_WIDTH / 5,
                QR_HEIGHT / 5, true);

        // 防止出现Immutable bitmap passed to Canvas constructor错误
        Bitmap newBitmap = null;

        newBitmap = Bitmap.createBitmap(resource);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();

        int w = result.getWidth();
        int h = result.getHeight();

        int w_2 = resource.getWidth();
        int h_2 = resource.getHeight();

        paint.setColor(Color.GRAY);
        paint.setAlpha(125);
        paint = new Paint();
        canvas.drawBitmap(result, Math.abs(w - w_2) / 2, Math.abs(h - h_2) / 2,
                paint);
        canvas.save();
        // 存储新合成的图片
        canvas.restore();

        return newBitmap;
    }

    //平铺图片
    public static Bitmap createRepeater(int count, Bitmap src) {
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth() * count, src.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        for (int idx = 0; idx < count; ++idx) {
            canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
        }

        return bitmap;
    }


    /**
     * 根据图片路径分析并旋转图片（通过计算sampleSize并压缩图片减少内存占用）
     */
    public static String doRotateImageAndSave(String filePath, String dstPath) {

        int rotate = getBitmapDegree(filePath);

        // 计算得到sampleSize
        int[] bounds = new int[2];
        int sampleSize = caculateSampleSize(filePath, rotate, bounds);
        if (bounds[0] == 0 || bounds[1] == 0) {
            return filePath;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        // 适当调整颜色深度
//        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        options.inJustDecodeBounds = false;

        try {
            Bitmap srcBitmap = BitmapFactory.decodeFile(filePath, options);// 加载原图

            //旋转
            Bitmap rotateBitmap = srcBitmap;
            if (rotate > 0) {
                rotateBitmap = rotateImage(srcBitmap, rotate);
            }

//            // 保存缩放后图片
//            File preFile = new File(Constant.FilePath.CACHE_PATH + DateUtils.getNowDefaultPhotoPattern() + "_resize.jpg");
//            saveImage(rotateBitmap, preFile.getAbsolutePath());

            // 压缩
            compressImage(rotateBitmap, dstPath);
            srcBitmap.recycle();
            rotateBitmap.recycle();
            return dstPath;
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
    }

    /**
     * Get bitmap from specified image path
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    private static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 读取图片的sampleSize
     */
    public static int caculateSampleSize(String imgFilePath, int rotate, int[] out) {
        if (null == out) {
            // 默认size
            return 1;
        }

        int imgWidth = 0;// 原始图片的宽
        int imgHeight = 0;// 原始图片的高
        int sampleSize = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imgFilePath);
            BitmapFactory.decodeStream(inputStream, null, options);// 由于options.inJustDecodeBounds位true，所以这里并没有在内存中解码图片，只是为了得到原始图片的大小
            imgWidth = options.outWidth;
            imgHeight = options.outHeight;
            // 初始化
            out[0] = imgWidth;
            out[1] = imgHeight;
            // 如果旋转的角度是90的奇数倍,则输出的宽和高和原始宽高调换
            if ((rotate / 90) % 2 != 0) {
                out[0] = imgHeight;
                out[1] = imgWidth;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        // 计算输出bitmap的sampleSize
        while (imgWidth / sampleSize > MAX_SIZE || imgHeight / sampleSize > MAX_SIZE) {
            sampleSize = sampleSize << 1;
        }
        return sampleSize;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param img    需要旋转的图片
     * @param rotate 旋转角度
     * @return 旋转后的图片
     */
    private static Bitmap rotateImage(Bitmap img, int rotate) {
        int width = img.getWidth();
        int height = img.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);

        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, false);
        return img;
    }

    private static Bitmap resizedImage(Bitmap img) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        // 1500为宽高最大值
        float scale = (float) Math.max(imgWidth, imgHeight) / (float) MAX_SIZE;

        imgWidth = (int) (img.getWidth() / scale);
        imgHeight = (int) (img.getHeight() / scale);

        Matrix matrix = new Matrix();
        matrix.postScale(imgWidth, imgHeight);

        // 得到新的图片
        img = Bitmap.createBitmap(img, 0, 0, imgWidth, imgHeight, matrix, true);
        return img;
    }

    /**
     * 将图片按照制定路径保存
     */
    public static void saveImage(Bitmap image, File f) {
        try {
            FileOutputStream out = new FileOutputStream(f);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
    }

    /**
     * 将图片按照制定路径保存
     */
    public static void saveImage(Bitmap image, String fPath) {
        try {
            File f = new File(fPath);
            FileOutputStream out = new FileOutputStream(f);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将view转换为bitmap
     * @param v
     * @return
     */
    public static Bitmap getBitmapFromView(View v){
        v.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
