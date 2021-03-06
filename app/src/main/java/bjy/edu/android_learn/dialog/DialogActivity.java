package bjy.edu.android_learn.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import bjy.edu.android_learn.MainActivity;
import bjy.edu.android_learn.R;
import bjy.edu.android_learn.util.ToastUtil;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);

        //style
//        final Dialog dialog = new Dialog(this, R.style.dialog);
//        dialog.setContentView(R.layout.in_box_dialog);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.getWindow().setDimAmount(0f);
//        dialog.show();
//
//
//        //宽度全屏
//        //1. style 里设置 android:windowBackground
//        //2. dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//
//
//        //获取dialog的高度 todo 通过window获取高度暂时获取不到
//        final View decorView = dialog.getWindow().getDecorView();
//        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (decorView.getHeight() > 0){
//                    decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//                    //修改 dialog 高度
//                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//                    lp.height = 600;
//                    dialog.getWindow().setAttributes(lp);
//                }
//            }
//        });

        TextView tv_1 = findViewById(R.id.tv_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.activities.get(0))
                        .setMessage("bbbjy")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

        //全局弹窗
        TextView tv_2 = findViewById(R.id.tv_2);
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23){
                    if (Settings.canDrawOverlays(getApplicationContext())){
                        AlertDialog dialog = new AlertDialog.Builder(MainActivity.activities.get(0))
                                .setMessage("bbbjy")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        if (Build.VERSION.SDK_INT >= 26){
                            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                        }else {
                            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        }

                        dialog.show();
                    }else {
                        ToastUtil.show("弹窗权限未开启！！");
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 10);
                    }
                }
            }
        });

        TextView tv_3 = findViewById(R.id.tv_3);
        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = MainActivity.activities.get(0);
                activity.startActivity(new Intent(activity, DialogThemeActivity.class));
            }
        });


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, 1);
    }
}
