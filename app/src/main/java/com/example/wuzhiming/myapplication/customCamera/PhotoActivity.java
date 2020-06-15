package com.example.wuzhiming.myapplication.customCamera;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuzhiming.myapplication.R;

public class PhotoActivity extends Activity {

	private TextView tv_path;
	private ImageView iv_photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		String path = getIntent().getStringExtra("path");
		tv_path = (TextView) findViewById(R.id.tv_path);
		// 显示路径
		tv_path.setText(path);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);

		// 调整角度
		try {
			FileInputStream fis = new FileInputStream(path);
			Bitmap bitmap = BitmapFactory.decodeStream(fis);
			// 矩阵
			Matrix matrix = new Matrix();
			matrix.setRotate(90);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
			iv_photo.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
