package com.jys.lib_kline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IndexView extends View {
    Paint linePaint;

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

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init(){
        linePaint = new Paint();
        linePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linePaint.setDither(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (indexLineEnable){
            //在指定时间内只要有滑动就取消绘制十字线
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                beginIndexPress = true;
            }else {
                if (Math.pow(xIndex-xDown, 2) + Math.pow(yIndex-yDown, 2) > minTouchSlopPow2){
                    beginIndexPress = false;
                }
            }
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
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
                                if (indexListener != null){
                                    indexListener.drawIndexStart();
                                }
                                invalidate();
                            }
                        }
                    }, TIME_LONG_PRESS);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    xIndex = event.getX();
                    yIndex = event.getY();
                    if (drawIndexLineEnable){
                        invalidate();
                        return true;
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    cancelIndex();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    cancelIndex();
                    break;
            }
        }
        return super.onTouchEvent(event);
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

        initIndexPaint();

        //计算
        float xIndexOffset = xIndex - getPaddingLeft();

        int x_index_position = 0;
        if (indexSpaceCount != 0){
            x_index_position = (int) (xIndexOffset / drawWidth * indexSpaceCount);
            xIndexOffset = x_index_position * (drawWidth / indexSpaceCount);
        }

        canvas.drawLine(0f, yIndex, drawWidth, yIndex, linePaint);
        canvas.drawLine(xIndexOffset, 0f, xIndexOffset, drawHeight, linePaint);

        int w = 80;
        int h = 20;
        canvas.drawRect(0, Math.max(0, yIndex -h), w, Math.min(yIndex+h, drawHeight), linePaint);
        canvas.drawRect(drawWidth-w, Math.max(0, yIndex -h), drawWidth, Math.min(yIndex+h, drawHeight), linePaint);

        int h2 = 10;
        int w2 = 70;
        linePaint.setColor(Color.WHITE);
        linePaint.setTextSize(24);
        linePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("10.69", 40, Math.max(0, yIndex), linePaint);

        if (indexListener != null){
            float yRate = Math.abs((yIndex-drawHeight) / drawHeight);
            indexListener.drawIndexPosition(x_index_position, yRate);
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

    public interface IndexListener{
        void drawIndexStart();

        void drawIndexEnd();

        //返回当前十字线对应的点的位置
        void drawIndexPosition(int position);
    }

    private KLineView.IndexListener indexListener;

    public void setIndexListenr(KLineView.IndexListener indexListener){
        this.indexListener = indexListener;
    }
}
