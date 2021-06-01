package com.jys.lib_kline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KLineView extends View {
    private static final String TAG = KLineView.class.getSimpleName();
    private static final String TAG_TEST = "112233";
    private Context context;
    private Paint linePaint;
    private Path linePath;
    private List<KLineData> kLineDataList = new ArrayList<>(); // k线数据
    private List<CandleData> candleDataList = new ArrayList<>(); //蜡烛图数据
    private List<RectData> rectDataList = new ArrayList<>(); //柱形图数据, 对用户屏蔽
    private List<PillarData> pillarDataList = new ArrayList<>();
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


    //长按 十字线
    public int indexColor = Color.BLACK;
    public int indexWidth = 1;
    private Handler handler = new Handler(Looper.getMainLooper());
    public static int TIME_LONG_PRESS = 500; //长按n秒后开始绘制
    public static int TIME_INDEX_DISMISS = 1000;//手指离开n秒后消失

    public int indexSpaceCount;
    @Deprecated
    public boolean indexLineEnable = false;
    private boolean drawIndexLineEnable = false;
    private boolean beginIndexPress = false;
    private float xDown;
    private float yDown;
    private float xIndex;
    private float yIndex;
    private int minTouchSlop;
    private double minTouchSlopPow2;
    public int xIndexPosition = -1;

    //十字线，随k线点变化
    int index_k_position = 0; //随第n条k线变化而变化，默认第一条

    //背景线
    public boolean xBgLineEnable = false;
    public float[] xBgLineArray;
    public List<BgLineItem> xBgLines = new ArrayList<>();

    public boolean yBgLineEnable = false;
    public float[] yBgLineArray;//按照比例设置y轴虚线
    public List<BgLineItem> yBgLines = new ArrayList<>();

    public KLineView(Context context) {
        super(context);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //获取自定义属性，设置默认值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KLineView);
        linePaddingTop = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingTop, 0);
        linePaddingTopRate = typedArray.getFloat(R.styleable.KLineView_linePaddingTopRate, 0);
        linePaddingBottom = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingBottom, 0);
        linePaddingBottomRate = typedArray.getFloat(R.styleable.KLineView_linePaddingBottomRate, 0);
        linePaddingLeft = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingLeft, 0);
        linePaddingLeftRate = typedArray.getFloat(R.styleable.KLineView_linePaddingLeftRate, 0);
        linePaddingRight = typedArray.getDimensionPixelOffset(R.styleable.KLineView_linePaddingRight, 0);
        linePaddingRightRate = typedArray.getFloat(R.styleable.KLineView_linePaddingRightRate, 0);
        typedArray.recycle();

        //初始化paint
        linePaint = new Paint();

        //初始化path，用于绘制阴影
        linePath = new Path();

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        minTouchSlop = viewConfiguration.getScaledTouchSlop();
        minTouchSlopPow2 = Math.pow(minTouchSlop, 2);
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
        canvas.save();

        canvas.translate(linePaddingLeft, vHeight-linePaddingBottom);

        //背景线
        drawBgLine(canvas);

        //绘制蜡烛图
        drawCandle(canvas);

        //绘制柱形图
        drawRect(canvas);

        //绘制线
        drawLines(canvas);

        drawIndex(canvas);

        drawIndexByPosition(canvas);

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);

