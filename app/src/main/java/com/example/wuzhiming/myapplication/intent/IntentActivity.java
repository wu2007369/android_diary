package com.example.wuzhiming.myapplication.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wuzhiming.myapplication.R;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Uri locationForPhotos;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        image=findViewById(R.id.image);

        findViewById(R.id.btn14).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn14:
                Intent intent2 = new Intent();
                ComponentName cn = new ComponentName("com.jinying.mobile", "com.jinying.mobile.wxapi.WXEntryActivity");
//                ComponentName cn = new ComponentName("net.sourceforge.simcpux",  "net.sourceforge.simcpux.wxapi.WXEntryActivity");
                intent2.setComponent(cn);
                startActivity(intent2);
                break;

            case R.id.btn1:
//                startActivity(new Intent(this,AliasActivity.class));
                startActivity(new Intent(this, JumpActivity.class));
                break;

            case R.id.btn2:
//                startActivity(new Intent(this,AliasActivity.class));
                Intent intent = new Intent();
                ComponentName cn2 = new ComponentName("com.jinying_oa", "com.jinying_oa.activity.GuideActivity");
                intent.setComponent(cn2);
                startActivity(intent);
                break;

            case R.id.btn3:
//                startActivity(new Intent(this,AliasActivity.class));
                Intent intent3 = new Intent();
                intent3.setAction("android.intent.action.VIEW");
                intent3.addCategory("android.intent.category.BROWSABLE");
                intent3.setData(Uri.parse("geoaapp://"));
                startActivity(intent3);
                break;
            case R.id.btn4:
//                startActivity(new Intent(this,AliasActivity.class));
                Intent intent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent4.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.withAppendedPath(locationForPhotos, "targetFilename"));
                startActivityForResult(intent4, REQUEST_IMAGE_CAPTURE);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
            Glide.with(this).load(thumbnail).into(image);
        }
    }
}