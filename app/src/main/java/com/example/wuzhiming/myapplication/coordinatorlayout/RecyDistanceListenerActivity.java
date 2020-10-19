package com.example.wuzhiming.myapplication.coordinatorlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.adapter.IconAdapter;

import java.util.Arrays;

public class RecyDistanceListenerActivity extends AppCompatActivity {

    private TextView text;
    private RecyclerView recy;

    String[] urls = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384382663&di=7c864b6861f35b47e1e75f865e2e201a&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw516h255%2F20180213%2F2991-fyrpeie2552043.jpg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384382655&di=b64f1f0d4e2132f946e4c281221f84fb&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200224%2F40c3daa15c07447b96c4b03779e8bfa7.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384412795&di=0b20db5a1e602ce1cf8484355e255727&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201903%2F06%2F20190306001711_ridqy.jpeg"
            , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590384412794&di=fc4a2f7663506ab7fa05265a3a22c493&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D0e0b5ced9e82d158bb8259b9b00a19d5%2F457013d162d9f2d3882efeecabec8a136327cc7f.jpg"};
    private IconAdapter mAdapter;
    private RecyclerView.OnScrollListener mOnScrollListener=new RecyclerView.OnScrollListener() {
        private int mmRvScrollY;

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mmRvScrollY += dy;
            Log.d("TAG", "onScrolled: mmRvScrollY: " + mmRvScrollY + ", dy: " + dy);

            text.setText("滑动距离为："+mmRvScrollY);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy_distance_listener);

        recy=(RecyclerView)findViewById(R.id.recy);
        text=(TextView)findViewById(R.id.text);


        mAdapter = new IconAdapter(Arrays.asList(urls), this);
        recy.setLayoutManager(new LinearLayoutManager(this));
        recy.setAdapter(mAdapter);

        recy.addOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recy.removeOnScrollListener(mOnScrollListener);
    }
}