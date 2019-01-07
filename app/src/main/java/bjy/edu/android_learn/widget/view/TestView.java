package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by sogubaby on 2018/8/13.
 */

public class TestView extends View {
    Paint paint, paint_1, paint_2, paint_3;
    Path path;
    float x, y;
    Random random = new Random(47);

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //测试pathEffect
    public void init(){
        paint = new Paint();

        path = new Path();
//        CornerPathEffect
//        DiscretePathEffect
//        DashPathEffect
//        PathDashPathEffect
//        ComposePathEffect
//        SumPathEffect

        paint_1 = new Paint();
        paint_1.setAntiAlias(true);
        paint_1.setColor(Color.BLUE);
        paint_1.setStrokeWidth(5);
        paint_1.setStyle(Paint.Style.STROKE);
        paint_1.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

        paint_2 = new Paint();
        paint_2.setAntiAlias(true);
        paint_2.setColor(Color.BLUE);
        paint_2.setStrokeWidth(5);
        paint_2.setStyle(Paint.Style.STROKE);
        paint_2.setPathEffect(new DashPathEffect(new float[]{10, 5, 10, 15, 3, 15}, 0));

        paint_3 = new Paint();
        paint_3.setAntiAlias(true);
        paint_3.setColor(Color.BLUE);
        paint_3.setStrokeWidth(5);
        paint_3.setStyle(Paint.Style.STROKE);
        Path path_3 = new Path();
        path_3.addCircle(0, 0, 3, Path.Direction.CW);
        paint_3.setPathEffect(new PathDashPathEffect(path_3, 15, 0, PathDashPathEffect.Style.ROTATE));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.moveTo(100, 100);
        path.lineTo(500, 100);
        canvas.drawPath(path, paint_1);

        path.moveTo(100, 200);
        path.lineTo(500, 200);
        canvas.drawPath(path, paint_2);

        path.moveTo(100, 300);
        path.lineTo(500, 300);
        canvas.drawPath(path, paint_3);
    }


    //测试invalidata()
//    public void init(){
//        path = new Path();
//
//        paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                path.moveTo(event.getX(), event.getY());
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                x = event.getX();
//                y = event.getY();
////                path.moveTo(100, 100);
////                path.lineTo(event.getX(), event.getY());
//                invalidate();
//                return true;
//            case MotionEvent.ACTION_UP:
//                return true;
//        }
//        return super.onTouchEvent(event);
//    }

//    @Override
//    protected void onDraw(final Canvas canvas) {
//        super.onDraw(canvas);
////        path.reset();
//        path.moveTo(200, 200);
//        path.lineTo(400, 200);
//        path.moveTo(100, 100);
//        path.lineTo(400, 100);
//
//        canvas.drawPath(path, paint);
////        canvas.drawLine(100, 100, x, y, paint);
//
////        canvas.drawLine(100+x, 100-x, 200+x, 200-x, paint);
////        path.lineTo(200+x, 400+x);
////        canvas.drawPath(path, paint);
////        postDelayed(new Runnable() {
////            @Override
////            public void run() {
////
////                x = random.nextFloat()*100;
////                invalidate();
////                postDelayed(this, 2000);
////            }
////        }, 2000);
//    }

}
