package bjy.edu.android_learn.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bjy.edu.android_learn.R;

public class StackActivity extends AppCompatActivity {
    private int deep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);

        try {
            loopM();
        }catch (StackOverflowError e){
            System.out.println("最大栈深度" + deep);
        }{

        }
    }

    private void loopM(){
        deep++;

        loopM();
    }
}
