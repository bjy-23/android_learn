package bjy.edu.android_learn.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import bjy.edu.android_learn.R;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PermissionActivity extends AppCompatActivity {
    private static final String TAG = PermissionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        //常规写法
        test_1();

        //rxpermission
//        test_2();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001){
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Log.i(TAG, "相机: PERMISSION_GRANTED");
//            }else{
//                Log.i(TAG, "相机: PERMISSION_DENIED");
//                //在申请一次权限后使用此方法，用来判断是否禁止再次询问
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, permissions[0])){
//                    Log.i(TAG, "相机: 禁止再次询问");
//                }else {
//                    Log.i(TAG, "相机: 可以再次询问");
//                }
//            }
//
//            if (grantResults[1] == PackageManager.PERMISSION_GRANTED){
//                Log.i(TAG, "存储: PERMISSION_GRANTED");
//            }else{
//                Log.i(TAG, "存储: PERMISSION_DENIED");
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, permissions[0])){
//                    Log.i(TAG, "存储: 禁止再次询问");
//                }else {
//                    Log.i(TAG, "存储: 可以再次询问");
//                }
//            }
        }
    }

    private void test_1(){
        String[] permissions = {Manifest.permission.READ_PHONE_STATE};

        //申请权限
        //1.同时申请多个权限，会在所有的权限都应答后再回调onRequestPermissionsResult
        ActivityCompat.requestPermissions(this, permissions, 1001);
    }

    private void test_2(){
        RxPermissions rxPermissions = new RxPermissions(this);

        //只能判断是否有权限
//        rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                //true:获得权限；false:未获得权限
//                if (aBoolean){
//                    Log.i("permission", "yes");
//                }else {
//                    Log.i("permission", "no");
//                }
//            }
//        });

        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        switch (permission.name){
                            case Manifest.permission.CAMERA:
                                Log.i("permission", "CAMERA");
                                if (permission.granted){//获得权限
                                    Log.i("permission", "ok");
                                }else if (permission.shouldShowRequestPermissionRationale){//禁止权限
                                    Log.i("permission", "no");
                                }else {// 禁止权限并不再访问
                                    Log.i("permission", "never ask again");
                                }
                                break;
                            case Manifest.permission.READ_EXTERNAL_STORAGE:
                                Log.i("permission", "STORAGE");
                                break;
                        }
                    }
                });

        rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Boolean aBoolean) {
                System.out.println("onNext: " + aBoolean);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.toString());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
