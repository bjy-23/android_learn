package bjy.edu.android_learn.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.util.ReflectUtil;

public class CameraCaptureActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private WebView webView;

    private Camera camera;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_capture);

        surfaceView = findViewById(R.id.surfaceview);
        webView = findViewById(R.id.webView);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            return;
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String url = "https://www.baidu.com";
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    if (webView.canGoBack())
                        webView.goBack();
                    else
                        finish();

                    return true;
                }
                return false;
            }
        });

       handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    openCamera();
                    lunxun();
                }catch (Exception e){

                }
            }
        }, 2000);
    }

    private void openCamera() {
        try {
            int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            camera = Camera.open(cameraId);

            camera.setPreviewDisplay(surfaceView.getHolder());
            Camera.Parameters parameters = camera.getParameters();
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) ReflectUtil.getFiled(parameters, "mMap");
            parameters.set("flash-value", 2);
            parameters.set("flash-mode", "off");
            parameters.set("zoom", "2.7");
            parameters.set("taking-picture-zoom", 27);
            camera.setDisplayOrientation(90);
            camera.setParameters(parameters);
//            camera.startPreview();

        } catch (Exception e) {

        }
    }

    private void lunxun(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this::run, 2000);
                camera.startPreview();
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                File dir = CameraCaptureActivity.this.getExternalFilesDir("camera_bjy");
                                File file = new File(dir, System.currentTimeMillis() + "_.jpg");
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                    fileOutputStream.write(data);
                                    fileOutputStream.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
            }
        };

       handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
    }
}