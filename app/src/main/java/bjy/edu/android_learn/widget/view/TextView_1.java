package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class TextView_1 extends TextView {
    public TextView_1(Context context) {
        super(context);
    }

    public TextView_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i("dispatchTouchEvent", "TextView_1  " + ev.getAction());

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("onTouchEvent", "TextView_1   " + event.getAction());

        return super.onTouchEvent(event);
    }
}
