package bjy.edu.android_learn.thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import bjy.edu.android_learn.R;

public class ThreadActivity extends AppCompatActivity {
    private int times = 10000;
    private int count = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

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


       // todo 测试 java.lang.InternalError: Thread starting during runtime shutdown
       new ThreadNow().start();
       android.os.Process.killProcess(android.os.Process.myPid());
    }

    class ThreadNow extends Thread{
        @Override
        public void run() {
            super.run();
            for (int i=0; i<1000; i++){
                new ThreadNow().start();
            }
        }
    }
}
