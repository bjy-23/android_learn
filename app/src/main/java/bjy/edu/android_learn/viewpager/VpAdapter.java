package bjy.edu.android_learn.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
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
        Log.e("1", "getCount");
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.e("1", "isViewFromObject");
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("1", "instantiateItem");
        TextView textView = new TextView(context);
        position %= 5;
        textView.setText("我的爱 +" + position);
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        Log.e("1", "destroyItem");
        container.removeView((View) object);
    }
}
