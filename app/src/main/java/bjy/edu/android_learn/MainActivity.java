package bjy.edu.android_learn;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bjy.edu.android_learn.broadcastreceiver.ReceiverActivity;
import bjy.edu.android_learn.dialog.DialogActivity;
import bjy.edu.android_learn.drawable.DrawableActivity;
import bjy.edu.android_learn.fragment.FragmentActivity;
import bjy.edu.android_learn.fragment.FragmentContainerActivity;
import bjy.edu.android_learn.fragment.Fragment_1;
import bjy.edu.android_learn.http.HttpActivity;
import bjy.edu.android_learn.imageview.ImageViewActivity;
import bjy.edu.android_learn.json.TestBean;
import bjy.edu.android_learn.memory.MemoryActivity;
import bjy.edu.android_learn.notification.NotifyActivity;
import bjy.edu.android_learn.popupwindow.PopupwindowActivity;
import bjy.edu.android_learn.recyclerView.RvActivity;
import bjy.edu.android_learn.service.ServiceActivity;
import bjy.edu.android_learn.service.ServiceUtil;
import bjy.edu.android_learn.service.TestService;
import bjy.edu.android_learn.stackoverflow.StackActivity;
import bjy.edu.android_learn.time.TimerActivity;
import bjy.edu.android_learn.toolbar.ToolbarActivity;
import bjy.edu.android_learn.util.SpUtil;
import bjy.edu.android_learn.viewflipper.ViewFlipperActivity;
import bjy.edu.android_learn.viewpager.ViewPagerActivity;
import bjy.edu.android_learn.webView.WebViewActivity;
import bjy.edu.android_learn.widget.ViewActivity;

public class MainActivity extends AppCompatActivity {
    public static final List<Activity> activities = new ArrayList<>();
    public static int notif_id = 1;
    private volatile int tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activities.add(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boolean hh = bundle.getBoolean("hh");
        }

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
                test_23();
            }
        });

        //statusBar 隐藏


//        DisplayUtil.setStatusBarColor(this, Color.TRANSPARENT);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String[] array = Build.SUPPORTED_ABIS;
        for (String s : array) {
            Log.e("abi", s + "\n");
        }

        //隐式启动app
//        Intent intent = new Intent();
////        intent.setAction("stockalert");
//        intent.setData(Uri.parse("sogukj://stockalert"));
//        startActivity(intent);

        String s = "18616272196";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(s.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            System.out.println("md5 加密值 " + result.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
        TestBean testBean2  = SpUtil.get("luffy");
        System.out.println("luffy" +testBean2.getName());

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

    private void test_21(){
        startActivity(new Intent(this, TimerActivity.class));
    }

    private void test_22(){

        //todo 初始化容量了，为什么性能没有提升
        long t0 = System.nanoTime();
        int capacity = 8192;
        int num = 6000;


        HashMap<Integer, String> hashMap2 = new HashMap<>(capacity);
        long t3 = System.currentTimeMillis();
        for (int i=0; i<num; i++){
            hashMap2.put(i, i+"");
        }
        long t4 = System.currentTimeMillis();
        System.out.println("时间 2  " + (t4-t3));

        tag = 1;

        HashMap<Integer, String> hashMap = new HashMap<>();
        long t1 = System.currentTimeMillis();
        for (int i=0; i<num; i++){
            hashMap.put(i, i+"");
        }
        long t2 = System.currentTimeMillis();
        System.out.println("时间 1  " + (t2-t1));

    }

    private void test_23(){
        startActivity(new Intent(this, PopupwindowActivity.class));
    }

    public static void main(String[] args) {

    }
}
