package com.example.wuzhiming.myapplication.recyexpansion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.adapter.ItemClipAdapter;
import com.example.wuzhiming.myapplication.recyexpansion.decoration.SimpleDividerItemDecoration;
import com.example.wuzhiming.myapplication.utils.DpPxExchange;

import java.util.Arrays;

public class DecorationTestActivity extends AppCompatActivity {

    private RecyclerView mRecy;
    private ItemClipAdapter itemClipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration_test);

        mRecy = findViewById(R.id.recy);
        mRecy.setLayoutManager(new LinearLayoutManager(this));
        itemClipAdapter = new ItemClipAdapter(Arrays.asList(new Integer[]{1, 2,3,4,5,6}), this);
        mRecy.setAdapter(itemClipAdapter);

        mRecy.addItemDecoration(new SimpleDividerItemDecoration(this, DpPxExchange.Dp2Px(this,1)));
    }
}