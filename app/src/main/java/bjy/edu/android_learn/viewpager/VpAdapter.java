package bjy.edu.android_learn.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.widget.view.DragIndicator;
import bjy.edu.android_learn.widget.view.MyViewPager;


/**
 * Created by sogubaby on 2018/6/13.
 */

public class VpAdapter extends PagerAdapter {
    private Context context;
    MyViewPager viewPager;

    public VpAdapter(Context context, MyViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
//        Log.e("1", "getCount");
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
//        Log.e("1", "isViewFromObject");
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        Log.e("1", "instantiateItem");
        View view = View.inflate(context, R.layout.layout_drag, null);
        DragIndicator dragIndicator = view.findViewById(R.id.drag_indicator);
        dragIndicator.setDragListener(new DragIndicator.DragListener() {
            @Override
            public void dragStart() {
                Log.e("1", "dragStart");
                viewPager.setTouchEnabled(false);

            }

            @Override
            public void dragEnd() {
                Log.e("1", "dragEnd");
                viewPager.setTouchEnabled(true);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        Log.e("1", "destroyItem");
        container.removeView((View) object);
    }
}
