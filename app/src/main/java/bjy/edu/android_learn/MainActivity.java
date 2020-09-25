package bjy.edu.android_learn;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bjy.edu.android_learn.activity.BigWordsActivity;
import bjy.edu.android_learn.activity.ContactActivity;
import bjy.edu.android_learn.activity.IntentActivity;
import bjy.edu.android_learn.activity.LocationActivity;
import bjy.edu.android_learn.activity.MemoryTestActivity;
import bjy.edu.android_learn.activity.SystemSettingActivity;
import bjy.edu.android_learn.broadcastreceiver.ReceiverActivity;
import bjy.edu.android_learn.camera.CameraActivity;
import bjy.edu.android_learn.camera.CameraCaptureActivity;
import bjy.edu.android_learn.contentprovider.ContentProviderActivity;
import bjy.edu.android_learn.dialog.DialogActivity;
import bjy.edu.android_learn.drawable.DrawableActivity;
import bjy.edu.android_learn.edittext.EditTextActivity;
import bjy.edu.android_learn.fragment.FragmentActivity;
import bjy.edu.android_learn.fragment.FragmentContainerActivity;
import bjy.edu.android_learn.fragment.Fragment_1;
import bjy.edu.android_learn.http.HttpActivity;
import bjy.edu.android_learn.imageview.GalleryActivity;
import bjy.edu.android_learn.imageview.GalleryPreviewActivity;
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
import bjy.edu.android_learn.service.AidlTestService;
import bjy.edu.android_learn.service.ServiceActivity;
import bjy.edu.android_learn.socket.SocketActivity;
import bjy.edu.android_learn.sqlite.SqliteActivity;
import bjy.edu.android_learn.stackoverflow.StackActivity;
import bjy.edu.android_learn.textview.TextViewActivity;
import bjy.edu.android_learn.thread.ThreadActivity;
import bjy.edu.android_learn.time.TimerActivity;
import bjy.edu.android_learn.toolbar.ToolbarActivity;
import bjy.edu.android_learn.util.BitmapUtil;
import bjy.edu.android_learn.util.DisplayUtil;
import bjy.edu.android_learn.util.IntentUtil;
import bjy.edu.android_learn.util.SpUtil;
import bjy.edu.android_learn.util.ToastUtil;
import bjy.edu.android_learn.util.ToastUtil2;
import bjy.edu.android_learn.viewflipper.ViewFlipperActivity;
import bjy.edu.android_learn.viewpager.ViewPagerActivity;
import bjy.edu.android_learn.webView.WebViewActivity;
import bjy.edu.android_learn.websocket.WebSocketActivity;
import bjy.edu.android_learn.widget.ViewActivity;
import bjy.edu.android_learn.widget.view.DragView;
import bjy.edu.android_learn.window.WindowActivity;
import bjy.edu.android_learn.zxing.ZxingActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final List<Activity> activities = new ArrayList<>();
    private volatile int tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activities.add(this);

        final TextView textView = findViewById(R.id.text);
        textView.getTypeface();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 34;
                switch (position){
                    case 999:
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                        break;
                    case 49:
                        IntentUtil.startActivity(MainActivity.this, new Intent(MainActivity.this, LocationActivity.class));
                        break;
                    case 48:
                        IntentUtil.startActivity(MainActivity.this, new Intent(MainActivity.this, GalleryPreviewActivity.class));
//                        IntentUtil.startActivity(MainActivity.this, new Intent(MainActivity.this, GalleryActivity.class));
                        position = 48;
                        break;
                    case 47:
                        IntentUtil.startActivity(MainActivity.this, new Intent(MainActivity.this, MemoryTestActivity.class));
                        position = 47;
                        break;
                    case 46:
                        startActivity(new Intent(MainActivity.this, SystemSettingActivity.class));
                        break;
                    case 45:
                        startActivity(new Intent(MainActivity.this, ContactActivity.class));
                        position = 45;
                        break;
                    case 44:
                        startActivity(new Intent(MainActivity.this, BigWordsActivity.class));
                        break;
                    case 43:
                        startActivity(new Intent(MainActivity.this, WindowActivity.class));
                        break;
                    case 42:
                        String type = null;
                        String url = "https://wap.spdb.com.cn/mspmk-cli-lifehome/lifeHome";
//                        String url = "https://wap.spdb.com.cn/mspmk-cli-wealthchannelhome/static/img/bg-top@2x.8334606.png";
//                        String url = "https://wap.spdb.com.cn/mspmk-cli-lifehome/static/js/manifest.00ae9297977b4a670057.js";
//                        String url = "https://wap.spdb.com.cn/mspmk-cli-life/#/lifeHome?OsType=android&APP_VERSION=10.26";
                        String extension = "png";
                        Log.i("111222", "extension: " + extension);
                        if (extension != null) {
                            MimeTypeMap mime = MimeTypeMap.getSingleton();
                            type = mime.getMimeTypeFromExtension(extension);
                        }
                        Log.i("111222", "type: " + type);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
                                    urlConnection.setConnectTimeout(5000);
                                    urlConnection.setReadTimeout(5000);
                                    Map<String, List<String>> map = urlConnection.getHeaderFields();
                                    String contentType = urlConnection.getHeaderField("Content-Type");
                                    Log.i("111222", "Content-Type: " + contentType);
//                                    if (map != null && !map.isEmpty()){
//                                        for (Map.Entry<String, List<String>> entry : map.entrySet()){
//                                            Log.i("111222", "entry: " + entry.getKey());
//                                            List<String> list = entry.getValue();
//                                            if (list != null && !list.isEmpty()){
//                                                for (String s : list){
//                                                    Log.i("111222", entry.getKey() +" : " + s);
//                                                }
//                                            }
//                                        }
//                                    }
                                } catch (Exception e) {

                                }
                            }
                        }).start();
                        break;
                    case 41:
                        //隐式启动
                        test_41();
                        break;
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
                        position = 37;
                        break;
                    case 36:
                        //rxjava
                        test_36();
                        break;
                    case 34:
                        //相机
