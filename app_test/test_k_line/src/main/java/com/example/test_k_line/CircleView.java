package com.example.test_k_line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
    private int vWidth;
    private int vHeight;
    private float radius;
    private Paint paint;

    private int paintColor;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

        int circleColor = paintColor;
        if (circleColor == 0){
            circleColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.BLACK);
        }
        typedArray.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(circleColor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        vWidth = getWidth();
        vHeight = getHeight();
        radius = (float) Math.min(vWidth, vHeight) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle((float) vWidth/2, (float) vHeight/2, radius, paint);
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }
}
