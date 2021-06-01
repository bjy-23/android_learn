package com.jys.lib_kline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;

public class IndexView extends View {
    private static final String TAG = "IndexView";
    Paint linePaint;
    TextPaint textPaint;
    Rect textRect; //文字背景
    int rectWidth;
    int rectHeight;

    private float drawHeight; // 实际绘制的区域高度
    private float drawWidth; // 实际绘制的区域宽度
    public int indexSpaceCount;
    private Handler handler = new Handler(Looper.getMainLooper());
    public static int TIME_LONG_PRESS = 300;
    public static int TIME_INDEX_DISMISS = 1000;
    public boolean indexLineEnable = false;
    private boolean drawIndexLineEnable = false;
    public int colorIndex = Color.BLACK;
    public boolean indexByPoint = true;
    private boolean beginIndexPress = false;
    private float xDown;
    private float yDown;
    private float xIndex;
    private float yIndex;
    private int minTouchSlop;
    private double minTouchSlopPow2;
    public int xIndexPosition = -1;

    public int indexStyle = INDEX_STYLE_LIMIT;
    public static final int INDEX_STYLE_FREE = 0;
    public static final int INDEX_STYLE_LIMIT = 1;

    //十字线随数据改变
    public float yMax;
    public float yMin;
    public float yHeight;
    public float[] yValues;
    public int xStartPosition;
    public int xEndPosition;
    public boolean drawEnable = true;
    public int xDrawPosition; // 十字线对应的x轴具体位置
    public int xSpaceOffset; //对应KLineData的xSpaceOffset
    public float xDraw; // 十字线对应的x轴具体位置
    public float yDraw; // 十字线对应的y轴具体位置

    public int bgColor = Color.parseColor("#FA6029");
    public int textColor = Color.WHITE;
    public int textSize = 20;

    public TextItem textX;

    public int leftOffset = 10;
    public int rightOffset = 10;
    public int topOffset = 5;
    public int bottomOffset = 5;


    //允许滑动展示数据，作用于KlineView
    public boolean scrollEnable;
    public Rect scrollRect; //在这个区间内滑动

    //缩放监听
    private ScaleGestureDetector mScaleGestureDetector;
    private boolean isScale;


    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        linePaint = new Paint();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setDither(true);

        textPaint = new TextPaint();
        textRect = new Rect();

        minTouchSlop =  ViewConfiguration.get(context).getScaledTouchSlop();
        minTouchSlopPow2 = Math.pow(minTouchSlop, 2);

        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                Log.i(TAG, "onScale scaleFactor: " + scaleFactor);
                if (scaleListener != null){
                    scaleListener.onScale(scaleFactor);
                }
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                Log.i(TAG, "onScaleBegin");
                isScale = true;
                if (scaleListener != null){
                    scaleListener.scaleStart();
                }
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                isScale = false;
                Log.i(TAG, "onScaleEnd");
                if (scaleListener != null){
                    scaleListener.scaleEnd();
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);

        if (indexLineEnable){
            //在指定时间内只要有滑动就取消绘制十字线
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                beginIndexPress = true;
            }else {
                if (Math.pow(xIndex-xDown, 2) + Math.pow(yIndex-yDown, 2) > minTouchSlopPow2){
                    beginIndexPress = false;

                    setClickable(false);
                }
            }
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    setClickable(true);
                    xDown = event.getX();
                    yDown = event.getY();
                    xIndex = xDown;
                    yIndex = yDown;

                    //长按监听
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (beginIndexPress){
                                //处于长按状态，则允许绘制十字线
                                drawIndexLineEnable = true;
                                setClickable(false);
                                getIndexState();
                                if (drawEnable){
                                    if (indexListener != null){
                                        indexListener.drawIndexStart(xDrawPosition);
                                    }
                                    invalidate();
                                }

                            }
                        }
                    }, TIME_LONG_PRESS);

                    if (scrollEnable && scrollListener != null){
                        if (canScroll(yDown))
                            scrollListener.scrollStart();
                    }
                    break;
