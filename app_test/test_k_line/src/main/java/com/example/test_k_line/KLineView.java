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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KLineView extends View {
    private static final String TAG = KLineView.class.getSimpleName();
    private Paint linePaint;
    private Path linePath;
    private List<KLineData> kLineDataList = new ArrayList<>(); // k线数据
    private List<CandleData> candleDataList = new ArrayList<>(); //蜡烛图数据
    private static final int COLOR_DEFAULT = Color.BLACK; //画笔默认颜色
    private static final float SHADOW_ALPHA_DEFAULT = 0.3f; //阴影透明度
    private static final float OFFSET_Y_DEFAULT = 0.01f; //y轴默认偏移值
    private float drawHeight; // 实际绘制的区域高度
    private float drawWidth; // 实际绘制的区域宽度
    private int linePaddingTop;
    private int linePaddingBottom;
    protected float linePaddingTopRate;
    protected float linePaddingBottomRate;
    private int linePaddingLeft;
    private int linePaddingRight;
    protected float linePaddingLeftRate;
    protected float linePaddingRightRate;
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
        linePaddingTopRate = typedArray.getFloat(R.styleable.KLineView_linePaddingTopRate, 0);
        linePaddingBottom = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingBottom, dp2px(3));
        linePaddingBottomRate = typedArray.getFloat(R.styleable.KLineView_linePaddingBottomRate, 0);
        linePaddingLeft = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingLeft, 0);
        linePaddingLeftRate = typedArray.getFloat(R.styleable.KLineView_linePaddingLeftRate, 0);
        linePaddingRight = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingRight, 0);
        linePaddingRightRate = typedArray.getFloat(R.styleable.KLineView_linePaddingRightRate, 0);
        typedArray.recycle();

        //初始化paint、path
        linePaint = new Paint();
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

        canvas.translate(linePaddingLeft, vHeight-linePaddingBottom);

        //绘制K线
        if (kLineDataList != null && !kLineDataList.isEmpty()){
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
                    linePath.lineTo(kLineData.points[kLineData.points.length-1].x, 0);
                    linePath.lineTo(kLineData.points[0].x, 0);

                    setShadowPaint(kLineData);
                    canvas.drawPath(linePath, linePaint);
                }
            }
        }

        //绘制蜡烛图
        if (candleDataList != null && !candleDataList.isEmpty()){
            for(CandleData candleData : candleDataList){
                if (candleData == null)
                    continue;

                linePaint.reset();
                linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
                linePaint.setDither(true);
                float strokeWidth = candleData.lineWidth != 0 ? candleData.lineWidth : dp2px(1.5f);
                linePaint.setStrokeWidth(strokeWidth);

                //每个柱形图
                for (CandleData.Item item : candleData.itemList){
                    if (item == null || item.points == null || item.points.length == 0)
                        continue;

                    int paintColor = Color.BLACK;
                    if (item.start - item.end > 0){
                        paintColor = MainActivity.colorGreen; // TODO: 2021/4/14
                        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    }else if (item.start - item.end < 0){
                        paintColor = MainActivity.colorRed; // TODO: 2021/4/14
                        linePaint.setStyle(Paint.Style.STROKE);
                    }
                    linePaint.setColor(paintColor); // TODO: 2021/4/14

                    Point[] points = item.points;
                    canvas.drawLine(points[0].x, -points[0].y, points[1].x, -points[1].y, linePaint);
                    canvas.drawRect(points[2].x, -points[2].y, points[6].x, -points[6].y, linePaint);
                    canvas.drawLine(points[4].x, -points[4].y, points[5].x, -points[5].y, linePaint);

                    // lineto的绘制方式，会因为paint的style的设置导致绘制的内容和边框展示不全
//                    linePath.reset();
//                    linePath.moveTo(points[0].x, -points[0].y);
//                    for (int i=1; i<points.length; i++){
//                        linePath.lineTo(points[i].x, -points[i].y);
//
//                        if (i == 5){
//                            linePath.lineTo(points[4].x, -points[4].y);
//                        }
//
//                        if (i==7){
//                            linePath.lineTo(points[1].x, -points[1].y);
//                        }
//                    }
////                    canvas.drawPath(linePath, linePaint);
//                    canvas.drawPath(linePath, candlePaint);
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
        if (vWidth == 0 || vHeight == 0)
            return;

        //设置x轴左右边距
        if (linePaddingLeftRate > 0){
            linePaddingLeft = (int) (vWidth * linePaddingLeftRate);
        }
        if (linePaddingRightRate > 0){
            linePaddingRight = (int) (vWidth * linePaddingRightRate);
        }

        //设置y轴上下边距
        if (linePaddingTopRate > 0){
            linePaddingTop = (int) (vHeight * linePaddingTopRate);
        }
        if (linePaddingBottomRate > 0){
            linePaddingBottom = (int) (vHeight * linePaddingBottomRate);
        }
        //计算可绘制的界面高度
        drawWidth = vWidth - linePaddingLeft - linePaddingRight;
        drawHeight = vHeight - linePaddingTop - linePaddingBottom;

        //计算k线具体坐标
        for (KLineData kLineData : kLineDataList){
            if (kLineData == null || kLineData.kLineValues == null || kLineData.kLineValues.length < 2)
                continue;

            kLineData.points = new KLineData.Point[kLineData.kLineValues.length];
            //设置x轴分割个数
            int spaceCount = (kLineData.xSpaceCount > 0) ? kLineData.xSpaceCount : kLineData.kLineValues.length -1;
            float offsetX = drawWidth / spaceCount;
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

        //计算蜡烛图的数据
        for (CandleData candleData : candleDataList){
            List<CandleData.Item> list = candleData.itemList;
            //计算数据中的最大值和最小值
            float[] minMax = minMaxCandleData(list);
            float minValue = minMax[0];
            float maxValue = minMax[1];
            //x轴间距
            int spaceCount = (candleData.xSpaceCount > 0) ? candleData.xSpaceCount : list.size() -1;
            float offsetX = drawWidth / spaceCount;
            float offsetY = maxValue - minValue;
            if (offsetY < 2 * OFFSET_Y_DEFAULT){
                minValue -= OFFSET_Y_DEFAULT;
                offsetY += OFFSET_Y_DEFAULT * 2;
            }

            //计算每个蜡烛图的数据
            for (int i=0; i<list.size(); i++){
                CandleData.Item item = list.get(i);
                //计算蜡烛图矩形的宽度值
                float widthRate = candleData.candleWidthRate;
                if (widthRate <= 0)
                    widthRate = 0.6f;
                float candleWidth = offsetX * widthRate;
                float maxCandleWidth = candleData.maxCandleWidth;
                if (maxCandleWidth > 0 && candleWidth > maxCandleWidth){
                    candleWidth = candleData.maxCandleWidth;
                }

                //计算蜡烛图的8个point
                Point[] points = new Point[8];
                Point pointTop1 = new Point();
                pointTop1.x = i * offsetX;
                pointTop1.y = (item.max - minValue) / offsetY * drawHeight;
                points[0] = pointTop1;

                Point pointTop2 = new Point();
                pointTop2.x = pointTop1.x;
                pointTop2.y = (Math.max(item.start, item.end) - minValue) / offsetY * drawHeight;
                points[1] = pointTop2;

                Point pointLT = new Point();
                pointLT.x = pointTop1.x - candleWidth/2;
                pointLT.y = pointTop2.y;
                points[2] = pointLT;

                Point pointLB = new Point();
                pointLB.x = pointLT.x;
                pointLB.y = (Math.min(item.start, item.end) - minValue) / offsetY * drawHeight;
                points[3] = pointLB;

                Point pointBottom1 = new Point();
                pointBottom1.x = pointTop1.x;
                pointBottom1.y = pointLB.y;
                points[4] = pointBottom1;

                Point pointBottom2 = new Point();
                pointBottom2.x = pointBottom1.x;
                pointBottom2.y = (item.min - minValue) / offsetY * drawHeight;
                points[5] = pointBottom2;

                Point pointRB = new Point();
                pointRB.x = pointTop1.x + candleWidth/2;
                pointRB.y = pointBottom1.y;
                points[6] = pointRB;

                Point pointRT = new Point();
                pointRT.x = pointRB.x;
                pointRT.y = pointLT.y;
                points[7] = pointRT;

                item.points = points;
            }
        }
    }

    public void addCandleData(CandleData candleData){
        candleDataList.add(candleData);
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    //计算蜡烛图的最大值，最小值，来确定上下界
    private float[] minMaxCandleData(List<CandleData.Item> list){
        float[] result = new float[2];
        List<CandleData.Item> listTemp = new ArrayList<>(list);
        //计算最小值
        Collections.sort(listTemp, new Comparator<CandleData.Item>() {
            @Override
            public int compare(CandleData.Item o1, CandleData.Item o2) {
                return Float.compare(o1.min-o2.min, 0f);
            }
        });
        result[0] = listTemp.get(0).min;

        //计算最大值
        Collections.sort(listTemp, new Comparator<CandleData.Item>() {
            @Override
            public int compare(CandleData.Item o1, CandleData.Item o2) {
                return Float.compare(o1.max-o2.max, 0f);
            }
        });
        result[1] = listTemp.get(listTemp.size()-1).max;

        return result;
    }
}
