package bjy.edu.android_learn.thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import bjy.edu.android_learn.R;

public class ThreadActivity extends AppCompatActivity {

    private ThreadPoolExecutor executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        executorService = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executorService.allowCoreThreadTimeOut(true);
        final Handler handler = new Handler();

        Button btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("111222", String.format("btn_start onClick isShutdown[%s], isTerminated[%s]", String.valueOf(executorService.isShutdown()), String.valueOf(executorService.isTerminated())));
                for (int i=0; i<10; i++){
                    final int i_final = i;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Log.i("111222", "runnable_" + i_final + " start");

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Log.i("111222", "runnable_" + i_final + " done");
                        }
                    };
                    executorService.execute(runnable);

                    Runnable runnable2 = new Runnable() {
                        @Override
                        public void run() {
                            boolean isShutdown = executorService.isShutdown();
                            boolean isTerminated = executorService.isTerminated();
                            int poolSize = executorService.getPoolSize();
                            Log.i("111222", String.format("isShutdown[%s], isTerminated[%s],poolSize[%d]", isShutdown, isTerminated, poolSize));

                            if (!isShutdown || !isTerminated)
                                handler.postDelayed(this, 1000);
                        }
                    };
                    handler.post(runnable2);
                }
            }
        });

        Button btn_destroy = findViewById(R.id.btn_destroy);
        btn_destroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.shutdown();
                try {
                    Log.i("111222", "awaitTermination - 1");
                    executorService.awaitTermination(3, TimeUnit.SECONDS);
                    Log.i("111222", "awaitTermination - 2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        boolean isShutdown = executorService.isShutdown();
                        boolean isTerminated = executorService.isTerminated();
                        Log.i("111222", String.format("isShutdown[%s], isTerminated[%s]", String.valueOf(isShutdown), String.valueOf(isTerminated)));

                        if (!isShutdown || !isTerminated)
                            handler.postDelayed(this, 1000);
                    }
                };
                handler.post(runnable);
            }
        });

    }
}
