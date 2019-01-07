package bjy.edu.android_learn.viewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.widget.view.MyViewPager;

public class ViewPagerActivity extends AppCompatActivity {
    private MyViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.viewPager);
        VpAdapter adapter = new VpAdapter(this, viewPager);
        viewPager.setAdapter(adapter);

        //viewpager的页面监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("vp", "position: " + position + "   " + "positionOffset: " + positionOffset + "   " + "positionOffsetPixels: " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("vp", "position: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.e("vp", "state: " + state);
                /*
                * state: 0（SCROLL_STATE_IDLE）:vp停止滑动
                *        1（SCROLL_STATE_DRAGGING）:vp正被用户拖拽
                *        2（SCROLL_STATE_SETTLING）:vp将要停止
                * */
            }
        });

        //viewpager禁止滑动
//        viewPager.setTouchEnabled(false);
    }
}
