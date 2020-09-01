package bjy.edu.android_learn.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import bjy.edu.android_learn.widget.ViewActivity;

public class TestReceiver extends BroadcastReceiver {
    private static final String TAG = TestReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "intent action: " + intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            System.out.println("收到广播：SCREEN_ON");
//            context.startActivity(new Intent(context, ViewActivity.class));
        }else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){

        }


    }
}
