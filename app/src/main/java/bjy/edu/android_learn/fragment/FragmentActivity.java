package bjy.edu.android_learn.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class FragmentActivity extends AppCompatActivity {

    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int pos = 1;
        switch (pos){
            case 2:
                //入栈出栈操作
                test_2();
                break;
                //验证fragment的生命周期
            case 1:
                test_1();
                break;
        }
    }

    private void test_1(){
        setContentView(R.layout.activity_fragment);

        Button btn_add = findViewById(R.id.btn_add);
        final Fragment fragment_1 = new FragmentLifeCycle("bjy");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.layout_container, fragment_1).commit();
            }
        });
        Button btn_add2 = findViewById(R.id.btn_add2);
        btn_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.layout_container, new FragmentLifeCycle("bjy2")).commit();
            }
        });
        Button btn_remove = findViewById(R.id.btn_remove);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().remove(fragment_1).commit();
            }
        });
        Button btn_replace = findViewById(R.id.btn_replace);
        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, new FragmentLifeCycle("replace")).commit();
            }
        });
    }

    private void test_2(){
        setContentView(R.layout.activity_fragment_2);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final TextView tv_add = findViewById(R.id.tv_add);
        final TextView tv_pop = findViewById(R.id.tv_pop);

//        tv_add.setText("添加  " + num);
//        tv_pop.setText("回退  ");

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().add(R.id.fl_container, new Fragment_1(""+num)).addToBackStack(null).commit();

//                tv_pop.setText("回退  " +num);
//
                num++;
//                tv_add.setText("添加  " + num);
            }
        });

        tv_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num == 0)
                    return;

                num--;
                fragmentManager.popBackStackImmediate();
                fragmentManager.getFragments();

//                tv_add.setText("添加  " + num);
//
//                num--;
//                if (num == 0)
//                    tv_pop.setText("回退  ");
//                else
//                    tv_pop.setText("回退  " + num);
            }
        });

    }
}
