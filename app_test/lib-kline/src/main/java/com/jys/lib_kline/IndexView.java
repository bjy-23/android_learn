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
import android.view.MotionEvent;
import android.view.View;

public class IndexView extends View {
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

    public int bgColor = Color.parseColor("#FA6029");
    public int textColor = Color.WHITE;
    public int textSize = 20;

    public TextItem textX;

    public int leftOffset = 10;
    public int rightOffset = 10;
    public int topOffset = 5;
    public int bottomOffset = 5;

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

        textPaint = new TextPaint();
        textRect = new Rect();
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


        //线
        canvas.drawLine(0f, yIndex, drawWidth, yIndex, linePaint);
        canvas.drawLine(xIndexOffset, 0f, xIndexOffset, drawHeight, linePaint);

        //文字背景
        textPaint.setTextSize(textSize);

        int xPosition = x_index_position;
        float yRate = yIndex/drawHeight;
        if (leftValueListenr != null){
            String textLeft = leftValueListenr.getTextValue(xPosition, yRate, yIndex);
            if (!TextUtils.isEmpty(textLeft)){
                textPaint.getTextBounds(textLeft, 0, textLeft.length(), textRect);
                rectHeight = textRect.bottom - textRect.top + topOffset + bottomOffset;
                rectWidth = textRect.right - textRect.left + leftOffset + rightOffset;
                //线左边的文字背景,注意y轴方向不超过上下边距
                float top = Math.max(0, Math.min(drawHeight-rectHeight, yIndex- rectHeight/2));
                textPaint.setColor(bgColor);
                canvas.drawRect(0, top, rectWidth, top+rectHeight, textPaint);
                //左边文字
                //基线以左下角为准
                textPaint.setColor(textColor);
                canvas.drawText(textLeft, leftOffset, top+rectHeight-bottomOffset, textPaint);
            }
        }

        if (rightValueListenr != null){
            String textRight = rightValueListenr.getTextValue(xPosition, yRate, yIndex);
            if (!TextUtils.isEmpty(textRight)){
                //右边文字
                textPaint.getTextBounds(textRight, 0, textRight.length(), textRect);
                rectHeight = textRect.bottom - textRect.top + topOffset + bottomOffset;
                rectWidth = textRect.right - textRect.left + leftOffset + rightOffset;
                //线左边的文字背景,注意y轴方向不超过上下边距
                float top = Math.max(0, Math.min(drawHeight-rectHeight, yIndex- rectHeight/2));
                //右边文字背景
                textPaint.setColor(bgColor);
                canvas.drawRect(drawWidth-rectWidth, top, drawWidth, top+rectHeight, textPaint);
                textPaint.setColor(textColor);
                canvas.drawText(textRight, drawWidth-rectWidth+leftOffset, top+rectHeight-bottomOffset, textPaint);
            }
        }

        if (XValueListenr != null && textX != null){
            String xText = XValueListenr.getTextValue(xPosition, yRate, yIndex);
            if (!TextUtils.isEmpty(xText)){
                //绘制x轴方向上的文字展示
                textPaint.getTextBounds(xText, 0, xText.length(), textRect);
                //文字宽高
                int textWidth = textRect.right - textRect.left;
                int textHeight = textRect.bottom - textRect.top;
                //默认背景区域
                rectHeight = textHeight + topOffset + bottomOffset;
                rectWidth = textWidth + leftOffset + rightOffset;
                //参数中读取
                rectHeight = textX.bgHeight > 0 ? textX.bgHeight : rectHeight;
                rectWidth = textX.bgWidth > 0 ? textX.bgWidth : rectWidth;

                float left = Math.max(0, Math.min(drawWidth-rectWidth, xIndexOffset-rectWidth/2));
                textPaint.setColor(textX.bgColor);
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


    public interface TextValueListener{
        //返回x轴的位置 和 y轴方向上距顶部的比例和距顶部的值
        String getTextValue(int xPosition, float yRate, float yValue);
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
        public int bgHeight;
    }
}
