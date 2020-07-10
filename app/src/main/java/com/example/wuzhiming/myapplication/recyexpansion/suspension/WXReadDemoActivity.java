package com.example.wuzhiming.myapplication.recyexpansion.suspension;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.recyexpansion.suspension.bean.Header;
import com.example.wuzhiming.myapplication.recyexpansion.suspension.bean.Item;
import com.example.wuzhiming.myapplication.recyexpansion.suspension.bean.Section;

import java.util.ArrayList;
import java.util.List;

public class WXReadDemoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ReadAdapter mReadAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private FrameLayout mSectionHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_x_read_demo);

        initViews();

    }

    private void initViews() {
        mSectionHeaderView = findViewById(R.id.section_header_container);
        mRecyclerView = findViewById(R.id.recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mReadAdapter = new ReadAdapter(this);
        mRecyclerView.setAdapter(mReadAdapter);
        mReadAdapter.setData(getData());
        mRecyclerView.addItemDecoration(new PinnedSectionItemDecoration(mSectionHeaderView, callback));
    }

    /**
     *构造adapter使用的初始数据
     * @return
     */
    private List<Section> getData(){
        ArrayList<Section> sections = new ArrayList<>();
        for (int i =0 ;i<10;i++){
            Header header = new Header("selection "+i);
            List<Item> items = new ArrayList<>();
            for (int j =0 ;j<30;j++){
                items.add(new Item("selection "+i+" ,item "+j));
            }
            sections.add(new Section(header,items,true));
        }
        return sections;
    }

    private PinnedSectionItemDecoration.Callback<RecyclerView.ViewHolder> callback = new PinnedSectionItemDecoration.Callback<RecyclerView.ViewHolder>() {
        @Override
        public int getRelativeFixedItemPosition(int position) {
            return mReadAdapter.getRelativeFixedItemPosition(position);
        }

        @Override
        public boolean isHeader(int position) {
            return mReadAdapter.getItemViewType(position) == ReadAdapter.ITEM_TYPE_SECTION_HEADER;
        }

        @Override
        public void afterCreateFixedViewHolder(RecyclerView.ViewHolder viewHolder, int viewType) {
        }

        @Override
        public void afterBindFixedViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
            mReadAdapter.afterBindFixedViewHolder(viewHolder, pos);
        }

        @Override
        public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int viewType) {
            return mReadAdapter.createViewHolder(parent, viewType);
        }

        @Override
        public void bindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            mReadAdapter.bindViewHolder(viewHolder, position);
        }

        @Override
        public int getItemViewType(int position) {
            return mReadAdapter.getItemViewType(position);
        }

        @Override
        public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            mReadAdapter.registerAdapterDataObserver(observer);
        }

        @Override
        public void onHeaderVisibilityChanged(boolean visible) {
        }
    };
}