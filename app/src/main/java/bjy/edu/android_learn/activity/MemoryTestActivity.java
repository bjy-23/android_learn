package bjy.edu.android_learn.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import bjy.edu.android_learn.R;

public class MemoryTestActivity extends AppCompatActivity {
    private Button btn_add;
    private EditText et_memory;
    private List<MemoryBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_test);
        et_memory = findViewById(R.id.et_memory);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_memory.getText().toString();
                int value = Integer.parseInt(content);
                list.add(new MemoryBean(value));
            }
        });
    }

    class MemoryBean{
        int value;
        byte[] bytes;

        public MemoryBean(int value){
            this.value = value;
            bytes = new byte[ value * 1024 * 1024];
            for (int i=0; i<bytes.length; i++){
                bytes[i] = 1;
            }
        }
    }
}