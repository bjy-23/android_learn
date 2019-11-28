package bjy.edu.android_learn.dialog;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class DialogThemeActivity extends AppCompatActivity {

    // TODO: 2019-11-26 点击事件怎么消失了？？？

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_theme);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        getWindow().setAttributes(layoutParams);

        String notice = getIntent().getStringExtra("notice");
        String btnText = getIntent().getStringExtra("btnText");

        TextView button = findViewById(R.id.btn);
        if (!TextUtils.isEmpty(btnText))
            button.setText(btnText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        LinearLayout layout_dialog = findViewById(R.id.layout_dialog);
        layout_dialog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public static class Builder{
        private Context context;
        private String notice;
        private String btnText;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setNotice(String notice){
            this.notice = notice;
            return this;
        }

        public Builder setButtontext(String btnText){
            this.btnText = btnText;
            return this;
        }

        public void build(){
            if (context == null)
                throw new IllegalArgumentException("context must not be null");

            if (TextUtils.isEmpty(notice))
                return;

            Intent intent = new Intent(context, DialogThemeActivity.class);
            intent.putExtra("notice", notice);
            intent.putExtra("btnText", btnText);
            context.startActivity(intent);
        }
    }
}
