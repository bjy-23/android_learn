package bjy.edu.android_learn;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //屏幕设置
        method_1();

        //屏幕亮度调节
//        method_2();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Settings.System.canWrite(this)){
            Log.i("Settings.System", "canWrite");
        }
    }

    public void method_1(){
        setContentView(R.layout.activity_full_screen);

        //隐藏statusBar
        TextView tv_1 = findViewById(R.id.tv_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });

        //显示statusBar
        TextView tv_2 = findViewById(R.id.tv_2);
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });

        //activity 对应的布局从statusBar左上方开始，statusBar覆盖在上
        TextView tv_5 = findViewById(R.id.tv_5);
        tv_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        });

        //回到正常布局，status在上，activity布局在下
        TextView tv_6 = findViewById(R.id.tv_6);
        tv_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });

        TextView tv_7 = findViewById(R.id.tv_7);
        tv_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //api 23 以下没效果
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        });

        //横屏
        TextView tv_3 = findViewById(R.id.tv_3);
        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        //竖屏
        TextView tv_4 = findViewById(R.id.tv_4);
        tv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });

    }

    public void method_2(){
        setContentView(R.layout.activity_full_screen);

//        修改屏幕亮度包含两种：
//        1.修改APP界面屏幕亮度，不会影响其他APP; 并且也只对当前Activity有效果
//        2.修改系统 Settings 中屏幕亮度，影响所有APP.


        TextView tv_8 = findViewById(R.id.tv_8);
        tv_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window window = getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.screenBrightness = 0.8f;
                window.setAttributes(lp);
            }
        });

        TextView tv_9 = findViewById(R.id.tv_9);
        tv_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window window = getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.screenBrightness = 0.1f;
                window.setAttributes(lp);
            }
        });
    }
}
