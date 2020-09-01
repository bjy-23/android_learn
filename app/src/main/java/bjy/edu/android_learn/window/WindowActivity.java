package bjy.edu.android_learn.window;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class WindowActivity extends AppCompatActivity {
    private static final String TAG = WindowActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        Log.i(TAG, "Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 23){
            if (!Settings.canDrawOverlays(this)){
                Log.i(TAG, "Settings.canDrawOverlays: false");
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }else {
                Log.i(TAG, "Settings.canDrawOverlays: true");

                addWindow_1();
            }
        }else {

        }
    }

    private void addWindow_1(){
        TextView textView = new TextView(this);
        textView.setText("柏建宇");
        textView.setTextSize(28);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        if (Build.VERSION.SDK_INT >= 26)
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        WindowManager windowManager = getWindowManager();
        windowManager.addView(textView, layoutParams);
    }
}
