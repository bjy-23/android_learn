package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import bjy.edu.android_learn.R;


public class DragIndicator extends FrameLayout {
    private Context context;
    private int maxWidth;
    private LinearLayout.LayoutParams lp_tv;

    private LinearLayout layout_drag;
    private DragView dragView;
    View view;
    TextView tv;
    private float xDown;
    private float xMove;
    LinearLayout.LayoutParams layoutParams;

    public DragIndicator(@NonNull Context context) {
        this(context, null);
    }

    public DragIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        maxWidth = (int) (layout_drag.getWidth() - dragView.getWidth() + dp2px(15));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                if (dragListener != null)
                    dragListener.dragStart();
//                Log.e("xDown", xDown+"");
                return true;
            case MotionEvent.ACTION_MOVE:
                if (dragView.isDrag){
                    xMove = event.getRawX();
//                    Log.e("xMove", xMove+"");
                    float x = xMove - xDown;
                    if (layoutParams.width + x < 0)
                        layoutParams.width = 0;
                    else if (layoutParams.width + x > maxWidth) {
                        layoutParams.width = maxWidth;
                    } else {
                        layoutParams.width = (int) (layoutParams.width + x);
                    }
                    view.setLayoutParams(layoutParams);

                    if (lp_tv.leftMargin + x < 0) {
                        lp_tv.leftMargin = 0;
                    } else if (lp_tv.leftMargin + x > maxWidth) {
                        lp_tv.leftMargin = maxWidth;
                    } else {
                        lp_tv.leftMargin = (int) (lp_tv.leftMargin + x);
                    }
                    tv.setLayoutParams(lp_tv);
                    tv.setText(getFcNumber());
                    xDown = xMove;
                    return true;
                }else
                    break;

            case MotionEvent.ACTION_UP:
                if (dragListener != null)
                    dragListener.dragEnd();
                dragView.isDrag = false;
                Log.e("isDrag", dragView.isDrag + "");


        }
        return super.onTouchEvent(event);
    }

    public interface DragListener{
        void dragStart();

        void dragEnd();
    }

    DragListener dragListener;

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    public void init() {
        addView(View.inflate(context, R.layout.drag_indicator, null));

        tv = findViewById(R.id.tv);
        lp_tv = (LinearLayout.LayoutParams) tv.getLayoutParams();

        layout_drag = findViewById(R.id.layout_drag);

        view = findViewById(R.id.view_left);
        layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();

        dragView = findViewById(R.id.drag_view);
        dragView.setDragListener(new DragView.DragListener() {
            @Override
            public void dragX(float x) {
                if (layoutParams.width + x < 0)
                    layoutParams.width = 0;
                else if (layoutParams.width + x > maxWidth) {
                    layoutParams.width = maxWidth;
                } else {
                    layoutParams.width = (int) (layoutParams.width + x);
                }
                view.setLayoutParams(layoutParams);

                if (lp_tv.leftMargin + x < 0) {
                    lp_tv.leftMargin = 0;
                } else if (lp_tv.leftMargin + x > maxWidth) {
                    lp_tv.leftMargin = maxWidth;
                } else {
                    lp_tv.leftMargin = (int) (lp_tv.leftMargin + x);
                }
                tv.setLayoutParams(lp_tv);
                tv.setText(getFcNumber());
            }
        });
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private String getFcNumber() {
        return ((int) ((float) lp_tv.leftMargin / maxWidth * 5000)) + "Fc";
    }

    public static class DragView extends View {
        private float xDown;
        private float xMove;
        private DragListener dragListener;
        public boolean isDrag;

        public DragView(Context context) {
            this(context, null);
        }

        public DragView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getRawX();
                    isDrag = true;
                    Log.e("isDrag", ""+isDrag);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (dragListener != null) {
                        xMove = event.getRawX();
                        dragListener.dragX(xMove - xDown);
                        xDown = xMove;
                    }
                    break;

            }
            return super.onTouchEvent(event);
        }

        interface DragListener {
            void dragX(float x);
        }

        public void setDragListener(DragListener dragListener) {
            this.dragListener = dragListener;
        }
    }
}
