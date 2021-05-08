package com.example.wuzhiming.myapplication.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * Author:      明桑
 * Email:       wu2007369@126.com
 * Date:        2021/2/23
 * Describe:
 */
public class ScreenUtils {
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 将dp转换为px
     *
     * @param resources 资源管理器
     * @param dpVal     dp值
     * @return 返回px值
     */
    public static float getPxFromDp(@NonNull Resources resources, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.getDisplayMetrics());
    }

    /**
     * 将sp转换成px
     *
     * @param resources 资源管理器
     * @param spVal     sp值
     * @return 返回px值
     */
    public static float getPxFromSp(@NonNull Resources resources, float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, resources.getDisplayMetrics());
    }


    public static void setViewRatio(View v, int width, int height) {
        v.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = v.getLayoutParams();
                params.height=params.width*height/width;
                v.setLayoutParams(params);
            }
        });
    }

    public static void setViewRatioWithScreenWidth(View v, int screenWidth, int width, int height) {

                ViewGroup.LayoutParams params = v.getLayoutParams();
                params.height=screenWidth*height/width;
                v.setLayoutParams(params);
    }
}
