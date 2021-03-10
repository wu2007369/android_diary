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
        //调整明暗度，float值，完全透明不变暗是0.0f，完全变暗不透明是1.0f
        lp.dimAmount=0.6f;
        //必须要设置回去
        getWindow().setAttributes(lp);

        getWindow().setGravity(Gravity.BOTTOM);



        //根据谷歌文档，给对应的Window添加FLAG_DIM_BEHIND标志位，dimAmount值才有效。
//        mWindowDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_bottom_in_normal, R.anim.pop_bottom_out_normal);

    }
}
