package com.example.x_test.testClick;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.x_test.R;

public class TestClickActivity extends AppCompatActivity {
    IndexView indexView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_click);

        indexView = findViewById(R.id.index);
        indexView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("111222", "indexView onClick");
            }
        });

        TextView tv_dianwo = findViewById(R.id.tv_dianwo);
        tv_dianwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("111222", "tv_dianwo onClick");
            }
        });
    }
}