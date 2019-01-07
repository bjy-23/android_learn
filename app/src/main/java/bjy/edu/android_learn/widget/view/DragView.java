package bjy.edu.android_learn.widget.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class DragView extends FrameLayout{
    private Context context;

    private float xDown;
    private float yDown;

    private float tempTranslationX;
    private float tempTranslationY;

    int parentWidth;

    public DragView(@NonNull Context context) {
        this(context, null);
    }

    public DragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        ViewGroup parent = (ViewGroup) getParent();
        parentWidth = parent.getWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                yDown = event.getRawY();

                tempTranslationX = getTranslationX();
                tempTranslationY = getTranslationY();
                return true;

            case MotionEvent.ACTION_MOVE:
//                Log.e("left", getLeft()+"");
//                Log.e("top", getTop()+"");

                float xMove = event.getRawX();
                float yMove = event.getRawY();

                //1.layout()
//                int offX = (int) (event.getRawX() - xDown);
//                int offY = (int) (event.getRawY() - yDown);
//                layout(getLeft()+offX, getTop()+offY, getRight()+offX, getBottom()+offY);

                //2.offsetLR/offsetTB
//                offsetLeftAndRight((int)(xMove-xDown));
//                offsetTopAndBottom((int) (yMove-yDown));
//                xDown = xMove;
//                yDown = yMove;

                //3.translationX/Y
                //限制不超过父View
                ViewGroup parent = (ViewGroup) getParent();
                int leftParent = parent.getLeft()+parent.getPaddingLeft();
                int rightParent = parent.getRight()+parent.getPaddingRight();
                int width = parent.getWidth();
                int height = parent.getHeight();

                if ((getLeft() + tempTranslationX + xMove - xDown) < 0
                        || (getRight() + tempTranslationX + xMove - xDown) > width
                        || (getTop() + tempTranslationY + yMove - yDown) <0
                        || (getBottom() +tempTranslationY + yMove - yDown) >height)
                    return true;

                setTranslationX(tempTranslationX + xMove - xDown);
                setTranslationY(tempTranslationY + yMove - yDown);



                return true;

            case MotionEvent.ACTION_UP:
//                Log.e("ACTION_UP", "ACTION_UP");
//                Log.e("left", getLeft()+"");
//                Log.e("top", getTop()+"");

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationX", parentWidth - 30 - getRight())
                        .setDuration(200);
                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.e("x", ""+getX());
                        Log.e("y", ""+getY());
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                objectAnimator.start();

//                requestLayout();

                return true;

        }
        return super.onTouchEvent(event);
    }
}
