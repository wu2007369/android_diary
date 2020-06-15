package com.example.wuzhiming.myapplication.customCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.wuzhiming.myapplication.R;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {

	private Button btn_camera;
	private Camera mCamera;
	private SurfaceView sv;
	private SurfaceHolder sh;
	private PictureCallback pc = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// data为二进制完整数据
			Bitmap bitmap=BitmapFactory.decodeByteArray(data,0,data.length);
//			华为 oppo等机型，拍过一次之后camera就会自动停止没需要手动再开启一次
			camera.startPreview();

			/*
			//文件操作需要添加权限
			File file = new File("/sdcard/photo.png");
			// 使用流进行读写
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(data);
				// 关闭流
				fos.close();
				// 查看图片
				Intent intent = new Intent(CameraActivity.this,
						PhotoActivity.class);
				// 传递路径
				intent.putExtra("path", file.getAbsolutePath());
				startActivity(intent);
				CameraActivity.this.finish();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("CameraView");
		setContentView(R.layout.activity_camera);

		sv = (SurfaceView) findViewById(R.id.sv);
		// 初始化SurfaceHolder
		sh = sv.getHolder();
		sh.addCallback(this);

		btn_camera = (Button) findViewById(R.id.btn_camera);
		btn_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取当前相机参数
				Camera.Parameters parameters = mCamera.getParameters();
				// 设置相片格式
				parameters.setPictureFormat(ImageFormat.JPEG);
				// 设置预览大小
				parameters.setPreviewSize(800, 480);
				// 设置对焦方式，这里设置自动对焦
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				mCamera.autoFocus(new AutoFocusCallback() {

					@Override
					public void onAutoFocus(boolean success, Camera camera) {
						// 判断是否对焦成功
						if (success) {
							// 拍照 第三个参数为拍照回调
							mCamera.takePicture(null, null, pc);
						}
					}
				});

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 在activity运行时绑定
		if (mCamera == null) {
			mCamera = getcCamera();
			if (sh != null) {
				showViews(mCamera, sh);
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// activity暂停时我们释放相机内存
		clearCamera();

	}

	/**
	 * 获取系统相机
	 * 
	 * @return
	 */
	private Camera getcCamera() {
		Camera camera = null;
		try {
			Log.e("CameraActivity","getNumberOfCameras"+Camera.getNumberOfCameras());
			camera = Camera.open(Camera.getNumberOfCameras()>=2?1:0);
		} catch (Exception e) {
			camera = null;
		}
		return camera;
	}

	/**
	 * 与SurfaceView传播图像
	 */
	private void showViews(Camera camera, SurfaceHolder holder) {
		// 预览相机,绑定
		try {
			camera.setPreviewDisplay(holder);
			// 系统相机默认是横屏的，我们要旋转90°
			camera.setDisplayOrientation(90);
			// 开始预览
			camera.startPreview();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 释放相机的内存
	 */
	private void clearCamera() {

		// 释放hold资源
		if (mCamera != null) {
			// 停止预览
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			// 释放相机资源
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 开始预览
		showViews(mCamera, sh);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// 重启功能
		mCamera.stopPreview();
		showViews(mCamera, sh);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// 释放
		clearCamera();
	}
}
