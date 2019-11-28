package bjy.edu.android_learn.zxing;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import bjy.edu.android_learn.R;

public class ZxingActivity extends AppCompatActivity {
    private static final String TAG = ZxingActivity.class.getSimpleName();

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);

//        surfaceView = findViewById(R.id.surfaceview);
//        surfaceHolder = surfaceView.getHolder();
//        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                surfaceHolder = holder;
//
//                initCamera();
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//
//            }
//        });

        Display display = ZxingActivity.this.getWindowManager().getDefaultDisplay();
        int width = display.getWidth() * 2 / 3;
        int height = width + 100;
        String code = "hQVDUFYwMWFRTwigAAADMwEBAlcMYiUhFXMJCELVASIBYzOCAgAAnxARBwEBA6AAAAEINDk5OTAwMTefJghW+c2mWevp0p8nAYCfNgJMm583BGtrMwhfNAEB";
        String code2 = "6265560026631602644";

        Bitmap qrCodeBitmap = QRAndBarCodeUtil.createQRImage(ZxingActivity.this, code, width, height);
        Bitmap qrCodeBitmap2 = QRAndBarCodeUtil.createQRImage(ZxingActivity.this, code2, width, height);
        Bitmap qrCodeBitmap3 = QRAndBarCodeUtil.createQRImage(ZxingActivity.this, code2, width, height);
    }

    private void initCamera(){
        if (ActivityCompat.checkSelfPermission(ZxingActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Camera camera = Camera.open();
            try {
                camera.setPreviewDisplay(surfaceHolder);

                //屏幕旋转90度，图像展示默认是横屏的
                camera.setDisplayOrientation(90);
                Camera.Parameters parameters = camera.getParameters();
                String previewFormat = parameters.get("preview-format");
                Log.i(TAG, "camera preview-format: " + previewFormat);
                String previewSizeValue = parameters.get("preview-size-values");
                Log.i(TAG, "camera preview-size-values: " + previewSizeValue);
                //第一个参数width对应的是Y轴方向的预览尺寸；第二个参数height对应X轴方向的预览尺寸
                //参数值必须是对应preview-size-values中定义的
                //如果这两个值和显示区域的宽高比不一致，图像显示会变形
//                parameters.setPreviewSize(1920, 1080);
                camera.setParameters(parameters);

                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            ActivityCompat.requestPermissions(ZxingActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
        }
    }
}
