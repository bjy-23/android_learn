package bjy.edu.android_learn.edittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

import bjy.edu.android_learn.R;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
