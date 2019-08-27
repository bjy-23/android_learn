package bjy.edu.android_learn.recyclerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import bjy.edu.android_learn.R;

//recyclerView左划删除View
// 1: 同一屏幕下左右两个view各自改变width来显隐; (缺点：width很小的情况下view显示会变形)
// 2：左边view改变margin_left来显隐
public class LeftDragView extends LinearLayout {
    private Context context;
    private FrameLayout frameLayout;
    private View viewRight;

    private float x0;
    private float offset;
    private LinearLayout.LayoutParams lp_right;
    private LinearLayout.LayoutParams lp_left;
    private int maxLength;

    private boolean childClickable = true;

    public LeftDragView(Context context) {
        this(context, null);
    }

    public LeftDragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftDragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);

        maxLength = dp2px(120);
    }

    //onMeasure被调用时，xml布局下的所有子view已经被add了
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO: 2019-08-22 第一次渲染 onMeasure为什么会加载两次
        //
//        Log.i("onMeasure childCount", "" + getChildCount());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        if (getChildCount() == 1) {
//            View child0 = getChildAt(0);
////            Log.i("onMeasure child0", child0.toString());
////            Log.i("onMeasure child0 width", child0.getWidth() + "");
//            lp_left = (LayoutParams) child0.getLayoutParams();
//            viewRight = View.inflate(context, R.layout.view_right, null);
//            lp_right = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
//            addView(viewRight, lp_right);
//        }

        if (getChildCount() == 1) {
            View child0 = getChildAt(0);
//            Log.i("onMeasure child0", child0.toString());
//            Log.i("onMeasure child0 width", child0.getWidth() + "");
            lp_left = (LayoutParams) child0.getLayoutParams();
            viewRight = View.inflate(context, R.layout.view_right, null);
            TextView tv_delete = viewRight.findViewById(R.id.tv_delete);
            tv_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "删删删", Toast.LENGTH_SHORT).show();
                }
            });
            lp_right = new LinearLayout.LayoutParams(maxLength, LayoutParams.MATCH_PARENT);
//            lp_right.rightMargin = -maxLength;
            addView(viewRight, lp_right);
        }

        //参考ScrollView
        if (getChildCount() > 1){
            if (viewRight != getChildAt(1)){
                throw new IllegalStateException("LeftDragView can host only one direct child");
            }
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.i("onLayout childCount", "" + getChildCount());
        super.onLayout(changed, l, t, r, b);

        View child0 = getChildAt(0);
        lp_left = (LayoutParams) child0.getLayoutParams();
//        Log.i("onLayout child0 ", child0.toString());
        //match_parent 对应的lp.width = -1; 需要手动设置为固定长度
        lp_left.width = child0.getWidth();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        Log.i("leftDrag dispatch", event.getAction() + "");

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x0 = event.getX();
//                Log.i("x0", "" + x0);

                childClickable = true;
                break;
            case MotionEvent.ACTION_MOVE:
                offset = x0 - event.getX();
                x0 = event.getX();
//                Log.i("offset", "" + offset);
//                setRighWidth();
                setMargin();

                //有move事件时，屏蔽子view的onClicklistener
                childClickable = false;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // TODO: 2019-08-22 最后一个事件不是UP ？？
//                Log.i("111", "ACTION_UP");
//                Log.i("111", "" + event.getAction());
//                setRighWidthUp();
                setEnd2();
                if (!childClickable)
                    return true;
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //直接 true：tv_delete无法响应点击事件
//        return true;

        //默认返回：拖动textview所在区域无效

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                x0 = event.getX();
////                Log.i("x0", "" + x0);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                offset = x0 - event.getX();
//                x0 = event.getX();
////                Log.i("offset", "" + offset);
////                setRighWidth();
//                setMargin();
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                // TODO: 2019-08-22 最后一个事件不是UP ？？
////                Log.i("111", "ACTION_UP");
////                Log.i("111", "" + event.getAction());
////                setRighWidthUp();
//                setEnd2();
//                break;
//        }
        
        Log.i("onTouchEvent", event.getAction() + "  " + super.onTouchEvent(event));
        // TODO: 2019-08-23 如果down事件不return true, 后续的事件dispatchtouchevent也收不到，why???? 
        if (MotionEvent.ACTION_DOWN == event.getAction())
            return true;
        return super.onTouchEvent(event);
    }

    private void setRighWidth() {
        lp_right = (LayoutParams) viewRight.getLayoutParams();
        if (lp_right.width < 0)
            lp_right.width = 0;
        if (lp_right.width > maxLength)
            lp_right.width = maxLength;

        lp_left.width = getWidth() - lp_right.width;

        Log.i("lp_right  width", "" + lp_right.width);
//        Log.i("lp_left  width", "" + lp_left.width);
        requestLayout();
    }

    private void setMargin(){
        lp_left.leftMargin -= offset;
        if (lp_left.leftMargin > 0)
            lp_left.leftMargin = 0;
        if (lp_left.leftMargin < -maxLength)
            lp_left.leftMargin = -maxLength;

        requestLayout();
    }

    private void setRighWidthUp() {
        lp_right = (LayoutParams) viewRight.getLayoutParams();
        int end = 0;
        if (lp_right.width > maxLength / 2) {
            end = maxLength;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(lp_right.width, end).setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp_right.width = (int) animation.getAnimatedValue();
                lp_left.width = getWidth() - lp_right.width;

                requestLayout();
            }
        });
        valueAnimator.start();
    }

    private void setEnd2(){
        int end = 0;
        if (lp_left.leftMargin < -maxLength/2){
            end = -maxLength;
        }

//        Log.i("leftMargin", ""+lp_left.leftMargin);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(lp_left.leftMargin, end).setDuration(150);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.i("int value", ""+(int) animation.getAnimatedValue());
                lp_left.leftMargin = (int) animation.getAnimatedValue();

                requestLayout();
            }
        });
        valueAnimator.start();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
