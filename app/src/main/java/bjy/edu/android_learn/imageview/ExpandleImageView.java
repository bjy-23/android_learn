package bjy.edu.android_learn.imageview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ExpandleImageView extends ImageView {
    private float x0, x1;
    private int id0, id1;
    private float offset0;
    private float offsetTemp;
    private long time_0;
    private long time_1;
    public ExpandleImageView(Context context) {
        this(context, null);
    }

    public ExpandleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("MotionEvent", "action: " + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: // 0
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i("MotionEvent", "count: "+event.getPointerCount());
                id0 = event.getPointerId(0);
                x0 = event.getX(id0);
                id1 = event.getPointerId(1);
                x1 = event.getX(id1);
                offset0 = Math.abs(x1 - x0);
                return true;
            case MotionEvent.ACTION_POINTER_2_DOWN: //261
                Log.i("MotionEvent", "count: "+event.getPointerCount());
                id0 = event.getPointerId(0);
                x0 = event.getX(id0);
                id1 = event.getPointerId(1);
                x1 = event.getX(id1);
                offset0 = Math.abs(x1 - x0);
                time_0 = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_MOVE: // 2
                if (offset0 == 0)
                    return true;
                // TODO: 2019-09-23
                if (event.getPointerCount() < 2)
                    return true;
                x0 = event.getX(id0);
                x1 = event.getX(id1);

                float offset_move = Math.abs(x1 - x0);
                int limit = 8;
                if ( Math.abs(offset_move- offsetTemp) < limit && Math.abs(offset_move- offset0) < limit)
                    return true;
                float scaleX = Math.abs(x1 - x0) / offset0;

                if (System.currentTimeMillis() - time_0 > 100){
                    setScaleX(scaleX);
                    time_0 = System.currentTimeMillis();
                }


                offsetTemp = Math.abs(x1 - x0);
                break;
            case MotionEvent.ACTION_POINTER_UP: // 6
                return true;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);

    }
}
