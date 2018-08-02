package bjy.edu.android_learn.widget;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import bjy.edu.android_learn.R;

public class ViewActivity extends AppCompatActivity {
    SixDimensionIndicator sixDimensionIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        view_1();

        view_2();

    }

    private void view_1(){
        setContentView(R.layout.activity_view);
        sixDimensionIndicator = findViewById(R.id.sixDimensionIndicator);
//        View view = new View(ViewActivity.this);
//        view.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("1", "post");
//                sixDimensionIndicator.requestLayout();
//            }
//        }, 3000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("1", "post");
                sixDimensionIndicator.showIndicator(4);
            }
        }, 100);
    }

    private void view_2(){
        setContentView(R.layout.activity_view_2);

        final LineView lineView = findViewById(R.id.line_view);
        lineView.postDelayed(new Runnable() {
            @Override
            public void run() {
                final ImageView imageView = new ImageView(ViewActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(R.color.colorAccent);
//                imageView.setLeft(290);
//                imageView.setRight(310);
//                imageView.setTop(290);
//                imageView.setBottom(310);
                lineView.addView(imageView);

                imageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.layout(290, 290, 310, 310);
                    }
                },1000);
            }
        }, 2000);
    }
}
