package bjy.edu.android_learn.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// TODO: 2020-01-15  
public class ThreadPoolUtil {
    private static ExecutorService executorService;

    public static void run(Runnable runnable){
        if (executorService == null){
            synchronized (ThreadPoolUtil.class){
                if (executorService == null){
                    Executors.newSingleThreadExecutor();
                    executorService = new ThreadPoolExecutor(0, 128, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }

        executorService.execute(runnable);
        executorService.submit(runnable);
    }
}
