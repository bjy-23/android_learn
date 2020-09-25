package bjy.edu.android_learn.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.util.BitmapUtil;
import bjy.edu.android_learn.util.CameraUtil;
import bjy.edu.android_learn.util.ReflectUtil;
import io.reactivex.functions.Consumer;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraActivity extends AppCompatActivity {
    private static final String TAG = CameraActivity.class.getSimpleName();
    private CameraManager cameraManager;
    private String cameraId;
    private Camera camera;

    RxPermissions rxPermissions;

    private boolean cameraEnable;
    private boolean storageEnable;
    public static final int REQUEST_TAKE_PHOTO = 1001;
    public static final int REQUEST_READ_PHOTO = 1002;

    private File imgFile;
    private SurfaceView surfaceView;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd_HH-mm-ss", Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rxPermissions = new RxPermissions(this);

//        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()){
//            String[] names = cursor.getColumnNames();
//            System.out.println("names: " + Arrays.asList(names).toString());
//        }

        Log.i(TAG, "hasSystemFeature camera : " + getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
        Log.i(TAG, "hasSystemFeature camera.any : " + getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY));

        int position = 2;
        switch (position) {
            case 1:
                //开启闪光灯测试
                method_1();
                break;
            case 2:
                //surfaceview （把相机的图像投射到surfaceview上）
                method_2();
                break;
            case 3:
                //启动相机拍照
                method_3();
        }

        //不可以通过
//        Log.i(TAG, "context.getFilesDir(): " + CameraActivity.this.getFilesDir());
//        Log.i(TAG, "Environment.getDownloadCacheDirectory(): " + Environment.getExternalStorageDirectory().getPath());
        //可以通过'文件管理'访问，但是app被卸载也会被删除
//        Log.i(TAG, "context.getExternalFilesDir(Environment.DIRECTORY_PICTURES): " + CameraActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
//        Log.i(TAG, "context.getExternalCacheDir(): " + CameraActivity.this.getExternalCacheDir());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Log.i(TAG, "data.getData(): " + uri);
                Uri uri2 = Uri.parse(imgFile.toString());
                Log.i(TAG, "imgFile: " + uri2);
                Log.i("file 大小", imgFile.length() / 1024 + "K");
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getPath());
                Log.i("bitmap 像素：", bitmap.getWidth() + "*" + bitmap.getHeight() + "    大小：" + bitmap.getByteCount() / 1024 + "K");
                Bitmap bitmap2 = BitmapUtil.decodeBitmap(imgFile.getPath(), 500, 500);
                Log.i("bitmap2 像素：", bitmap2.getWidth() + "*" + bitmap2.getHeight() + "    大小：" + bitmap2.getByteCount() / 1024 + "K");
            }
        } else if (requestCode == REQUEST_READ_PHOTO) {
            if (resultCode == RESULT_OK && data != null) {
                Uri uri = data.getData();
                Log.i("112233", "uri: " + uri.toString());
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Log.i("bitmap 像素：", bitmap.getWidth() + "*" + bitmap.getHeight() + "    大小：" + bitmap.getByteCount() / 1024 + "K");
                    File file = BitmapUtil.bitmapToFile(CameraActivity.this, bitmap);
                    Log.i("fiel 大小：", file.length() / 1024 + "K");
                    FileInputStream fileInputStream = new FileInputStream(file);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int count = -1;
                    while ((count = fileInputStream.read(bytes)) != -1) {
                        byteArrayOutputStream.write(bytes, 0, count);
                    }
                    fileInputStream.close();
                    String ss = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    Log.i("112233", ss.length() + "");
//                    Bitmap bitmap2 = BitmapUtil.decodeBitmap(inputStream, 500, 500);
//                    int inSampleSize = BitmapUtil.calculateInSampleSize(bitmap, 500, 500);
//                    Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / inSampleSize, bitmap.getHeight() / inSampleSize, true);
//                    Log.i("bitmap2 像素：", bitmap2.getWidth() + "*" + bitmap2.getHeight() + "    大小：" + bitmap2.getByteCount() / 1024 + "K");
//                    File file2 = BitmapUtil.bitmapToFile(CameraActivity.this, bitmap2);
//                    Log.i("fiel2 大小：", file2.length() / 1024 + "K");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void method_1() {
        setContentView(R.layout.activity_camera);

        TextView tv_1 = findViewById(R.id.tv_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CameraUtil.openFlashlight();


//                cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//                try {
//                    String[] ids = cameraManager.getCameraIdList();
//                    for (String id : ids) {
//                        CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(id);
//                        //是否包含闪光灯
//
//                        if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
////                            if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK){
//                            cameraId = id;
//                            cameraManager.setTorchMode(id, true);
////                            }
//                        }
//                    }
//                } catch (CameraAccessException e) {
//
//                }


                if (camera == null)
                    camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                Log.i("FlashMode", parameters.getFlashMode());
                if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH))
                    return;
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
            }
        });


        TextView tv_2 = findViewById(R.id.tv_2);
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                CameraUtil.closeFlashLight();


//                try{
//                    cameraManager.setTorchMode(cameraId, false);
//                }catch (CameraAccessException e){
//
//                }


                if (camera == null)
                    return;
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.release();
                camera = null;
            }
        });
    }

    private int times = 5;

    private void method_2() {
        setContentView(R.layout.activity_camera_2);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            return;
        }

        surfaceView = findViewById(R.id.surfaceview);
        //隐藏预览
