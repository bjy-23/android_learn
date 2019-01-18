package bbbjy.edu.bbbjy_support.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import bbbjy.edu.bbbjy_support.R;


public class LoadingDialog {
    private static final String TAG = "LoadingDialog";
    private static Dialog loadingDialog;
    private static final String DEFAULT_MESSAGE = "请稍等...";

    public static void show(Activity activity) {
        show(activity, DEFAULT_MESSAGE, true);
    }

    public static void show(Activity activity, String message){
        show(activity, message, true);
    }

    public static void show(Activity activity, boolean cancelable){
        show(activity, DEFAULT_MESSAGE, cancelable);
    }

    //参数多的话改用builder模式
    public static void show(Activity activity, String message, boolean cancelable){
        if (loadingDialog == null){
            loadingDialog = new Dialog(activity, R.style.loading_dialog);
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null);
            loadingDialog.setContentView(view);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            Animation runAnim = AnimationUtils.loadAnimation(activity, R.anim.loading_dialog);
            img.startAnimation(runAnim);
            TextView tv = (TextView) view.findViewById(R.id.tv);
            tv.setText(message);
            loadingDialog.setCancelable(cancelable);

            //宽高设置
            WindowManager wm = activity.getWindowManager();
            Display display = wm.getDefaultDisplay();
            WindowManager.LayoutParams lp = loadingDialog.getWindow().getAttributes();
            //黄金比例 0.618
            lp.width = (int) (display.getWidth() * 0.618f);
            lp.height = (int) (lp.width * (1f - 0.618f));
            loadingDialog.getWindow().setAttributes(lp);
            loadingDialog.show();

            //cancelable为true的情况下，手动点击导致消失，则置空
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Log.i(TAG, "dismiss");
                    loadingDialog = null;
                }
            });

        }
    }

    public static void dismiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
