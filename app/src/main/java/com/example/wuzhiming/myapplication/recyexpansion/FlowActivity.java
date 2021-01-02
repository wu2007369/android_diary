package com.example.wuzhiming.myapplication.recyexpansion;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.beloo.widget.chipslayoutmanager.layouter.breaker.IRowBreaker;
import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.adapter.MatchItemAdapter;
import com.example.wuzhiming.myapplication.adapter.TextItemAdapter;
import com.example.wuzhiming.myapplication.utils.DpPxExchange;
import com.example.wuzhiming.myapplication.wideget.FlowLayoutManager;

import java.util.Arrays;

public class FlowActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);

        String[] strings=new String[]{"sacasdc","csdcsdcsdcsdc","kxco;lkco","ckalo;kcoask","casjlclsa"
        ,"cjileajcliveververver","casdc","dcsdcsdc","klkco","ckalo;kcoask","lclsa"
                ,"cjileajcliveververver"     ,"cjileajcliveververver","casdc","dcsdcsdc","klkco","ckalo;kcoask","lclsa"
                ,"cjileajcliveververver"
        };

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(false)
                //set maximum views count in a particular row
                .setMaxViewsInRow(5)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                //you are able to break row due to your conditions. Row breaker should return true for that views
                .setRowBreaker(new IRowBreaker() {
                    @Override
                    public boolean isItemBreakRow(@IntRange(from = 0) int position) {
                        return position == 6 || position == 11 || position == 2;
                    }
                })
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                // whether strategy is applied to last row. FALSE by default
                .withLastRow(true)
                .build();

        mRecyclerView=findViewById(R.id.recycler);
        ViewCompat.setLayoutDirection(mRecyclerView, ViewCompat.LAYOUT_DIRECTION_LTR);
        mRecyclerView.setLayoutManager(chipsLayoutManager);
        mAdapter=new TextItemAdapter(Arrays.asList(strings),this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new SpacingItemDecoration(
                DpPxExchange.Dp2Px(this,20),DpPxExchange.Dp2Px(this,10)));

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                int height = mRecyclerView.getHeight();
                Log.i("flow","recyc height="+height);
/*                if (height>500){
                    ((ConstraintLayout.LayoutParams) mRecyclerView.getLayoutParams()).height=500;
                    mRecyclerView.requestLayout();
                }*/

            }
        });

    }
}