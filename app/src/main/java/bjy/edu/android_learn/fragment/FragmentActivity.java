package bjy.edu.android_learn.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.widget.TabGroup;

public class FragmentActivity extends AppCompatActivity {
    TabGroup tabGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        tabGroup = findViewById(R.id.tab_group);
        tabGroup.setOnSelectListener(new TabGroup.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                fragmentManager.beginTransaction().add(R.id.layout_container, fragment_1).commit();
            }
        });
    }
}
