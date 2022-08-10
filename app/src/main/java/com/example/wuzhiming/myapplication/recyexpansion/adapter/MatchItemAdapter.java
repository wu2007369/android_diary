package com.example.wuzhiming.myapplication.recyexpansion.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.wuzhiming.myapplication.R;

import java.util.List;

public class MatchItemAdapter extends RecyclerView.Adapter {
    List<String> mdata;
    Context mContext;
    public MatchItemAdapter(List<String> mdata, Context mContext) {
        this.mdata=mdata;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder){
            ((ViewHolder) viewHolder).btn.setOnClickListener((v)-> Toast.makeText(mContext,"csdcsdcs",Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.item_btn);
        }
    }
}
