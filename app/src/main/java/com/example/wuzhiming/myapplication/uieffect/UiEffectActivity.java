package com.example.wuzhiming.myapplication.uieffect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.utils.RotateTransformation;
import com.example.wuzhiming.myapplication.utils.ScreenUtils;

public class UiEffectActivity extends AppCompatActivity {

    private ImageView img1;
    private ImageView img4, img7, img9, img10, img11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_effect);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            startActivity(new Intent(this, ShadowActivity.class));
        });

        img1 = findViewById(R.id.img1);
        img4 = findViewById(R.id.img4);
        img7 = findViewById(R.id.img7);
        img9 = findViewById(R.id.img9);
        img10 = findViewById(R.id.img10);
        img11 = findViewById(R.id.img11);

        img1.setRotation(90f);

        img4.setRotation(90f);
        ViewGroup.LayoutParams params = img4.getLayoutParams();
        params.width = ScreenUtils.dp2px(this, 120);
        params.height = ScreenUtils.dp2px(this, 60);
        img4.setLayoutParams(params);


        ViewGroup.LayoutParams params2 = img7.getLayoutParams();
        params2.width = ScreenUtils.dp2px(this, 60);
        params2.height = ScreenUtils.dp2px(this, 120);
        img7.setLayoutParams(params2);
        img7.setPivotY(0f);
        img7.setPivotX(90f);
        img7.setRotation(90f);


//        Glide.with(this)
//                .load(R.drawable.bg)
//                .apply(RequestOptions.bitmapTransform(new RotateTransformation(false, 90)))
//                .into(img9);

        RotateTransformation rotateTransformation = new RotateTransformation(false, 270);

        Glide.with(this)
                .load(R.drawable.bg)
                .apply(RequestOptions.bitmapTransform(rotateTransformation))
                .into(img10);

/*        Glide.with(this)
                .load(R.drawable.bg)
                .into(img10);*/

        Glide.with(this)
                .load(R.drawable.bg)
                .into(img11);
    }
}