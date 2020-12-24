package com.example.wuzhiming.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuzhiming.myapplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextItemAdapter extends RecyclerView.Adapter {
    List<String> mdata;
    Context mContext;
    public TextItemAdapter(List<String> mdata, Context mContext) {
        this.mdata=mdata;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_text_item,viewGroup,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder){
            String content = mdata.get(i);
            ((ViewHolder) viewHolder).btn.setText(content);
            ((ViewHolder) viewHolder).btn.setOnClickListener((v)-> Toast.makeText(mContext,"csdcsdcs",Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.item_btn);
        }
    }
}
