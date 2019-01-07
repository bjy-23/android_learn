package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * Created by sogubaby on 2018/8/13.
 */

public class Clock extends View {
    private Paint paint;
    private Path path;
    private boolean drawn;

    public Clock(Context context) {
        super(context);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        //将要画的位置移动到屏幕中间
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
//        canvas.drawCircle(0, 0, 150, paint);
//        canvas.drawCircle(0, 0, 180, paint);
//        canvas.save();
//
//        //绘制弧形文字
//        canvas.translate(0, 0);
//        Path path = new Path();
//        RectF rect = new RectF(-100, -100, 100, 100);
//        path.addArc(rect, -110, 100);
//        Paint citePaint = new Paint(paint);
//        citePaint.setTextSize(28);
//        citePaint.setStrokeWidth(3);
//        canvas.drawTextOnPath("柏建宇的闹钟", path, 14, 0, paint);
//        canvas.restore();
//
//        Paint smallPaint = new Paint(paint); //非数字刻度画笔对象
//        smallPaint.setStrokeWidth(2);
//        smallPaint.setColor(Color.GRAY);
//        float y = 150;
//        int count = 60; //总刻度数
//        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        textPaint.setStrokeWidth(6);
//        textPaint.setColor(Color.BLUE);
//        textPaint.setTextSize(24);
//        textPaint.setStrokeWidth(3);
//        for (int i = 0; i < 60; i++) {
//            if (i % 5 == 0) {//数字刻度
//                canvas.drawText(i == 0 ? "12" : String.valueOf(i / 5), ((i / 5) > 9 || i == 0) ? -15f : -6f, -y - 5f, textPaint);
//            } else {//非数字刻度
//                canvas.drawLine(0f, y, 0f, y + 15f, smallPaint);
//            }
//            canvas.rotate(360 / count, 0, 0);
//        }

        Calendar calendar = Calendar.getInstance();
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentHour = calendar.get(Calendar.HOUR);
        int currentSecond = calendar.get(Calendar.SECOND);
        // 计算分针和时间的角度
        double secondRadian = Math.toRadians((360 - ((currentSecond * 6) - 90)) % 360);
        double minuteRadian = Math.toRadians((360 - ((currentMinute * 6) - 90)) % 360);
        double hourRadian = Math.toRadians((360 - ((currentHour * 30) - 90)) % 360 - (30 * currentMinute / 60));
        // 设置实针为6个象素粗
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(6);
        // 在表盘上画时针
        float mCenterX = 0;
        float mHourLength = 100;
        canvas.drawLine(mCenterX, mCenterX,
                (int) (mCenterX + mHourLength * Math.cos(hourRadian)),
                (int) (mCenterX - mHourLength * Math.sin(hourRadian)), paint);

        // 设置分针为4个象素粗
        paint.setStrokeWidth(4);
        float mMinuteLength = 120;
        // 在表盘上画分针
        canvas.drawLine(mCenterX, mCenterX,
                (int) (mCenterX + mMinuteLength * Math.cos(minuteRadian)),
                (int) (mCenterX - mMinuteLength * Math.sin(minuteRadian)),
                paint);
        // 设置分针为2个象素粗
        paint.setStrokeWidth(2);
        // 在表盘上画秒针
        float mSecondLength = 145;
        int centerY = 30;
        canvas.drawLine((int) (mCenterX - centerY * Math.cos(secondRadian)), (int) (mCenterX + centerY * Math.sin(secondRadian)),
                (int) (mCenterX + mSecondLength * Math.cos(secondRadian)),
                (int) (mCenterX - mSecondLength * Math.sin(secondRadian)),
                paint);
        canvas.drawCircle(0, 0, 5, paint);

        drawn = true;
        post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
//            Class clazz = Clock.this.getClass();
//            try {
//                Method method = clazz.getMethod("invalidate");
//
//                method.setAccessible(true);
//                method.invoke(Clock.this);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            postDelayed(this, 1000);
        }
    };
}