//        Log.i("111222", "Kline : event: " + event.getAction()+ "  result: " +result);
        return result;
    }

    public void addKLineData(KLineData kLineData){
        kLineDataList.add(kLineData);
    }

    public void clearKLineData(){
        kLineDataList.clear();
    }

    public void addCandleData(CandleData candleData){
        candleDataList.add(candleData);
    }

    public void clearCandleData(){
        candleDataList.clear();
    }

    public void addPillarData(PillarData pillarData){
        pillarDataList.add(pillarData);
    }

    public void clearAllData(){
        kLineDataList.clear();
        candleDataList.clear();
        pillarDataList.clear();

        indexSpaceCount = 0;
        yBgLineEnable = false;
        xBgLineEnable = false;
    }

    //重新计算所有数据
    public void refreshAllData(){
        makeKLineData();
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
        drawWidth = vWidth - linePaddingLeft - linePaddingRight;
        drawHeight = vHeight - linePaddingTop - linePaddingBottom;

        //计算k线的数据
        calculateKData();

        //计算蜡烛图的数据
        calculateCandleData();

        //计算柱形图数据
        calculateRectData();
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

    //计算蜡烛图的最大值，最小值，来确定上下界
    private float[] minMaxPillarData(List<PillarData.Item> list){
        float[] result = new float[2];
        List<PillarData.Item> listTemp = new ArrayList<>(list);
        //计算最小值
        Collections.sort(listTemp, new Comparator<PillarData.Item>() {
            @Override
            public int compare(PillarData.Item o1, PillarData.Item o2) {
                return Float.compare(o1.bottom-o2.bottom, 0f);
            }
        });
        result[0] = listTemp.get(0).bottom;

        //计算最大值
        Collections.sort(listTemp, new Comparator<PillarData.Item>() {
            @Override
            public int compare(PillarData.Item o1, PillarData.Item o2) {
                return Float.compare(o1.top-o2.top, 0f);
            }
        });
        result[1] = listTemp.get(listTemp.size()-1).top;

        return result;
    }

    private void calculateKData(){
        //计算具体坐标
        for (KLineData kLineData : kLineDataList){
            if (kLineData == null || kLineData.kLineValues == null || kLineData.kLineValues.length < 2)
                continue;

            kLineData.points = new KLineData.Point[kLineData.kLineValues.length];

            //设置x轴分割个数
            int spaceCount = (kLineData.xSpaceCount > 0) ? kLineData.xSpaceCount : kLineData.kLineValues.length -1;
            float offsetX = drawWidth / spaceCount;

            //计算y轴数据的间距差
            float minValue = kLineData.yValueMin;
            float maxValue = kLineData.yValueMax;
            if (minValue == maxValue){
                float[] min_max = Util.minMaxValue(kLineData.kLineValues);
                minValue = min_max[0];
                maxValue = min_max[1];
            }
            float offsetY = maxValue - minValue;
            // TODO: 2021/4/15
            if (offsetY < 2 * OFFSET_Y_DEFAULT){
                minValue -= OFFSET_Y_DEFAULT;
                offsetY += OFFSET_Y_DEFAULT * 2;
            }

            for (int i=0; i<kLineData.kLineValues.length; i++){
                KLineData.Point point = new KLineData.Point();
                point.x = (i + kLineData.xSpaceOffset) * offsetX;
                point.y = (kLineData.kLineValues[i] - minValue) / offsetY * drawHeight;

                kLineData.points[i] = point;
            }
        }
    }

    //计算矩形图数据
    private void calculateCandleData(){
        for (CandleData candleData : candleDataList){
            List<CandleData.Item> list = candleData.itemList;
            //x轴间距、个数
            int spaceCount = (candleData.xSpaceCount > 0) ? candleData.xSpaceCount : list.size() -1;
            float offsetX = drawWidth / spaceCount;

            //y轴间距
            float minValue = candleData.yValueMin;
            float maxValue = candleData.yValueMax;
            if (minValue == maxValue){
                float[] min_max = minMaxCandleData(list);
                minValue = min_max[0];
                maxValue = min_max[1];
            }
            float offsetY = maxValue - minValue;
            // TODO: 2021/4/15
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

    private void drawBgLine(Canvas canvas){
        initBgPaint();

        if (xBgLineEnable){
            for (BgLineItem bgLineItem : xBgLines){
                initBgLinePaint(bgLineItem);
                canvas.drawLine(bgLineItem.rate*drawWidth, 0, bgLineItem.rate*drawWidth, -drawHeight, linePaint);
            }
        }

        if (yBgLineEnable){
            for (BgLineItem bgLineItem : yBgLines){
                initBgLinePaint(bgLineItem);
                canvas.drawLine(0, -bgLineItem.rate*drawHeight, drawWidth, -bgLineItem.rate*drawHeight, linePaint);
            }
        }


        if (xBgLineEnable && xBgLineArray != null && xBgLineArray.length != 0){
            if (xBgLineArray != null && xBgLineArray.length != 0){
                for (float value : xBgLineArray){
                    canvas.drawLine(value*drawWidth, 0, value*drawWidth, -drawHeight, linePaint);
                }
            }
        }

        if (yBgLineEnable && yBgLineArray != null && yBgLineArray.length != 0){
            if (yBgLineArray != null && yBgLineArray.length != 0){
                for (float value : yBgLineArray){
                    canvas.drawLine(0, -value*drawHeight, drawWidth, -value*drawHeight, linePaint);
                }
            }
        }
    }

    private void initBgPaint(){
        linePaint.reset();
        linePaint.setDither(true);
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.parseColor("#E4E4E4"));
        linePaint.setStrokeWidth(dp2px(1f));
        linePaint.setPathEffect(new DashPathEffect(new float[]{dp2px(2), dp2px(1)}, 0));
    }

    private void initBgLinePaint(BgLineItem bgLineItem){
        linePaint.reset();
        linePaint.setDither(true);
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(bgLineItem.color);
        linePaint.setStrokeWidth(bgLineItem.width);
        if (bgLineItem.style == BgLineItem.STYLE_DOTED){
            linePaint.setPathEffect(new DashPathEffect(new float[]{dp2px(3), dp2px(2)}, 0));
        }
    }

    private void drawLines(Canvas canvas){
        if (kLineDataList != null && !kLineDataList.isEmpty()){
            for (KLineData kLineData : kLineDataList){
                if (kLineData == null || kLineData.points == null || kLineData.points.length == 0)
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
    }

    private void drawCandle(Canvas canvas){
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
                    if (item.drawColor != 0)
                        paintColor = item.drawColor;
                    linePaint.setColor(paintColor);

                    if (item.style == CandleData.Item.STYLE_DEFAULT){
                        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    }else {
                        linePaint.setStyle(Paint.Style.STROKE);
                    }

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


    //计算矩形图数据
    private void calculateRectData(){
        //每次计算清除数据
        rectDataList.clear();
        if (pillarDataList == null || pillarDataList.isEmpty())
            return;

        for (PillarData pillarData : pillarDataList){
            if (pillarData == null || pillarData.list == null || pillarData.list.isEmpty())
                continue;

            List<PillarData.Item> list = pillarData.list;
            //x轴间距、个数
            int spaceCount = (pillarData.xSpaceCount > 0) ? pillarData.xSpaceCount : list.size() -1;
            float offsetX = drawWidth / spaceCount;
            //x轴起始点位置
            int positionStart = Math.max(pillarData.xSpaceOffset, 0);

            //y轴间距
            float minValue = pillarData.yValueMin;
            float maxValue = pillarData.yValueMax;
            if (minValue == maxValue){
                float[] min_max = minMaxPillarData(list);
                minValue = min_max[0];
                maxValue = min_max[1];
            }
            float offsetY = maxValue - minValue;
            // TODO: 2021/4/15
            if (offsetY < 2 * OFFSET_Y_DEFAULT){
                minValue -= OFFSET_Y_DEFAULT;
                offsetY += OFFSET_Y_DEFAULT * 2;
            }

            for (int i=0; i<list.size(); i++){
                PillarData.Item item = list.get(i);
                if (item == null)
                    continue;

                float pillarWidthRate = Math.max(0f, pillarData.pillarWidthRate);
                if (pillarWidthRate == 0f)
                    pillarWidthRate = 0.6f;
                float pillarWidth = offsetX * pillarWidthRate;
                if (pillarData.pillarWidthMax > 0f)
                    pillarWidth = Math.min(pillarWidth, pillarData.pillarWidthMax);

                RectData rectData = new RectData();
                rectData.drawColor = item.color;
                rectData.lineWidth = item.lineWidth;
                rectData.style = item.style;
                RectData.Item rectItem = new RectData.Item();
                rectItem.left = (positionStart+i) * offsetX - pillarWidth/2;
                rectItem.right = (positionStart+i) * offsetX + pillarWidth/2;
                rectItem.top = (item.top - minValue) / offsetY * drawHeight;
                rectItem.bottom = (item.bottom - minValue) / offsetY * drawHeight;
                rectData.item = rectItem;

                rectDataList.add(rectData);
            }
        }
    }

    private void drawRect(Canvas canvas){
       if (rectDataList == null || rectDataList.isEmpty())
           return;

       //todo ceshi
//        for (int i=0; i<rectDataList.size(); i++){
//            Log.i("drawRect-test-" + i, "width " + (rectDataList.get(i).item.right - rectDataList.get(i).item.left));
//        }

        //设置画笔
        initPaint();

       for(RectData rectData : rectDataList){
           if (rectData == null || rectData.item == null)
               continue;

           if (rectData.drawColor != 0)
                linePaint.setColor(rectData.drawColor);
           if (rectData.lineWidth != 0)
               linePaint.setStrokeWidth(rectData.lineWidth);

           if (rectData.style == PillarData.Item.STYLE_DEFAULT){
               linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
           }else {
               linePaint.setStyle(Paint.Style.STROKE);
           }

            canvas.drawRect(rectData.item.left, -rectData.item.top, rectData.item.right, -rectData.item.bottom, linePaint);
       }
    }

    private void drawIndex(Canvas canvas){
        if (!indexLineEnable)
            return;

        if (!drawIndexLineEnable)
            return;

        initIndexPaint();

        //计算
        float xIndexOffset = xIndex - getPaddingLeft() - linePaddingLeft;

        int x_index_position = 0;
        if (indexSpaceCount != 0){
            x_index_position = (int) (xIndexOffset / drawWidth * indexSpaceCount);
            xIndexOffset = x_index_position * (drawWidth / indexSpaceCount);
        }

        canvas.drawLine(0f, yIndex-drawHeight, drawWidth, yIndex-drawHeight, linePaint);
        canvas.drawLine(xIndexOffset, 0f, xIndexOffset, -drawHeight, linePaint);

        if (indexListener != null){
            float yRate = Math.abs((yIndex-drawHeight) / drawHeight);
            indexListener.drawIndexPosition(x_index_position, yRate);
        }
    }


    private void drawIndexByPosition(Canvas canvas){
        if (!indexLineEnable)
            return;

        if (xIndexPosition <0 || xIndexPosition > indexSpaceCount)
            return;

        initIndexPaint();

        float xValue =  drawWidth / indexSpaceCount * xIndexPosition;
        canvas.drawLine(xValue, 0f, xValue, -drawHeight, linePaint);
    }


    private void cancelIndex(){
        beginIndexPress = false;
        drawIndexLineEnable = false;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                if (indexListener != null){
                    indexListener.drawIndexEnd();
                }
            }
        }, TIME_INDEX_DISMISS);
    }

    private void initPaint(){
        if (linePaint == null)
            linePaint = new Paint();

        linePaint.reset();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(1);
    }

    private void initIndexPaint(){
        if (linePaint == null)
            linePaint = new Paint();

        linePaint.reset();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setDither(true);
        linePaint.setColor(indexColor);
        linePaint.setStrokeWidth(indexWidth);
    }


    public interface IndexListener{
        void drawIndexStart();

        void drawIndexEnd();

        //返回当前十字线对应的点的位置
        void drawIndexPosition(int position, float yRate);
    }

    private IndexListener indexListener;

    public void setIndexListenr(IndexListener indexListener){
        this.indexListener = indexListener;
    }

    public static class BgLineItem{
        float rate; //x轴或者y轴的比例
        int color;
        int width;
        int style = STYLE_STRAIGHT;

        public BgLineItem(float rate, int color, int width, int style) {
            this.rate = rate;
            this.color = color;
            this.width = width;
            this.style = style;
        }

        public static final int STYLE_STRAIGHT  = 0;//直线
        public static final int STYLE_DOTED = 1;//虚线
    }
}
