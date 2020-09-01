package bjy.edu.android_learn;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.view.Gravity;
import android.widget.Toast;

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
        MultiDex.install(this);

//        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());

        //判断app是否在前台
        //api >= 14
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                System.out.println("ActivityLifecycleCallbacks :  app在前台运行  yes");
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
//                System.out.println("ActivityLifecycleCallbacks :  app在前台运行  no");
//                Toast toast= Toast.makeText(getApplicationContext(), "\"浦发手机银行\"仍在后台运行,如需退出,请进入浦发手机银行,点击\"安全退出\"按钮并使用手机返回键退出应用. ", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.BOTTOM, 0, 240);
//                toast.show();
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
//
//        Hawk.init(this).build();
//        SpUtil.init(this);

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
