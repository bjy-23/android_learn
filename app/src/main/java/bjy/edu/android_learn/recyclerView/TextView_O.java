package bjy.edu.android_learn.recyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class TextView_O extends TextView {
    public TextView_O(Context context) {
        super(context);
    }

    public TextView_O(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView_O(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextView_O(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("textview onTouchEvent", event.getAction()+"");
        return super.onTouchEvent(event);
    }
}
