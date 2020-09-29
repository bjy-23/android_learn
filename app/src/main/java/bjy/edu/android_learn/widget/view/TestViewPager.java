package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/*
* 只测试Viewpager的拦截事件处理
* */
public class TestViewPager extends ViewPager {
    private String TAG = "111222";
    public TestViewPager(@NonNull Context context) {
        super(context);
    }

    public TestViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "action " + ev.getAction() + " dispatchTouchEvent TestViewPager");
        boolean result = super.dispatchTouchEvent(ev);
        Log.i(TAG, "action " + ev.getAction() + " dispatchTouchEvent TestViewPager      [return] " + result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "action " + ev.getAction() + " onInterceptTouchEvent TestViewPager");
        boolean result = super.onInterceptTouchEvent(ev);
        Log.i(TAG, "action " + ev.getAction() + " onInterceptTouchEvent TestViewPager      [return] " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "action " + ev.getAction() + " onTouchEvent TestViewPager");
        boolean result = super.onTouchEvent(ev);
        Log.i(TAG, "action " + ev.getAction() + " onTouchEvent TestViewPager      [return] " + result);
        return result;
    }
}
