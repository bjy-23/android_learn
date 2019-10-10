package bjy.edu.android_learn.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bjy.edu.android_learn.R;

public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        int position = 1;
        switch (position){
            case 1:
                // Observable Observer
                Test.test1();
                break;
        }
    }
}
