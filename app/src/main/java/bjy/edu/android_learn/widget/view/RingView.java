package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RingView extends View {
    private int width;
    private int height;
    Context context;
    Paint paint1;
    Paint paint2;
    RectF rectF;

    SweepGradient sweepGradient2;

    int color0 = Color.parseColor("#9DB3FF");
    int color1 = Color.parseColor("#4A6AFF");

    float offset = 0.75f;

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

        width = getWidth();
        height = getHeight();
        Log.e("width", width+"");
        Log.e("height", width+"");

        //设置弧线的内切的矩形, 偏移值为 弧宽的一半
        rectF = new RectF(0+dp2px(15), 0+dp2px(15), getWidth()-dp2px(15), getHeight()-dp2px(15));

        //设置渐变
        // color0 正右方向处的颜色值；color1是旋转360度回到正右方向的颜色值（与实际的起始角度、结束角度无关）; 所以如果画弧不满360度不适合用这个构造
//        sweepGradient = new SweepGradient(width/2, height/2, Color.parseColor("#9DB3FF"), Color.parseColor("#4A6AFF"));

        //positions范围[0, 1] 0:对应一个平面的正右方向；1:顺时针旋转360度回到正右方向
        //实际绘图时需要旋转，使实际起始位置和 position 0 相对应
        //paint的线头设置为圆头，考虑位置0线头的颜色，需要在position 0.99处设置 position 0的颜色值
        sweepGradient2 = new SweepGradient(width/2, height/2, new int[]{color0, color1, color0}, new float[]{0, offset, 0.99f});
        paint2.setShader(sweepGradient2);
        paint2.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //0 : 起始角度； 270：旋转角度(>0, 顺时针)
//        canvas.drawArc(rectF, 270, 270, false, paint1);

//        canvas.rotate(-90, width/2, height/2);
        canvas.rotate(270, width/2, height/2);
        canvas.drawArc(rectF, 0, 360*offset, false, paint2);

    }

    public void init(){

        paint1 = new Paint();
        paint1.setAntiAlias(true);
//        paint1.setColor(0xff4A6AFF);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(dp2px(30)); // 画笔的宽度一半在rectF外，一半在rectF里

        paint2 = new Paint();
        paint2.setAntiAlias(true);
//        paint2.setColor(0xff4A6AFF);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(dp2px(30));
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }


}
