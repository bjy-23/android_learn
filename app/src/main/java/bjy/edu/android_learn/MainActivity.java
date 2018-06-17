package bjy.edu.android_learn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bjy.edu.android_learn.util.SpUtil;
import bjy.edu.android_learn.webView.WebViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewpager测试
//                startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));

                //webView测试
//                startActivity(new Intent(MainActivity.this, WebViewActivity.class));

//                List<TestBean> list = new ArrayList<>();
//                list.add(new TestBean("路飞", 17));
//                list.add(new TestBean("索隆", 19));
//                SpUtil.put("list", list);
//
//                List list1 = SpUtil.get("list");
//                System.out.println(list1.size());
//
//                Map<String, String> map = new HashMap<>();
//                map.put("路飞", "17");
//                map.put("索隆", "19");
//
//                SpUtil.put("map", map);
//
//                Map map1 = SpUtil.get("map");
//                System.out.println(map1.toString());
//
//                Set<TestBean> set = new HashSet<>();
//                set.add(new TestBean("路飞", 17));
//                set.add(new TestBean("索隆", 19));
//                SpUtil.put("set", set);
//
//                Set set1 = SpUtil.get("set");
//                System.out.println(set1.toString());

                Gson gson = new Gson();

                List<TestBean> list = new ArrayList<>();
                list.add(new TestBean("bjy", 22));

                System.out.println(gson.toJson(list));
            }
        });
    }
}