//        surfaceView.setAlpha(.9f);
        Button btn_camera_on = findViewById(R.id.btn_camera_on);
        btn_camera_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (true) {
                    openCamera();
                } else {
                    openCamera2();
                }

                //连续拍照
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            times--;
                            if (times > 0) {
                                new Handler().postDelayed(this::run, 5000);
                            }
                            camera.startPreview();
                            camera.takePicture(null, null, new Camera.PictureCallback() {
                                @Override
                                public void onPictureTaken(byte[] data, Camera camera) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.i(TAG, "onPictureTaken jpeg");
                                            File dir = CameraActivity.this.getExternalFilesDir("camera_bjy");
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
                                            Log.i(TAG, "onPictureTaken jpeg  finish");

                                        }
                                    }).start();
                                }
                            });

                        }
                    };
                    new Handler().postDelayed(runnable, 5000);
            }
        });
    }

    private void openCamera() {
        try {

            //默认打开后置
//                Camera camera = Camera.open();

            int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
//                    int cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            camera = Camera.open(cameraId);

            camera.setPreviewDisplay(surfaceView.getHolder());
            Camera.Parameters parameters = camera.getParameters();
            Log.i(TAG, "[parameters.getPreviewFormat] " + parameters.getPreviewFormat());
            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) ReflectUtil.getFiled(parameters, "mMap");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Log.i(TAG, "[parameters] " + entry.getKey() + " : " + entry.getValue());
            }
            parameters.set("flash-value", 2);
            parameters.set("flash-mode", "off");
            parameters.set("zoom", "2.7");
            parameters.set("taking-picture-zoom", 27);
            camera.setDisplayOrientation(90);
            camera.setParameters(parameters);
            camera.startPreview();

            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {

                }
            });
        } catch (Exception e) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCamera2() {
        if (Build.VERSION.SDK_INT < 21)
            return;
        try {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            //获取摄像头列表
            String cameraIdChecked = "";
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                Integer facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                Log.i(TAG, "[cameraId] " + cameraId + " [facing] " + facing);
                //选取后置摄像头
                if (facing != null && facing == CameraMetadata.LENS_FACING_BACK) {
                    cameraIdChecked = cameraId;
                }
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraManager.openCamera(cameraIdChecked, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {

                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {

                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void method_3() {
        setContentView(R.layout.activity_camera_3);

        TextView tv_start_camera = findViewById(R.id.tv_start_camera);
        tv_start_camera.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {

                // 1.申请相机权限和存储权限
                // TODO: 2019-11-19 Android Q 适配
                if (Build.VERSION.SDK_INT >= 29) {
                    String[] permissions = new String[]{Manifest.permission.CAMERA};
                    rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
//                                File dir = CameraActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//                                takePhoto(dir);

                                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
                                Log.i(TAG, "uri : " + uri.toString());
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(intent, 100);
                                }
                            }
                        }
                    });
                } else {
                    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
                    rxPermissions.requestEach(permissions)
                            .subscribe(new Consumer<Permission>() {
                                @Override
                                public void accept(Permission permission) throws Exception {
                                    if (permission.name.equals(Manifest.permission.CAMERA)) {
                                        if (permission.granted) {//获得权限
                                            cameraEnable = true;
                                        } else if (permission.shouldShowRequestPermissionRationale) {//禁止权限
                                            cameraEnable = false;
                                        } else {// 禁止权限并不再访问
                                            cameraEnable = false;
                                            // TODO: 2019-10-17 需要提示用户打开相机权限
                                        }
                                    } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        if (permission.granted) {
                                            storageEnable = true;
                                        } else if (permission.shouldShowRequestPermissionRationale) {
                                            storageEnable = false;
                                        } else {
                                            storageEnable = false;
                                            // TODO: 2019-10-17 需要提示用户打开存储权限
                                        }
                                    }

                                    // 只有相机权限和存储权限都开启才能拍照并保存
                                    if (cameraEnable && storageEnable) {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd_HH-mm-ss", Locale.CHINA);

                                        // todo 这个路径如何访问 ？？？
//                                    File file = new File(Environment.DIRECTORY_PICTURES, simpleDateFormat.format(new Date()) + ".jpg");

                                        //todo Environment定义的外存目录可能会被删除,且无法创建;      so, why ？？？
//                                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                                        File dir = new File(Environment.getExternalStorageDirectory(), CameraActivity.this.getPackageName() + "/Pictures");

                                        if (!dir.exists()) {
                                            boolean dirCan = dir.mkdirs();
                                            if (!dirCan) {
                                                Log.i("dir mk fail: ", dir.toString());
                                                return;
                                            }
                                        }

                                        takePhoto(dir);

                                        //使用rxpermission请求权限会依次发出
                                        //防止多次调用
                                        cameraEnable = false;
                                        storageEnable = false;
                                    }
                                }
                            });
                }
            }
        });


        TextView tv_start_gallery = findViewById(R.id.tv_start_gallery);
        tv_start_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限申请
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
////
//                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(intent, REQUEST_READ_PHOTO);
//                }else {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            storageEnable = true;
                        } else if (permission.shouldShowRequestPermissionRationale) {

                        } else {

                        }

                        if (storageEnable) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(intent, REQUEST_READ_PHOTO);
                        }
                    }
                });
            }
//            }
        });
    }

    private void takePhoto(File dir) {
        imgFile = new File(dir, simpleDateFormat.format(new Date()) + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);

        Uri uri;
        //android 7以上 需要使用fileprovider获得uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(CameraActivity.this, CameraActivity.this.getPackageName() + ".fileProvider", imgFile);
        } else {
            uri = Uri.fromFile(imgFile);
        }
        Log.i("camera_uri", uri.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }
}
