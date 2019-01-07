package bjy.edu.android_learn.widget.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bjy.edu.android_learn.R;


public class TabGroup extends LinearLayout {
    private Context context;

    private List<Tab> tabs = new ArrayList<>();
    private String[] titles = {"行情", "分析师", "评论"};

    public TabGroup(Context context) {
        this(context, null);
    }

    public TabGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        initView();
    }

    public void initView(){
       for (int i=0; i<titles.length; i++){

           Tab tab = new Tab(context);
           tab.setText(titles[i]);
           final int position = i;
           tab.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v) {
                   setSelectPosition(position);

                   if (onSelectListener != null)
                       onSelectListener.onSelect(position);
               }
           });

           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
           if (i != 0)
               lp.setMargins(dp2px(20), 0, 0, 0);

           tab.setLayoutParams(lp);
           addView(tab);

           tabs.add(tab);
       }

       //默认选中第一个位置
       setSelectPosition(0);
    }

    public void setSelectPosition(int position){
        for (int i=0; i<tabs.size(); i++){
            if (i == position)
                tabs.get(i).setSelected(true);
            else
                tabs.get(i).setSelected(false);
        }

    }

    public interface OnSelectListener{
        void onSelect(int position);
    }

    private OnSelectListener onSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public class Tab extends RelativeLayout {
        private Context context;
        private boolean selected;//是否选中

        TextView tv;
        View indicator;

        public Tab(Context context) {
            this(context, null);
        }

        public Tab(Context context, AttributeSet attrs) {
            super(context, attrs);

            this.context = context;
            initView();
        }

        public void initView(){
            tv = new TextView(context);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(16.0f);

            RelativeLayout.LayoutParams lp_1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp_1.addRule(RelativeLayout.CENTER_IN_PARENT);
            tv.setLayoutParams(lp_1);
            addView(tv);

            indicator = new View(context);
            // TODO: 2018/10/14 宽高怎么限制 ？？？根据文字长度; drawable怎么限制弧度、宽高？？？
            indicator.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rect_white_1));
            RelativeLayout.LayoutParams lp_2 = new RelativeLayout.LayoutParams(dp2px(30), dp2px(3));
            lp_2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp_2.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lp_2.setMargins(0, 0, 0, dp2px(1));
            indicator.setLayoutParams(lp_2);
            addView(indicator);
        }

        public void setText(String text){
            tv.setText(text);
        }

        public void setSelected(boolean selected){
            this.selected = selected;

            if (selected){
                indicator.setVisibility(View.VISIBLE);
                tv.setTextColor(Color.parseColor("#ffffff"));
            }else {
                indicator.setVisibility(View.GONE);
                tv.setTextColor(Color.parseColor("#b2ffffff"));
            }
        }
    }

    private int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
