package com.example.wuzhiming.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public class Main7Activity extends AppCompatActivity implements View.OnClickListener {

    private ViewStub stub;
    private Button btn;
    boolean stubShow = false;


    private ViewStub viewStub;
    private TextView hintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        stub = findViewById(R.id.viewstub);
        stubShow = true;
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(v -> {
            stubShow = !stubShow;
            stub.setVisibility(stubShow ? View.VISIBLE : View.INVISIBLE);
        });

        init();
    }


    /**
     * 初始化
     */
    private void init() {
        viewStub = (ViewStub) findViewById(R.id.viewstub_test);

        Button btn_show = (Button) findViewById(R.id.btn_vs_showView);
        Button btn_hide = (Button) findViewById(R.id.btn_vs_hideView);
        Button btn_change = (Button) findViewById(R.id.btn_vs_changeHint);

        btn_show.setOnClickListener(this);
        btn_hide.setOnClickListener(this);
        btn_change.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vs_showView:

                //inflate 方法只能被调用一次，因为调用后viewStub对象就被移除了视图树；
                // 所以，如果此时再次点击显示按钮，就会崩溃，错误信息：ViewStub must have a non-null ViewGroup viewParent；
                // 所以使用try catch ,当此处发现exception 的时候，在catch中使用setVisibility()重新显示
                try {
                    View iv_vsContent = viewStub.inflate();     //inflate 方法只能被调用一次，
                    hintText = (TextView) iv_vsContent.findViewById(R.id.tv_vsContent);
                    //                    hintText.setText("没有相关数据，请刷新");

//TODO test
                    View content2=stub.inflate();

                } catch (Exception e) {
                    viewStub.setVisibility(View.VISIBLE);
                } finally {
                    hintText.setText("没有相关数据，请刷新");
                }
                break;
            case R.id.btn_vs_hideView:  //如果显示
                viewStub.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_vs_changeHint:
                if (hintText != null) {
                    hintText.setText("网络异常，无法刷新，请检查网络");
                }
                break;
        }
    }
}
