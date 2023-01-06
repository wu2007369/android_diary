package com.example.wuzhiming.myapplication.coordinatorlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.adapter.IconAdapter;
import com.example.wuzhiming.myapplication.behavior.GaoDeBottomSheetBehavior;
import com.example.wuzhiming.myapplication.extention.ViewExtentionKt;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main8Activity extends AppCompatActivity {
    public static final String LAYOUT_TYPE = "layoutType";
    String[] urls = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384382663&di=7c864b6861f35b47e1e75f865e2e201a&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw516h255%2F20180213%2F2991-fyrpeie2552043.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384382655&di=b64f1f0d4e2132f946e4c281221f84fb&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200224%2F40c3daa15c07447b96c4b03779e8bfa7.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607513176769&di=0d1dcfb53bf75dc61af8c562004877e0&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F6bb26a330ba1371c4f767dc9443e939d2242555e.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384412794&di=fc4a2f7663506ab7fa05265a3a22c493&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D0e0b5ced9e82d158bb8259b9b00a19d5%2F457013d162d9f2d3882efeecabec8a136327cc7f.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607513203332&di=6bbb138a914525c59302d6519915c91c&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201903%2F10%2F20190310131630_nhtzu.jpg"
    };

    AppBarLayout appbarLayout;
    RecyclerView recy;
    IconAdapter mAdapter;
    NestedScrollView mNestedScrollView;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int mmRvScrollY;

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mmRvScrollY += dy;
            Log.d("TAG", "onScrolled: mmRvScrollY: " + mmRvScrollY + ", dy: " + dy);

//            text.setText("滑动距离为："+mmRvScrollY);
        }
    };
    private int layoutType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
         layoutType = intent.getIntExtra(LAYOUT_TYPE, 1);
        switch (layoutType) {
            case 1:
                setContentView(R.layout.activity_main8);
                break;
            case 2:
                setContentView(R.layout.activity_main8_2);
                AppBarLayout appBar = findViewById(R.id.appbarLayout);
                appBar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, verticalOffset) -> {
                    Log.i("AppBarLayout", "verticalOffset=" + verticalOffset);
                });
                break;
            case 3:
                setContentView(R.layout.activity_main8_3);
                break;
            case 4:
                setContentView(R.layout.activity_main8_4);
                break;
            case 5:
                setContentView(R.layout.activity_main8_5);
                break;
            case 6:
                setContentView(R.layout.activity_main8_6);
                break;
            case 7:
                setContentView(R.layout.activity_main8_2_2);
                AppBarLayout appBar2 = findViewById(R.id.appbarLayout);
                appBar2.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, verticalOffset) -> {
                    Log.i("AppBarLayout", "verticalOffset=" + verticalOffset);
                });
                break;
            case 8:
                setContentView(R.layout.activity_main8_7);
                break;
            case 9:
                setContentView(R.layout.activity_main8_8);
                break;
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        recy = findViewById(R.id.img_recy);
        appbarLayout = findViewById(R.id.appbarLayout);

        List<String> mData = new ArrayList<>();
        mData.addAll(Arrays.asList(urls));
        mData.addAll(Arrays.asList(urls));
        mData.addAll(Arrays.asList(urls));
        mAdapter = new IconAdapter(mData, this);
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(mAdapter);

        recy.addOnScrollListener(mOnScrollListener);
        if (appbarLayout!=null){
            appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    Log.d("TAG", "onOffsetChanged: i: " + i);

                }
            });
        }


    }



    BottomSheetBehavior.BottomSheetCallback callBack = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_EXPANDED: {
                    Toast.makeText(Main8Activity.this, "STATE_EXPANDED", Toast.LENGTH_SHORT).show();
                    break;
                }
                case BottomSheetBehavior.STATE_COLLAPSED: {
                    Toast.makeText(Main8Activity.this, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            Log.i("Main8Activity","BottomSheetBehavior onSlide slideOffset="+slideOffset);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recy.removeOnScrollListener(mOnScrollListener);
        if (layoutType==8){
            getBehavior().removeBottomSheetCallback(callBack);
        }else if (layoutType==9) {
            getCustomeBehavior().removeBottomSheetCallback();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (layoutType==8){
            getBehavior().addBottomSheetCallback(callBack);
            getBehavior().setPeekHeight((int) ViewExtentionKt.getDp(330));
        } else if (layoutType==9) {
            getCustomeBehavior().setBottomSheetCallback(callBack);
        }

    }


    BottomSheetBehavior<View> getBehavior() {
        return BottomSheetBehavior.from(findViewById(R.id.img_recy));
    }

    GaoDeBottomSheetBehavior<View> getCustomeBehavior() {
        return GaoDeBottomSheetBehavior.from(findViewById(R.id.img_recy));
    }
}
