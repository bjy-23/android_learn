package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sogubaby on 2018/6/28.
 */

public class SixDimensionIndicator extends LinearLayout {
    private Context context;
    private static final int SIZE = 6;//长度
    private int interval;//默认间隔
//    private int interval = = dp2px(3);// TODO: 2018/6/29 context为空
    private int viewHeight;//默认高度
    private List<TextView> tvList;//
    private List<View> viewList;//
    private boolean addChild;

    public SixDimensionIndicator(Context context) {
        this(context, null);
    }

    public SixDimensionIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        interval = dp2px(3);
        viewHeight = dp2px(8);
        setOrientation(LinearLayout.HORIZONTAL);
//        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.e("1", "onMeasure");
//        Log.e("totalWidth3", getMeasuredWidth() + "");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //在onLayout中获取宽度和高度后，直接使用requestLayout存在风险，这个响应可能会丢失
        Log.e("2", "onLayout");
        if (!addChild){
            Handler handler = new Handler();

//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e("1", "post");
//                    initView();
//                }
//            }, 0);

            post(new Runnable() {
                @Override
                public void run() {
                    Log.e("1", "post");
                    initView();
                    //未调用requestLayout(),布局也得到了更新
                }
            });

//            initView();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.e("3", "onDraw");
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        Log.e("1", "requestLayout");
    }

    public void initView() {
        addChild = true;
        Log.e("3", "initView");
        int totalWidth = getWidth();
//        int totalWidth = 1080;
        Log.e("totalWidth  -> ", totalWidth + "");

        int width = (totalWidth - (SIZE - 1) * interval) / SIZE;

        tvList = new ArrayList<>();
        viewList = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            if (i != 0)
                lp.setMargins(interval, 0, 0, 0);
            linearLayout.setLayoutParams(lp);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView textView = new TextView(context);
            LinearLayout.LayoutParams lp_1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(lp_1);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(12);
            linearLayout.addView(textView);
            tvList.add(textView);

            View view = new View(context);
            LinearLayout.LayoutParams lp_2 = new LinearLayout.LayoutParams(width, viewHeight);
            view.setLayoutParams(lp_2);
            view.setBackgroundColor(Color.RED);
            linearLayout.addView(view);
            viewList.add(view);

            addView(linearLayout);
        }

//        requestLayout();
//        Log.e("3", "requestLayout");
//
//        invalidate();
//        Log.e("3", "invalidate");
    }

    public void showIndicator(int size) {
        if (size < 0)
            size = 0;
        if (size > SIZE)
            size = SIZE;

        //设置文字
        if (size > 0) {
            tvList.get(size - 1).setText(size + "");
        }

        //设置颜色
        for (int i = 0; i < SIZE; i++) {
            if (i == 0) {
                if (i < size)
                    viewList.get(i).setBackgroundColor(Color.RED);
                else
                    viewList.get(i).setBackgroundColor(Color.RED);
            } else if (i == SIZE - 1) {
                if (i < size)
                    viewList.get(i).setBackgroundColor(Color.RED);
                else
                    viewList.get(i).setBackgroundColor(Color.RED);
            } else {
                if (i < size)
                    viewList.get(i).setBackgroundColor(Color.RED);
                else
                    viewList.get(i).setBackgroundColor(Color.RED);
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
