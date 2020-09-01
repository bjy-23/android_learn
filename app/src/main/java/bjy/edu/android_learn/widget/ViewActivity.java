package bjy.edu.android_learn.widget;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.view.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bjy.edu.android_learn.R;
import bjy.edu.android_learn.widget.view.CircleWaveView;
import bjy.edu.android_learn.widget.view.DragView;
import bjy.edu.android_learn.widget.view.LineView;
import bjy.edu.android_learn.widget.view.LineView2;
import bjy.edu.android_learn.widget.view.PickView;
import bjy.edu.android_learn.widget.view.SixDimensionIndicator;
import bjy.edu.android_learn.widget.view.TabGroup;

public class ViewActivity extends AppCompatActivity {
    SixDimensionIndicator sixDimensionIndicator;
    private int count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position = 5;
        switch (position) {
            case 14:
                //pickview
                view_14();
                break;
            case 13:
//                圆图
                view_13();
                break;
            case 5:
                view_5();
                break;
        }
        //paint
//        base();

//        view_1();

//        view_2();
//        view_2_1();

//        view_3();

//        view_4();

        //可拖拽view
//        view_5();

        //波浪
//        view_6();

        //圆环
//        view_7();

        //visible 和 clickable 关系
//        view_8();

        //dragIndicator
//        view_9();

        //pickView
//        view_10();

        //验证MotionEvent的传递机制
//        view_11();

