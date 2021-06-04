package com.example.x_test.testClick;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class IndexView  extends View {
    private static final String TAG = "111222";
    public IndexView(Context context) {
        super(context);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN
//        }
        Log.i(TAG, String.format("event action %d",  event.getAction()));
        return super.onTouchEvent(event);
    }
}
