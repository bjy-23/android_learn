package bjy.edu.android_learn.viewflipper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import bjy.edu.android_learn.R;

public class ViewFlipperActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);

        viewFlipper = findViewById(R.id.viewFilpper);

        for (int i=0; i<3; i++){
            View view = View.inflate(ViewFlipperActivity.this, R.layout.view_flipper_item, null);
            TextView tv_1 = view.findViewById(R.id.tv_1);
            TextView tv_2 = view.findViewById(R.id.tv_2);

            tv_1.setText("B---"+i);
            tv_2.setText("JY---"+i);

            viewFlipper.addView(view);
        }

        viewFlipper.startFlipping();
    }
}
