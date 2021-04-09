package com.example.test_k_line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private int vWidth;
    private int vHeight;
    private float radius;
    private Paint paint;

    private int backgroundColor;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.background});
        backgroundColor = typedArray.getColor(0, Color.BLACK);
        typedArray.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setColor(Color.GREEN);
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
        //创建新的canvas, bitmap
        Bitmap bitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(bitmap);
        paint.setColor(Color.WHITE);
        paint.setColor(Color.GREEN);
        canvasTemp.drawCircle(vWidth/2, vHeight/2, radius, paint);
//        canvasTemp.drawBitmap(bitmap, 0, 0, paint);

//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));

        canvas.drawBitmap(bitmap, 0, 0, paint);

//        super.onDraw(canvas);
    }
}
