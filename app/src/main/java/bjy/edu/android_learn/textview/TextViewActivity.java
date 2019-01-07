package bjy.edu.android_learn.textview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class TextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        //失去焦点，跑马灯效果（android:ellipsize="marquee"）无效
        final TextView tv = findViewById(R.id.tv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setFocusable(false);
            }
        }, 3000);
    }
}
