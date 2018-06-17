package bjy.edu.android_learn.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import bjy.edu.android_learn.App;

/**
 * Created by sogubaby on 2018/5/21.
 */

public class ToastUtil {

    public static void show(Context context, String message) {
        if (context != null) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (!activity.isFinishing()) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //使用application的Context也可以显示
    public static void show(String message){
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
