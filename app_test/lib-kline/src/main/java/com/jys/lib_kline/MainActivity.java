package com.jys.lib_kline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int colorRed = Color.parseColor("#EE3F3C");
    public static int  colorGreen = Color.parseColor("#00A846");

    int startPosition;
    int endPosition;
    int numCOunt = 5;

    int startPDown;
    int lastStartP;
    int Offset = 50;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
        System.out.println("getScaledTouchSlop: " + viewConfiguration.getScaledTouchSlop());



        final float[] valuesAll = new float[]{20, 30, 20, 30, 20, 10, 20, 10, 20, 10};

        endPosition = valuesAll.length - 1;
        startPosition = endPosition - numCOunt +1;

        TextView tv_test = findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VpTestActivity.class));
            }
        });
        {
            float[] values = Arrays.copyOfRange(valuesAll, startPosition, endPosition+1);
            KLineView kLineView = findViewById(R.id.kLine);
            KLineData kLineData = new KLineData();
            kLineData.yValueMax = 50;
            kLineData.yValueMin = 0;
            kLineData.xSpaceCount = values.length - 1;
            kLineData.kLineValues = values;

            kLineView.addKLineData(kLineData);

            kLineView.refreshAllData();
            kLineView.invalidate();
//            kLineView.requestLayout();

            IndexView indexView = findViewById(R.id.indexView);
            indexView.scrollEnable = true;
            indexView.scrollListener = new IndexView.ScrollListener() {
                @Override
                public void scrollOffset(float deltaX, float deltaY) {
                    int count = (int) (deltaX/Offset);
                    startPosition = Math.min(Math.max(0, startPDown-count), valuesAll.length-numCOunt);
                    endPosition = startPosition + numCOunt;

                    if (startPosition == lastStartP)
                        return;

                    float[] arrayTemp = Arrays.copyOfRange(valuesAll, startPosition, endPosition);
                    kLineData.kLineValues = arrayTemp;
                    kLineView.refreshAllData();
                    kLineView.invalidate();

                    lastStartP = startPosition;
                }

                @Override
                public void scrollStart() {
                    startPDown = startPosition;
                    lastStartP = startPosition;
                }

                @Override
                public void scrollEnd() {

                }
            };

//            indexView.setOnTouchListener(new View.OnTouchListener(){
//                float xDown;
//                int lastStartP;
//                int startPDown; //
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()){
//                        case MotionEvent.ACTION_DOWN:
//                            xDown = event.getX();
//                            lastStartP = startPosition;
//                            startPDown= startPosition;
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            Log.i("111222", "delta " + (event.getX() - xDown));
//                            float delta = event.getX() - xDown;
//                            int count = (int) (delta / 50);
//                            Log.i("111222", "count:" + count);
//
//                            startPosition = Math.min(Math.max(0, startPDown-count), valuesAll.length-numCOunt);
//                            endPosition = startPosition + numCOunt;
//                            Log.i("111222", "startP:" + startPosition + "  lastP " + lastStartP);
//                            if (startPosition == lastStartP)
//                                return false;
//                            float[] arrayTemp = Arrays.copyOfRange(valuesAll, startPosition, endPosition);
//                            kLineData.kLineValues = arrayTemp;
//                            kLineView.refreshAllData();
//                            kLineView.invalidate();
//
//                            lastStartP = startPosition;
//                            break;
//                    }
//                    return false;
//                }
//            });
            indexView.indexLineEnable = true;
            indexView.indexSpaceCount = 240;
            indexView.xEndPosition = 240;
            indexView.yHeight = 120;
            float[] yValues = new float[120];
            for (int i=0; i< 120; i++){
                yValues[i] = i;
            }
            indexView.yValues = yValues;

            indexView.yMax = 120;
            indexView.yMin = 0;
            indexView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.i("111222", "indexView.setOnClickListener");
                }
            });

            indexView.leftValueListenr = new IndexView.TextValueListener() {
                @Override
                public String getTextValue(int xPosition) {
//                return String.valueOf(100 * (1-yRate));

                    if (xPosition < yValues.length)
                        return String.valueOf(yValues[xPosition]);
                    return "--";
                }
            };

            indexView.rightValueListenr = new IndexView.TextValueListener() {
                @Override
                public String getTextValue(int xPosition) {
                    return String.valueOf((xPosition));
                }
            };

            indexView.XValueListenr = new IndexView.TextValueListener() {
                @Override
                public String getTextValue(int xPosition) {
                    return String.valueOf(xPosition);
                }
            };
        }

        TextView textView = null;
        {
//            KLineView kLineView = findViewById(R.id.kline);
//            {
//                KLineData kLineData = new KLineData();
//                kLineData.kLineColor = colorRed;
////            kLineData.shadowEnable = true;
////            kLineData.kLineValues = new float[]{100, 160, 210, 120, 330, 440, 170, 100, 90, 50, 333, 222, 111, 180};
//                kLineData.kLineValues = new float[]{55, 75, 175};
////            kLineData.xStart = 1;
////                kLineData.yValueMin = 0f;
////                kLineData.yValueMax = 300f;
//                kLineView.indexSpaceCount = kLineData.kLineValues.length-1;
//                kLineView.addKLineData(kLineData);
//                kLineView.indexLineEnable = true;
//
//                //虚线
//                kLineView.xBgLineEnable = true;
//                kLineView.xBgLineArray = new float[]{0.25f, 0.5f, 0.75f};
//                kLineView.yBgLineEnable = true;
//                kLineView.yBgLineArray = new float[]{0f, 0.25f, 0.5f, 0.75f, 1f};
//            }

            {
                //蜡烛图
//                CandleData candleData = new CandleData();
//                List<CandleData.Item> list = new ArrayList<>();
//                CandleData.Item item1 = new CandleData.Item(50, 60, 80, 30);
//                list.add(item1);
//                CandleData.Item item2 = new CandleData.Item(80, 70, 100, 50);
//                list.add(item2);
//                CandleData.Item item3 = new CandleData.Item(170, 180, 200, 150);
//                candleData.yValueMin = 0f;
//                candleData.yValueMax = 300f;
//                candleData.maxCandleWidth = dp2px(5);
//                list.add(item3);
//                candleData.itemList = list;

//            CandleData candleData = getTestCandleData(kLineData.kLineValues);
//            candleData.xSpaceCount = candleData.itemList.size()-1;
//            candleData.lineWidth = dp2px(2f);
//            candleData.maxCandleWidth = dp2px(5);
//            candleData.candleWidthRate = 0.6f;

//                kLineView.addCandleData(candleData);
            }


//            {
//                PillarData pillarData = new PillarData();
//                List<PillarData.Item> pillarItemList = new ArrayList<>();
//                PillarData.Item item1 = new PillarData.Item();
//                item1.top = 100;
//                item1.color = colorGreen;
//                pillarItemList.add(item1);
//
//                PillarData.Item item2 = new PillarData.Item();
//                item2.top = 120;
//                item2.color = colorRed;
//                pillarItemList.add(item2);
//
//                PillarData.Item item3 = new PillarData.Item();
//                item3.top = 200;
//                pillarItemList.add(item3);
//
//                pillarData.list = pillarItemList;
//                pillarData.pillarWidthMax = dp2px(5);
//                pillarData.xSpaceCount = 5;
//                pillarData.yValueMax = 400f;
////                kLineView.addPillarData(pillarData);
//            }
//
//            kLineView.requestLayout();
        }