        //tablayout
//        view_12();
    }

    private void base() {
        Paint paint = new Paint();

        //设置线头的形状 Cap.ROUND(圆头) Cap.SQUARE(方头) Cap.BUTT(平头，默认设置)
        paint.setStrokeCap(Paint.Cap.ROUND);

        //设置线拐角的形状 Join.ROUND(圆角) Join.BEVEL(平角) Join.MITER(尖角)
        paint.setStrokeJoin(Paint.Join.ROUND);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i("dispatchTouchEvent", "activity");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("onTouchEvent", "activity");
        return super.onTouchEvent(event);
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

                for (LineView.LineViewItem lineViewItem : lineView.getLines()) {
                    lineViewItem.getCanvas().scale(.5f, .5f);
                    lineViewItem.draw();
                }
            }
        });
    }

    private void view_2_1() {
        setContentView(R.layout.activity_view_2_1);

        final List<TextView> tvs = new ArrayList<>();
        final LineView2 lineView = findViewById(R.id.line_view);
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

    public void view_3() {
        setContentView(R.layout.activity_view_3);
    }

    public void view_4() {
        setContentView(R.layout.activity_view_4);
    }

    public void view_5() {
        setContentView(R.layout.activity_view_5);

        DragView dragView = findViewById(R.id.dragView);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(0, 0);
        lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//        dragView.setX(300);
//        dragView.setY(300);
    }

    public void view_6() {
        setContentView(R.layout.activity_view_6);

        final CircleWaveView circleWaveView = findViewById(R.id.circleWaveView);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleWaveView.setHeightBase(300);
            }
        }, 3000);
    }

    public void view_7() {
        setContentView(R.layout.activity_view_7);
    }

    public void changeState(List<TextView> tvs) {
        for (TextView tv : tvs) {
            tv.setTag("0");
            tv.setBackgroundColor(Color.WHITE);
        }
    }

    public void view_8() {
        setContentView(R.layout.activity_view_8);

        Button button = findViewById(R.id.btn_invisible);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewActivity.this, "点我点我", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void view_9() {
        setContentView(R.layout.activity_view_9);
    }

    public void view_10() {
        setContentView(R.layout.activity_view_10);

        List<String> data = new ArrayList<>();
        data.add("北京");
        data.add("上海");
        data.add("深证");
        data.add("杭州");

        PickView pickView = findViewById(R.id.pickView);
        pickView.setData(data);
        pickView.show();
    }

    public void view_11() {
        setContentView(R.layout.activity_view_11);
    }

    public void view_12() {
        setContentView(R.layout.activity_view_12);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        //使用默认布局
//        tabLayout.addTab(tabLayout.newTab().setText("火影"));
//        tabLayout.addTab(tabLayout.newTab().setText("海贼"));


        //使用自定义布局
        TabLayout.Tab tab_1 = tabLayout.newTab().setCustomView(R.layout.tab_item);
        TextView tv_1 = tab_1.getCustomView().findViewById(R.id.tv);
        tv_1.setText("火影");
        tabLayout.addTab(tab_1);

        TabLayout.Tab tab_2 = tabLayout.newTab().setCustomView(R.layout.tab_item);
        TextView tv_2 = tab_2.getCustomView().findViewById(R.id.tv);
        tv_2.setText("海贼");
        tabLayout.addTab(tab_2);

        TabLayout.Tab tab_3 = tabLayout.newTab().setCustomView(R.layout.tab_item);
        TextView tv_3 = tab_3.getCustomView().findViewById(R.id.tv);
        tv_3.setText("龙珠");
        tabLayout.addTab(tab_3);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tv = tab.getCustomView().findViewById(R.id.tv);
                View view = tab.getCustomView().findViewById(R.id.view);

                tv.setTextColor(Color.YELLOW);
                view.setBackgroundColor(Color.YELLOW);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tv = tab.getCustomView().findViewById(R.id.tv);
                View view = tab.getCustomView().findViewById(R.id.view);

                tv.setTextColor(Color.GREEN);
                view.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        tab_3.select(); //选中
//        tabLayout.getTabAt(2).select(); //选中
//        tabLayout.setSelectedTabIndicatorHeight(0); // 底部指示器高度
//        tabLayout.setSelectedTabIndicatorColor(Color.BLACK); //指示器颜色

        TabGroup tabGroup = findViewById(R.id.tabGroup);

        TabGroup.Tab tab_4 = tabGroup.new Tab(R.layout.tab_item) {
        };
        View view_root4 = tab_4.getView().findViewById(R.id.view_root);
        TextView tv_4 = tab_4.getView().findViewById(R.id.tv);
        tv_4.setText("鸣人");
        tabGroup.addTab(tab_4);

        TabGroup.Tab tab_5 = tabGroup.new Tab(R.layout.tab_item);
        View view_root5 = tab_5.getView().findViewById(R.id.view_root);
        TextView tv_5 = tab_5.getView().findViewById(R.id.tv);
        tv_5.setText("路飞");
        tabGroup.addTab(tab_5);

        TabGroup.Tab tab_6 = tabGroup.new Tab(R.layout.tab_item);
        View view_root6 = tab_6.getView().findViewById(R.id.view_root);
        TextView tv_6 = tab_6.getView().findViewById(R.id.tv);
        tv_6.setText("悟空");
        tabGroup.addTab(tab_6);

        tabGroup.addTabSelectListener(new TabGroup.TabSelectListener() {
            @Override
            public void tabSelect(TabGroup.Tab tab) {
                TextView tv = tab.getView().findViewById(R.id.tv);
                View view = tab.getView().findViewById(R.id.view);

                tv.setTextColor(Color.YELLOW);
                view.setBackgroundColor(Color.YELLOW);
            }

            @Override
            public void tabUnSelect(TabGroup.Tab tab) {
                TextView tv = tab.getView().findViewById(R.id.tv);
                View view = tab.getView().findViewById(R.id.view);

                tv.setTextColor(Color.GREEN);
                view.setBackgroundColor(Color.GREEN);
            }
        });

        tabGroup.selectTab(2);
    }

    private void view_13() {
        setContentView(R.layout.activity_view_13);
    }

    private void view_14(){
        setContentView(R.layout.activity_view_14);

        TextView tv_pickView = findViewById(R.id.tv_pickView);
        tv_pickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView optionsPickerView = new OptionsPickerBuilder(ViewActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {

                    }
                })
//                        .setSubmitText("ok")////确定按钮文字
//                        .setSubmitColor(Color.RED)//确定按钮文字颜色
//                        .setCancelText("cancel")//取消按钮文字
//                        .setCancelColor(Color.BLACK)//取消按钮文字颜色
//                        .setSubCalSize(20)//确定和取消文字大小
                        .setTitleText("草帽海贼团")//标题
//                        .setTitleSize(22)//标题文字大小
//                        .setTitleColor(Color.YELLOW)////标题文字颜色
//                        .setTitleBgColor(Color.MAGENTA)//标题背景颜色
                        .setContentTextSize(20)//滚轮文字大小
//                        .setTypeface(Typeface.DEFAULT_BOLD)//滚轮的字体
//                        .setBgColor(Color.DKGRAY)//滚轮背景颜色
//                        .setCyclic(true, true, true)//循环与否
//                        .setSelectOptions(3)//设置默认选中项
                        .setDividerColor(Color.BLACK)//分割线颜色
//                        .setDividerType(WheelView.DividerType.FILL)//分割线类型
//                        .setTextXOffset(500, 50, 50)
                        .setLineSpacingMultiplier(2.0f)//设置Item 的间距倍数
                        .build();

                List<String> list = new ArrayList<>();
                list.add("路飞");
                list.add("索隆");
                list.add("山治");
                list.add("娜美");
                list.add("乌索普");
                list.add("乔巴");
                list.add("罗宾");
                list.add("弗兰奇");
                optionsPickerView.setPicker(list);
                optionsPickerView.show();
            }
        });
    }
}
