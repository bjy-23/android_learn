package bjy.edu.android_learn.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import bjy.edu.android_learn.R;

/**
 * Created by sogubaby on 2018/8/2.
 */

public class LineView extends FrameLayout {
    private Context context;
    private Paint paint;
    private Path path;
    private int pointWidth = 20;//线两端点view的宽度
    private int validLength = 30;//有效距离；在初始点有效距离内可以画线
    private List<LineViewItem> lines;
    private int drawPosition = 0;//需要绘制的位置

    public LineView(@NonNull Context context) {
        this(context, null);
    }

    public LineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    private void init() {
        //画笔
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5.0f);
        paint.setAntiAlias(true);

        lines = new ArrayList<>();
        addDraw();
    }

    public void addDraw() {
        drawPosition++;
        LineViewItem lineViewItem = new LineViewItem(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lineViewItem.setLayoutParams(lp);
        addView(lineViewItem);

        lines.add(lineViewItem);
    }

    private class LineViewItem extends FrameLayout {
        private Context context;
        private Bitmap bitmap;
        private Canvas canvas;

        private float x1;
        private float y1;
        private float x2;
        private float y2;
        //上一次evet的坐标
        private float x0;
        private float y0;
        //上一次evet的坐标

        private float xDown;
        private float yDown;

        private int state;
        private final static int STATE_NULL = 0;
        private final static int STATE_DRAW = 1;//画线
        private final static int STATE_DRAG = 2;//拖动
        private boolean hasStart, hasEnd;//初始点、终点

        private ImageView img1, img2;

        public LineViewItem(@NonNull Context context) {
            this(context, null);
        }

        public LineViewItem(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            init();
        }

        private void init() {
            bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);//
            canvas = new Canvas(bitmap);
            setBackgroundColor(Color.TRANSPARENT);
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getX();
                    yDown = event.getY();
                    Log.e("xDown", xDown +"");
                    Log.e("yDown", yDown +"");
                    if (!hasStart) {
                        x1 = xDown;
                        y1 = yDown;
                        hasStart = true;
                        state = STATE_DRAW;

                        img1 = new ImageView(context);
                        img1.setClickable(false);
                        img1.setLongClickable(false);
                        putPointView(img1, (int) x1, (int) y1);
                    } else {
                        if (!hasEnd) {
                            if (Math.abs(xDown - (x1-getScrollX())) < validLength && Math.abs(yDown - (y1-getScrollY())) < validLength) {//在初始点附近
                                state = STATE_DRAW;
                            }
                        } else {
                            if (Math.abs(xDown - (x1-getScrollX())) < validLength && Math.abs(yDown - (y1-getScrollY())) < validLength) {//在初始点附近
                                state = STATE_DRAW;
                                //两点的位置交换下
                                float tempx = x1;
                                x1 = x2;
                                x2 = tempx;

                                float tempy = y1;
                                y1 = y2;
                                y2 = tempy;

                                //imageView 互换
                                ImageView tempImg = img1;
                                img1 = img2;
                                img2 = tempImg;

                                return true;
                            }

                            if (Math.abs(xDown - (x2-getScrollX())) < validLength && Math.abs(yDown - (y2-getScrollY())) < validLength) {//在终点附近
                                state = STATE_DRAW;
                                return true;
                            }

                            if (((event.getX() > (x1-getScrollX()) && event.getX() < (x2-getScrollX())) || (event.getX() > (x2-getScrollX()) && event.getX() < (x1-getScrollX()))
                                    || (event.getY() > (y1-getScrollY()) && event.getY() < (y2-getScrollY())) || (event.getY() > (y2-getScrollY()) && event.getY() < (y1-getScrollY())))
                                    && pointToline(x1-getScrollX(), y1-getScrollY(), x2-getScrollX(), y2-getScrollY(), event.getX(), event.getY()) < 50) {
                                img1.setVisibility(VISIBLE);
                                img2.setVisibility(VISIBLE);
                                state = STATE_DRAG;
                                x0 = xDown;
                                y0 = yDown;

                                return true;
                            }
                        }
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (state == STATE_DRAW) {
                        x2 = event.getX();
                        y2 = event.getY();

                        if (((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) > pointWidth * pointWidth + pointWidth * pointWidth) {//大于一定长度才画线
                            drawLine();

                            if (hasEnd) {
                                changePointPosition(img2, (int) (x2+getScrollX()), (int) (y2+getScrollY()));
                            }
                        }
                        return true;
                    }

                    if (state == STATE_DRAG) {
                        scrollBy((int) (x0 - event.getX()), (int) (y0 - event.getY()));
                        x0 = event.getX();
                        y0 = event.getY();
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (state == STATE_DRAW) {
                        x2 = event.getX();
                        y2 = event.getY();

                        if (((x1- x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) > pointWidth * pointWidth + pointWidth * pointWidth) {//大于一定长度才画线
                            drawLine();
                            if (!hasEnd) {
                                img2 = new ImageView(context);
                                img2.setClickable(false);
                                img2.setLongClickable(false);
                                putPointView(img2, (int) x2, (int) y2);
                                hasEnd = true;
                            } else {
                                changePointPosition(img2, (int) (x2+getScrollX()), (int) (y2+getScrollY()));
                            }
                        }
                    }

                    if (state == STATE_DRAG) {
//                        x1 += (event.getX() - xDown);
//                        x2 += (event.getX() - xDown);
//                        y1 += (event.getY() - yDown);
//                        y2 += (event.getY() - yDown);
//
                        Log.e("x1", x1 + "");
                        Log.e("y1", y1 + "");
                        Log.e("x2", x2+ "");
                        Log.e("y2", y2 + "");
                        Log.e("scrollX", getScrollX() + "");
                        Log.e("scrollY", getScrollY() + "");
                    }
                    state = STATE_NULL;
                    return true;
            }
            return super.onTouchEvent(event);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//        this.canvas = canvas;
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        public void putPointView(ImageView imageView, final int x, final int y) {
            if (imageView == null)
                return;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(pointWidth, pointWidth);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.circle_red);
            layoutParams.setMargins(x - pointWidth / 2, y - pointWidth / 2, 0, 0);
            addView(imageView);
//        post(new Runnable() {
//            @Override
//            public void run() {
//                imageView.layout(Math.max(x - 10, 0), Math.max(y - 10, 0), Math.max(x + 10, 0), Math.max(y + 10, 0));
//            }
//        });
        }

        public void changePointPosition(ImageView imageView, int x, int y) {
            if (imageView == null)
                return;
            FrameLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
            layoutParams.setMargins(x - pointWidth / 2, y - pointWidth / 2, 0, 0);
            requestLayout();
        }

        public void drawLine() {
            if (canvas != null) {
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);//清除之前的line
                canvas.drawLine(x1, y1, x2+getScrollX(), y2+getScrollY(), paint);
                invalidate();
            }
        }

        //点到直线的垂直距离 x1,y1 x2,y2 为线的两个端点
        public int pointToline(double x1, double y1, double x2, double y2, double x0, double y0) {
            double a = lineSpace(x1, y1, x2, y2);
            double b = lineSpace(x1, y1, x0, y0);
            double c = lineSpace(x2, y2, x0, y0);

            double p = (a + b + c) / 2;// 半周长

            double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积

            double space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）

            Log.e("space", space + "");
            return (int) space;
        }

        public double lineSpace(double x1, double y1, double x2, double y2) {
            return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        }
    }
}
