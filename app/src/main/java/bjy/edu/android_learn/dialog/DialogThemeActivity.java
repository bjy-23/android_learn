package bjy.edu.android_learn.dialog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class DialogThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_theme);

        String notice = getIntent().getStringExtra("notice");
        String btnText = getIntent().getStringExtra("btnText");

        TextView tv = findViewById(R.id.tv);
        tv.setText(notice);
        TextView button = findViewById(R.id.btn);
        if (!TextUtils.isEmpty(btnText))
            button.setText(btnText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
