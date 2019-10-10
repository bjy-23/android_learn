package bjy.edu.android_learn.permission;

import android.Manifest;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        // 通常写法
//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
//                .subscribe(new Consumer<Permission>() {
//                    @Override
//                    public void accept(Permission permission) throws Exception {
//                        switch (permission.name){
//                            case Manifest.permission.CAMERA:
//                                Log.i("permission", "CAMERA");
//                                if (permission.granted){//获得权限
//                                    Log.i("permission", "ok");
//                                }else if (permission.shouldShowRequestPermissionRationale){//禁止权限
//                                    Log.i("permission", "no");
//                                }else {// 禁止权限并不再访问
//                                    Log.i("permission", "never ask again");
//                                }
//                                break;
//                            case Manifest.permission.READ_EXTERNAL_STORAGE:
//                                Log.i("permission", "STORAGE");
//                                break;
//                        }
//                    }
//                });

        test();
    }

    private void test(){
        RxPermissions rxPermissions = new RxPermissions(this);

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

        // TODO: 2019-10-08
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


//        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        //true:获得所有权限；false:未获得所有
//                        if (aBoolean) {
//                            Log.i("permission", "yes");
//                        } else {
//                            Log.i("permission", "no");
//                        }
//                    }
//                });
    }
}