//        {
//            KLineView kLineView = findViewById(R.id.kline2);
//            KLineView kLineView3 = findViewById(R.id.kline3);
//
//            KLineData kLineData = new KLineData();
//            kLineData.kLineValues = getTestData(1,241, 300);
//            kLineData.xSpaceCount = 240;
//            kLineData.kLineColor = colorRed;
//            kLineData.kLineWidth = dp2px(1);
//            kLineView.addKLineData(kLineData);
//            kLineView.indexSpaceCount = 240;
//            kLineView.indexLineEnable = true;
//            kLineView.setIndexListenr(new KLineView.IndexListener() {
//                @Override
//                public void drawIndexStart() {
//
//                }
//
//                @Override
//                public void drawIndexEnd() {
//                    kLineView3.xIndexPosition = -1;
//                    kLineView3.requestLayout();
//                }
//
//                @Override
//                public void drawIndexPosition(int position, float rate) {
//                    kLineView3.xIndexPosition = position;
//                    kLineView3.requestLayout();
//                }
//            });
//
//
//            KLineData kLineData3 = new KLineData();
//            kLineData3.kLineValues = getTestData(2,241, 300);
//            kLineData3.xSpaceCount = 240;
//            kLineData3.kLineColor = colorRed;
//            kLineData3.kLineWidth = dp2px(1);
//            kLineView3.addKLineData(kLineData3);
//            kLineView3.requestLayout();
//            kLineView3.indexSpaceCount = 240;
//            kLineView3.indexLineEnable = true;
//            kLineView3.setIndexListenr(new KLineView.IndexListener() {
//                @Override
//                public void drawIndexStart() {
//
//                }
//
//                @Override
//                public void drawIndexEnd() {
//                    kLineView.xIndexPosition = -1;
//                    kLineView.requestLayout();
//                }
//
//                @Override
//                public void drawIndexPosition(int position, float rate) {
//                    kLineView.xIndexPosition = position;
//                    kLineView.requestLayout();
//                }
//            });
//        }


