package com.jys.lib_kline;

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

import java.util.ArrayList;
import java.util.List;

public class KLineView extends View {
    private static final String TAG = KLineView.class.getSimpleName();
    private Paint linePaint;
    private Path linePath;
    private List<KLineData> kLineDataList = new ArrayList<>(); // k线数据
    private static final int COLOR_DEFAULT = Color.BLACK; //画笔默认颜色
    private static final float SHADOW_ALPHA_DEFAULT = 0.3f; //阴影透明度
    private static final float OFFSET_Y_DEFAULT = 0.01f; //y轴默认偏移值
    private float drawHeight; // 实际绘制的区域高度
    private int linePaddingTop;
    private int linePaddingBottom;
    protected float linePaddingTopRate;
    protected float linePaddingBottomRate;
    private float vWidth;
    private int vHeight;

    public KLineView(Context context) {
        super(context);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //获取自定义属性，设置默认值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KLineView);
        linePaddingTop = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingTop, dp2px(3));
        linePaddingBottom = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingBottom, dp2px(3));
        linePaddingTopRate = typedArray.getFloat(R.styleable.KLineView_linePaddingTopRate, 0);
        linePaddingBottomRate = typedArray.getFloat(R.styleable.KLineView_linePaddingBottomRate, 0);
        typedArray.recycle();

        //初始化paint
        linePaint = new Paint();

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

        if (kLineDataList != null && !kLineDataList.isEmpty()){
            canvas.translate(0, vHeight-linePaddingBottom);
            for (KLineData kLineData : kLineDataList){
                if (kLineData == null || kLineData.points == null)
                    continue;
                setKLinePaint(kLineData);
                //画k线
                linePath.reset();
                linePath.moveTo(kLineData.points[0].x, -kLineData.points[0].y);
                for (int i=1; i<kLineData.points.length; i++) {
                    linePath.lineTo(kLineData.points[i].x, -kLineData.points[i].y);
                }
                canvas.drawPath(linePath, linePaint);

                //绘制K线下的阴影
                if (kLineData.shadowEnable){
                    linePath.lineTo(kLineData.points[kLineData.points.length-1].x, drawHeight);
                    linePath.lineTo(kLineData.points[0].x, drawHeight);

                    setShadowPaint(kLineData);
                    canvas.drawPath(linePath, linePaint);
                }

            }
        }
    }

    public void addKLineData(KLineData kLineData){
        kLineDataList.add(kLineData);
    }

    //配置k线画笔
    private void setKLinePaint(KLineData kLineData){
        if (linePaint == null || kLineData == null)
            return;

        linePaint.reset();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setDither(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND); //线头形状
        linePaint.setStrokeJoin(Paint.Join.ROUND); //线拐角的形状
        linePaint.setColor(getKLineColor(kLineData));
        linePaint.setStrokeWidth(kLineData.kLineWidth > 0 ? kLineData.kLineWidth : dp2px(1));//线宽
    }

    //配置阴影画笔
    private void setShadowPaint(KLineData kLineData){
        if (linePaint == null || kLineData == null)
            return;

        linePaint.reset();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setDither(true);
        float shadowAlpha = kLineData.shadowAlpha != 0 ? kLineData.shadowAlpha : SHADOW_ALPHA_DEFAULT;
        linePaint.setAlpha((int) (shadowAlpha * 255));
        linePaint.setStyle(Paint.Style.FILL);

        int shadowColorStart = kLineData.shadowColorStart;
        if (shadowColorStart == 0){
            shadowColorStart = getKLineColor(kLineData);
        }

        //设置渐变
        linePaint.setShader(new LinearGradient(0, -drawHeight, 0, 0, shadowColorStart, kLineData.shadowColorEnd, Shader.TileMode.CLAMP));
    }

    //获取K线颜色
    private int getKLineColor(KLineData kLineData){
        if (kLineData == null)
            return COLOR_DEFAULT;

        return kLineData.kLineColor != 0 ? kLineData.kLineColor : COLOR_DEFAULT;
    }

    //计算k线数据
    private void makeKLineData(){
        if (vHeight == 0)
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

        //计算具体坐标
        for (KLineData kLineData : kLineDataList){
            if (kLineData == null || kLineData.kLineValues == null || kLineData.kLineValues.length < 2)
                continue;

            kLineData.points = new KLineData.Point[kLineData.kLineValues.length];

            //设置x轴分割个数
            int spaceCount = (kLineData.xSpaceCount > 0) ? kLineData.xSpaceCount : kLineData.kLineValues.length -1;
            float offsetX = vWidth / spaceCount;
            float[] min_max = Util.minMaxValue(kLineData.kLineValues);
            float minValue = min_max[0];
            float maxValue = min_max[1];
            float offsetY = maxValue - minValue;
            if (offsetY < 2 * OFFSET_Y_DEFAULT){
                minValue -= OFFSET_Y_DEFAULT;
                offsetY += OFFSET_Y_DEFAULT * 2;
            }
            for (int i=0; i<kLineData.kLineValues.length; i++){
                KLineData.Point point = new KLineData.Point();
                point.x = i * offsetX;
                point.y = (kLineData.kLineValues[i] - minValue) / offsetY * drawHeight;

                kLineData.points[i] = point;
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

}
