package bjy.edu.android_learn.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import bjy.edu.android_learn.R;

public class FragmentActivity extends AppCompatActivity {

    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int pos = 2;
        switch (pos){
            case 2:
                test_2();
                break;
        }
    }

    private void test_1(){
        setContentView(R.layout.activity_fragment);

        final Fragment_1 fragment_1 = new Fragment_1();
        fragment_1.tag = "1";
        Fragment_1 fragment_2 = new Fragment_1();
        fragment_2.tag = "2";
        Fragment_1 fragment_3 = new Fragment_1();
        fragment_3.tag = "3";
        final FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.layout_container, fragment_1).commit();
//        fragmentManager.beginTransaction().add(R.id.layout_container, fragment_1).commit();
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
//                System.out.println("");

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
