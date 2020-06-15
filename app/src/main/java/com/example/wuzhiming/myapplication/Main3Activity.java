package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuzhiming.myapplication.adapter.recyAdapter;

import java.util.Arrays;

public class Main3Activity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    recyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        String[] strings=new String[10];

        mRecyclerView=findViewById(R.id.recycler);
//        mRecyclerView.addItemDecoration();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mAdapter=new recyAdapter(Arrays.asList(strings),this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
