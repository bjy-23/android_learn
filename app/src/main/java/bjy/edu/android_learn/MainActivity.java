package bjy.edu.android_learn;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.CoreComponentFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bjy.edu.android_learn.broadcastreceiver.ReceiverActivity;
import bjy.edu.android_learn.camera.CameraActivity;
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
import bjy.edu.android_learn.keystore.KeyStoreActivity;
import bjy.edu.android_learn.memory.MemoryActivity;
import bjy.edu.android_learn.memory_leak.MemoryLeakActivity;
import bjy.edu.android_learn.notification.NotifyActivity;
import bjy.edu.android_learn.permission.PermissionActivity;
import bjy.edu.android_learn.popupwindow.PopupwindowActivity;
import bjy.edu.android_learn.recyclerView.RvActivity;
import bjy.edu.android_learn.reflect.ReflectActivity;
import bjy.edu.android_learn.rxjava.RxJavaActivity;
import bjy.edu.android_learn.service.ServiceActivity;
import bjy.edu.android_learn.socket.SocketActivity;
import bjy.edu.android_learn.sqlite.SqliteActivity;
import bjy.edu.android_learn.stackoverflow.StackActivity;
import bjy.edu.android_learn.textview.TextViewActivity;
import bjy.edu.android_learn.thread.ThreadActivity;
import bjy.edu.android_learn.time.TimerActivity;
import bjy.edu.android_learn.toolbar.ToolbarActivity;
import bjy.edu.android_learn.util.SpUtil;
import bjy.edu.android_learn.util.ToastUtil;
import bjy.edu.android_learn.util.ToastUtil2;
import bjy.edu.android_learn.viewflipper.ViewFlipperActivity;
import bjy.edu.android_learn.viewpager.ViewPagerActivity;
import bjy.edu.android_learn.webView.WebViewActivity;
import bjy.edu.android_learn.websocket.WebSocketActivity;
import bjy.edu.android_learn.widget.ViewActivity;
import bjy.edu.android_learn.zxing.ZxingActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final List<Activity> activities = new ArrayList<>();
    public static int notif_id = 1;
    private volatile int tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activities.add(this);

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //uri 匹配
        String url = "spdbbank://wap.spdb.com.cn/pay?Plain=TranAbbr=IPER|MasterID=2006512137|MercDtTm=20191107195950|TermSsn=071959503621|OSttDate=|OAcqSsn=|MercCode=983708160001601|TermCode=00000000|TranAmt=66.0|Remark1=%CF%DF%CF%C2%B8%B6%BF%EE-%B2%E2%CA%D4%D6%A7%B8%B6|Remark2=%B6%A9%B5%A5%BA%C5%A3%BA1911071959503621|MercUrl=https://paymenttest.dragonpass.com.cn/spdpay/pageRetUrl|Ip=121.14.200.51|SubMercFlag=0|SubMercName=%C1%FA%CC%DA%B3%F6%D0%D0|SubMercGoodsName=%CF%DF%CF%C2%B8%B6%BF%EE-%B2%E2%CA%D4%D6%A7%B8%B6MerAccountType=|BackUrl=https://paymenttest.dragonpass.com.cn/spdpay/bgRetUrl|&Signature=17275a797915233e0b7c7e8ae4822dc396e944f6170aabcf973c982efdd22792a8c87388ada86b9bb9726bd6d5aef2415291b92da20652e46b9968c99f0287f5ac7a5fed99a77902ab64052ae5394bd983d623aa2a494c6ad8d9a9daa57d101cd0f265b0e14392f0773bd0b3a0aea9d473dc7f12dd61c75536e1e08d0a2e7bfb";
        Uri uri_1 = Uri.parse(url);
        Log.i(TAG, "uri_1: " + uri_1.getScheme() + "://" + uri_1.getAuthority());
        String url_2 = url.replace("spdbbank", "content");
        Uri uri_2 = Uri.parse(url_2);
        Log.i(TAG, "uri_2: " + uri_2.getScheme() + "://" + uri_2.getAuthority());
        //自定义toast
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Toast toast = new Toast(MainActivity.this);
//                final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.toast_1, null, false);
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
//        }, 3000);

        //隐式启动
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                intent.setData(Uri.parse("spdbbank://wap.spdb.com.cn"));
//
//                startActivity(intent);
//            }
//        }, 3000);

        String s = "spdbbank://wap.spdb.com.cn/pay? Plain=fff&Signature=fff";
        Uri uri = Uri.parse(s);
        System.out.println("uri: " + s);
        System.out.println("scheme: " + uri.getScheme());
        System.out.println("host: " + uri.getHost());
        System.out.println("path: " + uri.getPath());


        final TextView textView = findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = 37;
                switch (position){
                    case 40:
                        //zxing扫码
                        test_40();
                        break;
                    case 39:
                        //KeyStore
                        test_39();
                        break;
                    case 38:
                        //thread
                        test_38();
                        break;
                    case 37:
                        //permission
                        test_37();
                        break;
                    case 36:
                        //rxjava
                        test_36();
                        break;
                    case 34:
                        //相机
                        test_34();
                        break;
                    case 33:
                        //sqlite
                        test_33();
                        break;
                        //edittext
                    case 25:
                        test_25();
                        break;
                    case 24:
                        test_24();
                        break;
                    case 23:
                        //popupwindow
                        test_23();
                        break;
                    case 15:
                        //fragment
                        test_15();
                        break;
                    case 13:
                        //dialog
                        test_13();
                        break;
                    case 8:
                        //图片测试
                        test_8();
                        break;
                    case 6:
                        //webView
                        test_6();
                        break;
                    case 4:
                        //自定义View
                        test_4();
                        break;
                    case 3:
                        //自定义View
                        test_3();
                        break;
                }
            }
        });

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
    protected void onDestroy() {
        super.onDestroy();
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

    public void test_36(){
        startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
    }

    public void test_37(){
        startActivity(new Intent(MainActivity.this, PermissionActivity.class));
    }

    public void test_38(){
        startActivity(new Intent(MainActivity.this, ThreadActivity.class));
    }

    private void test_39(){
        startActivity(new Intent(MainActivity.this, KeyStoreActivity.class));
    }

    private void test_40(){
        startActivity(new Intent(MainActivity.this, ZxingActivity.class));
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
        Bundle bundle = new Bundle();
        bundle.putString("tag", "1229");
        intent.putExtras(bundle);
        startActivity(intent);
//        startActivity(new Intent(this, FragmentActivity.class));
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

    private void test_35() {
        startActivity(new Intent(this, ContentProviderActivity.class));
    }

    public static void main(String[] args) {
        char zc = '3';
        System.out.println("zc = " + (int) zc);
    }
}
