package com.example.wuzhiming.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.entity.CalendarBean;

import java.util.ArrayList;
import java.util.List;

public class CalendarListAdapter extends RecyclerView.Adapter {
//    List<List<CalendarBean>> mDataList;
    public List<CalendarBean> mDataList;
    Context mContext;

    public CalendarListAdapter(List<CalendarBean> dataList, Context context) {
        mDataList = dataList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_calendar_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder){
            ((ViewHolder) viewHolder).fill(i);
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList.size()>28){
            return 5;
        }else {
            return 4;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position%getItemCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private  CalendarChildListAdapter adapter;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recy);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext,7));
            adapter=new CalendarChildListAdapter(mContext);
        }

        void fill(int index) {
            List<CalendarBean> childDataList=new ArrayList<>();
            if (index>=0&&index<=3){
                childDataList = mDataList.subList(index * 7, index * 7 + 7);
            }else if (index==4){
                childDataList = mDataList.subList(index*7,mDataList.size());
            }

            adapter.setmDataList(childDataList);
            recyclerView.setAdapter(adapter);

        }

    }
}
