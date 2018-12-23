package bjy.edu.android_learn;

import android.app.Application;
import android.content.Intent;

import com.orhanobut.hawk.Hawk;
import com.squareup.leakcanary.LeakCanary;

import bjy.edu.android_learn.service.TestService;
import bjy.edu.android_learn.util.SpUtil;


/**
 * Created by sogubaby on 2018/6/11.
 */

public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        Hawk.init(this).build();
        SpUtil.init(this);

//        LeakCanary.install(this);
//        startForegroundService(new Intent(this, TestService.class));
//        startService(new Intent(this, TestService.class));
    }

    public static App getInstance(){
        return app;
    }
}