//                    return false;
                case MotionEvent.ACTION_MOVE:
                    xIndex = event.getX();
                    yIndex = event.getY();
                    if (drawIndexLineEnable){
                        getIndexState();
                        if (drawEnable){
                            if (indexListener != null){
                                indexListener.drawIndex(xDrawPosition);
                            }
                            invalidate();
                        }
//                        return true;
                    }

                    //滑动监听
                    //有十字线时不监听滑动
                    if (scrollEnable && !drawIndexLineEnable && scrollListener != null){
                        if (canScroll(yIndex) && !isScale){
                            scrollListener.scrollOffset(event.getX() - xDown, event.getY() - yDown);
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    cancelIndex();
                    cancelScroll();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    cancelIndex();
                    cancelScroll();
                    break;
            }
        }

        boolean result = super.onTouchEvent(event);
//        Log.i("111222", "index : event: " + event.getAction()+ "  result: " +result);
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        drawWidth = getWidth();
        drawHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!indexLineEnable)
            return;

        if (!drawIndexLineEnable)
            return;

        if (!drawEnable)
            return;

        initIndexPaint();

        //线
        canvas.drawLine(0f, yDraw, drawWidth, yDraw, linePaint);
        canvas.drawLine(xDraw, 0f, xDraw, drawHeight, linePaint);

        //文字
        textPaint.setTextSize(textSize);

        if (leftValueListenr != null){
            String textLeft = leftValueListenr.getTextValue(xDrawPosition);
            if (!TextUtils.isEmpty(textLeft)){
                textPaint.getTextBounds(textLeft, 0, textLeft.length(), textRect);
                rectHeight = textRect.bottom - textRect.top + topOffset + bottomOffset;
                rectWidth = textRect.right - textRect.left + leftOffset + rightOffset;
                //线左边的文字背景,注意y轴方向不超过上下边距
                float top = Math.max(0, Math.min(drawHeight-rectHeight, yDraw- rectHeight/2));
                textPaint.setColor(bgColor);
                textPaint.bgColor = bgColor;
                canvas.drawRect(0, top, rectWidth, top+rectHeight, textPaint);
                //左边文字
                //基线以左下角为准
                textPaint.setColor(textColor);
                canvas.drawText(textLeft, leftOffset, top+rectHeight-bottomOffset, textPaint);
            }
        }

        if (rightValueListenr != null){
            String textRight = rightValueListenr.getTextValue(xDrawPosition);
            if (!TextUtils.isEmpty(textRight)){
                //右边文字
                textPaint.getTextBounds(textRight, 0, textRight.length(), textRect);
                rectHeight = textRect.bottom - textRect.top + topOffset + bottomOffset;
                rectWidth = textRect.right - textRect.left + leftOffset + rightOffset;
                //线左边的文字背景,注意y轴方向不超过上下边距
                float top = Math.max(0, Math.min(drawHeight-rectHeight, yDraw- rectHeight/2));
                //右边文字背景
                textPaint.setColor(bgColor);
                textPaint.bgColor = bgColor;
                canvas.drawRect(drawWidth-rectWidth, top, drawWidth, top+rectHeight, textPaint);
                textPaint.setColor(textColor);
                canvas.drawText(textRight, drawWidth-rectWidth+leftOffset, top+rectHeight-bottomOffset, textPaint);
            }
        }

        if (XValueListenr != null && textX != null){
            String xText = XValueListenr.getTextValue(xDrawPosition);
            if (!TextUtils.isEmpty(xText)){
                //绘制x轴方向上的文字展示
                textPaint.getTextBounds(xText, 0, xText.length(), textRect);
                //文字宽高
                int textWidth = textRect.right - textRect.left;
                int textHeight = textRect.bottom - textRect.top;
                //默认背景区域
                rectHeight = textHeight + textX.topMargin + textX.bottomMargin;
                rectWidth = textWidth + textX.leftMargin + textX.rightMargin;
                //参数中读取
                rectHeight = textX.bgHeight > 0 ? textX.bgHeight : rectHeight;
                rectWidth = textX.bgWidth > 0 ? textX.bgWidth : rectWidth;

                float left = Math.max(0, Math.min(drawWidth-rectWidth, xDraw-rectWidth/2));
                textPaint.setColor(textX.bgColor);
                textPaint.bgColor = textX.bgColor;
                canvas.drawRect(left, textX.textXOffset, left+rectWidth, textX.textXOffset+rectHeight, textPaint);

                float offsetX = 0f;
                if (rectWidth > textWidth){
                    offsetX = (float)(rectWidth - textWidth) / 2;
                }
                float offsetY = 0f;
                if (rectHeight > textHeight){
                    offsetY = (float)(rectHeight - textHeight) / 2;
                }
                textPaint.setColor(textX.textColor);
                canvas.drawText(xText, left+offsetX, textX.textXOffset+rectHeight-offsetY, textPaint);
            }
        }

    }

    private void initIndexPaint(){
        if (linePaint == null)
            linePaint = new Paint();

        linePaint.reset();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setDither(true);
        linePaint.setColor(colorIndex);
        linePaint.setStrokeWidth(1);
    }

    //当前十字线的位置和状态
    private void getIndexState(){
        //计算手指处x轴的位置
        xDraw = xIndex - getPaddingLeft();

        //x轴位置
        xDrawPosition = 0;
        if (indexSpaceCount != 0){
            xDrawPosition = (int) (xDraw / drawWidth * indexSpaceCount);
            xDraw = xDrawPosition * (drawWidth / indexSpaceCount);
        }

        if (indexStyle == INDEX_STYLE_LIMIT){
            if (yHeight <= 0 || yMax - yMin == 0 || xDrawPosition < xStartPosition || xDrawPosition > xEndPosition
                    || xDrawPosition - xSpaceOffset < 0) {
                drawEnable = false;
                return;
            }

            drawEnable = true;
            if (yValues != null && xDrawPosition < yValues.length && (yMax-yMin != 0)){
                yDraw = yHeight * (1 - (yValues[xDrawPosition-xSpaceOffset] - yMin) / (yMax-yMin));
            }

        }
    }

    private void cancelIndex(){
        beginIndexPress = false;
        drawIndexLineEnable = false;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, TIME_INDEX_DISMISS);
    }

    private void cancelScroll(){
        if (scrollEnable){
            if (scrollListener != null){
                scrollListener.scrollEnd();
            }
        }
    }

    //手指区域是否在可滑动的区域内
    private boolean canScroll(float yValue){
        boolean canScroll = true;
        if (scrollRect != null){
            if (scrollRect.bottom < yValue){
                canScroll = false;
            }
        }

        return canScroll;
    }

    public interface TextValueListener{
        //返回x轴的位置 和 y轴方向上距顶部的比例和距顶部的值
        String getTextValue(int xPosition);
    }

    public TextValueListener leftValueListenr;
    public TextValueListener rightValueListenr;
    public TextValueListener XValueListenr;


    public static class TextItem{
        //随x轴移动的文字
        public int textXOffset; //距顶部的距离

        public int textSize = 10;
        public int textColor = Color.BLACK;
        public int bgColor = Color.WHITE;

        public int bgWidth;
        public int leftMargin;
        public int rightMargin;
        public int topMargin;
        public int bottomMargin;
        public int bgHeight;
    }

    //返回当前十字线对应的点的位置
    public interface IndexListener{
        void drawIndexStart(int posotion);

        void drawIndexEnd(int posotion);

        void drawIndex(int position);
    }

    public IndexListener indexListener;

    public interface ScrollListener{
        // >0 ,向右滑；<0,向左滑
        void scrollOffset(float deltaX, float deltaY);

        //down事件时触发
        void scrollStart();
        
        void scrollEnd();
    }

    public ScrollListener scrollListener;


    public interface ScaleListener{
        void scaleStart();

        void onScale(float scale);

        void scaleEnd();
    }

    public ScaleListener scaleListener;
}
