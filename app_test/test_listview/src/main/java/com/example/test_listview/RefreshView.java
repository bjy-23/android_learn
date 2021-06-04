package com.example.test_listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;


//下拉刷新测试
public class RefreshView extends FrameLayout {
    private static final String TAG = "RefreshView";
    private float yDown;
    private Context context;
    private Scroller scroller;

    public RefreshView(@NonNull Context context) {
        this(context, null);
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        scroller = new Scroller(context);
        init();
    }

    private void init(){

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, String.format("onInterceptTouchEvent action[%d]", ev.getAction()));
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            yDown = ev.getY();
        }else if (ev.getAction() == MotionEvent.ACTION_MOVE){
            float dealtY = ev.getY() - yDown;

            if (getChildCount() == 1){
                View view = getChildAt(0);
                if (view instanceof RecyclerView){
                    RecyclerView recyclerViewChild = (RecyclerView) view;
                    if (recyclerViewChild.getScrollY() == 0){
                        if (dealtY > 0){
                            return true;
                        }
                    }
                }
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, String.format("onTouchEvent action[%d]", event.getAction()));
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                scrollBy(0, (int)(yDown - event.getY()));

                yDown = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "up getScrollY " + getScrollY());
                scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
                invalidate();
//                scrollBy(0, -getScrollY());
//                scrollTo(0, 0);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
//        Log.i(TAG, "computeScroll");
        if (scroller.computeScrollOffset()){
            Log.i(TAG, "computeScroll scroller.getCurrX() " +scroller.getCurrX() + " scroller.getCurrY()" + scroller.getCurrY());
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
}
