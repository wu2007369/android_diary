package com.example.wuzhiming.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.entity.CalendarBean;

import java.util.ArrayList;
import java.util.List;

public class CalendarChildListAdapter extends RecyclerView.Adapter {
    List<CalendarBean> mDataList=new ArrayList<>();
    Context mContext;

    public CalendarChildListAdapter( Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_calendar_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder){
            ((ViewHolder) viewHolder).fill(i);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position%getItemCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView week;
        TextView day;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            week = itemView.findViewById(R.id.week);
            day = itemView.findViewById(R.id.dayNum);
        }

        void fill(int index) {
            CalendarBean data = mDataList.get(index);
            week.setText(data.getWeek());
            day.setText(data.getDateNum()+"");

        }

    }

    public void setmDataList(List<CalendarBean> mDataList) {
        this.mDataList = mDataList;
    }
}
