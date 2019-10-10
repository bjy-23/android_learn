package bjy.edu.android_learn.util;

import android.util.Log;

public class LogHelper {
    boolean showable = true;

    public LogHelper() {
        this(true);
    }

    public LogHelper(boolean showable) {
        this.showable = showable;
    }

    public void e(String tag, String msg){
        if (showable)
            Log.e(tag, msg);
    }
}
