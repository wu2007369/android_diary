package com.example.wuzhiming.myapplication.immerse;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wuzhiming.myapplication.R;

public class ImmerseWebviewActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immerse_webview);

        webview=findViewById(R.id.webview);
        webview.setHorizontalScrollBarEnabled(false);
        webview.setVerticalScrollBarEnabled(false);
        // mWebView.setFocusable(true);

        webview.getSettings().setDomStorageEnabled(true);

        WebSettings setting = webview.getSettings();

        setting.setJavaScriptEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setGeolocationEnabled(true);
        // setting.setSupportZoom(true);
        // setting.setBuiltInZoomControls(true);
        // setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setDefaultTextEncodingName("UTF-8");
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        setting.setLightTouchEnabled(false);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setBuiltInZoomControls(true);
        setting.setSupportZoom(true);
        setting.setBlockNetworkImage(false); // 解决图片不显示
        webview.setWebViewClient(new WebViewClient());

        String url="http://go.jinying.com";

        if (getIntent()!=null){
            url=getIntent().getStringExtra("url");
        }

        webview.loadUrl(url);

        setHeader();
    }

    private void setHeader(){

        View decorView = getWindow().getDecorView();
        //全面屏
/*        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);*/
        //全面屏+背景可伸展到系统栏（状态栏+导航栏）
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        //状态栏背景色为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}