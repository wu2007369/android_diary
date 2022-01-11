package com.example.wuzhiming.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;


import com.example.wuzhiming.myapplication.encrypt.BASE64Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description Bitmap辅助类
 * @author cxy
 * @Date 2018/11/14 15:22
 * Matrix 的 postXXX方法是在上一次修改的基础上进行再次修改  setXXX方法每次操作都是最新的 会覆盖上次的操作
 *
 *  1.Bitmap 内存分配
 *      2.3之前的像素存储需要的内存是在native上分配的，并且生命周期不太可控，可能需要用户自己回收。
 *      2.3-7.1之间，Bitmap的像素存储在Dalvik的Java堆上，当然，4.4之前的甚至能在匿名共享内存上分配（Fresco采用），
 *      8.0之后的像素内存又重新回到native上去分配，不需要用户主动回收，8.0之后图像资源的管理更加优秀，极大降低了OOM
 *
 *  2.内存分配实现原理
 *      8.0之前：
 *          * java层创建Bitmap最终会走向native层：Bitmap.cpp的Bitmap_creator函数
 *          * 然后调用GraphicsJNI::allocateJavaPixelRef函数，在native层创建Java层byte[]，并将这个byte[]作为像素存储结构
 *          * 最后在GraphicsJNI::createBitmap函数中，在native层构建Java Bitmap对象，将生成的byte[]传递给Bitmap.java对象
 *      8.0之后：
 *          * 流程跟上面差不多，只不过在分配内存里有点区别
 *          * 调用Bitmap::allocateHeapBitmap函数，在native层创建bitmap，并分配native内存
 *          * 该函数里调用calloc分配像素内存，最后创建Bitmap
 *
 *  3.Bitmap内存回收
 *      NativeAllocationRegistry是Android 8.0引入的一种辅助自动回收native内存的一种机制，
 *      当Java对象因为GC被回收后，NativeAllocationRegistry可以辅助回收Java对象所申请的native内存
 *
 *  4.Bitmap压缩
 *      * 采样率压缩：也叫尺寸压缩，通过改变图片的宽高来减少图片的分辨率，最终达到内存压缩的效果；
 *          这种方法是减小图片的宽高以减少图片像素数达到降低内存占用，一般用于缩略图展示
 *      * 质量压缩：该压缩法不会改变图像所拥有的像素点，即图片的最终尺寸不会改变，而是在保持像素前提下改变图片的位深及透明度等来压缩图片，以降低其保存到磁盘上的大小
 *          但有些格式，例如png,它是无损的，这个值设置了也是无效的；该方法适合去传递二进制的图片数据，比如微信分享图片，要传入二进制数据过去，限制32kb之内
 *          要注意这种方法降低的是保存到磁盘的文件大小，这个文件再加载到内存中作为Bitmap，其开辟的内存还是跟压缩前一样，因为图片的宽高及像素都没改变；
 *          比如三个像素点的透明度都是100，那么进行质量压缩时，用一个100去保存，这样最终保存的文件大小肯定会减小；但是加载到内存时，又会将这一个100分别用到三个像素点上，
 *          最终这个加载出来的Bitmap所开辟的内存并不会变小
 *      * 哈夫曼压缩：是广泛使用的开源JPEG图像库，Android所用的是skia的压缩算法，而Skia对libjpeg进行了的封装，https://www.j4ml.com/t/8179
 *
 *   5.Bitmap内存复用
 *      Bitmap内存复用是通过BitmapFactory.Options的inBitmap属性实现的，原理就是多个Bitmap共用同一块内存，其复用规则如下
 *      * Bitmap一定要是可变的，即inmutable设置一定为ture
 *      * Android4.4（API19）之前，要求正在解码的图像必须是JPG或者PNG格式，无论是从resource还是stream获取；
 *          并且正在解码的Bitmap和待重用的Bitmap必须是相同大小的，同时inSampleSize值要求设置为1；
 *          另外待重用的Bitmap的inPreferredConfig属性将会覆盖正在解码的Bitmap的这个属性
 *      * Android4.4（API19）开始，只需要正在解码的Bitmap的size小于或等于待重用的Bitmap即可
 */
