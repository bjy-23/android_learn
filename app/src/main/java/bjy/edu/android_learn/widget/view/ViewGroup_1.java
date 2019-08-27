package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ViewGroup_1 extends FrameLayout {
    public ViewGroup_1(Context context) {
        super(context);
    }

    public ViewGroup_1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i("dispatchTouchEvent", "ViewGroup_1   " + ev.getAction());

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.i("onInterceptTouchEvent", "ViewGroup_1   " + ev.getAction());

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("onTouchEvent", "ViewGroup_1   " + event.getAction());

        if (MotionEvent.ACTION_DOWN == event.getAction())
            return true;
        return super.onTouchEvent(event);
    }
}
