package com.example.wuzhiming.myapplication.recyexpansion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.recyexpansion.adapter.ItemClipAdapter;

import java.util.Arrays;

public class RecyItemClipActivity extends AppCompatActivity {

    RecyclerView mRecy;
    ItemClipAdapter itemClipAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy_item_clip);

        mRecy = findViewById(R.id.recy);
        mRecy.setLayoutManager(new LinearLayoutManager(this));
        itemClipAdapter = new ItemClipAdapter(Arrays.asList(new Integer[]{1, 2}), this);
        mRecy.setAdapter(itemClipAdapter);

    }
}