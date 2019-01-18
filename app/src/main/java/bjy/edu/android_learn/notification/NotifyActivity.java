package bjy.edu.android_learn.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import bjy.edu.android_learn.MainActivity;
import bjy.edu.android_learn.R;

import static android.app.Notification.VISIBILITY_SECRET;

public class NotifyActivity extends AppCompatActivity {
    NotificationManager notificationManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent();
        intent.setAction("bbbjy");
        Bundle bundle = new Bundle();
        bundle.putBoolean("hh", true);
        intent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        notificationManager.notify(1, getNotificationBuilder().build());
        Notification notification = getNotificationBuilder().build();
    }

    private NotificationCompat.Builder getNotificationBuilder() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();//是否绕过请勿打扰模式
            channel.enableLights(true);//闪光灯
            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            channel.setLightColor(Color.RED);//闪关灯的灯光颜色
            channel.canShowBadge();//桌面launcher的消息角标
            channel.enableVibration(true);//是否允许震动
            channel.getAudioAttributes();//获取系统通知响铃声音的配置
            channel.getGroup();//获取通知取到组
            channel.setBypassDnd(true);//设置可绕过  请勿打扰模式
            channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
            channel.shouldShowLights();//是否会有灯光

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id");
        builder.setContentTitle("新消息来了");
        builder.setContentText("周末到了，不用上班了");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        builder.setTicker("bbbjy"); // todo 华为NXT8.0暂时无效
//        builder.setWhen(System.currentTimeMillis()); // 默认值就是 System.currentTimeMillis()
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        return builder;
    }

}
