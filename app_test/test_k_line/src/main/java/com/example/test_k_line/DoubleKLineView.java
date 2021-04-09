package com.example.test_k_line;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DoubleKLineView extends FrameLayout {
    private int tagColor1;
    private String tagName1;
    private int tagColor2;
    private String tagName2;
    private String nameLeft;
    private String nameRight;
    private KLineData kLineDataLeft;
    private KLineData kLineDataRight;

    private CircleView c1;
    private CircleView c2;
    private TextView tv_tag_1;
    private TextView tv_tag_2;
    private TextView tv_name_left;
    private TextView tv_name_right;
    private KLineView klineview;
    private TextView tv_k_left_top;
    private TextView tv_k_left_mid;
    private TextView tv_k_left_bot;
    private TextView tv_k_right_top;
    private TextView tv_k_right_mid;
    private TextView tv_k_right_bot;
    private TextView tv_x_name_left;
    private TextView tv_x_name_mid;
    private TextView tv_x_name_right;


    public DoubleKLineView(Context context) {
        super(context);
    }

    public DoubleKLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.line_detail, null);
        initView(view);
        setData();
        addView(view);
    }

    public void initView(View view){
        c1 = view.findViewById(R.id.c1);
        c2 = view.findViewById(R.id.c2);
        tv_tag_1 = view.findViewById(R.id.tv_tag_1);
        tv_tag_2 = view.findViewById(R.id.tv_tag_2);
        tv_name_left = view.findViewById(R.id.tv_name_left);
        tv_name_right = view.findViewById(R.id.tv_name_right);
        klineview = view.findViewById(R.id.klineview);
        tv_k_left_top = view.findViewById(R.id.tv_k_left_top);
        tv_k_left_mid = view.findViewById(R.id.tv_k_left_mid);
        tv_k_left_bot = view.findViewById(R.id.tv_k_left_bot);
        tv_k_right_top = view.findViewById(R.id.tv_k_right_top);
        tv_k_right_mid = view.findViewById(R.id.tv_k_right_mid);
        tv_k_right_bot = view.findViewById(R.id.tv_k_right_bot);
        tv_x_name_left = view.findViewById(R.id.tv_x_name_left);
        tv_x_name_mid = view.findViewById(R.id.tv_x_name_mid);
        tv_x_name_right = view.findViewById(R.id.tv_x_name_right);
    }

    private void setData(){
        if (c1 != null && tagColor1 != 0){
            c1.setPaintColor(tagColor1);
        }

        if (c2 != null && tagColor2 != 0){
            c2.setPaintColor(tagColor2);
        }

        Util.setText(tv_tag_1, tagName1);
        Util.setText(tv_tag_2, tagName2);
        Util.setText(tv_name_left, nameLeft);
        Util.setText(tv_name_right, nameRight);

        //设置k线数据
        if (klineview != null){
            klineview.addKLineData(kLineDataLeft);
            klineview.addKLineData(kLineDataRight);
        }
        //设置左Y轴坐标
        if (kLineDataLeft != null){
            float[] minMaxLeft = Util.minMaxValue(kLineDataLeft.kLineValues);
            if (minMaxLeft != null){
                Util.setText(tv_k_left_top, Util.transFloat(minMaxLeft[1], 2));
                Util.setText(tv_k_left_bot, Util.transFloat(minMaxLeft[0], 2));
                Util.setText(tv_k_left_mid, Util.transFloat(Util.getAvg(minMaxLeft), 2));
            }
        }

        //设置右Y轴坐标
        if (kLineDataRight != null){
            float[] minMaxLeft = Util.minMaxValue(kLineDataRight.kLineValues);
            if (minMaxLeft != null){
                Util.setText(tv_k_right_top, Util.transFloat(minMaxLeft[1], 2));
                Util.setText(tv_k_right_bot, Util.transFloat(minMaxLeft[0], 2));
                Util.setText(tv_k_right_mid, Util.transFloat(Util.getAvg(minMaxLeft), 2));
            }
        }
    }



    public void refresh(){
        setData();

        requestLayout();
    }

    public void setTagColor1(int tagColor1) {
        this.tagColor1 = tagColor1;
    }

    public void setTagName1(String tagName1) {
        this.tagName1 = tagName1;
    }

    public void setTagColor2(int tagColor2) {
        this.tagColor2 = tagColor2;
    }

    public void setTagName2(String tagName2) {
        this.tagName2 = tagName2;
    }

    public void setNameLeft(String nameLeft) {
        this.nameLeft = nameLeft;
    }

    public void setNameRight(String nameRight) {
        this.nameRight = nameRight;
    }

    public void setkLineDataLeft(KLineData kLineDataLeft) {
        this.kLineDataLeft = kLineDataLeft;
    }

    public void setkLineDataRight(KLineData kLineDataRight) {
        this.kLineDataRight = kLineDataRight;
    }
}
