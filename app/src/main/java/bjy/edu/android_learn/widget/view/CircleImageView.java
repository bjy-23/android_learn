package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by BJY on 2018/4/19.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private int length;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.e("宽度", getMeasuredWidth() +"");
        Log.e("高度", getMeasuredHeight() +"");

        length = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        Drawable drawable = getDrawable();
        int width = drawable.getIntrinsicWidth();
        Log.e("drawable 宽度", width + "");
        int height = drawable.getIntrinsicHeight();
        Log.e("drawable 高度", height + "");
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        Log.e("bitmap大小", bitmap.getByteCount() / 1024 + "K");

        float scale = getWidth() / Math.min(width, height);

        drawable.setBounds(0, 0, (int)(scale* width), (int)(scale*height));


        //画笔
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        //画布
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(target);

//        drawable.draw(c);

        //先绘制圆
        c.drawCircle(length/2, length/2, length/2, paint);

        paint.reset();
        paint.setFilterBitmap(false);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Rect rect = new Rect(0, 0, length, length);
        //绘制图片
        c.drawBitmap(bitmap,0, 0, paint);

        canvas.drawBitmap(target, 0, 0, null);

    }
}
