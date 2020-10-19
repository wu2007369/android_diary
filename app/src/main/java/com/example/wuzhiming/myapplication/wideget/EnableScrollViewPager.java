package com.example.wuzhiming.myapplication.wideget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class EnableScrollViewPager extends ViewPager
{
    private boolean isCanScroll = false;
	
	public EnableScrollViewPager(Context context)
	{  
        super(context);  
		// TODO Auto-generated constructor stub
    }
	
	public EnableScrollViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setScanScroll(boolean isCanScroll)
	{  
        this.isCanScroll = isCanScroll;  
    }

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isCanScroll && super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return isCanScroll && super.onTouchEvent(ev);

	}
	 
//    @Override  
//    public void scrollTo(int x, int y)
//    {  
//        if (isCanScroll)
//        {  
//            super.scrollTo(x, y);  
//        }  
//    }
}
