package bjy.edu.android_learn;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bbbjy.edu.bbbjy_support.util.LoadingDialog;
import bjy.edu.android_learn.broadcastreceiver.ReceiverActivity;
import bjy.edu.android_learn.contentprovider.ContentProviderActivity;
import bjy.edu.android_learn.dialog.DialogActivity;
import bjy.edu.android_learn.drawable.DrawableActivity;
import bjy.edu.android_learn.edittext.EditTextActivity;
import bjy.edu.android_learn.fragment.FragmentActivity;
import bjy.edu.android_learn.fragment.FragmentContainerActivity;
import bjy.edu.android_learn.fragment.Fragment_1;
import bjy.edu.android_learn.http.HttpActivity;
import bjy.edu.android_learn.imageview.ImageViewActivity;
import bjy.edu.android_learn.io.IOActivity;
import bjy.edu.android_learn.json.TestBean;
import bjy.edu.android_learn.memory.MemoryActivity;
import bjy.edu.android_learn.memory_leak.MemoryLeakActivity;
import bjy.edu.android_learn.notification.NotifyActivity;
import bjy.edu.android_learn.popupwindow.PopupwindowActivity;
import bjy.edu.android_learn.recyclerView.RvActivity;
import bjy.edu.android_learn.reflect.ReflectActivity;
import bjy.edu.android_learn.service.ServiceActivity;
import bjy.edu.android_learn.service.ServiceUtil;
import bjy.edu.android_learn.service.TestService;
import bjy.edu.android_learn.socket.SocketActivity;
import bjy.edu.android_learn.sqlite.SqliteActivity;
import bjy.edu.android_learn.stackoverflow.StackActivity;
import bjy.edu.android_learn.textview.TextViewActivity;
import bjy.edu.android_learn.time.TimerActivity;
import bjy.edu.android_learn.toolbar.ToolbarActivity;
import bjy.edu.android_learn.util.ResUtil;
import bjy.edu.android_learn.util.SpUtil;
import bjy.edu.android_learn.util.ToastUtil;
import bjy.edu.android_learn.util.ToastUtil2;
import bjy.edu.android_learn.viewflipper.ViewFlipperActivity;
import bjy.edu.android_learn.viewpager.ViewPagerActivity;
import bjy.edu.android_learn.webView.WebViewActivity;
import bjy.edu.android_learn.websocket.WebSocketActivity;
import bjy.edu.android_learn.widget.ViewActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    public static final List<Activity> activities = new ArrayList<>();
    public static int notif_id = 1;
    private volatile int tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activities.add(this);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boolean hh = bundle.getBoolean("hh");
        }

        int a = 65;
        char c = (char) a;
        String s = new String(new char[]{c});
        Log.e("65", s);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                //true:获得权限；false:未获得权限
                if (aBoolean){
                    Log.i("permission", "yes");
                }else {
                    Log.i("permission", "no");
                }
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

        final TextView textView = findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //viewpager测试
//                test_1();

                //recyclerView
//                test_2();

                //自定义View
//                test_3();

                //http测试
//                test_4();

                //toolbar
//                test_5();

                //webView
//                test_6();

                //sharedprefrence
//                test_7();

                //imageview
//                test_8();

                //screen
//                test_9();

                //动态代理
//                test_10();

                //drawable
//                test_11();

                //嵌套滑动
//                test_12();

                //dialog
//                test_13();

                //view_flipper
//                test_14();

                //fragment
//                test_15();

                //stackoverflow
//                test_16();

                //broadcastReceiver
//                test_17();

                //service
//                test_18();

                //notification
//                test_19();

                //memory
//                test_20();

                //timer
//                test_21();

                //hashmap
//                test_22();

                //popupWindow
//                test_23();

                //textView
//                test_24();

                //editText
//                test_25();

                //toast
//                test_26();

                //io
//                test_27();

                //reflect
//                test_28();

                //webSocket
//                test_29();

                //socket
//                test_30();

                //内存泄漏
//                test_31();

                //eventbus
//                test_32();

                //sqlite
//                test_33();

                //camera
                test_34();

                //contentprovider
//                test_40();
            }
        });

        //手机cpu架构
//        String[] array = Build.SUPPORTED_ABIS;
//        for (String s : array) {
//            Log.e("abi", s + "\n");
//        }

        //隐式启动app
