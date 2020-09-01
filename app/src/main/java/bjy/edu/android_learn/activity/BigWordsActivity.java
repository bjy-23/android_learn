package bjy.edu.android_learn.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import bjy.edu.android_learn.R;

//横屏大字
public class BigWordsActivity extends AppCompatActivity {
    private ViewGroup layout_root;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_words);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        layout_root = findViewById(R.id.layout_root);
        tv_content = findViewById(R.id.tv_content);


        layout_root.setBackgroundColor(Color.BLACK);

        tv_content.setTextColor(Color.WHITE);
        tv_content.setText("走，一起去划水");
        tv_content.setTextSize(100);
    }
}