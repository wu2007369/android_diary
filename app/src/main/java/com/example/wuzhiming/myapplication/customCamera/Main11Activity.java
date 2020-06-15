package com.example.wuzhiming.myapplication.customCamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wuzhiming.myapplication.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main11Activity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CAMERA_CODE=999;


    private Button on_camera;
    private ImageView iv_photo;
    private Button on_camera_big,on_cameraview;
    private ImageView iv_photo_big;

    // 返回码
    private static final int CODE = 1;
    private static final int CODEBIG = 2;

    // 记录文件保存位置
    private String mFilePath;
    private FileInputStream is = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        // 获取SD卡路径
        mFilePath = Environment.getExternalStorageDirectory().getPath();
        // 文件名
        mFilePath = mFilePath + "/" + "photo.png";

        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        iv_photo_big = (ImageView) findViewById(R.id.iv_photo_big);
        on_camera = (Button) findViewById(R.id.on_camera);
        on_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * 我们使用Intent的方式打开系统相机
                 * 1.直接跳转相机包名，前提是你知道这个应用的包名
                 * 2.就是使用隐式Intent了，在这里我们就使用隐式intent
                 */

                if (!requestPermission()){
                    return;
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 指定拍照
                // 拍照返回图片
                startActivityForResult(intent, CODE);
            }
        });
        on_camera_big = (Button) findViewById(R.id.on_camera_big);
        on_camera_big.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!requestPermission()){
                    return;
                }
                // 指定拍照
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 加载路径
                Uri uri = Uri.fromFile(new File(mFilePath));
                // 指定存储路径，这样就可以保存原图了
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                // 拍照返回图片
                startActivityForResult(intent, CODEBIG);

            }
        });
        on_cameraview = (Button) findViewById(R.id.on_cameraview);
        on_cameraview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!requestPermission()){
                    return;
                }
                startActivity(new Intent(Main11Activity.this,CameraActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // 判断是否返回值
        if (resultCode == RESULT_OK) {
            // 判断返回值是否正确
            if (requestCode == CODE) {
                // 获取图片
                Bundle bundle = data.getExtras();
                // 转换图片的二进制流
                Bitmap bitmap = (Bitmap) bundle.get("data");
                // 设置图片
                iv_photo.setImageBitmap(bitmap);
                // 加载原图
            } else if (requestCode == CODEBIG) {
                try {
                    // 获取输入流
                    is = new FileInputStream(mFilePath);
                    // 把流解析成bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    // 设置图片
                    iv_photo_big.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    // 关闭流
                    try {
                        is.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(Main11Activity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //具有拍照权限，直接调用相机
                //具体调用代码
                return true;
            } else {
                //不具有拍照权限，需要进行权限申请
                ActivityCompat.requestPermissions(Main11Activity.this,new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA_CODE);
                return false;
            }
        } else {
            //当前系统小于6.0，直接调用拍照
            return true;

        }
    }


}