//        Intent intent = new Intent();
////        intent.setAction("stockalert");
//        intent.setData(Uri.parse("sogukj://stockalert"));
//        startActivity(intent);

        TextView text_2 = findViewById(R.id.text_2);
        text_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhonePropertyActivity.class));
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        float x = ev.getRawX();
        float y = ev.getRawY();

        View decorView = getWindow().getDecorView();
        ActionBar actionBar;
        View child = decorView.findViewById(R.id.tv);
        if (child != null) {

        }

        return super.dispatchTouchEvent(ev);
    }

    public void test_1() {
        startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
    }

    public void test_2() {
        startActivity(new Intent(MainActivity.this, RvActivity.class));
    }

    private void test_3() {
        startActivity(new Intent(MainActivity.this, ViewActivity.class));
    }

    public void test_4() {
        startActivity(new Intent(MainActivity.this, HttpActivity.class));
    }

    public void test_5() {
        startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
    }

    public void test_6() {
        startActivity(new Intent(MainActivity.this, WebViewActivity.class));
    }

    public void test_7() {
        //非集合类对象，对应JSONObject
        TestBean testBean0 = SpUtil.get("luffy");
        TestBean testBean1 = new TestBean("路飞", 17);
        SpUtil.put("luffy", testBean1);
        TestBean testBean2 = SpUtil.get("luffy");
        System.out.println("luffy" + testBean2.getName());

        List list = new ArrayList();
        List list0 = SpUtil.get("list");
        TestBean testBean3 = new TestBean("索隆", 19);
        list.add(testBean1);
        list.add(testBean3);
        SpUtil.put("list", list);
        List list1 = SpUtil.get("list");
        System.out.println("l");

//        List<TestBean> list = new ArrayList<>();
//        list.add(new TestBean("路飞", 17));
//        list.add(new TestBean("索隆", 19));
//        SpUtil.put("list", list);
//
//        List list1 = SpUtil.get("list");
//        System.out.println(list1.size());
//
//        Map<String, String> map = new HashMap<>();
//        map.put("路飞", "17");
//        map.put("索隆", "19");
//
//        SpUtil.put("map", map);
//
//        Map map1 = SpUtil.get("map");
//        System.out.println(map1.toString());
//
//        Set<TestBean> set = new HashSet<>();
//        set.add(new TestBean("路飞", 17));
//        set.add(new TestBean("索隆", 19));
//        SpUtil.put("set", set);
//
//        Set set1 = SpUtil.get("set");
//        System.out.println(set1.toString());
//
//        List<List> list_ = new ArrayList<>();
//        List<TestBean> list__ = new ArrayList<>();
//        list__.add(new TestBean("bjy", 26));
//        list_.add(list__);
//
//        System.out.println(new Gson().toJson(list_));
//        Hawk.put("1", list_);
//
//        List list2 = Hawk.get("1");
//
//        List<String> list = new ArrayList<>();
//        TypeVariable[] array = list.getClass().getTypeParameters();
//        Type type = list.getClass().getGenericSuperclass();
//        ParameterizedType parameterizedType = (ParameterizedType) type;
//        Type[] type_array = parameterizedType.getActualTypeArguments();
//        Class c = (Class<String>) type_array[0];
//        System.out.println(type_array[0]);

//        GenericParent<String, Integer> genericParent = new GenericParent<>();
    }

    private void test_8() {
        startActivity(new Intent(this, ImageViewActivity.class));
    }

    public void test_9() {
        startActivity(new Intent(this, FullScreenActivity.class));
    }

    public void test_10() {
        class ProxyImpl implements InvocationHandler {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                return null;
            }
        }
    }

    public void test_11() {
        startActivity(new Intent(this, DrawableActivity.class));
    }

    public void test_12() {
        startActivity(new Intent(this, ScrollActivity.class));
    }

    public void test_13() {
        startActivity(new Intent(this, DialogActivity.class));
    }

    public void test_14() {
        startActivity(new Intent(this, ViewFlipperActivity.class));
    }

    public void test_15() {
        Intent intent = new Intent(this, FragmentContainerActivity.class);
        intent.putExtra(FragmentContainerActivity.NAME, Fragment_1.class.getName());
//        startActivity(intent);

        startActivity(new Intent(this, FragmentActivity.class));
    }

    public void test_16() {
        startActivity(new Intent(this, StackActivity.class));
    }

    public void test_17() {
        startActivity(new Intent(this, ReceiverActivity.class));
    }

    public void test_18() {
        startActivity(new Intent(this, ServiceActivity.class));
    }

    public void test_19() {
        startActivity(new Intent(this, NotifyActivity.class));
        notif_id++;

    }

    private void test_20() {
        startActivity(new Intent(this, MemoryActivity.class));
    }

    private void test_21() {
        startActivity(new Intent(this, TimerActivity.class));
    }

    private void test_22() {

        //todo 初始化容量了，为什么性能没有提升
        long t0 = System.nanoTime();
        int capacity = 8192;
        int num = 6000;


        HashMap<Integer, String> hashMap2 = new HashMap<>(capacity);
        long t3 = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            hashMap2.put(i, i + "");
        }
        long t4 = System.currentTimeMillis();
        System.out.println("时间 2  " + (t4 - t3));

        tag = 1;

        HashMap<Integer, String> hashMap = new HashMap<>();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            hashMap.put(i, i + "");
        }
        long t2 = System.currentTimeMillis();
        System.out.println("时间 1  " + (t2 - t1));

    }

    private void test_23() {
        startActivity(new Intent(this, PopupwindowActivity.class));
    }

    private void test_24() {
        startActivity(new Intent(this, TextViewActivity.class));
    }

    private void test_25() {
        startActivity(new Intent(this, EditTextActivity.class));
    }

    private void test_26() {
        ToastUtil.show(this, "哈哈哈");

        ToastUtil2.show(this, "不不不");
    }

    private void test_27() {
        startActivity(new Intent(this, IOActivity.class));
    }

    private void test_28(){
        startActivity(new Intent(this, ReflectActivity.class));
    }

    private void test_29(){
        startActivity(new Intent(this, WebSocketActivity.class));
    }

    private void test_30(){
        startActivity(new Intent(this, SocketActivity.class));
    }

    private void test_31(){
        startActivity(new Intent(this, MemoryLeakActivity.class));
    }

    private void test_32(){
//        EventBus.getDefault().register(this);
    }

    private void test_33(){
        startActivity(new Intent(this, SqliteActivity.class));
    }

    private void test_34(){
        startActivity(new Intent(new Intent(this, CameraActivity.class)));
    }

    private void test_40() {
        startActivity(new Intent(this, ContentProviderActivity.class));
    }

    public static void main(String[] args) {
        char zc = '3';
        System.out.println("zc = " + (int) zc);
    }
}
