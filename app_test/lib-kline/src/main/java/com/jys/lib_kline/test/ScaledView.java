package com.jys.lib_kline.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ScaledView extends View {
    private static final String TAG = "ScaledView";
    private Context context;
    private ScaleGestureDetector mScaleGestureDetector;

    public ScaledView(Context context) {
        this(context, null);
    }

    public ScaledView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    public void init(){
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                Log.i(TAG, "onScale scaleFactor: " + scaleFactor);
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                Log.i(TAG, "onScaleBegin");
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                Log.i(TAG, "onScaleEnd");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        boolean result = super.onTouchEvent(event);

//        Log.i(TAG, "onTouchEvent event: " + event.getAction() + " result: " + result);
        return true;
    }
}
