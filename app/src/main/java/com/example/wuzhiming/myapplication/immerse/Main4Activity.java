package com.example.wuzhiming.myapplication.immerse;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.wuzhiming.myapplication.R;

public class Main4Activity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            View decorView = getWindow().getDecorView();
            //全面屏
            /*int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(option);*/
            //全面屏+背景可伸展到系统栏（状态栏+导航栏）
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //状态栏背景色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(0);
            //状态栏背景色为透明
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
        });


            findViewById(R.id.btn3).setOnClickListener(v -> {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        });
        findViewById(R.id.btn4).setOnClickListener(v -> {
            Window window = this.getWindow();
            //状态栏背景 可以绘制+透明色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //状态栏字体 黑色（白色背景状态栏，被上面的FLAG_TRANSLUCENT_STATUS 抵消了）
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
}
