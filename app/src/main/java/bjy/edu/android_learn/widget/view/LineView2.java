package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import bjy.edu.android_learn.R;

/**
 * Created by sogubaby on 2018/8/9.
 */

public class LineView2 extends View {
    private Context context;
    private Paint paint;
    private Path path;
    private List<DrawHolder> drawHolders;//所有画好成型的线
    private DrawHolder drawHolder;//正在操作的线

    private boolean canDraw = true;//是否可以绘制

    private int drawWidth = -1;//绘制区域的宽高
    private int drawHeight = -1;

    //画线类型
    public enum Type {
        //直线
        LINE,

        //射线
        RAY,

        //矩形
        RECT,

        //平行线
        PARALLEL,

        //图像
        BITMAP
    }

    public LineView2(Context context) {
        this(context, null);
    }

    public LineView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!canDraw)
                    return false;
                if (drawHolder != null){//需要加新线段
                    drawHolder.pointNewDown(x, y);
                    for (DrawHolder drawHolder : drawHolders){
                        drawHolder.focus_state = DrawHolder.UNFOCUS;
                    }
                    drawHolders.add(drawHolder);
                    invalidate();
                }else {
                    int num = 0;//只有一个能被选中
                    for (int i =0; i<drawHolders.size(); i++){
                        if (num == 0 && drawHolders.get(i).checkFocus(x, y)){
                            num++;
                            drawHolder = drawHolders.get(i);
                            drawHolder.focus_state = DrawHolder.FOCUS;
                        }else {
                            drawHolders.get(i).focus_state = DrawHolder.UNFOCUS;
                        }
                    }
                    if (num == 1)
                        invalidate();
                    else//没有选中的图形，不处理
                        return false;
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                if (drawHolder.pointMove(x, y))
                    invalidate();

                return true;
            case MotionEvent.ACTION_UP:
                drawHolder = null;

                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (drawWidth == -1 && drawHeight ==-1){
            drawWidth = getWidth();
            drawHeight = getHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (DrawHolder drawHolder : drawHolders) {
            drawHolder.draw(canvas);
        }
    }

    private void init() {
        drawHolders = new ArrayList<>();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

        path = new Path();
    }

    public void newDraw(Type type) {
        switch (type) {
            case LINE:
                drawHolder = new LineHolder(paint, path);
                break;
            case RAY:
                drawHolder = new RayHolder(paint, path);
                break;
            case RECT:
                drawHolder = new RectHolder(paint);
                break;
            case PARALLEL:
                drawHolder = new ParallelHolder(paint, path);
                break;
            case BITMAP:
                drawHolder = new BitmapHolder(context, paint);
                break;
        }
    }

    public void clearDraw(){
        drawHolder = null;
    }

    public void setPaintColor(@ColorInt int colorId) {
        paint.setColor(colorId);
    }

    public void setPaintStrokeWidth(float width) {
        paint.setStrokeWidth(width);
    }

    public void setPaintPathEffect(PathEffect pathEffect) {
        paint.setPathEffect(pathEffect);
    }

    abstract class DrawHolder {
        int focus_state = UNFOCUS;
        final static int UNFOCUS = 0;//无焦点
        final static int FOCUS = 1;//获得焦点

        Paint paint;

        //绘制图像
        abstract void draw(Canvas canvas);

        //第一次添加图像时的调用
        abstract void pointNewDown(float x, float y);

        //手指移动时的操作: return true,需要刷新View
        abstract boolean pointMove(float x, float y);

        //action_down时检查是否选中
        abstract boolean checkFocus(float x, float y);
    }

    //根据两个坐标点来画---直线、射线、矩形、平行线...
    abstract class TwoPointDrawHolder extends DrawHolder{
        //线的各种状态--------1--------
        int state = STATE_DRAW;
        final static int STATE_DRAW = 1;//画线,移动坐标
        final static int STATE_DRAG = 2;//拖动整体
        final static int STATE_DRAG_PARALLEL = 3;//拖动平行线的副线
        //线的各种状态--------1--------
        float x1 = -1, y1 = -1;//始点
        float x2 = -1, y2 = -1;//终点
        double validLength = dp2px(8);//大于这个值才画线
        float xd, yd;//当需要拖动时，记录上一次手指的位置，方便与action_move计算位移
        int changePosition = 2;//移动点的位置
        Path path;

        float radius = dp2px(3);//焦点圆半径

        @Override
        void draw(Canvas canvas) {
            switch (focus_state) {
                case UNFOCUS:
                    drawLine(canvas);
                    break;
                case FOCUS:
                    drawLine(canvas);
                    drawFocus(canvas);
                    break;
            }
        }

        //绘制线
        void drawLine(Canvas canvas){
            if (x1 != -1 && x2 == -1){//只有一个点
                canvas.drawPoint(x1, y1, paint);
            }
        }

        @Override
        void pointNewDown(float x, float y) {
            x1 = x;
            y1 = y;
            drawHolder.focus_state = DrawHolder.FOCUS;

            Log.e("x1", x1+"");
            Log.e("y1", y1+"");
            Log.e("VALID", dp2px(8)+"");
        }

        @Override
        boolean pointMove(float x, float y) {
            if (x2 == -1){
                Log.e("x", x+"");
                Log.e("y", y+"");
                if (lineSpace(x1, y1, x, y) > validLength) {
                    x2 = x;
                    y2 = y;
                    return true;
                }
            }else {
                // TODO: 2018/8/16 两个点不要重合
                changePosition(x, y);
                return true;
            }

            return false;
        }

        //绘制焦点
        void drawFocus(Canvas canvas) {
            if (x1 != -1)
                canvas.drawCircle(x1, y1, radius, paint);
            if (x2 != -1)
                canvas.drawCircle(x2, y2, radius, paint);
        }

        void changePosition(float x, float y) {
            switch (state){
                case STATE_DRAW:
                    if (changePosition == 1){
                        x1 = x;
                        y1 = y;
                    }else {
                        x2 = x;
                        y2 = y;
                    }
                    break;
                case STATE_DRAG:
                    x1 += (x-xd);
                    y1 += (y-yd);
                    x2 += (x-xd);
                    y2 += (y-yd);

                    xd = x;
                    yd = y;
                    break;
            }
        }
    }

    //直线
    class LineHolder extends TwoPointDrawHolder {

        LineHolder(Paint paint, Path path) {
            this.paint = paint;
            this.path = path;
        }

        @Override
        void drawLine(Canvas canvas) {
            super.drawLine(canvas);
            path.reset();
            if (x1 != -1 && x2 != -1){
                path.moveTo(x1, y1);
                path.lineTo(x2, y2);
                canvas.drawPath(path, paint);
            }
        }

        @Override
        boolean checkFocus(float x, float y) {
            if (x2 == -1) {//只有绘制了一个点
                if (lineSpace(x1, y1, x, y) < validLength)
                    return true;
            } else {
                if (Math.abs(x1 - x) < validLength && Math.abs(y1 - y) < validLength) {
                    state = STATE_DRAW;
                    changePosition = 1;
                    return true;
                }

                if (Math.abs(x2 - x) < validLength && Math.abs(y2 - y) < validLength) {
                    state = STATE_DRAW;
                    changePosition = 2;
                    return true;
                }

                if ((x1 < x && x < x2)
                        || (x2 < x && x < x1)
                        || (y1 < y && y < y2)
                        || (y2 < y && y < y1)) {
                    if (pointToline(x1, y1, x2, y2, x, y) < validLength) {
                        state = STATE_DRAG;
                        xd = x;
                        yd = y;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    //射线
    class RayHolder extends TwoPointDrawHolder {
        float x3, y3;//延长到屏幕上的交点

        RayHolder(Paint paint, Path path) {
            this.paint = paint;
            this.path = path;
        }

        @Override
        void drawLine(Canvas canvas) {
            super.drawLine(canvas);
            path.reset();
            if (x1 != -1 && x2 != -1){
                path.moveTo(x1, y1);
                path.lineTo(x2, y2);
                calculatePoint();
                path.lineTo(x3, y3);
                canvas.drawPath(path, paint);
            }
        }

        @Override
        boolean checkFocus(float x, float y) {
            if (x2 == -1) {//只有绘制了一个点
                if (lineSpace(x1, y1, x, y) < validLength)
                    return true;
            } else {
                if (Math.abs(x1 - x) < validLength && Math.abs(y1 - y) < validLength) {
                    state = STATE_DRAW;
                    changePosition = 1;
                    return true;
                }

                if (Math.abs(x2 - x) < validLength && Math.abs(y2 - y) < validLength) {
                    state = STATE_DRAW;
                    changePosition = 2;
                    return true;
                }

                if ((x1 < x2 && x1 < x)
                        || (x1 > x2 && x1 > x)
                        || (y1 < y2 && y1 < y)
                        || (y1 > y2 && y1 > y)) {
                    if (pointToline(x1, y1, x2, y2, x, y) < validLength) {
                        state = STATE_DRAG;
                        xd = x;
                        yd = y;
                        return true;
                    }
                }
            }
            return false;
        }

        //计算射线在屏幕的交点
        public void calculatePoint(){
            if (x1 != -1 && x2 != -1 && drawWidth != -1 && drawHeight != -1){
                if (x1 == x2){
                    x3 = x1;
                    if (y1 > y2)
                        y3 = drawWidth ;
                    else
                        y3 = 0;
                }

                if (y1 == y2){
                    y3 = y1;
                    if (x1 < x2)
                        x3 = drawWidth;
                    else
                        x3 = 0;
                }

                if (x1 < x2){
                    //屏幕右上角与起点的斜率 正值
                    float k1 = -(0 - y1) / ((drawWidth - x1));
                    //屏幕右下角与起点的斜率 负值
                    float k2 = (drawHeight - y1) / (x1 - drawWidth);
                    //变化值
                    float k3 = (y1-y2) / (x2-x1);
                    if (k3 > k2 && k3 < k1){//交点在view右边 //考虑画布的拖动，把x轴往右增加一倍，y轴相应改变
                        x3 = drawWidth;
                        y3 = y1 + k3*(x1-x3);
                    }else if (k3 >= k1){//交点在view上边
                        y3 = 0;
                        x3 = x1 + y1/k3;
                    }else if (k3 <= k2){//交点在view下边
                        y3 = drawHeight;
                        x3 = x1 + (y1-y3)/k3;
                    }
                }else {
                    //屏幕左上上角与起点的斜率 负值
                    float k1 = -(0 -y1) / (0 - x1);
                    //屏幕左下角与起点的斜率 正值
                    float k2 = -(drawHeight - y1) / (0 -x1);
                    //变化值
                    float k3 = (y1-y2) / (x2-x1);
                    if (k3 > k1 && k3 < k2){//交点在view左边
                        x3 = 0;
                        y3 = y1 + k3*x1;
                    }else if (k3 <= k1){//交点在view上边
                        y3 = 0;
                        x3 = x1 + (y1-y3)/k3;
                    }else if(k3 >= k2){//交点在view下边
                        y3 = drawHeight;
                        x3 = x1 + (y1-y3)/k3;
                    }
                }
            }
        }
    }

    //矩形
    class RectHolder extends TwoPointDrawHolder {

        RectHolder(Paint paint) {
            this.paint = paint;
        }

        @Override
        void drawLine(Canvas canvas) {
            super.drawLine(canvas);
            if (x1 != -1 && x2 != -1){
                float left = Math.min(x1, x2);
                float right = Math.max(x1, x2);
                float top = Math.min(y1, y2);
                float bottom = Math.max(y1, y2);

                canvas.drawRect(left, top, right, bottom, paint);
            }
        }

        @Override
        boolean checkFocus(float x, float y) {
            if (x2 == -1) {//只有绘制了一个点
                if (lineSpace(x1, y1, x, y) < validLength)
                    return true;
            } else {
                if (Math.abs(x1 - x) < validLength && Math.abs(y1 - y) < validLength) {
                    state = STATE_DRAW;
                    changePosition = 1;
                    return true;
                }

                if (Math.abs(x2 - x) < validLength && Math.abs(y2 - y) < validLength) {
                    state = STATE_DRAW;
                    changePosition = 2;
                    return true;
                }

                if (x>=Math.min(x1, x2)
                        && x<=Math.max(x1, x2)
                        && y>=Math.min(y1, y2)
                        && y<=Math.max(y1, y2)){
                    state = STATE_DRAG;
                    xd = x;
                    yd = y;
                    return true;
                }
            }
            return false;
        }
    }

    //水平线
    class ParallelHolder extends TwoPointDrawHolder {
        Path path;
        float interval = dp2px(20);//垂直间隔

        ParallelHolder(Paint paint, Path path) {
            this.paint = paint;
            this.path = path;
        }

        @Override
        void drawLine(Canvas canvas) {
            super.drawLine(canvas);
            path.reset();
            if (x1 != -1 && x2 != -1){
                path.moveTo(x1, y1);
                path.lineTo(x2, y2);
                canvas.drawPath(path, paint);

                path.moveTo(x1, y1 + interval);
                path.lineTo(x2, y2 + interval);
                canvas.drawPath(path, paint);
            }
        }

        @Override
        void changePosition(float x, float y) {
            super.changePosition(x, y);

            if (state == STATE_DRAG_PARALLEL){
                interval += y-yd;
                yd = y;
            }
        }

        @Override
        boolean checkFocus(float x, float y) {
            if (x2 == -1) {//只有绘制了一个点
                if (lineSpace(x1, y1, x, y) < validLength)
                    return true;
            } else {
                if (Math.abs(x1 - x) < validLength && Math.abs(y1 - y) < validLength) {
                    state = STATE_DRAW;
                    changePosition = 1;
                    return true;
                }

                if (Math.abs(x2 - x) < validLength && Math.abs(y2 - y) < validLength) {
                    state = STATE_DRAW;
                    changePosition = 2;
                    return true;
                }

                //选中基线
                if (pointToline(x1, y1, x2, y2, x, y) < validLength) {
                    if ((x1<x && x<x2)
                            || (x2<x && x<x1)
                            || (y1<y && y<y2)
                            || (y2<y && y<y1)){
                        state = STATE_DRAG;
                        xd = x;
                        yd = y;
                        return true;
                    }
                }

                //选中副线
                if (pointToline(x1, y1+interval, x2, y2+interval, x, y) < validLength) {
                    if ((x1<x && x<x2)
                            || (x2<x && x<x1)
                            || (y1<y && y<y2)
                            || (y2<y && y<y1)){
                        state = STATE_DRAG_PARALLEL;
                        xd = x;
                        yd = y;
                        return true;
                    }
                }
            }
            return false;
        }

    }

    //图像
    class BitmapHolder extends DrawHolder{
        Bitmap bitmap;
        Context context;

        float x, y;//bitmap显示的中心点
        int width, height;//bitmap宽高

        public BitmapHolder(Context context, Paint paint) {
            this.context = context;
            this.paint = paint;
        }

        @Override
        void draw(Canvas canvas) {
            canvas.drawBitmap(bitmap, x-width/2, y-height/2, paint);
        }

        @Override
        void pointNewDown(float x, float y) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            this.x = x;
            this.y = y;
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        }

        @Override
        boolean pointMove(float x, float y) {
            this.x = x;
            this.y = y;
            return true;
        }

        @Override
        boolean checkFocus(float x, float y) {
            if (this.x-width/2 <= x
                    && x <= this.x+width/2
                    && this.y-height/2 <= y
                    && y <= this.y+height/2){
                return true;
            }

            return false;
        }
    }

    //两点之间的距离
    public double lineSpace(double x1, double y1, double x2, double y2) {
        Log.e("lineSpace", Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))+"");
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    //点到直线的垂直距离 x1,y1 x2,y2 为线的两个端点
    public int pointToline(double x1, double y1, double x2, double y2, double x0, double y0) {
        double a = lineSpace(x1, y1, x2, y2);
        double b = lineSpace(x1, y1, x0, y0);
        double c = lineSpace(x2, y2, x0, y0);

        double p = (a + b + c) / 2;// 半周长

        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积

        double space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）

//            Log.e("space", space + "");
        return (int) space;
    }

    public float dp2px(int dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }
}
