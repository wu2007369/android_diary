package com.example.wuzhiming.myapplication.coordinatorlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuzhiming.myapplication.R;

public class TitleGradientAnimateActivity extends AppCompatActivity {
    private static final String TAG="TitleGradient";

    private TextView title;

    private MyScrollView.ScrollListener mScrollListener=new MyScrollView.ScrollListener() {
        @Override
        public void listenScrollChange(int l, int t, int oldl, int oldt) {
            Log.i(TAG,"Current horizontal="+l);
            Log.i(TAG,"Current vertical="+t);
            Log.i(TAG,"Previous horizontal="+oldl);
            Log.i(TAG,"Previous vertical="+oldt);
            Log.i(TAG,"******************************************************************************************");
            ArgbEvaluator argbEvaluator=new ArgbEvaluator();
            if (t<0){
                return;
            }
            float fraction= t/(float)heightValue;
            if (fraction>1){
                return;
            }
            int colorValue=(int)argbEvaluator.evaluate(fraction,getResources().getColor(R.color.white),getResources().getColor(R.color.black));
            title.setTextColor(colorValue);
        }
    };
    private MyScrollView myscroll;
    private LinearLayout heightView;
    private int heightValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_gradient_animate);
        title=(TextView)findViewById(R.id.title);
        myscroll=(MyScrollView)findViewById(R.id.myscroll);
        myscroll.setScrollListener(mScrollListener);

        heightView=(LinearLayout)findViewById(R.id.get_height_view);

        heightView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                heightView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                heightValue=heightView.getHeight();

            }
        });
    }

}