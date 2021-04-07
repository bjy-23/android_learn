package com.example.test_k_line;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KLineView kLineView = findViewById(R.id.kline);
//        kLineView.setValueArray(new float[]{100, 160, 210, 120, 330, 440, 170, 100, 90, 50, 333, 222, 111, 180});
        kLineView.setValueArray(new float[]{100, 200, 200});
//        kLineView.setLineColor("#EE3F3C");
//        kLineView.setShadowEnable(true);
////        kLineView.setValueArray(new float[]{100, 200, 100});

        kLineView.requestLayout();


//        LinearLayout layout_root = findViewById(R.id.layout_root);
//        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas();
//        canvas.setBitmap(bitmap);
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//
//        canvas.drawLine(0, 0, 50, 50, paint);
//
//        View view = new View(MainActivity.this);
////        view.setBackgroundColor(Color.BLUE);
////        view.draw(canvas);
//        view.setBackground(new BitmapDrawable(bitmap));
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
////        layout_root.addView(view, layoutParams);
    }
}