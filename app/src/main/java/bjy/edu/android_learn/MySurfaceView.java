package bjy.edu.android_learn;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private boolean isDrawing;

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("SurfaceView", "surfaceCreated");
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("SurfaceView", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("SurfaceView", "surfaceDestroyed");
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing){
            draw();
        }
    }

    private void draw(){
        try {
            canvas = surfaceHolder.lockCanvas();
            //
        }finally {
            if (canvas != null){
                surfaceHolder.unlockCanvasAndPost(canvas);
                Log.i("canvas", "canvas unlock");
            }
        }
    }


}
