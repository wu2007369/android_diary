package com.example.wuzhiming.myapplication.recyexpansion.diff;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.wuzhiming.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author crazyZhangxl on 2019-1-21 9:52:36.
 * Describe: 给我的启发就是数据集合的处理
 *
 * 不错的Dif比较案例
 *
 * 因为你改原数据集那么匹配的数据也会改变的.那获取应该在之前比较好吧
 * https://www.jianshu.com/p/fee2fb3c27d6
 */

public class DiffActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private List<Man> mManList = new ArrayList<>();
    private List<Man> mManCloneList = new ArrayList<>();
    private List<Man> mManListOrigin = new ArrayList<>();
    private String[] names = {"张三","李四","王二","麻子","小王","小李","小白","小黑","小红","小徐","啧啧","哈哈"};
    private DifAdapter mDifAdapter;
    private DiffUtil.DiffResult diffResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);
        initData();
        initViews();
        initListener();
    }

    private void initListener() {


        findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiffActivity.this, "执行了修改了啊", Toast.LENGTH_SHORT).show();
                doChanged();
            }
        });

        findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReturn();
            }
        });
    }

    private void doReturn() {
        diffResult = DiffUtil.calculateDiff(new ManDifCallback(mDifAdapter.getDataList(), mManCloneList), true);
//        mDifAdapter.setDataList(mManCloneList);
        diffResult.dispatchUpdatesTo(mDifAdapter);
        mDifAdapter.setDataList(mManCloneList);//与顺序无关，但数据源一定要重新设置到adapter

    }

    /**
     */
    private void doChanged() {
        // 进行修改
        mManListOrigin.clear();
        mManListOrigin.addAll(mManCloneList);
        mManListOrigin.set(1,new Man(1,"哈哈哈哈哈哈"));
        mManListOrigin.add(new Man(12,"我已经在末尾了"));
        mManListOrigin.remove(5);
        // 调用DiffUtils帮助工具
        diffResult = DiffUtil.calculateDiff(new ManDifCallback(mDifAdapter.getDataList(), mManListOrigin), true);
//        mDifAdapter.setDataList(mManListOrigin);
        diffResult.dispatchUpdatesTo(mDifAdapter);
        mDifAdapter.setDataList(mManListOrigin);//与顺序无关，但数据源一定要重新设置到adapter

    }

    private void initData() {
        for (int i=0;i<10;i++){
            mManList.add(new Man(i,names[i]));
        }
        mManCloneList.addAll(mManList);
    }

    private void initViews() {
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mDifAdapter = new DifAdapter(mManCloneList, this);
        recyclerview.setAdapter(mDifAdapter);
    }
}
