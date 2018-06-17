package bjy.edu.android_learn;

import android.app.Application;

/**
 * Created by sogubaby on 2018/6/11.
 */

public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
    }

    public static App getInstance(){
        return app;
    }
}
