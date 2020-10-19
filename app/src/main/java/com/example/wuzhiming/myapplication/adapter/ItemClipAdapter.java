package com.example.wuzhiming.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuzhiming.myapplication.R;

import java.util.List;

public class ItemClipAdapter extends RecyclerView.Adapter {
    List<Integer> mdata;
    private Context mContext;

    public ItemClipAdapter(List<Integer> mdata, Context mContext) {
        this.mdata = mdata;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new recyAdapter.ViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_color_fulled, parent, false));
        } else {
            return new recyAdapter.ViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_top_round, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else
            return 1;
    }

    @Override
    public int getItemCount() {
        return null == mdata ? 0 : mdata.size();
    }


}
