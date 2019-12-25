package bjy.edu.android_learn;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

import bjy.edu.android_learn.util.DisplayUtil;
import bjy.edu.android_learn.util.SpUtil;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by sogubaby on 2018/6/11.
 */

public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

//        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());

        Hawk.init(this).build();
        SpUtil.init(this);

//        LeakCanary.install(this);
//        startForegroundService(new Intent(this, TestService.class));
//        startService(new Intent(this, TestService.class));

        //极光推送
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

    public static App getInstance(){
        return app;
    }
}
