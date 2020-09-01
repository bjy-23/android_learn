package bjy.edu.android_learn.util;

import android.util.Log;

public class LogUtil {
    private boolean logEnable;
    private String tag;

    public LogUtil(boolean logEnable, String tag) {
        this.logEnable = logEnable;
        this.tag = tag;
    }

    public void log(String msg){
        if (!logEnable)
            return;

        Log.i(tag, msg);
    }
}
