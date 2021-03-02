package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;

public class LoadingTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_test);

        SpinKitView spin_kit=findViewById(R.id.spin_kit);
        Sprite drawable = SpriteFactory.create(Style.FADING_CIRCLE);
        spin_kit.setIndeterminateDrawable(drawable);

    }
}