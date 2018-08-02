package bjy.edu.android_learn.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import bjy.edu.android_learn.R;

/**
 * Created by sogubaby on 2018/8/2.
 */

public class LineView extends LinearLayout {
    private Context context;
    private Paint paint;
    private Canvas canvas;
    private Path path;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private boolean canDraw;//可以画线
    private boolean hasStart;//初始点

    public LineView(@NonNull Context context) {
        this(context, null);
    }

    public LineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    private void init() {
        //画笔
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);

        setOrientation(LinearLayout.HORIZONTAL);

//        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                if (!hasStart) {
                    x1 = event.getX();
                    y1 = event.getY();
                    hasStart = true;
                    putPointView((int)x1, (int)y1);
//                } else {
//                    float tempx = event.getX();
//                    float tempy = event.getY();
//                    if (Math.abs(event.getX() - x1) < 100 && Math.abs(event.getY() - y1) < 100) {//在初始点附近
//                        canDraw = true;
//                    }
//                }
                return true;
            case MotionEvent.ACTION_MOVE:
//                if (canDraw) {
//                    x2 = event.getX();
//                    y2 = event.getY();
//
//                    if (canvas != null)
//                        canvas.drawLine(x1, y1, x2, y2, paint);
//                }
                return true;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
    }

    public void putPointView(int x, int y){
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.color.colorAccent);
        imageView.setLeft(x-10);
        imageView.setRight(x+10);
        imageView.setTop(y-10);
        imageView.setBottom(y+10);
        addView(imageView);
        imageView.layout(Math.max(x-5, 0), Math.max(y-5, 0), Math.max(x+5, 0), Math.max(y+5, 0));

//        requestLayout();
        invalidate();
    }
}
