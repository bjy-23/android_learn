package com.jys.lib_kline.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RectView extends View {
    Paint paint;
    Path path;

    int arc = 10;

    public RectView(Context context) {
        this(context, null);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.RED);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.moveTo(0, arc);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}

