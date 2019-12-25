package bjy.edu.android_learn.textview;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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


        //通过设置颜色透明的方式来实现文字缩进
        SpannableString spannableString = new SpannableString("柏建宇");
        spannableString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView tv_2 = findViewById(R.id.tv_2);
        tv_2.setText(spannableString);
    }
}
