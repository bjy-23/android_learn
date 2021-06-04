package com.example.x_test;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private String TAG = "MainActivity";
    int count = 0;
    private SensorManager sensorManager;
    private Vibrator vibrator;

    private boolean create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(new FrameLayout(MainActivity.this)){};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                FrameLayout frameLayout = (FrameLayout) holder.itemView;
                frameLayout.removeAllViews();
                TextView textView = new TextView(MainActivity.this);
                textView.setText("position: " + position);
                textView.setTextSize(16f);
                frameLayout.addView(textView);
            }

            @Override
            public int getItemCount() {
                return 150;
            }
        });

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                Log.i("111222", " recyclerView.getHeight()" + recyclerView.getHeight());
            }
        });

//        WebView webView = findViewById(R.id.webView);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl("http://beta.zgjys.com/newsStock?id=SH600237");

//        startActivity(new Intent(this, TestNestActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float[] values = sensorEvent.values;
            if (values != null){
//                for (int i=0; i< values.length; i++){
//                    Log.i(TAG, String.format("onSensorChanged values[%d]: %s", i, values[i]+""));
//                }

                if (values.length < 3)
                    return;
                if (Math.abs(values[0]) >= 15  || Math.abs(values[1]) >= 15 || Math.abs(values[2]) >= 15){

                    for (int i=0; i< values.length; i++){
                        Log.i(TAG, String.format("onSensorChanged values[%d]: %s", i, values[i]+""));
                    }

                    Log.i(TAG, String.format("onSensorChanged --------------------------"));

                    if (create)
                        return;

                    vibrator.vibrate(500);
                    TestDialogFragment dialogFragment = new TestDialogFragment();
                    dialogFragment.show(getSupportFragmentManager(), "");
                    dialogFragment.getLifecycle().addObserver(new LifecycleEventObserver() {
                        @Override
                        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                            if (event == Lifecycle.Event.ON_CREATE){
                                Log.i(TAG, "Lifecycle.Event.ON_CREATE");
                                create = true;
                            }else if (event == Lifecycle.Event.ON_DESTROY){
                                Log.i(TAG, "Lifecycle.Event.ON_DESTROY");
                                create = false;
                            }
                        }
                    });

                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}