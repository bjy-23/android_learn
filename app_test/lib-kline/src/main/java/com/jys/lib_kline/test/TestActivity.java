package com.jys.lib_kline.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jys.lib_kline.IndexView;
import com.jys.lib_kline.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        IndexView indexView = findViewById(R.id.index);
        indexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("111222", "indexView onClick");
            }
        });
    }
}