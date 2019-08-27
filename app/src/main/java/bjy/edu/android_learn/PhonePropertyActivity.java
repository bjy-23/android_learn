package bjy.edu.android_learn;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.widget.TextView;

import bjy.edu.android_learn.util.DisplayUtil;

public class PhonePropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_property);

        final TextView tv_1 = findViewById(R.id.tv_1);
        tv_1.post(new Runnable() {
            @Override
            public void run() {
                Display defaultDisplay = getWindowManager().getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                int x = point.x;
                int y = point.y;

                DisplayMetrics metrics = PhonePropertyActivity.this.getResources().getDisplayMetrics();


                tv_1.setText("像素：" + metrics.widthPixels +"*" + metrics.heightPixels + " dpi: " + metrics.densityDpi);
            }
        });
    }
}