//                        IntentUtil.startActivity(MainActivity.this, new Intent(new Intent(MainActivity.this, CameraActivity.class)));
                        IntentUtil.startActivity(MainActivity.this, new Intent(new Intent(MainActivity.this, CameraCaptureActivity.class)));
                        position = 34;
                        break;
                    case 33:
                        //sqlite
                        startActivity(new Intent(MainActivity.this, SqliteActivity.class));
                        position = 33;
                        break;
                        //edittext
                    case 27:
                        startActivity(new Intent(MainActivity.this, IOActivity.class));
                        position = 27;
                        break;
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
                    case 19:
                        //notification
                        IntentUtil.startActivity(MainActivity.this, new Intent(MainActivity.this, NotifyActivity.class));
                        position = 19;
                        break;
                    case 18:
                        startActivity(new Intent(MainActivity.this, ServiceActivity.class));

                        position = 18;
                        break;
                    case 17:
                        test_17();
                        break;
                    case 15:
                        //fragment
                        test_15();
                        break;
                    case 13:
                        //dialog
                        test_13();
                        break;
                    case 9:
                        startActivity(new Intent(MainActivity.this, FullScreenActivity.class));
                        position = 9;
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
                        //http
                        test_4();
                        break;
                    case 3:
                        //自定义View
                        test_3();
                        break;
                }
            }
        });

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

    private void test_41(){
        IntentUtil.startActivity(this, new Intent(this, IntentActivity.class));
    }

    private void test_40(){
        startActivity(new Intent(MainActivity.this, ZxingActivity.class));
    }

    private void test_39(){
        startActivity(new Intent(MainActivity.this, KeyStoreActivity.class));
    }

    public void test_38(){
        startActivity(new Intent(MainActivity.this, ThreadActivity.class));
    }

    public void test_37(){
        startActivity(new Intent(MainActivity.this, PermissionActivity.class));
    }

    public void test_36(){
        startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
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
//        Intent intent = new Intent(this, FragmentContainerActivity.class);
//        intent.putExtra(FragmentContainerActivity.NAME, Fragment_1.class.getName());
//        Bundle bundle = new Bundle();
//        bundle.putString("tag", "1229");
//        intent.putExtras(bundle);
//        startActivity(intent);
        startActivity(new Intent(this, FragmentActivity.class));
    }

    public void test_16() {
        startActivity(new Intent(this, StackActivity.class));
    }

    public void test_17() {
        startActivity(new Intent(this, ReceiverActivity.class));
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

    private void test_35() {
        startActivity(new Intent(this, ContentProviderActivity.class));
    }

    public static void main(String[] args) {
        char zc = '3';
        System.out.println("zc = " + (int) zc);
    }
}
