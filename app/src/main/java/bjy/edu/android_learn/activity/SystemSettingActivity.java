package bjy.edu.android_learn.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import bjy.edu.android_learn.MainActivity;
import bjy.edu.android_learn.R;

public class SystemSettingActivity extends AppCompatActivity {
    private static final String TAG = "111222";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);

        int position = 1;
        switch (position){
            case 1:
                //打开系统读取pdf的应用
                test_1();
                break;
            case 0:
                //通知管理页面
                test_0();
                break;
        }
    }

    private void test_1(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            File file = new File("/storage/emulated/0/1/2020.pdf");
            Uri uri = null;
            if (Build.VERSION.SDK_INT >= 23){
                uri = FileProvider.getUriForFile(this, "bjy.edu.android_learn.fileProvider", file);
            }else {
                uri = Uri.fromFile(file);
            }

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(uri, "application/pdf");
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//这个flag很重要
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

            Intent intent = new Intent(Intent.ACTION_SEND); //分享
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(uri, "application/pdf");
            intent = Intent.createChooser(intent, "分享到");

            try{
                startActivity(intent);
            }catch (Exception e){
                Log.w(TAG, e);
            }

        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
        }
    }

    private void test_0(){
        //检测通知是否开启
        boolean openable = NotificationManagerCompat.from(this).areNotificationsEnabled();
        Log.i("111222", "通知开启： " + openable);

        //打开通知设置页面
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26){
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE,getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21){
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package",getPackageName(), null));
        }
        try{
            startActivity(intent);
        }catch (Exception e){
            Log.w(TAG, e);
        }

        //部分华为手机的通知页面（代码依然有问题，提示无权限）
//        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationSettingsActivity"));

    }
}