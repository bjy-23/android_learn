package bjy.edu.android_learn.widget;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class FloatingOnTouchListener implements View.OnTouchListener {
    private static final String TAG  = FloatingOnTouchListener.class.getSimpleName();
    private float x_down;
    private float y_down;
    private float x_move;
    private float y_move;
    private float x_up;
    private float y_up;

    private MoveListener moveListener;

    public FloatingOnTouchListener(MoveListener moveListener){
     this.moveListener = moveListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x_down = event.getRawX();
                y_down = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                x_move = event.getRawX();
                y_move = event.getRawY();
                if (moveListener != null){
                    moveListener.move((int)(x_move-x_down), (int)(y_move-y_down));
                }
                x_down = x_move;
                y_down = y_move;
                break;
            case MotionEvent.ACTION_UP:
                x_up = event.getRawX();
                y_up = event.getRawY();
                if (moveListener != null){
                    moveListener.move((int)(x_up-x_down), (int)(y_up-y_down));
                }
                x_down = x_up;
                y_down = y_up;
                break;
        }
        return true;
    }

    public interface MoveListener{
        void move(int offsetX, int offsetY);
    }
}
