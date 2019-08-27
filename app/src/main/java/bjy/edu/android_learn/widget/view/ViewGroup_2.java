package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class ViewGroup_2 extends FrameLayout {
    public ViewGroup_2(Context context) {
        super(context);
    }

    public ViewGroup_2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i("dispatchTouchEvent", "ViewGroup_2   " + ev.getAction());

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.i("onInterceptTouchEvent", "ViewGroup_2   " + ev.getAction());

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("onTouchEvent", "ViewGroup_2   " + event.getAction());

        return super.onTouchEvent(event);
    }
}
