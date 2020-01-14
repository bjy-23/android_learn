package bjy.edu.android_learn.edittext;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bjy.edu.android_learn.R;

/**
 * textview\edittext
 */
public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        method_1();

//        method_2();

        method_3();
    }

    private void method_1(){
        setContentView(R.layout.activity_edit_text);

        // 设置输入框设置
        // 对应manefist中 activity的标签 android:windowSoftInputMode="adjustPan"
        //
        // (adjust : 设置布局和输入框的位置关系)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // (state : 输入框的状态)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        EditText editText = findViewById(R.id.edit_text);

        editText.setText("bbbjy");
        // 输入限制
        //数字 0-9
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        // 小数
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        //设置光标位置
        editText.setSelection(0);
        editText.setSelection(0, 2);

        //文字改变检测 - (例如： 输入字数长度不超过10； 输入数字< 5000)
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void method_2(){
        setContentView(R.layout.activity_textview);

        TextView tv_1 = findViewById(R.id.tv_1);
        SpannableString spannableString = new SpannableString("默认开通小额免密服务，点此设置");

        //设置下划线
//        spannableString.setSpan(new UnderlineSpan(), 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv_1.setText(spannableString);


        //ClickableSpan自带下划线
        //xml设置下划线及点击区域文字颜色 android:textColorLink="#000000"
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(EditTextActivity.this, "点我", Toast.LENGTH_SHORT).show();
            }

            //可以不重写
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.YELLOW); //点击区域的文字颜色  对应xml android:textColorLink
                ds.setUnderlineText(false);//false:取消下划线
            }
        }, 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_1.setMovementMethod(LinkMovementMethod.getInstance());
        tv_1.setHighlightColor(Color.TRANSPARENT); //消除点击后的高亮显示
        tv_1.setText(spannableString);

    }

    public void method_3(){
        setContentView(R.layout.activity_edit_text_3);


    }

}
