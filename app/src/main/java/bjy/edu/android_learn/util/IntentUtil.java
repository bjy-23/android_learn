package bjy.edu.android_learn.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IntentUtil {
    private static final String TAG = "111222";

    public static void startActivity(Context context, Intent intent){
        try {
            context.startActivity(intent);
        }catch (Exception e){
            Log.w(TAG, e);
        }
    }
}


