package bjy.edu.android_learn.time;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import bjy.edu.android_learn.R;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 3000, 3000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        }, 3000, 2000);
    }
}
