package com.example.wuzhiming.myapplication.coordinatorlayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.adapter.IconAdapter;
import com.example.wuzhiming.myapplication.utils.PhoneUtils;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main9Activity extends AppCompatActivity {
    String[] urls = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384382663&di=7c864b6861f35b47e1e75f865e2e201a&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw516h255%2F20180213%2F2991-fyrpeie2552043.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384382655&di=b64f1f0d4e2132f946e4c281221f84fb&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200224%2F40c3daa15c07447b96c4b03779e8bfa7.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607513176769&di=0d1dcfb53bf75dc61af8c562004877e0&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F6bb26a330ba1371c4f767dc9443e939d2242555e.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384412794&di=fc4a2f7663506ab7fa05265a3a22c493&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D0e0b5ced9e82d158bb8259b9b00a19d5%2F457013d162d9f2d3882efeecabec8a136327cc7f.jpg"
            ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607513203332&di=6bbb138a914525c59302d6519915c91c&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201903%2F10%2F20190310131630_nhtzu.jpg"
    };

    RecyclerView recy;
    IconAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);




        recy = findViewById(R.id.img_recy);
        List<String> mData = new ArrayList<>();
        mData.addAll(Arrays.asList(urls));
        mData.addAll(Arrays.asList(urls));
        mData.addAll(Arrays.asList(urls));
        mAdapter = new IconAdapter(mData, this);
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(mAdapter);


        ImageView topImage=findViewById(R.id.top_img);
        Glide.with(this)
                .load("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2090320482,3778542441&fm=26&gp=0.jpg")
                .apply(new RequestOptions().centerCrop())
                .into(topImage);

        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("this is title");


        setHeader(toolbar);
    }

    private void setHeader(Toolbar toolbar) {
        View decorView = getWindow().getDecorView();
        //全面屏
        //全面屏+背景可伸展到系统栏（状态栏+导航栏）
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        //状态栏背景色为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        int statusHeight = PhoneUtils.getStatusBarHeight(this);

                toolbar.setPadding(0, statusHeight, 0, toolbar.getPaddingBottom());
                ViewGroup.LayoutParams params= toolbar.getLayoutParams();
                params.height=params.height+statusHeight;
                toolbar.setLayoutParams(params);

    }
}
