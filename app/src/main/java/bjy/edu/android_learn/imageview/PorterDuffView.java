package bjy.edu.android_learn.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import bjy.edu.android_learn.App;

public class PorterDuffView extends View {
    private PorterDuff.Mode mode;
    private Paint paint;

    public PorterDuffView(Context context) {
        this(context, null);
    }

    public PorterDuffView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = Math.min(getWidth(), getHeight());

        //dst
        paint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, width/2, width/2, paint);

        //src
        if (mode != null)
            paint.setXfermode(new PorterDuffXfermode(mode));
        paint.setColor(Color.GREEN);
        canvas.drawCircle(width/2, width/2, width/2, paint);




//        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
//        Canvas canvasTemp = new Canvas(bitmap);
//
//        //dst
//        paint.setColor(Color.BLUE);
//        canvasTemp.drawRect(0, 0, width/2, width/2, paint);
//
//
//        //src
//        if (mode != null)
//            paint.setXfermode(new PorterDuffXfermode(mode));
//        paint.setColor(Color.GREEN);
//        canvasTemp.drawCircle(width/2, width/2, width/2, paint);
//
//        canvas.drawBitmap(bitmap, 0, 0, new Paint());
    }

    public PorterDuff.Mode getMode() {
        return mode;
    }

    public void setMode(PorterDuff.Mode mode) {
        this.mode = mode;

        invalidate();
    }

    private float dp2px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
}
