package bjy.edu.android_learn.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import bjy.edu.android_learn.App;

public class AndroidUtil {
    private static Context context = App.getInstance();

    //获取剪切板的内容
    public static String getClipData(Context context){
        String content = "";
        //获取剪切板服务
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (mClipboardManager == null)
            return content;
        //剪切板是否有内容
        //对于android 10 以上的设备，应用必须处于焦点状态, 该方法才能返回true
        boolean hasPrimaryClip = mClipboardManager.hasPrimaryClip();
        if (!hasPrimaryClip)
            return content;
        ClipData clipData = mClipboardManager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            content = clipData.getItemAt(0).getText().toString();
        }

        return content;
    }
}
