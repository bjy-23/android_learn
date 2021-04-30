package com.example.test_listview;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_hello = findViewById(R.id.hello);
        tv_hello.setText("0123456789");
        tv_hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VpActivity.class));
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Typeface typeface = Typeface.createFromAsset(getAssets(), "DIN-Alternate-Bold.ttf");
//                tv_hello.setTypeface(typeface);
//            }
//        }, 3000);

//        startActivity(new Intent(MainActivity.this, RvActivity.class));
//        Float.parseFloat("");
    }
}