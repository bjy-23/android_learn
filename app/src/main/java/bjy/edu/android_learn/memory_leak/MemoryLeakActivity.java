package bjy.edu.android_learn.memory_leak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import bjy.edu.android_learn.R;

public class MemoryLeakActivity extends AppCompatActivity {
    private static View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak);

        view = new View(this);
    }
}