//        {
//            DoubleKLineView klineDetail = findViewById(R.id.klineDetail);
//            KLineData kLineDataLeft = new KLineData();
//            kLineDataLeft.kLineValues = getTestData(1, 241, 200);
//            logArray(kLineDataLeft.kLineValues);
//            kLineDataLeft.shadowEnable = true;
//            kLineDataLeft.xSpaceCount = 20;
//            kLineDataLeft.kLineColor = colorRed;
//            kLineDataLeft.kLineWidth = dp2px(1);
//            klineDetail.setkLineDataLeft(kLineDataLeft);
//
//            KLineData kLineDataRight = new KLineData();
////            kLineDataRight.kLineValues = getTestData(2, 241, 150);
//            kLineDataRight.kLineValues = new float[]{-10, 10, 0, 5, 0};
//            logArray(kLineDataRight.kLineValues);
//            kLineDataRight.xSpaceCount = 20;
//            kLineDataRight.xSpaceCount = kLineDataRight.kLineValues.length-1;
//            kLineDataRight.kLineColor = Color.BLACK;
//            kLineDataRight.kLineWidth = dp2px(1);
//            klineDetail.setkLineDataRight(kLineDataRight);
//
//            klineDetail.setTagName1("主力净流入");
//            klineDetail.setTagName2("板块指数");
//            klineDetail.setNameLeft("板块指数");
//            klineDetail.setNameRight("今日资金净流入(亿元)");
//
//            klineDetail.refresh();
//        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
//        Log.i("111222", "MainActivity : event: " + event.getAction()+ "  result: " +result);
        return result;
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float[] getTestData(int seed, int count, int maxValue){
        Random random = new Random(seed);

        float[] array = new float[count];
        for (int i=0; i<count; i++){
            array[i] = random.nextFloat() * maxValue;
        }

        return array;
    }

    private CandleData getTestCandleData(float[] array){
        CandleData candleData = new CandleData();
        List<CandleData.Item> list = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<array.length; i++){
            float value = array[i];
            if (random.nextFloat() > 0.5f){
                list.add(new CandleData.Item(value-20, value+30, value + 70, value-40));
            }else {
                list.add(new CandleData.Item(value+30, value-20, value + 70, value-40));
            }
        }

        candleData.itemList = list;

        return candleData;
    }

    private void logArray(float[] array){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        for (int i =0; i<array.length; i++){
            stringBuilder.append(array[i]).append(", ");
        }
        stringBuilder.append(" ]");
        Log.i("111222", stringBuilder.toString());
    }
}