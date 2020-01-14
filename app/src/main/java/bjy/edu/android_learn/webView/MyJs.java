package bjy.edu.android_learn.webView;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class MyJs {
    private Activity activity;

    public MyJs(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void clickNow(){
        Log.i("MyJs", "clickNow: " + Thread.currentThread().getName());
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(activity)
                        .setTitle("点我")
                        .setMessage("来自html的点击事件")
                        .show();
            }
        });
    }

    @JavascriptInterface
    public void clickNow2(String msg){
        Log.i("MyJs", "clickNow2: msg: " + msg + Thread.currentThread().getName());
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(activity)
                        .setTitle("点我")
                        .setMessage("来自html的点击事件")
                        .show();
            }
        });
    }

    @JavascriptInterface
    public void alertinfo(final String confirmStr, final String callbackFun) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, confirmStr, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
