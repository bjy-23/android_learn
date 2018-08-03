package bjy.edu.android_learn.widget;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class ViewActivity extends AppCompatActivity {
    SixDimensionIndicator sixDimensionIndicator;
    private int count = 1;
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
        final TextView tv_add = findViewById(R.id.tv_add);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                lineView.addDraw();
                tv_add.setText("2个canvas");
            }
        });
    }
}
