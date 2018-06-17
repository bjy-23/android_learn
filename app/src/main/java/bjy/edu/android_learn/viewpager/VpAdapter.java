package bjy.edu.android_learn.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sogubaby on 2018/6/13.
 */

public class VpAdapter extends PagerAdapter {
    private Context context;

    public VpAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView textView = new TextView(context);
        textView.setText("我的爱 +" + position);
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
