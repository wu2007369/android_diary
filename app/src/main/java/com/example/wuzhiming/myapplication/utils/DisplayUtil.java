package com.example.wuzhiming.myapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author: mango
 * Time: 2019/8/25 17:37
 * Version:
 * Desc:
 */
public class DisplayUtil {

    /**
     * 将其它格式值转换成px
     * @param unit 待转换的值的单位
     * @param value 待转换的值
     * @return
     */
    public static float convert2Px(int unit, float value) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * Resources.getSystem().getDisplayMetrics().density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * Resources.getSystem().getDisplayMetrics().scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * Resources.getSystem().getDisplayMetrics().xdpi * (1.0f/72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * Resources.getSystem().getDisplayMetrics().xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * Resources.getSystem().getDisplayMetrics().xdpi * (1.0f/25.4f);
        }
        return 0;
    }

    public static float dip2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public static float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    @Deprecated
    public static float dip2px(Context context, float dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return (dp*density+0.5f);
    }


    @Deprecated
    public static float sp2px(Context context,float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * fontScale + 0.5f;
    }


    public static float px2dip(Context context,float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return px / scale + 0.5f;
    }

    public static float px2sp(Context context,float px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return px / fontScale + 0.5f;
    }

    public static float getSmallDp(Activity context){
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics );
        int heightP = metrics.heightPixels;
        int widthP = metrics.widthPixels;
        float density = metrics.density;
        float smallestWidthDP;
        if(heightP < widthP ) {
            smallestWidthDP = heightP / density;
        }else {
            smallestWidthDP = widthP / density;
        }
        return smallestWidthDP;
    }

    //获取屏幕密度density
    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    //获取每英寸对应多少个点（不是像素点）
    public static int getDensityDpi(Context context){
        return context.getResources().getDisplayMetrics().densityDpi;
    }


    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 系统减去诸如状态栏，虚拟导航栏之类的装饰元素所占的高度
     * 在沉浸式设置下
     * 全面屏：
     *      有导航栏，就是屏幕高度-状态栏-导航栏
     *      没有导航栏，就是屏幕高度-状态栏
     * 刘海屏:
     *      有导航栏，就是屏幕高度-状态栏-导航栏
     *      没有导航栏，就是屏幕高度-状态栏
     * 这个还跟厂商有关，比如小米的一些手机即使没有显示导航栏，该方法返回值还是等于屏幕高度-状态栏-导航栏
     * 传统手机：
     *      获取的高度与屏幕高度一致
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取的是屏幕完整高度
     * @param context
     * @return
     */
    public static int getScreenRealHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 获得状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 根据屏幕真实高度与显示高度，判断虚拟导航栏是否显示
     * 但是有些小米手机即使使用全面屏手势 这个还是true
     * 所以还是通过是否开启全面屏手势来判断虚拟导航栏有没有显示
     * @param context
     * @return
     */
    public static boolean hasVirtualNavigationBar(Context context) {
        boolean hasSoftwareKeys ;
        Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        hasSoftwareKeys = (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        return hasSoftwareKeys;
    }

    /**
     * 获取导航栏高度，有些没有显示导航栏的手机也能获取到，建议先判断是否有虚拟按键
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    /**
     * 小米手机判断是否开启全面屏手势
     * @param context
     * @return
     */
    public static boolean isOpenGestuerXm(Context context){
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }

    /**
     * 华为手机是否开启全面屏手势
     * @param context
     * @return
     */
    public static boolean isOpenGestuerHw(Context context){
        return Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0) != 0;
    }

    /**
     * Vivo手机是否开启全面屏手势
     * @param context
     * @return
     */
    public static boolean isOpenGestuerVv(Context context){
        return Settings.Secure.getInt(context.getContentResolver(), "navigation_gesture_on", 0) != 0;
    }

    /**
     * 设置全屏 隐藏状态栏，但没有隐藏导航栏，不过布局内容会延申到导航栏
     * @param activity
     * @param enable
     */
    public static void fullscreen(Activity activity, boolean enable) {

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (enable) { //隐藏状态栏

            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;

            activity.getWindow().setAttributes(lp);

            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        } else { //显示状态栏

            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);

            activity.getWindow().setAttributes(lp);

            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }

    }

    /**
     * View.SYSTEM_UI_FLAG_FULLSCREEN 隐藏状态栏，同时应用布局可以延申到状态栏区域，从顶部下拉可以显示状态栏，会自动消失
     * View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 使用应用布局可以延申到状态栏区域，跟上面的区别就是没有隐藏状态栏
     *
     * View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 隐藏导航栏，同时应用布局可以延申到导航栏区域，点击屏幕任意区域，导航栏将重新出现，并且不会自动消失。
     * View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NA
     * VIGATION 使用应用布局可以延申到导航栏区域，跟上面的区别就是没有隐藏导航栏
     *
     * View.SYSTEM_UI_FLAG_LAYOUT_STABLE 稳定布局，主要是在全屏和非全屏切换时，布局不要移动，稳定在变化前的位置。
     * 一般和View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN、View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION搭配使用
     *
     * View.SYSTEM_UI_FLAG_IMMERSIVE 当设置View.SYSTEM_UI_FLAG_FULLSCREEN或者View.SYSTEM_UI_FLAG_HIDE_NAVIGATION后，
     * 点击屏幕任意位置，状态栏或者导航栏会重新显示
     * 加上这个标识后点击屏幕也不会让状态栏或者导航栏显示出来
     *
     * View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY 它的效果跟View.SYSTEM_UI_FLAG_IMMERSIVE一样。
     * 但是，它在全屏模式下，用户上下拉状态栏或者导航栏时，这些系统栏只是以半透明的状态显示出来，并且在一定时间后会自动消失。
     */

    /**
     * 完全隐藏状态栏 导航栏，点击屏幕也不会显示他们
     * @param activity
     * @param enable
     */
    public static void fullScreenTotal(Activity activity, boolean enable){
        if (enable){ //全屏
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else { //非全屏
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
    }

    public static void fullScreen(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(uiOptions);
    }

    /**
     * 判断是否全屏
     *
     * @param activity
     * @return
     */
    public static boolean isFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }
}
