package bjy.edu.android_learn.time;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import bjy.edu.android_learn.R;

public class TimerActivity extends AppCompatActivity {
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer.purge();
//        timer.cancel();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("TimerTask1", "run  " + System.currentTimeMillis());
            }
        }, 0, 1000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.e("TimerTask2", "run  " + System.currentTimeMillis());
            }
        }, 0, 1000);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.e("TimerTask3", "run  " + System.currentTimeMillis());
            }
        }, calendar.getTime(), 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        timer.cancel();
    }
}
