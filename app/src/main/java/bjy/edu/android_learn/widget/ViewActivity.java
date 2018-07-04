package bjy.edu.android_learn.widget;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import bjy.edu.android_learn.R;

public class ViewActivity extends AppCompatActivity {
    SixDimensionIndicator sixDimensionIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
