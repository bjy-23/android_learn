package bjy.edu.android_learn;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import bjy.edu.android_learn.activity.ContactActivity;
import bjy.edu.android_learn.util.FileUtil;
import bjy.edu.android_learn.widget.FloatingOnTouchListener;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "111222";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "[requestCode] " + requestCode + " [resultCode] " + resultCode);
        switch (requestCode){
            case 1000:
                if (Build.VERSION.SDK_INT >= 23){
                    if (Settings.System.canWrite(this)){
                        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                    }
                }
                break;
            case 10001:
                if (data == null)
                    return;
                Uri uri = data.getData();
                if (uri != null){
                    Log.i("111222", "uri: " + uri.toString());
                    Log.i("111222", "uri - filePath " + FileUtil.getByUri(uri));

//                    File file = FileUtil.UriToFile(uri);
//                    if (file != null)
//                        Log.i("111222", "uriToFile: " + file.getAbsolutePath());
                }
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //全局弹窗
        // TODO: 2020/8/21  子View不拦截touchEvent
        // TODO: 2020/8/21  OnTouchListener 为什么要用 getRawX(), 用GetX()会导致滑动迟钝
        // TODO: 2020/8/24 点击事件和滑动事件如何统一起来 
//        if(Build.VERSION.SDK_INT >= 23){
//            if (Settings.canDrawOverlays(this)){
//                WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                if (Build.VERSION.SDK_INT >=26){
//                    layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//                }else {
//                    layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//                }
//                layoutParams.format = PixelFormat.RGBA_8888;
//                layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.x = 300;
//                layoutParams.y = 300;
//
//                View view = LayoutInflater.from(this).inflate(R.layout.floating_view_1, null);
//                Button btn = view.findViewById(R.id.btn);
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.i(TAG, "btn onClick");
//                        Toast.makeText(TestActivity.this, "btn onClick", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                TextView tv_delete = view.findViewById(R.id.tv_delete);
//                tv_delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        windowManager.removeView(view);
//                    }
//                });
//                view.setOnTouchListener(new FloatingOnTouchListener(new FloatingOnTouchListener.MoveListener() {
//                    @Override
//                    public void move(int offsetX, int offsetY) {
//                        layoutParams.x += offsetX;
//                        layoutParams.y += offsetY;
//                        windowManager.updateViewLayout(view, layoutParams);
//                    }
//                }));
//                windowManager.addView(view, layoutParams);
//            }else {
//                //申请权限
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                intent.setData(Uri.parse("package:" + getPackageName()));
//                startActivity(intent);
//            }
//        }

//        if (ActivityCompat.checkSelfPermission(TestActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            File file
//        }else {
//            ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
//        }

//        final float brightness = getWindow().getAttributes().screenBrightness;
//        Log.i(TAG, "screenBrightness " + brightness);
//        Log.i(TAG, "Setting screenBrightness " + Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -999));
//        final WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        if (brightness < 0.3f){
//            layoutParams.screenBrightness = 0.3f;
//            getWindow().setAttributes(layoutParams);
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    layoutParams.screenBrightness = brightness;
//                    getWindow().setAttributes(layoutParams);
//                }
//            }, 10000);
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.i(TAG, "screenBrightness " + getWindow().getAttributes().screenBrightness);
//                    Log.i(TAG, "Setting screenBrightness " + Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -999));
//                }
//            }, 13000);
//        }
//        if (true)
//            return;
//
//
//
//        //测试屏幕亮度自动调节
//        // 需要权限 write_setting
//        try {
//            int mode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
//            Log.i(TAG, "mode " + mode);
//            //SCREEN_BRIGHTNESS 的默认默认范围是 0——255，但部分系统会修改这个值！！！
//            //在android 6.0及以后，WRITE_SETTINGS权限的保护等级已经由原来的dangerous升级为signature，这意味着我们的APP需要用系统签名或者成为系统预装软件
//            // 才能够申请此权限, 并且还需要提示用户跳转到修改系统的设置界面去授予此权限
//            Log.i(TAG, "screenBrightness  " + Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 125));
//
//            if (true)
//                return;
//
//            if (Build.VERSION.SDK_INT >= 23){
//                if (!Settings.System.canWrite(this)){
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                    intent.setData(Uri.parse("package:" + getPackageName()));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    // TODO: 2020/7/17  onActivityResult 的回调时机不对 ？？？
//                    startActivityForResult(intent, 1000);
//                }else {
////                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
////                    intent.setData(Uri.parse("package:" + getPackageName()));
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    startActivityForResult(intent, 1001);
//
//                    //修改系统的亮度，这个会影响整个手机的设置
//                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 2000);
//                    //修改系统亮度的模式
////                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
////                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
//                }
//            }else {
//                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
//            }
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }


        //测试防截屏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
//                Log.i(TAG, "防截屏去除");
//            }
//        }, 15000);


        //自定义toast
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Toast toast = new Toast(TestActivity.this);
//                final View view = LayoutInflater.from(TestActivity.this).inflate(R.layout.toast_1, null, false);
//                TextView tv = view.findViewById(R.id.tv);
//                tv.setText("在一般的android开发中我们一般弹出一些提示信息，例如 已打开蓝牙，wifi之类的提示，我们都是会选择Toast进行弹出。今天我们的客户提出们应用弹出提示太小，用户不注意的情况下，容易被忽略掉，要弹出的宽度填充整个屏幕，首先想到是不是需要自定义");
//                tv.setMaxEms(10);
//                // TODO: 2019-10-25 toast的最大宽度怎么设置
//                toast.setView(view);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.setDuration(Toast.LENGTH_LONG);
//
//                view.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
//                    @Override
//                    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
//                        Log.i("view", "onGlobalFocusChanged");
//                        Log.i("view", "width: " + view.getWidth());
//                    }
//                });
//                toast.show();
//            }
//        }, 1500);


//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("bjy", "柏建宇");
//            jsonObject.put("zhongshan", "中山公园");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        StringBuilder bodyParam = new StringBuilder();
//        Iterator<String> iterator = jsonObject.keys();
//        while (iterator.hasNext()){
//            String key = iterator.next();
//            // TODO: 2020/6/30 charset
//            bodyParam.append(key).append("=").append(URLEncoder.encode(jsonObject.optString(key)));
//        }
//        Log.i("111222", "bodyParam: " + bodyParam.toString());



//        String s = "spdbbank://wap.spdb.com.cn/pay?Plain=fff&Signature=fff";
//        Uri uri = Uri.parse(s);
//        System.out.println("uri: " + s);
//        System.out.println("scheme: " + uri.getScheme());
//        System.out.println("host: " + uri.getHost());
//        System.out.println("path: " + uri.getPath());
    }

    private static String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            if (cellValue == null)
                return value;
            Log.i("111222", "cellValue.getCellType() : " + cellValue.getCellType());
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
                    break;
            }
        } catch (NullPointerException e) {
            /* proper error handling should be here */
            Log.w("111222 catch ", e);
        }
        return value;
    }

    private void test_0(){

    }
}