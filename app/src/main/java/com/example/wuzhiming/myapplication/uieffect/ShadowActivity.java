package com.example.wuzhiming.myapplication.uieffect;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.utils.DensityUtils;
import com.sxu.shadowdrawable.ShadowDrawable;

public class ShadowActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);

        View frlayout = findViewById(R.id.frlyout);

        // 实例：设置背景为颜色为#3D5AFE，圆角为8dp, 阴影颜色为#66000000，宽度为10dp的背景
        ShadowDrawable.setShadowDrawable(frlayout, DensityUtils.dip2px(this,1),
                Color.parseColor("#f63D5AFE"), DensityUtils.dip2px(this,10), 0, 0);
    }
}