package bjy.edu.android_learn.widget.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by CH-ZH on 2018/7/11.
 */

public class CircleWaveView extends View {
    private Context context;
    private int  width = 0;
    private int height = 0;
    private int baseLine = 60;// 基线，用于控制水位上涨的，这里是写死了没动，你可以不断的设置改变。
    private int baseLine1 = 60;
    private int baseLine2 = 60;
    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;
    private int waveHeight = 70;// 波浪的最高度
    private int waveHeight1 = 80;
    private int waveHeight2 = 75;
    private int waveWidth  ;//波长
    private float offset =0f;//偏移量
    private float offset_1 =0f;//偏移量
    private float offset_0 =100f;//偏移量
    private float heightBase;
    private Path mPath = new Path();
    public CircleWaveView(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CircleWaveView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public CircleWaveView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 不断的更新偏移量，并且循环。
     */
    private void updateXControl(){
        //设置一个波长的偏移
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator mAnimator2 = ValueAnimator.ofFloat(0,offset_0,0,-offset_0,0);
        mAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float)animation.getAnimatedValue();
                offset_1 = animatorValue;//不断的设置偏移量，并重画
                Log.e("offset_1", offset_1+"");
            }
        });
//        mAnimator2.setRepeatCount(ValueAnimator.INFINITE);

        ValueAnimator mAnimator = ValueAnimator.ofFloat(0,1);
//        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float)animation.getAnimatedValue();
//                Log.e("TAG","animatorValue  ===" + animatorValue);
                offset = animatorValue;//不断的设置偏移量，并重画
                postInvalidate();
            }
        });
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        mAnimator.setDuration(3500);
//        mAnimator.start();

        animatorSet.playTogether(mAnimator, mAnimator2);
        animatorSet.setDuration(3500);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        waveWidth = width;
        heightBase = height/2 - 200f;

        updateXControl();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(offset*width, 0);
        mPath.reset();
        mPath.addCircle(width/2-offset*width, height/2, width/2, Path.Direction.CW);
        canvas.clipPath(mPath);
        mPath.reset();
        mPath.setFillType(Path.FillType.WINDING);

        //偏移值是100,但最低点不是-100！！！
        mPath.moveTo(- width, heightBase);
        mPath.cubicTo(-3*width/4, heightBase+offset_0, -width/4, heightBase-offset_0, 0, heightBase);
        mPath.lineTo(0, heightBase);
        mPath.lineTo(0, height);
        mPath.lineTo(-width, height);
        mPath.moveTo(0, heightBase);
        mPath.cubicTo(width/4, heightBase+offset_0, width*3/4, heightBase-offset_0, width, heightBase);
        mPath.lineTo(width, heightBase);
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);

//        mPath.addCircle(width/2, height/2, width/2, Path.Direction.CW);
//        mPath.close();
        canvas.drawPath(mPath, mPaint2);
//
//        canvas.restore();

//        double a = Math.atan(100/(width/2));
////        double a = Math.atan(1);
//        double b = Math.toDegrees(a);
//        b = 0;
//        Log.e("width", ""+width/2);
//        Log.e("度数", ""+b);
//        RectF rectF = new RectF(0-offset*width, 0, width-offset*width, height);
//        mPath.arcTo(rectF, (float) (0-b), (float) (180+2*b), true);
//        mPath.close();

//        canvas.drawPath(mPath, mPaint2);

    }

    private void initView(){
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#CBC1E8"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mPaint1 = new Paint();
        mPaint1.setColor(Color.parseColor("#b4FC2732"));
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setAntiAlias(true);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.parseColor("#7fFC2732"));
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setAntiAlias(true);

        LinearGradient linearGradient = new LinearGradient(0, 0, width, height, Color.parseColor("#7fFC2732"), Color.parseColor("#ffFC2732"), Shader.TileMode.MIRROR);
        mPaint2.setShader(linearGradient);
    }

    //todo 高度改变后从之前值梯度增长到改变值
    public void setHeightBase(float heightBase){
        this.heightBase = heightBase;
    }
}
