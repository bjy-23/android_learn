package com.bjy.app_product;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjy.app_product.contacts.ContactActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        ImageView img_menu = findViewById(R.id.img_menu);
        img_menu.setImageResource(R.drawable.icon_dianhua);
        TextView tv_menu = findViewById(R.id.tv_menu);
        tv_menu.setText("电话簿");
        ViewGroup layout_tel = findViewById(R.id.layout_tel);
        layout_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
            }
        });

        ViewGroup layout_joker = findViewById(R.id.layout_joker);
        ImageView img_joker = layout_joker.findViewById(R.id.img_menu);
//        img_joker.setImageResource(R.drawable.icon_dianhua);
        TextView tv_joker = layout_joker.findViewById(R.id.tv_menu);
        tv_joker.setText("joker");
        layout_joker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }
}
