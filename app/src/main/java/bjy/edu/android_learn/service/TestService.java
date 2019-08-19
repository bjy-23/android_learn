package bjy.edu.android_learn.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.broadcastreceiver.TestReceiver;
import bjy.edu.android_learn.dialog.DialogThemeActivity;

import static android.app.Notification.VISIBILITY_SECRET;

public class TestService extends Service {
    TestReceiver testReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("TestService", "onCreate");

        testReceiver = new TestReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(testReceiver, intentFilter);
//        startForeground(1, getNotificationBuilder().build());
        startForeground(1, new Notification());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new DialogThemeActivity.Builder(this)
                .setNotice("当前网络不可用!")
                .setButtontext("我知道了")
                .build();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TestService", "onDestroy");

        unregisterReceiver(testReceiver);
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

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id");
        builder.setContentTitle("快涨");
        builder.setContentText("网络监听服务");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
//        builder.setContentIntent(pendingIntent);

        builder.setTicker("bbbjy"); // todo 华为NXT8.0暂时无效
//        builder.setWhen(System.currentTimeMillis()); // 默认值就是 System.currentTimeMillis()
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        return builder;
    }
}
