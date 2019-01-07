package bjy.edu.android_learn.widget.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;

public class CircleWaveView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private volatile boolean isDrawing;
    private  float offset;


    public CircleWaveView2(Context context) {
        this(context, null);
    }

    public CircleWaveView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = true;
    }

    @Override
    public void run() {
        while (isDrawing){
            drawWave();
        }
    }

    private void drawWave(){
        try {
            canvas = surfaceHolder.lockCanvas();

            ValueAnimator mAnimator = ValueAnimator.ofFloat(0,1);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offset = (float)animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setDuration(3000);
            mAnimator.start();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (surfaceHolder != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }


    }
}
