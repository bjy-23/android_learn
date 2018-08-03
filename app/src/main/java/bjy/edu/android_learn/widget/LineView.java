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

    public void addDraw(){
        drawPosition++;
        LineViewItem lineViewItem = new LineViewItem(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lineViewItem.setLayoutParams(lp);
        addView(lineViewItem);

        lines.add(lineViewItem);
    }

    private class LineViewItem extends FrameLayout{
        private Context context;
        private Bitmap bitmap;
        private Canvas canvas;

        private float x1;
        private float y1;
        private float x2;
        private float y2;

        private boolean canDraw;//可以画线
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

        private void init(){
            bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);//
            canvas = new Canvas(bitmap);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!hasStart) {
                        x1 = event.getX();
                        y1 = event.getY();
                        hasStart = true;
                        canDraw = true;

                        img1 = new ImageView(context);
                        putPointView(img1, (int) x1, (int) y1);
                    } else {
                        if (!hasEnd){
                            if (Math.abs(event.getX() - x1) < validLength && Math.abs(event.getY() - y1) < validLength) {//在初始点附近
                                canDraw = true;
                            }
                        }else {
                            if (Math.abs(event.getX() - x1) < validLength && Math.abs(event.getY() - y1) < validLength) {//在初始点附近
                                canDraw = true;
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
                            }else {
                                if (Math.abs(event.getX() - x2) < validLength && Math.abs(event.getY() - y2) < validLength) {//在终点附近
                                    canDraw = true;
                                }
                            }
                        }
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (canDraw) {
                        x2 = event.getX();
                        y2 = event.getY();

                        if (((x1 - x2) * (x1 - x2) + (y1 - y2) *(y1 - y2)) > pointWidth*pointWidth + pointWidth * pointWidth) {//大于一定长度才画线
                            drawLine();

                            if (hasEnd){
                                changePointPosition(img2, (int) x2, (int) y2);
                            }
                        }

                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (canDraw){
                        x2 = event.getX();
                        y2 = event.getY();

                        if (((x1 - x2) * (x1 - x2) + (y1 - y2) *(y1 - y2)) > pointWidth*pointWidth + pointWidth * pointWidth){//大于一定长度才画线
                            drawLine();
                            if (!hasEnd){
                                img2 = new ImageView(context);
                                putPointView(img2, (int) x2, (int) y2);
                                hasEnd = true;
                            }else {
                                changePointPosition(img2, (int) x2, (int) y2);
                            }
                        }
                    }
                    canDraw = false;
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
            layoutParams.setMargins(x-pointWidth/2, y-pointWidth/2, 0, 0);
            addView(imageView);
//        post(new Runnable() {
//            @Override
//            public void run() {
//                imageView.layout(Math.max(x - 10, 0), Math.max(y - 10, 0), Math.max(x + 10, 0), Math.max(y + 10, 0));
//            }
//        });
        }

        public void changePointPosition(ImageView imageView, int x, int y){
            if (imageView == null)
                return;
            FrameLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
            layoutParams.setMargins(x-pointWidth/2, y-pointWidth/2, 0, 0);
            requestLayout();
        }

        public void drawLine(){
            if (canvas != null) {
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);//清除之前的line
                canvas.drawLine(x1, y1, x2, y2, paint);
                invalidate();
            }
        }
    }
}
