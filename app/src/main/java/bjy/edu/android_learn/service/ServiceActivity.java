package bjy.edu.android_learn.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import bjy.edu.android_learn.R;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = "111222";
    private Button btn_start;
    private Button btn_stop;
    private int tag_index_start = 0;
    private int tag_index_stop = 0;

    private Button btn_bind;
    private Button btn_unbind;
    private int tag_index_bind = 0;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "[onServiceConnected] " + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "[onServiceDisconnected] " + name);
        }
    };;

    //监听正在运行的service
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //循环检测
            new Handler().postDelayed(this, 5000);
            ActivityManager activityManager = (ActivityManager) ServiceActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null)
                return;

            List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
            if (!(serviceList.size() > 0)) {
                return ;
            }
            StringBuilder stringBuilder = new StringBuilder().append("所有存活的Service有：" + serviceList.size() + "个").append("\n");
            StringBuilder stringBuilder2 = new StringBuilder().append("当前应用存活的Service有：\n");
            boolean isHave = false;
            for (int i = 0; i < serviceList.size(); i++) {
                ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
                ComponentName componentName = serviceInfo.service;
                String process = serviceInfo.process;
                stringBuilder.append("[process] ").append(process).append(" [service name] ").append(componentName.toString()).append("\n");
                //获取当前应用的service
                if (getPackageName().equals(serviceInfo.process)){
                    isHave = true;
                    stringBuilder2.append("[process] ").append(process).append(" [service name] ").append(componentName.toString()).append("\n");
                }
            }

            Log.i(TAG, stringBuilder.toString());
            if (isHave)
                Log.i(TAG, stringBuilder2.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        initView();

        int position = 0;
        switch (position){
            case 0:
                //startSevice
                test_0();
                break;
            case 1:
                //bindSevice
                test_1();
                break;
        }
    }

    private void initView(){
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_bind = findViewById(R.id.btn_bind);
        btn_unbind = findViewById(R.id.btn_unbind);
    }

    private void test_0(){
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, TestService.class);
                intent.putExtra("tag", "start-service-" + tag_index_start);
                startService(intent);

                tag_index_start++;
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, TestService.class);
                intent.putExtra("tag", "stop-service-" + tag_index_stop);
                stopService(intent);

                tag_index_stop++;
            }
        });

        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, TestService.class);
                intent.putExtra("tag", "bind-service-" + tag_index_bind);
                bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);

                tag_index_bind++;
            }
        });

        btn_unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //只能调用一次
                    unbindService(serviceConnection);
                }catch (Exception e){
                    Log.w(TAG, e);
                }

            }
        });

        //监听存活的service
//        new Handler().post(runnable);
    }

    private void test_1(){
    }
}
