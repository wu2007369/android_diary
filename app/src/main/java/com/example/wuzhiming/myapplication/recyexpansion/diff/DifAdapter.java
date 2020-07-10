package com.example.wuzhiming.myapplication.recyexpansion.diff;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.wuzhiming.myapplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author crazyZhangxl on 2019/1/21.
 * Describe:
 */
public class DifAdapter extends RecyclerView.Adapter<DifAdapter.ViewHolder>{
    private List<Man> mDataList;
    private Context mContext;

    public DifAdapter(List<Man> dataList, Context context) {
        mDataList = dataList;
        mContext = context;
    }

    public List<Man> getDataList() {
        return mDataList;
    }

    public void setDataList(List<Man> dataList) {
        mDataList=dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_one_group, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Man man = mDataList.get(i);
        viewHolder.mTextName.setText(man.getName());
    }

    @Override
    public int getItemCount() {
        if (null != mDataList){
            return mDataList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextName =  itemView.findViewById(R.id.tvGroupName);
        }
    }
}
