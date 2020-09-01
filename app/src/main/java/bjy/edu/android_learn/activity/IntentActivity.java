package bjy.edu.android_learn.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.util.IntentUtil;

/*
* activity 启动方式 显式、隐式
* */
public class IntentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        int i = 3;
        switch (i){
            //显示启动
            case 3:
                test_3();
                break;
            case 0:
                //只通过action来启动
                test_0();
                break;
                //data
            case 1:
                test_1();
                break;
                //category
            case 2:
                test_2();
                break;
        }
    }

    private void test_3(){
        Intent intent = new Intent();
        //同一应用内的页面
//        intent.setComponent(new ComponentName(getPackageName(), "bjy.edu.android_learn.PhonePropertyActivity"));

        //其他应用的页面
        //android:export = true
        intent.setComponent(new ComponentName("bjy.edu.android_learn_ipc", "bjy.edu.android_learn_ipc.MainActivity"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        IntentUtil.startActivity(this, intent);
    }

    private void test_0(){
        Intent intent = new Intent();
        intent.setAction("intent_2_bbbjy");
        startActivity(intent);
    }

    private void test_1(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("intent2://");
        intent.setData(uri);

        uri = Uri.parse("intent22://");
        intent.setDataAndType(uri, "img");

        startActivity(intent);
    }

    private void test_2(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory("category_intent2");
        startActivity(intent);
    }
}
