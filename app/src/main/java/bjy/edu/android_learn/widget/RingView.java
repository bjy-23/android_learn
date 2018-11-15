package bjy.edu.android_learn.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RingView extends View {
    Context context;
    Paint paintRed;
    Paint paintGreen;
    RectF rectF;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.e("width", getWidth()+"");
        Log.e("height", getHeight()+"");

        rectF = new RectF(0+dp2px(15), 0+dp2px(15), getWidth()-dp2px(15), getHeight()-dp2px(15));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(rectF, 0, 270, false, paintRed);
        canvas.drawArc(rectF, 1, -91, false, paintGreen);
    }

    public void init(){
        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setColor(0xfff45d50);
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeWidth(dp2px(30));

        paintGreen = new Paint();
        paintGreen.setAntiAlias(true);
        paintGreen.setColor(0xff65b51f);
        paintGreen.setStyle(Paint.Style.STROKE);
        paintGreen.setStrokeWidth(dp2px(30));
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }


}
