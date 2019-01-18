package bjy.edu.android_learn.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import bjy.edu.android_learn.App;

/**
 * Created by sogubaby on 2018/7/18.
 */

public class DisplayUtil {
    private static Context context = App.getInstance();

    @TargetApi(23)
    public static void setStatusBarColor(Activity activity, int statusBarColor, boolean light) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            //取消状态栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(statusBarColor);
            if (light) {
                //状态栏图标为黑色
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }

//            //让view不根据系统窗口来调整自己的布局
//            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
//            View mChildView = mContentView.getChildAt(0);
//            if (mChildView != null) {
//                ViewCompat.setFitsSystemWindows(mChildView, false);
//                ViewCompat.requestApplyInsets(mChildView);
//            }
//            //全屏，对actionBar无效
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            //设置系统状态栏处于可见状态
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }

    @TargetApi(21)
    public static void setStatusBarColor(Activity activity, int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            //取消状态栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(statusBarColor);
        }
    }

    /**
     * 获取设备唯一标识
     */
    public static String getDeviceId(Context context) {

        StringBuilder stringBuilder = new StringBuilder();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            stringBuilder.append(telephonyManager.getDeviceId());
        }

        //androidId
        //恢复出厂设置会重置
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        System.out.println("androidId   " + androidId);
        stringBuilder.append(androidId);

        //Serial Number
        String serial_number = Build.SERIAL;
        System.out.println("serial_number   " + serial_number);
        stringBuilder.append(serial_number);

        // TODO: 2019/1/11 md5
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(stringBuilder.toString().getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                //循环每个字符 将计算结果转化为正整数;
                int digestInt = digest[i] & 0xff;
                //将10进制转化为较短的16进制
                String hexString = Integer.toHexString(digestInt);
                //转化结果如果是个位数会省略0,因此判断并补0
                if (hexString.length() < 2) {
                    sb.append(0);
                }
                //将循环结果添加到缓冲区
                sb.append(hexString);
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    // TODO: 2019/1/16  
    public static String getIMEI(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null)
                return telephonyManager.getDeviceId();

            telephonyManager.getImei();
            telephonyManager.getMeid();
        }
        return "";
    }
}
