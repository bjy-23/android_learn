package edu.test.lib_skin;

import android.app.Application;

import skin.support.SkinCompatManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        SkinCompatManager.withoutActivity(this).loadSkin();
    }
}
