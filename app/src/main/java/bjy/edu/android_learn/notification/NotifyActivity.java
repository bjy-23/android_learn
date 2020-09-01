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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import bjy.edu.android_learn.MainActivity;
import bjy.edu.android_learn.R;

import static android.app.Notification.VISIBILITY_SECRET;

public class NotifyActivity extends AppCompatActivity {
    NotificationManager notificationManager;
    PendingIntent pendingIntent;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Button btn_not = findViewById(R.id.btn_not);
        btn_not.setText("通知");
        btn_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationManager.notify(index, getNotificationBuilder().build());
                index++;

//                notification_1();
            }
        });

//        Intent intent = new Intent();
//        intent.setAction("bbbjy");
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("hh", true);
//        intent.putExtras(bundle);
//        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    }

    //这种写法兼容低版本
    private NotificationCompat.Builder getNotificationBuilder() {
        String channelId = "channel_id"+ (index % 2);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(channelId, "channel_name" + (index % 2),
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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setContentTitle("新消息来了_" + index);
        builder.setContentText("周末到了，不用上班了---" + index);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        builder.setTicker("bbbjy"); // todo 华为NXT8.0暂时无效
//        builder.setWhen(System.currentTimeMillis()); // 默认值就是 System.currentTimeMillis()
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        return builder;
    }

    //android 8 之前的写法；已作废
    private void notification_1(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotifyActivity.this)
                .setAutoCancel(true)
                .setContentTitle("你好")
                .setSmallIcon(getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setOngoing(false);
        Notification notification = builder.getNotification();
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, notification);
    }

}
