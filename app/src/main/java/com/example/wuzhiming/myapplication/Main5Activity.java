package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.height = dm.heightPixels/ 2; // 屏幕高度的一半
        getWindow().setAttributes(lp); // 设置参数给window

        getWindow().setGravity(Gravity.BOTTOM);
    }
}
