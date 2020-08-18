package com.example.wuzhiming.myapplication.wideget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener!=null){
            mScrollListener.listenScrollChange(l, t, oldl, oldt);
        }
    }


    public void setScrollListener(ScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    ScrollListener mScrollListener;
    public interface ScrollListener{
        void listenScrollChange(int l, int t, int oldl, int oldt);
    }
}
