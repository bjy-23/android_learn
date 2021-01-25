package bjy.edu.android_learn;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import bjy.edu.android_learn.util.DisplayUtil;

public class PhonePropertyActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> data = new ArrayList<>();
    private static final String TAG = "PhoneProperty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_property);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(PhonePropertyActivity.this, LinearLayoutManager.VERTICAL, false));

        //屏幕像素：
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int x = point.x;
        int y = point.y;
        DisplayMetrics metrics = PhonePropertyActivity.this.getResources().getDisplayMetrics();
        String s1 = "屏幕像素：" + metrics.widthPixels +" * " + metrics.heightPixels + "; dpi: " + metrics.densityDpi;
        data.add(s1);

        // cpu架构
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            String[] array = Build.SUPPORTED_ABIS;
            StringBuilder s2 = new StringBuilder("cpu架构：");
            for(String s: array){
                s2.append(s).append("; ");
            }
            data.add(s2.toString());
        }

        //内存
        //这两个值都是大概的值，只具有参考意义
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        data.add("内存：" + activityManager.getMemoryClass() + " mb" +
                " ; " + "最大内存：" + activityManager.getLargeMemoryClass() + " mb");
        Log.i(TAG, "品牌： " + Build.BRAND + " " + Build.MODEL);
        Log.i(TAG, "操作系统： Android " + Build.VERSION.RELEASE);


        //设备标识
        String androidId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        data.add("android_id: " + androidId);
        Log.i(TAG, String.format("android_id [%s]", androidId));
        String androidId2 = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(TAG, String.format("android_id2 [%s]", androidId2));

        recyclerView.setAdapter(new Adapter(PhonePropertyActivity.this, data));
    }

    class Adapter extends RecyclerView.Adapter{
        private Context context;
        private List<String> data;

        public Adapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_phone_property, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tv.setText(data.get(i));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                tv = itemView.findViewById(R.id.tv);
            }
        }
    }
}