public class BitmapUtil {

    private static String TAG= BitmapUtil.class.getSimpleName();

    /**================================================== 华丽丽的分割线 - 采样率压缩 ======================================================**/

    /**
     * 根据指定压缩比解码文件图片
     * @param filePath 图片路径
     * @param inSampleSize 压缩比
     * @return
     */
    public static Bitmap decodeFileBitmap(String filePath, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据指定宽高计算压缩比解码文件图片
     * @param filePath 图片路径
     * @param targetWidth 指定宽
     * @param targetHeight 指定高
     * @return
     */
    public static Bitmap decodeFileBitmap(String filePath, int targetWidth, int targetHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int inSampleSize = calSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据指定压缩比解码资源图片
     * @param res
     * @param resId
     * @param inSampleSize
     * @return
     */
    public static Bitmap decodeResourceBitmap(Resources res, int resId, int inSampleSize) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 根据指定宽高计算压缩比解码资源图片
     * @param res
     * @param resId
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap decodeResourceBitmap(Resources res, int resId,int targetWidth, int targetHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calSampleSize(options, targetWidth, targetHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 根据指定压缩比解码流图片
     * @param is
     * @param inSampleSize
     * @return
     */
    public static Bitmap decodeStreamBitmap(InputStream is, int inSampleSize){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeStream(is,null,options);
    }

    /**
     * 从assert读取图片
     * @param fileName
     * @return
     */
    public static Bitmap decodeAssertBitmap(String fileName,Context context){
        Bitmap       image = null;
        try {
            AssetManager am    = context.getResources().getAssets();
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 根据指定宽高解码流图片
     * 这里要注意通过流加载options后要重置流，否则无法加载bitmap，报错如下
     * skia: --- Failed to create image decoder with message 'unimplemented'
     * @param is
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap decodeStreamBitmap(InputStream is, int targetWidth, int targetHeight) throws IOException {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is,null,options);
        options.inSampleSize = calSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;
        is.reset();
        return BitmapFactory.decodeStream(is,null,options);
    }

    /**
     * 根据指定宽高解码字节数组图片
     * @param data
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap decodeByteBitmap(byte[] data, int targetWidth, int targetHeight){

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data,0,data.length,options);
        options.inSampleSize = calSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data,0,data.length);
    }


    /**
     * 获取压缩比最小值，使用图片的宽高大于等于目标宽高
     * @param options
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static int calSampleSize(BitmapFactory.Options options,
                                    int targetWidth, int targetHeight) {
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;
        int inSampleSize = 1;
        //保证宽高中最小的要大于指定值，避免被拉伸
        if (rawWidth > targetWidth || rawHeight > targetHeight) {
            float ratioHeight = (float) rawHeight / targetHeight;
            float ratioWidth = (float) rawWidth / targetWidth;
            inSampleSize = (int) Math.min(ratioWidth, ratioHeight);
        }
        return inSampleSize;
    }

    /**
     * 获取压缩比最大值，使用图片的宽高小于等于目标宽高
     * @param options
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static int calSampleSizeMax(BitmapFactory.Options options,
                                    int targetWidth, int targetHeight) {
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;
        int inSampleSize = 1;
        //保证宽高中最小的要大于指定值，避免被拉伸
        if (rawWidth > targetWidth || rawHeight > targetHeight) {
            float ratioHeight = (float) rawHeight / targetHeight;
            float ratioWidth = (float) rawWidth / targetWidth;
            inSampleSize = (int) Math.max(ratioWidth, ratioHeight);
        }
        return inSampleSize;
    }

    /**================================================== 华丽丽的分割线 - 质量压缩 ======================================================**/


    /**
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap,int quality){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        byte[] buff = bos.toByteArray();
        return BitmapFactory.decodeByteArray(buff,0,buff.length);
    }

    /**
     * 循环压缩内存，直到内存不大于指定大小
     * @param image
     * @param targetLength 要压缩的目标大小 kb
     * @return
     */
    public static Bitmap compressTargetImage(Bitmap image,long targetLength) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int quality = 90;
        // 循环判断如果压缩后图片是否大于targetLength kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > targetLength) {
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩quality%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            // 每次都减少10
            quality -= 10;
            if (quality < 0) {
                break;
            }
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**================================================== 华丽丽的分割线 - 变换处理 ======================================================**/

    /**
     * 修改像素颜色格式压缩图片
     * @param res
     * @param resId
     * @param inPreferredConfig
     * @return
     */
    public static Bitmap decodeResourceBitmap(Resources res, int resId,Bitmap.Config inPreferredConfig){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = inPreferredConfig;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    /**
     * 宽高同比缩放,填充目标尺寸
     * 当原图宽高都大于或者都小于目标宽高时，按宽高各自比率中的最大值进行缩放
     * @param source
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap scaleSameBitmap(Bitmap source, float targetWidth, float targetHeight,boolean scaleMax){
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        if (sourceWidth == targetWidth && sourceHeight == targetHeight) {
            return source;
        }
        float widthRadio = targetWidth / sourceWidth;
        float heightRadio = targetHeight / sourceHeight;
        if (scaleMax) {
            //按最大比例同比缩放
            return scaleBitmap(source, Math.max(widthRadio,heightRadio));
        } else {
            //按最小比例同比缩放
            return scaleBitmap(source, Math.min(widthRadio,heightRadio));
        }
    }

    /**
     * 宽高按各自比列缩放
     *
     * @param origin    原图
     * @param newWidth  新图的宽
     * @param newHeight 新图的高
     * @return new Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = (newWidth*1f) / width;
        float scaleHeight = (newHeight*1f) / height;
        Matrix matrix = new Matrix();
        // 使用后乘
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap scale = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return scale;
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, true);
        return newBM;
    }

    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    public static Bitmap cropBitmap(Bitmap bitmap) {
        // 得到图片的宽，高
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        // 裁切后所取的正方形区域边长
        int cropWidth = w >= h ? h : w;
        cropWidth /= 2;
        int cropHeight = (int) (cropWidth / 1.2);
        return Bitmap.createBitmap(bitmap, w / 3, 0, cropWidth, cropHeight, null, false);
    }

    public static Bitmap cropBitmap(Bitmap origin,int width,int height){
        int w = origin.getWidth();
        int h = origin.getHeight();
        int startX = (int) ((w-width)*1f/2);
        int startY = (int) ((h-height)*1f/2);
        /**
         * 第二/三个参数：起始x坐标，y坐标
         * 第四/五参数：  要截取的宽度，高度
         */
        origin = Bitmap.createBitmap(origin,startX,startY,width,height,null,false);
        return origin;
    }

    /**
     * 旋转
     * @param origin 原图
     * @param degree  旋转角度，正:顺时针，负:逆时针
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap origin, float degree) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 围绕原地进行旋转
        origin = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return origin;
    }

    /**
     * 旋转
     * @param origin 原图
     * @param degree  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap origin, float degree,int width,int height) {
        if (origin == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 围绕原地进行旋转
        origin = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return origin;
    }

    /**
     * 将图片做镜像处理
     * @param origin
     * @param direction 镜像方向
     *                  0 水平
     *                  1 垂直
     * @return
     */
    public static Bitmap reverseBitmap(Bitmap origin,float direction){
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        if (direction == 0) {
            matrix.postScale(-1,1);
            matrix.postTranslate(width,0);
        } else {
            matrix.postTranslate(0,height);
            matrix.postScale(1,-1);
        }

        origin = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, true);
        return origin;
    }

    /**
     * 偏移效果
     * @param origin 原图
     * @return 偏移后的bitmap
     */
    public static Bitmap skewBitmap(Bitmap origin) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.postSkew(-0.6f, -0.3f);
        origin = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return origin;
    }

    /**
     * 获取我们需要的整理过旋转角度的Uri
     * @param activity  上下文环境
     * @param path      路径
     * @return          正常的Uri
     */
    public static Uri getRotatedUri(Activity activity, String path){
        int degree = ImageHelper.getBitmapDegree(path);
        if (degree != 0){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Bitmap newBitmap = BitmapUtil.rotateBitmap(bitmap,degree);
            return Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(),newBitmap,null,null));
        }else{
            return Uri.fromFile(new File(path));
        }
    }

    /**
     * 将图片按照指定的角度进行旋转
     *
     * @param path   需要旋转的图片的路径
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(String path, int degree) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return rotateBitmap(bitmap,degree);
    }

    /**================================================== 华丽丽的分割线 - bitmap和string的转换 ======================================================**/

    /**
     * 图片转成base64码
     * @param bitmap
     * @return
     */
    public static String bitmap2String(Bitmap bitmap, Bitmap.CompressFormat format){
        String result = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            bitmap.compress(format,100,bos);
            result = BASE64Util.encodeByte(bos.toByteArray());
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * base64码转成Bitmap
     * @param msg
     * @return
     */
    public static Bitmap string2Bitmap(String msg){
        byte[] bytes = BASE64Util.decodeToBytes(msg);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**================================================== 华丽丽的分割线 - bitmap颜色变换 ======================================================**/


    /**
     * 修改bitmap颜色
     * @param bitmap
     * @param hue 色相
     * @param saturation 饱和度
     * @param lum 亮度
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bitmap,float hue,float saturation,float lum,boolean fromSource) {
        Bitmap currentBitmap ;
        if (fromSource) {
            currentBitmap = bitmap;
        } else {
            //由于不能直接在原图上修改，所以创建一个图片，设定宽度高度与原图相同。为32位ARGB图片
            currentBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        }
        //创建一个和原图相同大小的画布
        Canvas canvas = new Canvas(currentBitmap);
        //创建笔刷并设置抗锯齿
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //色相ColorMatrix
        ColorMatrix hueMatrix = null;
        if (hue != -1) {
            hueMatrix = new ColorMatrix();
            hueMatrix.setRotate(0, hue);
            hueMatrix.setRotate(1, hue);
            hueMatrix.setRotate(2, hue);
        }

        //饱和度ColorMatrix
        ColorMatrix saturationMatrix = null;
        if (saturation != -1) {
            saturationMatrix = new ColorMatrix();
            saturationMatrix.setSaturation(saturation);
        }

        //亮度ColorMatrix
        ColorMatrix lumMatrix = null;
        if (lum != -1) {
            lumMatrix = new ColorMatrix();
            lumMatrix.setScale(lum, lum, lum, 1);
        }

        //将三种效果融合起来
        ColorMatrix imageMatrix = new ColorMatrix();
        if (hueMatrix != null) {
            imageMatrix.postConcat(hueMatrix);
        }
        if (saturationMatrix != null) {
            imageMatrix.postConcat(saturationMatrix);
        }
        if (lumMatrix != null) {
            imageMatrix.postConcat(lumMatrix);
        }

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return currentBitmap;
    }

    public static Bitmap setBlackWhite(Bitmap source){
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap binary = source.copy(Bitmap.Config.RGB_565,true);
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                int pixel = source.getPixel(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;
//                        int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                int gray = (int) ((float) red * 0.213 + (float) green * 0.715 + (float) blue * 0.072);
                if(gray <= 100){
                    gray -= 35;
                } else if(gray > 100 && gray <= 150){
                    gray -= 15;
                } else{
                    gray += 55;
                }
                if (gray < 0) {
                    gray = 0;
                }
                if (gray > 255) {
                    gray = 255;
                }

                pixel = 0xff000000 | (gray << 16) | (gray << 8) | gray;
                binary.setPixel(x,y,pixel);
            }
        }
        return binary;
    }

}
