package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
    private int pointWidth = 20;//线两端点view的宽度
    private int validLength = 50;//有效距离；在初始点有效距离内可以画线
    private int parallel_space = 100;//平行线的间距
    private List<LineViewItem> lines;
    private int drawPosition = 0;//需要绘制的位置

    public enum Type{
        //直线
        LINE,

        //射线
        LONG_LINE,

        //矩形
        RECT,

        //平行线
        PARALLEL
    }

    public LineView(@NonNull Context context) {
        this(context, null);
    }

    public LineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            float xDown = ev.getX();
            float yDown = ev.getY();
            int position = -1;
            for (int i=0; i<lines.size(); i++){
                LineViewItem lineViewItem = lines.get(i);
                if (lineViewItem.hasPicked((int)xDown, (int)yDown)){
                    lineViewItem.ownFocus();
                    position = i;
                    break;
                }
            }
            if (position > -1){
                for (int i=0; i<lines.size(); i++){
                    if (i != position)
                        lines.get(i).loseFocus();
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void init() {

        lines = new ArrayList<>();
        Log.e("1", String.valueOf(isHardwareAccelerated()));

//        addDraw();
    }

    public void addDraw(){
        addDraw(Type.LINE);
    }

    public void addDraw(Type type) {
        drawPosition++;
        LineViewItem lineViewItem = new LineViewItem(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lineViewItem.setLayoutParams(lp);
        lineViewItem.setTag("第" + drawPosition + "个");
        lineViewItem.setType(type);
        addView(lineViewItem);

        lines.add(lineViewItem);

        //隐藏其他view的焦点
        if (lines.size() > 1){
            for (int i=0; i<lines.size()-1; i++){
                lines.get(i).loseFocus();
            }
        }
    }

    public void removeDraw(){
        removeDraw(lines.size()-1);
    }

    public void removeDraw(int position){
        LineViewItem lineViewItem = lines.get(position);
        removeView(lineViewItem);
        lines.remove(position);
    }

    public List<LineViewItem> getLines() {
        return lines;
    }

    public void setLines(List<LineViewItem> lines) {
        this.lines = lines;
    }

    public interface Callback{
        void drawSuccess();
    }

    Callback callback;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public class LineViewItem extends FrameLayout {
        private Context context;
        private Bitmap bitmap;
        private Canvas canvas;
        private Paint paint;

        private float x1;
        private float y1;
        private float x2;
        private float y2;
        private float x3;
        private float y3;
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
        private final static int STATE_DRAG_PARALLEL = 3;//拖动平行线

        private int drawPosition = 2;//移动点的位置
        private boolean hasStart, hasEnd;//初始点、终点

        private ImageView img1, img2;

        private boolean focusable;

        private Type type = Type.LINE;

        public LineViewItem(@NonNull Context context) {
            this(context, null);
        }

        public LineViewItem(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            init();
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
//            Log.e("dispatchTouchEvent", getTag().toString());
            return super.dispatchTouchEvent(ev);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
//            Log.e("onInterceptTouchEvent", getTag().toString());
//            Log.e("focusable", String.valueOf(focusable));
            if (!focusable)
                return false;
            return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (!focusable)
                return false;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getX();
                    yDown = event.getY();
//                    Log.e("onTouchEvent", getTag().toString());
//                    Log.e("xDown", xDown +"");
//                    Log.e("yDown", yDown +"");
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
                            if (Math.abs((xDown+getScrollX()) - x1) < validLength && Math.abs((yDown+getScrollY()) - y1) < validLength) {//在初始点附近
                                state = STATE_DRAW;
                                drawPosition = 1;
                            }
                        } else {
                            if (Math.abs((xDown+getScrollX()) - x1) < validLength && Math.abs((yDown+getScrollY()) - y1) < validLength) {//在初始点附近
                                state = STATE_DRAW;
                                drawPosition = 1;
                                return true;
                            }

                            if (Math.abs((xDown+getScrollX()) - x2) < validLength && Math.abs((yDown+getScrollY()) - y2) < validLength) {//在终点附近
                                state = STATE_DRAW;
                                drawPosition = 2;
                                return true;
                            }

                            if (hasPicked((int) xDown, (int)yDown)){
                                ownFocus();
                                x0 = xDown;
                                y0 = yDown;
                                return true;
                            }
                        }
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    float xMove = event.getX();
                    float yMove = event.getY();

                    if (state == STATE_DRAW) {
                        if (drawPosition == 1){
                            x1 = xMove + getScrollX();
                            y1 = yMove + getScrollY();
                        }else {
                            x2 = xMove + getScrollX();
                            y2 = yMove + getScrollY();
                        }

                        if (((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) > pointWidth * pointWidth + pointWidth * pointWidth) {//大于一定长度才画线
                            draw();
                            if (hasEnd) {
                                if (drawPosition == 1)
                                    changePointPosition(img1, (int) x1, (int)y1);
                                else
                                    changePointPosition(img2, (int) x2, (int)y2);
                            }
                        }
                        return true;
                    }

                    if (state == STATE_DRAG) {
                        // TODO: 2018/8/9 scrollBy的这种方式会导致部分区域无法绘制，暂时不知如何解决
//                        scrollBy((int) (x0 - event.getX()), (int) (y0 - event.getY()));
                        x1 += xMove-x0;
                        y1 += yMove-y0;
                        x2 += xMove-x0;
                        y2 += yMove-y0;

                        draw();

                        changePointPosition(img1, (int)x1, (int)y1);
                        changePointPosition(img2, (int)x2, (int)y2);

                        x0 = xMove;
                        y0 = yMove;
                    }

                    if (state == STATE_DRAG_PARALLEL){
                        parallel_space += yMove-y0;
                        draw();
                        x0 = xMove;
                        y0 = yMove;
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    float xUp = event.getX();
                    float yUp = event.getY();

                    if (state == STATE_DRAW) {
                        if (drawPosition == 1){
                            x1 = xUp + getScrollX();
                            y1 = yUp + getScrollY();
                        }else {
                            x2 = xUp + getScrollX();
                            y2 = yUp + getScrollY();
                        }

                        if (((x1- x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) > pointWidth * pointWidth + pointWidth * pointWidth) {//大于一定长度才画线
                            if (type == Type.LINE)
                                drawLine();
                            if (type == Type.LONG_LINE)
                                drawLongLine();

                            if (!hasEnd) {
                                img2 = new ImageView(context);
                                img2.setClickable(false);
                                img2.setLongClickable(false);
                                putPointView(img2, (int) xUp, (int) yUp);
                                hasEnd = true;
                                if (callback != null){
                                    callback.drawSuccess();
                                }
                            } else {
                                if (drawPosition == 1)
                                    changePointPosition(img1, (int)x1, (int)y1);
                                else
                                    changePointPosition(img2, (int)x2, (int)y2);
                            }
                        }
                    }

                    if (state == STATE_DRAG) {

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

        private void init() {
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5.0f);
            paint.setAntiAlias(true);

            bitmap = Bitmap.createBitmap(1080*3, 1920*3, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            setBackgroundColor(Color.TRANSPARENT);
            focusable = true;
        }

        public void setType(Type type){
            this.type = type;
            if (type == Type.RECT){
                paint.setStyle(Paint.Style.STROKE);
            }
        }

        public void loseFocus(){
            if (img1 != null)
                img1.setVisibility(View.INVISIBLE);
            if (img2 != null)
                img2.setVisibility(View.INVISIBLE);
            focusable = false;
        }

        public void ownFocus(){
            if (img1 != null)
                img1.setVisibility(View.VISIBLE);
            if (img2 != null)
                img2.setVisibility(View.VISIBLE);
            focusable = true;
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

        public void draw(){
            switch (type){
                case LINE:
                    drawLine();
                    break;
                case LONG_LINE:
                    drawLongLine();
                    break;
                case RECT:
                    drawRect();
                    break;
                case PARALLEL:
                    drawParallel();
                    break;
            }
        }

        //两点画线
        public void drawLine() {
            if (canvas != null) {
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);//清除之前的line
                canvas.drawLine(x1, y1, x2, y2, paint);
                invalidate();
            }
        }

        //画射线
        public void drawLongLine(){
            if (canvas != null){
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);//清除之前的line
                canvas.drawLine(x1, y1, x2, y2, paint);

//                射线与view边界交点坐标
                calculatePoint();
                canvas.drawLine(x2, y2, x3, y3, paint);

                invalidate();
            }
        }

        //画矩形
        public void drawRect(){
            if (canvas != null){
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);//清除之前的line
//                canvas.drawLine(x1, y1, x1, y2, paint);
//                canvas.drawLine(x1, y1, x2, y1, paint);
//                canvas.drawLine(x2, y2, x2, y1, paint);
//                canvas.drawLine(x2, y2, x1, y2, paint);
                float left=0, top =0, right=0, bottom=0;
                if (x1<=x2){
                    left = x1;
                    right = x2;
                }else {
                    left = x2;
                    right = x1;
                }

                if (y1<=y2){
                    top = y1;
                    bottom = y2;
                }else {
                    top= y2;
                    bottom = y1;
                }

                canvas.drawRect(left, top, right, bottom, paint);

                invalidate();
            }
        }

        //画平行线
        public void  drawParallel(){
            if (canvas != null){
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);//清除之前的line
                canvas.drawLine(x1, y1, x2, y2, paint);
                canvas.drawLine(x1, y1+parallel_space, x2, y2+parallel_space, paint);
                invalidate();
            }
        }

        //是否选中
        public boolean hasPicked(int xDown, int yDown){
            switch (type){
                case LINE:
                    if (pointToline(x1, y1, x2, y2, xDown, yDown) < validLength) {
                        if ((x1<xDown && xDown<x2)
                                || (x2<xDown && xDown<x1)
                                || (y1<yDown && yDown<y2)
                                || (y2<yDown && yDown<y1)){
                            state = STATE_DRAG;
                            return true;
                        }
                    }
                    break;

                case LONG_LINE:
                    if (pointToline(x1, y1, x2, y2, xDown, yDown) < validLength) {
                        if ((x1<x2 && x1<xDown)
                                || (x1>x2 && x1>xDown)
                                || (y1<y2 && y1<yDown)
                                || (y1>y2 && y1>yDown)){
                            state = STATE_DRAG;
                            return true;
                        }
                    }
                    break;

                case RECT:
                    if (inRect(xDown, yDown)){
                        state = STATE_DRAG;
                        return true;
                    }
                    break;

                case PARALLEL:
                    //选中基线
                    if (pointToline(x1, y1, x2, y2, xDown, yDown) < validLength) {
                        if ((x1<xDown && xDown<x2)
                                || (x2<xDown && xDown<x1)
                                || (y1<yDown && yDown<y2)
                                || (y2<yDown && yDown<y1)){
                            state = STATE_DRAG;
                            return true;
                        }
                    }

                    //选中副线
                    if (pointToline(x1, y1+parallel_space, x2, y2+parallel_space, xDown, yDown) < validLength) {
                        if ((x1<xDown && xDown<x2)
                                || (x2<xDown && xDown<x1)
                                || (y1<yDown && yDown<y2)
                                || (y2<yDown && yDown<y1)){
                            state = STATE_DRAG_PARALLEL;
                            return true;
                        }
                    }
            }

            return false;
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

        //两点之间的距离
        public double lineSpace(double x1, double y1, double x2, double y2) {
            return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        }

        //计算射线在屏幕的交点
        public void calculatePoint(){
            int width = getWidth();
            int height = getHeight();

            if (x1 == x2){
                x3 = x1;
                if (y1 > y2)
                    y3 = width + getScrollY();
                else
                    y3 = getScrollY();
            }

            if (y1 == y2){
                y3 = y1;
                if (x1 < x2)
                    x3 = width + getScrollX();
                else
                    x3 = getScrollX();
            }

            if (x1 < x2){
                //屏幕右上角与起点的斜率 正值
                float k1 = -(getScrollY() - y1) / ((width+getScrollX() - x1));
                //屏幕右下角与起点的斜率 负值
                float k2 = ((height+getScrollY() - y1)) / (x1 - (width+getScrollX()));
                //变化值
                float k3 = (y1-y2) / (x2-x1);
                if (k3 > k2 && k3 < k1){//交点在view右边 //考虑画布的拖动，把x轴往右增加一倍，y轴相应改变
                    x3 = width + getScrollX() + width;
                    y3 = y1 + k3*(x1-x3);
                }else if (k3 >= k1){//交点在view上边 //考虑画布的拖动，把y轴往右增加一倍，x轴相应改变
                    y3 = getScrollY() - height;
                    x3 = x1 + (y1-y3)/k3;
                }else if (k3 <= k2){//交点在view下边
                    y3 = height+getScrollY() + height;
                    x3 = x1 + (y1-y3)/k3;
                }
            }else {
                //屏幕左上上角与起点的斜率 负值
                float k1 = -(getScrollY() -y1) / (getScrollX() - x1);
                //屏幕左下角与起点的斜率 正值
                float k2 = -(height+getScrollY() - y1) / (getScrollX() -x1);
                //变化值
                float k3 = (y1-y2) / (x2-x1);
                if (k3 > k1 && k3 < k2){//交点在view左边
                    x3 = getScrollX() - width;
                    y3 = y1 + k3*(x1-x3);
                }else if (k3 <= k1){//交点在view上边
                    y3 = getScrollY() - height;
                    x3 = x1 + (y1-y3)/k3;
                }else if(k3 >= k2){//交点在view下边
                    y3 = height + getScrollY() + height;
                    x3 = x1 + (y1-y3)/k3;
                }
            }
        }

        //是否在矩形内
        public boolean inRect(int xDown, int yDown){
            if ((xDown > Math.min(x1, y2))
                    &&(xDown < Math.max(x1, x2))
                    && yDown > Math.min(y1, y2)
                    && yDown < Math.max(y1, y2))
                return true;

            return false;
        }

        public Canvas getCanvas() {
            return canvas;
        }

        public void setCanvas(Canvas canvas) {
            this.canvas = canvas;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }
}
