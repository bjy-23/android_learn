package com.example.test_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);



        HorizontalScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("111222", "scrollView.setOnClickListener");
            }
        });

        LinearLayout layout_container = findViewById(R.id.layout_container);
        layout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("111222", "layout_container.setOnClickListener");
            }
        });

//        TextView tv_1 = findViewById(R.id.tv_1);
//        tv_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("111222", "tv_1.setOnClickListener");
//            }
//        });

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://beta.zgjys.com/newsStock?id=SH600237");
    }
}