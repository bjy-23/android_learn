package bjy.edu.android_learn.widget;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bjy.edu.android_learn.R;

public class ViewActivity extends AppCompatActivity {
    SixDimensionIndicator sixDimensionIndicator;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        view_1();

//        view_2();
        view_2_1();

//        view_3();

//        view_4();

    }

    private void view_1() {
        setContentView(R.layout.activity_view);
        sixDimensionIndicator = findViewById(R.id.sixDimensionIndicator);
//        View view = new View(ViewActivity.this);
//        view.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("1", "post");
//                sixDimensionIndicator.requestLayout();
//            }
//        }, 3000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("1", "post");
                sixDimensionIndicator.showIndicator(4);
            }
        }, 100);
    }

    private void view_2() {
        setContentView(R.layout.activity_view_2);

        final LineView lineView = findViewById(R.id.line_view);
        final ArrayList<TextView> tvs = new ArrayList<>();
        lineView.setCallback(new LineView.Callback() {
            @Override
            public void drawSuccess() {
                for (TextView tv : tvs) {
                    if ("1".equals(tv.getTag().toString())) {
                        tv.setTag("0");
                        tv.setBackgroundColor(Color.WHITE);
                    }
                }
            }
        });
        //直线
        final TextView tv_1 = findViewById(R.id.tv_1);
        tv_1.setTag("0");
        tvs.add(tv_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(tv_1.getTag().toString())) {
                    tv_1.setBackgroundColor(Color.WHITE);
                    tv_1.setTag("0");
                    lineView.removeDraw();
                } else {
                    count++;
                    lineView.addDraw(LineView.Type.LINE);
                    tv_1.setBackgroundColor(Color.BLUE);
                    tv_1.setTag("1");
                }
            }
        });

        //射线
        final TextView tv_2 = findViewById(R.id.tv_2);
        tvs.add(tv_2);
        tv_2.setTag("0");
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(tv_2.getTag().toString())) {
                    tv_2.setBackgroundColor(Color.WHITE);
                    tv_2.setTag("0");
                    lineView.removeDraw();
                } else {
                    count++;
                    lineView.addDraw(LineView.Type.LONG_LINE);
                    tv_2.setBackgroundColor(Color.BLUE);
                    tv_2.setTag("1");
                }
            }
        });

        //矩形
        final TextView tv_3 = findViewById(R.id.tv_3);
        tvs.add(tv_3);
        tv_3.setTag("0");
        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(tv_3.getTag().toString())) {
                    tv_3.setBackgroundColor(Color.WHITE);
                    tv_3.setTag("0");
                    lineView.removeDraw();
                } else {
                    count++;
                    lineView.addDraw(LineView.Type.RECT);
                    tv_3.setBackgroundColor(Color.BLUE);
                    tv_3.setTag("1");
                }
            }
        });

        //平行线
        final TextView tv_4 = findViewById(R.id.tv_4);
        tvs.add(tv_4);
        tv_4.setTag("0");
        tv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(tv_4.getTag().toString())) {
                    tv_4.setBackgroundColor(Color.WHITE);
                    tv_4.setTag("0");
                    lineView.removeDraw();
                } else {
                    count++;
                    lineView.addDraw(LineView.Type.PARALLEL);
                    tv_4.setBackgroundColor(Color.BLUE);
                    tv_4.setTag("1");
                }
            }
        });

        //缩放
        TextView tv_5 = findViewById(R.id.tv_5);
        tv_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view层级的操作
//                for (int i = 0; i < lineView.getChildCount(); i++) {
//
//                    lineView.getChildAt(i).setScaleX(0.5f);
//                    lineView.getChildAt(i).setScaleY(0.5f);
//                }

                for (LineView.LineViewItem lineViewItem : lineView.getLines()){
                    lineViewItem.getCanvas().scale(.5f, .5f);
                    lineViewItem.draw();
                }
            }
        });
    }

    private void view_2_1(){
        setContentView(R.layout.activity_view_2_1);

        final List<TextView> tvs = new ArrayList<>();
        final LineView2 lineView= findViewById(R.id.line_view);
        //直线
        final TextView tv_1 = findViewById(R.id.tv_1);
        tv_1.setTag("0");
        tvs.add(tv_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(tv_1.getTag().toString())) {
                    tv_1.setBackgroundColor(Color.WHITE);
                    tv_1.setTag("0");
                    lineView.clearDraw();
                } else {
                    changeState(tvs);
                    lineView.newDraw(LineView2.Type.LINE);
                    tv_1.setBackgroundColor(Color.BLUE);
                    tv_1.setTag("1");
                }
            }
        });

        //射线
        final TextView tv_2 = findViewById(R.id.tv_2);
        tvs.add(tv_2);
        tv_2.setTag("0");
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(tv_2.getTag().toString())) {
                    tv_2.setBackgroundColor(Color.WHITE);
                    tv_2.setTag("0");
                    lineView.clearDraw();
                } else {
                    changeState(tvs);
                    lineView.newDraw(LineView2.Type.RAY);
                    tv_2.setBackgroundColor(Color.BLUE);
                    tv_2.setTag("1");
                }
            }
        });

        //矩形
        final TextView tv_3 = findViewById(R.id.tv_3);
        tvs.add(tv_3);
        tv_3.setTag("0");
        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(tv_3.getTag().toString())) {
                    tv_3.setBackgroundColor(Color.WHITE);
                    tv_3.setTag("0");
                    lineView.clearDraw();
                } else {
                    changeState(tvs);
                    lineView.newDraw(LineView2.Type.RECT);
                    tv_3.setBackgroundColor(Color.BLUE);
                    tv_3.setTag("1");
                }
            }
        });

        //平行线
        final TextView tv_4 = findViewById(R.id.tv_4);
        tvs.add(tv_4);
        tv_4.setTag("0");
        tv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(tv_4.getTag().toString())) {
                    tv_4.setBackgroundColor(Color.WHITE);
                    tv_4.setTag("0");
                    lineView.clearDraw();
                } else {
                    changeState(tvs);
                    lineView.newDraw(LineView2.Type.PARALLEL);
                    tv_4.setBackgroundColor(Color.BLUE);
                    tv_4.setTag("1");
                }
            }
        });

        //图形
        ImageView imageView = findViewById(R.id.img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineView.newDraw(LineView2.Type.BITMAP);
            }
        });
    }

    public void view_3(){
        setContentView(R.layout.activity_view_3);
    }

    public void view_4(){
        setContentView(R.layout.activity_view_4);
    }

    public void changeState(List<TextView> tvs){
        for (TextView tv : tvs){
            tv.setTag("0");
            tv.setBackgroundColor(Color.WHITE);
        }
    }
}
