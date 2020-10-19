package com.example.wuzhiming.myapplication.wideget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import androidx.core.view.MotionEventCompat;

/**
 * Auther:      明桑
 * Date:        2020/10/19
 * Describe:    自定义，用于处理viewpager嵌套webview，且webview内部有轮播图时的情况
 */
public class MyWebView extends WebView {
    private boolean isScrollX = false;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEventCompat.getPointerCount(event) == 1) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isScrollX = false;
                    //事件由webview处理
                    mCallBack.parentRequestDisallowIntercept(true);
//                    getParent().getParent()
//                            .requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //嵌套Viewpager时
                    mCallBack.parentRequestDisallowIntercept(!isScrollX);
//                    getParent().getParent()
//                            .requestDisallowInterceptTouchEvent(!isScrollX);
                    break;
                default:
                    mCallBack.parentRequestDisallowIntercept(false);
/*                    getParent().getParent()
                            .requestDisallowInterceptTouchEvent(false);*/
            }
        } else {
            //使webview可以双指缩放（前提是webview必须开启缩放功能，并且加载的网页也支持缩放）
            mCallBack.parentRequestDisallowIntercept(true);
//            getParent().getParent().
//                    requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
    }

    //当webview滚动到边界时执行
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        isScrollX = clampedX;
    }



    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    CallBack mCallBack;
    public interface CallBack{
        //父组件不允许拦截，即: 允许子组件 消费
        void parentRequestDisallowIntercept(boolean childConsume);
    }
}
