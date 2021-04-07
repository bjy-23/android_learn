package com.example.test_k_line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class KLineView extends View {
    private static final String TAG = KLineView.class.getSimpleName();
    private Paint linePaint;
    private Paint shadowPaint;
    private float shadowAlpha; //阴影透明度
    private boolean shadowEnable; // 是否允许绘制阴影
    private Path linePath;
    private float[] valueArray; // k线图高度值
    private int lineColor; // 绘制线的颜色
    private int shadowColorStart;
    private int shadowColorEnd = Color.TRANSPARENT; //默认透明
    private int lineWidth; //绘制线的宽度

    private float drawHeight; // 实际绘制的区域高度
    private int linePaddingTop;
    private int linePaddingBottom;
    private float linePaddingTopRate;
    private float linePaddingBottomRate;
    private int vWidth;
    private int vHeight;

    float[] lineValues; //作废
    Point[] points;

    public KLineView(Context context) {
        super(context);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KLineView);
        lineColor = typedArray.getColor(R.styleable.KLineView_lineColor, Color.RED);
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.KLineView_lineWidth, dp2px(2));
        shadowEnable = typedArray.getBoolean(R.styleable.KLineView_shadowEnable, true);
        shadowColorStart = typedArray.getColor(R.styleable.KLineView_shadowColorStart, lineColor);
        shadowColorEnd = typedArray.getColor(R.styleable.KLineView_shadowColorEnd, Color.TRANSPARENT);
        shadowAlpha = typedArray.getFloat(R.styleable.KLineView_shadowAlpha, 0.3f);
        linePaddingTop = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingTop, dp2px(3));
        Log.i(TAG, String.format("linePaddingTop [%s]", linePaddingTop + ""));
        linePaddingBottom = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingBottom, dp2px(3));
        linePaddingTopRate = typedArray.getFloat(R.styleable.KLineView_linePaddingTopRate, 0);
        linePaddingBottomRate = typedArray.getFloat(R.styleable.KLineView_linePaddingBottomRate, 0);
        typedArray.recycle();

        //初始化paint
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setDither(true);
        linePaint.setStrokeCap(Paint.Cap.ROUND); //线头形状
        linePaint.setStrokeJoin(Paint.Join.ROUND); //线拐角的形状
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWidth);//线宽
        linePaint.setStyle(Paint.Style.STROKE);

        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setDither(true);
        shadowPaint.setAlpha((int) (255 * shadowAlpha));
        setShadowShader();

        //初始化path，用于绘制阴影
        linePath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.i(TAG, String.format("onLayout width[%d] height[%d]", getWidth(), getHeight()));

        vWidth = getWidth();
        vHeight = getHeight();

        makeKLineData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (lineValues != null){
            canvas.translate(0, vHeight);
            linePath.reset();
            linePath.moveTo(points[0].x, -points[0].y);
            for (int i=1; i<points.length; i++) {
                linePath.lineTo(points[i].x, -points[i].y);
            }
            canvas.drawPath(linePath, linePaint);

            //绘制K线下的阴影
            if (shadowEnable){
                linePath.lineTo(points[points.length-1].x, drawHeight);
                linePath.lineTo(points[0].x, drawHeight);
                canvas.drawPath(linePath, shadowPaint);
            }
        }
    }


    public void setValueArray(float[] valueArray) {
        this.valueArray = valueArray;

        if (valueArray == null || valueArray.length < 2){
            return;
        }

        //初始化数组长度
        points = new Point[valueArray.length];
        lineValues = new float[4 * (valueArray.length - 1)];
    }

    // 格式：#FFFFFF
    public void setLineColor(String lineColor) {
        setLineColor(Color.parseColor(lineColor));
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        linePaint.setColor(lineColor);
    }

    public void setShadowEnable(boolean shadowEnable) {
        this.shadowEnable = shadowEnable;
    }

    public void setShadowColorStart(String shadowColorStart) {
        try {
            setShadowColorStart(Color.parseColor(shadowColorStart));
        }catch (Exception e){
        }
    }

    public void setShadowColorStart(int shadowColorStart) {
        this.shadowColorStart = shadowColorStart;
        setShadowShader();
    }

    public void setShadowColorEnd(String shadowColorEnd) {
        try {
            setShadowColorEnd(Color.parseColor(shadowColorEnd));
        }catch (Exception e){
        }
    }

    public void setShadowColorEnd(int shadowColorEnd) {
        this.shadowColorEnd = shadowColorEnd;
        setShadowShader();
    }

    public void setShadowAlpha(float shadowAlpha) {
        this.shadowAlpha = shadowAlpha;
        if (shadowPaint != null){
            shadowPaint.setAlpha((int) (255*shadowAlpha));
        }
    }

    private void setShadowShader(){
        if (shadowPaint != null){
            // TODO: 2021/4/7  y1 ?????
            shadowPaint.setShader(new LinearGradient(0, -dp2px(50), 0, 0, shadowColorStart, shadowColorEnd, Shader.TileMode.CLAMP));
        }
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        linePaint.setStrokeWidth(lineWidth);
    }

    private void makeKLineData(){
        if (lineValues == null || vHeight == 0)
            return;
        //设置y轴上下边距
        if (linePaddingTopRate > 0){
            linePaddingTop = (int) (vHeight * linePaddingTopRate);
        }
        if (linePaddingBottomRate > 0){
            linePaddingBottom = (int) (vHeight * linePaddingBottomRate);
        }
        //计算可绘制的界面高度
        drawHeight = vHeight - linePaddingTop - linePaddingBottom;
        Log.i(TAG, String.format("drawHeight [%s]", drawHeight+""));
        //x轴间距
        int offset = getWidth() / (valueArray.length -1);
        // TODO: 2021/4/7 考虑负数的处理
        float maxHeight = maxValue(valueArray);
        Log.i(TAG, String.format("maxHeight [%s]", maxHeight+""));
        for (int i=0; i<valueArray.length; i++){
            Point point = new Point();
            point.x = i * offset;
            point.y = valueArray[i] / maxHeight * drawHeight;
            points[i] = point;

            Log.i(TAG, point.toString());
        }

//        for (int i=0; i<valueArray.length-1; i++){
//            lineValues[4*i] = i * offset;
//            lineValues[4*i+1] = valueArray[i] / maxHeight * drawHeight;
//            lineValues[4*i+2] = (i+1) * offset;
//            lineValues[4*i+3] = valueArray[i+1] / maxHeight * drawHeight;
//
//            Log.i(TAG, String.format("i[%d] %s", i, lineValues[4*i] + "-" +lineValues[4*i + 1] + "-"+ lineValues[4*i+2] + "-"+ lineValues[4*i+3] + "-"));
//        }


    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    // TODO: 2021/4/7 可用的排序算法
    private float maxValue(float[] valueArray){
        if (valueArray == null || valueArray.length == 0)
            return -1;
        float tempValue = valueArray[0];
        for (int i=0; i<valueArray.length-1; i++){
            tempValue = Math.max(tempValue, valueArray[i+1]);
        }

        return tempValue;
    }


    public static class Point{
        float x;
        float y;

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
