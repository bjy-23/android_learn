package bjy.edu.android_learn.thread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import bjy.edu.android_learn.R;

public class ThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);


       new Thread(new FutureTask<String>(new Callable<String>() {
           @Override
           public String call() throws Exception {
               return null;
           }
       }));
    }
}
