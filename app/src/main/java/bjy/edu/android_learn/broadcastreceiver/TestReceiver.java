package bjy.edu.android_learn.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import bjy.edu.android_learn.widget.ViewActivity;

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            System.out.println("收到广播：SCREEN_ON");
//            context.startActivity(new Intent(context, ViewActivity.class));
        }
    }
}
